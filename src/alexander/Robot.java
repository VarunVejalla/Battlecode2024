package alexander;

import battlecode.common.*;

import java.util.Random;

enum OffensiveTargetType { CARRIED, DROPPED, APPROXIMATE };

enum SymmetryType {
    HORIZONTAL,
    VERTICAL,
    ROTATIONAL,
    DIAGONAL_RIGHT,
    DIAGONAL_LEFT
}


enum Mode {
    MOBILE_DEFENSE,
    STATIONARY_DEFENSE,
    OFFENSE,
    TRAPPING
}



public class Robot {

    RobotController rc;
    Comms comms;
    Navigation nav;
    DamScout scout;
    FlagMover flagMover;
    boolean potentialFlagMover = true;
    MapLocation myLoc; //current loc of robot
    MapInfo myLocInfo;
    int mapWidth, mapHeight;
    final Random rng;
    String indicatorString = "";
    String targetLocType = "";
    AttackModule attackModule;
    MovementModule movementModule;
    Team myTeam;
    Team oppTeam;
    MapLocation prevTargetLoc = null; // previous target I travelled to
    int distToSatisfy = 6;


    MapLocation crumbTarget = null;
    MapLocation prevCrumbTarget = null;
    int roundsChasingCrumb = 0;

    /**
     * Array containing all the possible movement directions.
     */
    static final Direction[] movementDirections = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };

    /* Array containing all directions */
    Direction[] allDirections = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };

    // array containing enemy flag locations (updated every round using comms)
    MapLocation[] approximateOppFlagLocations;
    MapLocation[] knownDroppedOppFlags;    // flags that
    MapLocation[] knownCarriedOppFlags;

    MapLocation sharedOffensiveTarget;
    OffensiveTargetType sharedOffensiveTargetType;
    MapLocation homeLocWhenCarryingFlag = null;
    FlagInfo[] sensedNearbyFlags;
    RobotInfo[] nearbyFriendlies; // friendly bots within vision radius of bot
    RobotInfo[] nearbyActionFriendlies; // friendly bots within action radius of bot
    RobotInfo[] nearbyActionEnemies; // enemy bots within action radius of bot
    RobotInfo[] nearbyVisionEnemies; // enemy bots within vision radius of bot
    MapLocation[] defaultHomeFlagLocs; // default spots where home flags should be after round 200 (populated after round 200)

    int flagProtectingIdx = -1;

    Mode mode;

    MapLocation spawnLoc;

    public Robot(RobotController rc) throws GameActionException {
        this.rc = rc;
        Util.rc = rc;
        Util.robot = this;
        this.mapWidth = rc.getMapWidth();
        this.mapHeight = rc.getMapHeight();
        this.nav = new Navigation(rc, this);
        this.comms = new Comms(rc, this);
        this.rng = new Random(rc.getID());  // seed the random number generator with the id of the bot
        this.attackModule = new AttackModule(this.rc, this);
        this.movementModule = new MovementModule(this.rc, this, this.comms, this.nav);
        this.scout = new DamScout(rc, this, this.comms, this.nav);
        this.flagMover = new FlagMover(rc, this, this.comms, this.nav);
        myTeam = rc.getTeam();
        oppTeam = rc.getTeam().opponent();

        // if the round number is less than 50, set all opponent flags in the shared array to null
        // since we don't know anything about them yet
        knownCarriedOppFlags = comms.getAllKnownOppFlagLocs();
        if (rc.getRoundNum() < 50 && knownCarriedOppFlags[0] != null) {
            comms.setKnownOppFlagsToNull();
            comms.setApproxOppFlags(new MapLocation[]{null, null, null});
        }

        boolean isTrapping = false;
        for(int flagIndex = 0; flagIndex < 3; flagIndex += 1) {
            if(comms.readTrapper(flagIndex) == 0) {
                isTrapping = true;
                mode = Mode.TRAPPING;
                flagProtectingIdx = flagIndex;
                comms.writeTrapper(flagIndex, 1);
                break;
            }
        }
        if(!isTrapping) {
            if(rng.nextDouble() < 0.0){ // TODO: Fix this once we figure out a good defense strat.
                mode = Mode.MOBILE_DEFENSE;
            }
            else{
                mode = Mode.OFFENSE;
            }
        }

        if(!comms.defaultFlagLocationsWritten()) {
            MapLocation[] spawnCenters = Util.getSpawnLocCenters();
            comms.writeDefaultHomeFlagLocs(0, spawnCenters[0]);
            comms.writeDefaultHomeFlagLocs(1, spawnCenters[1]);
            comms.writeDefaultHomeFlagLocs(2, spawnCenters[2]);
            comms.setAllHomeFlags_NotTaken();
        }
    }

    public void tryGlobalUpgrade() throws GameActionException {
        // TODO: make the upgrades dynamic based on how the game is going?
        // note i'm putting the extra checks on the getRoundNum() to reduce the number of rounds
        // we run the canBuyGlobal() method so we don't waste bytecode
        if(rc.getRoundNum() > 1500 && rc.getRoundNum() < 1600 && rc.canBuyGlobal(GlobalUpgrade.CAPTURING)){
            rc.buyGlobal(GlobalUpgrade.CAPTURING);
        }

        else if(rc.getRoundNum() > 750 && rc.getRoundNum() < 850 && rc.canBuyGlobal(GlobalUpgrade.ACTION)){
            rc.buyGlobal(GlobalUpgrade.ACTION);
        }
    }


    public Mode determineRobotTypeToSpawn() throws GameActionException{
        // this method determines what type a newly spawned robot should assume
        // considering the desiredRatios and currentTroop counts in comms

        // get the counts
        int numTrappers = comms.getBotCount(Mode.TRAPPING);
        int numStationaryDefenders = comms.getBotCount(Mode.STATIONARY_DEFENSE);
        int numMobileDefenders = comms.getBotCount(Mode.MOBILE_DEFENSE);
        int numOffensive = comms.getBotCount(Mode.OFFENSE);
        int totalNumOfTroops = numTrappers + numStationaryDefenders + numMobileDefenders + numOffensive;

        double currTrapperFrac = (double) numTrappers / totalNumOfTroops;
        double currStationaryDefenseFrac = (double) numStationaryDefenders / totalNumOfTroops;
        double currMobileDefendersFrac = (double) numMobileDefenders / totalNumOfTroops;
        double currOffenseFrac = (double) numOffensive / totalNumOfTroops;

        // get the numbers representing the ratios
        int trapperRatio = comms.readRatioVal(Mode.TRAPPING);
        int stationaryDefenderRatio = comms.readRatioVal(Mode.STATIONARY_DEFENSE);
        int mobileDefenderRatio = comms.readRatioVal(Mode.MOBILE_DEFENSE);
        int offensiveRatio = comms.readRatioVal(Mode.OFFENSE);
        int ratioDenom = trapperRatio + stationaryDefenderRatio + mobileDefenderRatio + offensiveRatio;

        double desiredTrapperFrac = (double) trapperRatio / ratioDenom;
        double desiredStationaryDefenderFrac = (double) stationaryDefenderRatio / ratioDenom;
        double desiredMobileDefenderFrac = (double) mobileDefenderRatio / ratioDenom;
        double desiredOffensiveFrac = (double) offensiveRatio / ratioDenom;

        int trapperDiff = (int) Math.ceil((desiredTrapperFrac - currTrapperFrac) * totalNumOfTroops);
        int stationaryDefenderDiff = (int)Math.ceil((desiredStationaryDefenderFrac - currStationaryDefenseFrac) * totalNumOfTroops);
        int mobileDefenderDiff = (int)Math.ceil((desiredMobileDefenderFrac - currMobileDefendersFrac) * totalNumOfTroops);
        int offenseDiff = (int)Math.ceil((desiredOffensiveFrac - currOffenseFrac) * totalNumOfTroops);

        if(offenseDiff >= trapperDiff && offenseDiff >= stationaryDefenderDiff
                && offenseDiff >= mobileDefenderDiff){
            return Mode.OFFENSE;
        }

        else if(mobileDefenderDiff >= offenseDiff && mobileDefenderDiff >= trapperDiff
                && mobileDefenderDiff >= stationaryDefenderDiff){
            return Mode.MOBILE_DEFENSE;
        }

        else if (trapperDiff >= stationaryDefenderDiff && trapperDiff >= mobileDefenderDiff
                && trapperDiff >= offenseDiff) {
            return Mode.TRAPPING;
        }
        else if(stationaryDefenderDiff >= trapperDiff && stationaryDefenderDiff >= mobileDefenderDiff
                && stationaryDefenderDiff >= offenseDiff){
            return Mode.STATIONARY_DEFENSE;
        }
        return Mode.OFFENSE;
    }


    public void spawn() throws GameActionException {
        if(mode == Mode.TRAPPING) {
            // TODO: what we want to do eventually (if we end up moving flags) is find the spawn location that is closest to the flag, but we're not even properly comming friendly flags yet
            MapLocation myFlagSpawn = comms.getDefaultHomeFlagLoc(flagProtectingIdx);
            if(rc.canSpawn(myFlagSpawn)) {
                spawnLoc = myFlagSpawn;
                rc.spawn(spawnLoc);
                return;
            }
            for(int deltaX = -1; deltaX <= 1; deltaX += 1) {
                for(int deltaY = -1; deltaY <= 1; deltaY += 1) {
                    MapLocation translated = myFlagSpawn.translate(deltaX, deltaY);
                    if(rc.canSpawn(translated)) {
                        spawnLoc = translated;
                        rc.spawn(translated);
                        return;
                    }
                }
            }
            return;
        }

        sharedOffensiveTarget = null;
        sharedOffensiveTargetType = null;
        prevTargetLoc = null;
        MapLocation[] spawnLocs = rc.getAllySpawnLocations();
        // Pick a random spawn location to attempt spawning in.
        if(defaultHomeFlagLocs == null){
            MapLocation randomLoc = spawnLocs[rng.nextInt(spawnLocs.length)];
            if (rc.canSpawn(randomLoc)) rc.spawn(randomLoc);
            spawnLoc = randomLoc;
        }
        // Spawn closest to friendly flags.
        // TODO: In the future, we should pick one of the 3 flags, and spawn closest to that flag so that we can circle that flag.
        else{
            spawnClosestToFlags();
        }
    }

    public void spawnClosestToFlags() throws GameActionException {
        spawnLoc = null;
        int bestDist = Integer.MAX_VALUE;
        for(MapLocation potentialSpawnLoc : rc.getAllySpawnLocations()){
            if(!rc.canSpawn(potentialSpawnLoc)){
                continue;
            }
            for(MapLocation flagLoc : defaultHomeFlagLocs){
                int dist = potentialSpawnLoc.distanceSquaredTo(flagLoc);
                if(dist < bestDist){
                    spawnLoc = potentialSpawnLoc;
                    bestDist = dist;
                }
            }
        }
        if(spawnLoc != null){
            rc.spawn(spawnLoc);
        }
    }


    public void run() throws GameActionException {
        indicatorString = "";
//        if (rc.getRoundNum() > 200 && rc.getRoundNum() % 100 == 0) testLog();

        // this is the main run method that is called every turn
        if (!rc.isSpawned()){
            spawn();
        }

        else {
            tryGlobalUpgrade();

            myLoc = rc.getLocation();
            readComms(); // update opp flags and the shared target loc index
            scanSurroundings();
            updateComms();

            if (rc.getRoundNum() <= 200) {
                // Scout the dam.
                if(potentialFlagMover){
                    potentialFlagMover = flagMover.runFlagMover();
                }
                else{
                    scout.runScout();
                }
            }

            else {
                indicatorString +="hasFlag: "+rc.hasFlag()+";";

                if(rc.hasFlag()){
                    attackModule.runSetup();
                    if(attackModule.heuristic.getSafe()){
                        movementModule.runMovement();
                    }
                    else{
                        myLoc = rc.getLocation();

                        // update shared array
                        attackModule.runUnsafeStrategy();
                        comms.removeKnownOppFlagLoc(myLoc);

                        if(sharedOffensiveTarget.equals(myLoc)){
                            sharedOffensiveTarget = rc.getLocation();
                            comms.writeSharedOffensiveTarget(sharedOffensiveTarget);
                        }
                        comms.writeKnownOppFlagLoc(sharedOffensiveTarget, true);
                    }
                }
                else{
                    homeLocWhenCarryingFlag = null;
                    attackModule.runSetup();
                    attackModule.runStrategy();
                    movementModule.runMovement();
                }
            }
        }
        rc.setIndicatorString(indicatorString);
    }


    public void testLog() throws GameActionException {
        Util.logArray("approximateOppFlagLocations: ", approximateOppFlagLocations);
        Util.logArray("knownDroppedOppFlagLocations: ", knownDroppedOppFlags);
        Util.logArray("knownCarriedOppFlagLocations: ", knownCarriedOppFlags);
        Util.logArray("flagBroadcasts: ", rc.senseBroadcastFlagLocations());
        if(defaultHomeFlagLocs != null){
            Util.logArray("defaultHomeFlagLocs: ", defaultHomeFlagLocs);
        }
        Util.logArray("homeFlagsTaken: ",
                new Boolean[] {
                        comms.getHomeFlagTakenStatus(0),
                        comms.getHomeFlagTakenStatus(1),
                        comms.getHomeFlagTakenStatus(2)});
        Util.log("Shared offensive target: " + sharedOffensiveTarget);
        Util.log("Shared offensive target type: " + sharedOffensiveTargetType);

        Util.log("--------------------------------");
    }


    public void readComms() throws GameActionException {
        // read approximate flag locations
        approximateOppFlagLocations = comms.getAllApproxOppFlags();

        // read carried flag locations
        knownCarriedOppFlags = comms.getCarriedOppFlags();

        // read dropped flag locations
        knownDroppedOppFlags = comms.getDroppedOppFlags();

        // read shared offensive target
        sharedOffensiveTarget = comms.getSharedOffensiveTarget();

        sharedOffensiveTargetType = null;
        if(Util.checkIfItemInArray(sharedOffensiveTarget, knownCarriedOppFlags)){
            sharedOffensiveTargetType = OffensiveTargetType.CARRIED;
        }
        else if(Util.checkIfItemInArray(sharedOffensiveTarget, knownDroppedOppFlags)){
            sharedOffensiveTargetType = OffensiveTargetType.DROPPED;
        }
        else if(Util.checkIfItemInArray(sharedOffensiveTarget, approximateOppFlagLocations)){
            sharedOffensiveTargetType = OffensiveTargetType.APPROXIMATE;
        }

        if(rc.getRoundNum() > 200 && defaultHomeFlagLocs == null){
            defaultHomeFlagLocs = comms.getDefaultHomeFlagLocs();
        }
    }


    public boolean isOppFlagKnown(FlagInfo flagInfo) {
        // returns true if a flagInfo object matches our records from shared array
        // this method is used in
        MapLocation[] arrToCheck;
        if (flagInfo.isPickedUp()) {
            arrToCheck = knownCarriedOppFlags;
        } else {
            arrToCheck = knownDroppedOppFlags;
        }
        for (MapLocation loc : arrToCheck) {
            if (flagInfo.getLocation().equals(loc)) {
                return true;
            }
        }
        return false;
    }


    public void listenToOppFlagBroadcast() throws GameActionException {
        // dropped opponent flags broadcast their location every 100 rounds
        // this method listens to the broadcast and adds the sets those broadcast locations as approximate locations
        if (rc.getRoundNum() >= 200 && comms.getApproxOppFlag_LastUpdated() + 100 <= rc.getRoundNum()) {
            approximateOppFlagLocations = rc.senseBroadcastFlagLocations();
            comms.setApproxOppFlags(approximateOppFlagLocations);
            Util.log("Approximate Flag Broadcast!");
            Util.logArray("approximateFlagLocs: ", approximateOppFlagLocations);
            Util.log("approximateFlagLos.length: " + approximateOppFlagLocations.length);
        }
    }


    public void tryCleaningKnownOppFlags() throws GameActionException {
        // this method tries to remove known opponent flag locations from the shared array if they are no longer valid
        for (int i = 0; i < knownCarriedOppFlags.length; i++) {
            if (knownCarriedOppFlags[i] != null) {
                if (rc.canSenseLocation(knownCarriedOppFlags[i])) {
                    boolean flagIsStillValid = false;
                    // check to see if we sensed the flag at the location in sensedNearbyFlags
                    // if we did, and it's being carried, it's valid
                    for (FlagInfo flagInfo : sensedNearbyFlags) {
                        if (flagInfo.getLocation().equals(knownCarriedOppFlags[i])) {
                            if (flagInfo.isPickedUp()) {
                                flagIsStillValid = true;
                            }
                        }
                    }
                    if (!flagIsStillValid) {
                        // if we didn't sense the flag at the location in sensedNearbyFlags, it's invalid
                        // remove it from the shared array
                        comms.removeKnownOppFlagLoc(knownCarriedOppFlags[i]);
                        knownCarriedOppFlags[i] = null;
                    }
                }
            }
        }

        // do the same thing for knownDroppedFlags
        for (int i = 0; i < knownDroppedOppFlags.length; i++) {
            if (knownDroppedOppFlags[i] != null) {
                if (rc.canSenseLocation(knownDroppedOppFlags[i])) {
                    boolean flagIsStillValid = false;
                    // check to see if we sensed the flag at the location in sensedNearbyFlags
                    // if we did, and it's not being carried, it's valid
                    for (FlagInfo flagInfo : sensedNearbyFlags) {
                        if (flagInfo.getLocation().equals(knownDroppedOppFlags[i])) {
                            if (!flagInfo.isPickedUp()) {
                                flagIsStillValid = true;
                            }
                        }
                    }
                    if (!flagIsStillValid) {
                        // if we didn't sense the flag at the location in sensedNearbyFlags, it's invalid
                        // remove it from the shared array
                        comms.removeKnownOppFlagLoc(knownDroppedOppFlags[i]);
                        knownCarriedOppFlags[i] = null;
                    }
                }
            }
        }
    }


    public void tryAddingKnownOppFlags() throws GameActionException {
        for (FlagInfo flagInfo : sensedNearbyFlags) {
            if (flagInfo.getTeam() == myTeam) continue;
            if (isOppFlagKnown(flagInfo)) continue;
            if (flagInfo.isPickedUp()) {
                // Update comms.
                comms.writeKnownOppFlagLoc(flagInfo.getLocation(), true);

                // Update self.
                for(int i = 0; i< Constants.KNOWN_OPP_FLAG_INDICES.length; i++) {
                    if(knownCarriedOppFlags[i] == null){
                        knownCarriedOppFlags[i] = flagInfo.getLocation();
                        break;
                    }
                }
            } else {
                // Update comms.
                comms.writeKnownOppFlagLoc(flagInfo.getLocation(), false);

                // Update self.
                for(int i=0; i<Constants.KNOWN_OPP_FLAG_INDICES.length; i++) {
                    if(knownDroppedOppFlags[i] == null){
                        knownDroppedOppFlags[i] = flagInfo.getLocation();
                        break;
                    }
                }
            }
        }
    }

    public void tryUpdatingHomeFlagTakenInfo() throws GameActionException {
        // this method tries to update the "taken" status of home flags if the current robot can see
        // the default locations of any of the flags

        // can't do anything if we don't know the default locations of the home flags
        if(defaultHomeFlagLocs == null){
            return;
        }

        for(int i=0;i<3; i++){
            MapLocation defaultHomeFlagLoc = defaultHomeFlagLocs[i];
            if(rc.canSenseLocation(defaultHomeFlagLoc)){
                boolean flagAtLocation = false;

                for(FlagInfo flagInfo: sensedNearbyFlags){
                    if(flagInfo.getTeam() == myTeam && flagInfo.getLocation().equals(defaultHomeFlagLoc)){
                        flagAtLocation = true;
                    }
                }


//                if(flagAtLocation && comms.getHomeFlagTakenStatus(i)){
//                    comms.writeHomeFlagTakenStatus(i, false);
//                }
//
//                else if(!flagAtLocation && !comms.getHomeFlagTakenStatus(i)){
//                    comms.writeHomeFlagTakenStatus(i, true);
//                }

                // note: this code is a simplification of the previous two conditionals
                // with this simplication, only one read to comms is needed
                boolean valsEqual = flagAtLocation == comms.getHomeFlagTakenStatus(i);
                if(valsEqual){
                    comms.writeHomeFlagTakenStatus(i, !flagAtLocation);
                }

            }
        }
    }


    public MapLocation getNewSharedOffensiveTarget() throws GameActionException {
        // if there is a known carried flag that is not the current target, go to that
        for (MapLocation loc : knownCarriedOppFlags) {
            if (loc != null) {
                return loc;
            }
        }
        // if there is a known dropped flag that is not the current target, go to that
        for (MapLocation loc : knownDroppedOppFlags) {
            if (loc != null && !loc.equals(sharedOffensiveTarget)) {
                return loc;
            }
        }
        // if there is an approximate location of a flag that is not the current target, go to that
        for (MapLocation loc : approximateOppFlagLocations) {
            if (loc != null && !loc.equals(sharedOffensiveTarget)) {
                return loc;
            }
        }
        return null;
    }


    public void tryUpdateSharedOffensiveTarget() throws GameActionException {
        // this method updates the sharedOffensiveTarget if the current target is no longer valid
        boolean needToGetNewTarget = false;

        // if we currently don't have a shared offensive target
        if (sharedOffensiveTarget == null) {
            needToGetNewTarget = true;
        }

        // if the current target is not in approximate areas or dropped flags, get a new one
        else if (!Util.checkIfItemInArray(sharedOffensiveTarget, approximateOppFlagLocations) &&
                !Util.checkIfItemInArray(sharedOffensiveTarget, knownDroppedOppFlags) &&
                !Util.checkIfItemInArray(sharedOffensiveTarget, knownCarriedOppFlags)) {
            needToGetNewTarget = true;
        }

        // If you are at the current target and there a good number of fellow bots are present, get a new one
        else if (sharedOffensiveTargetType != OffensiveTargetType.CARRIED
                && myLoc.distanceSquaredTo(sharedOffensiveTarget) <= distToSatisfy) {
            if (nearbyFriendlies.length >= Constants.BOT_THRESHOLD_TO_MARK_TARGET_AS_COMPLETE) {
//            if(Util.countBotsOfTeam(rc.getTeam(), sensedNearbyRobots) >= Constants.BOT_THRESHOLD_TO_MARK_TARGET_AS_COMPLETE){
                needToGetNewTarget = true;
            }
        }
        indicatorString += "NGST: " + needToGetNewTarget + ";";

        if (needToGetNewTarget) {
            sharedOffensiveTarget = getNewSharedOffensiveTarget();
            comms.writeSharedOffensiveTarget(sharedOffensiveTarget);

            sharedOffensiveTargetType = null;
            if(Util.checkIfItemInArray(sharedOffensiveTarget, knownCarriedOppFlags)){
                sharedOffensiveTargetType = OffensiveTargetType.CARRIED;
            }
            else if(Util.checkIfItemInArray(sharedOffensiveTarget, knownDroppedOppFlags)){
                sharedOffensiveTargetType = OffensiveTargetType.DROPPED;
            }
            else if(Util.checkIfItemInArray(sharedOffensiveTarget, approximateOppFlagLocations)){
                sharedOffensiveTargetType = OffensiveTargetType.APPROXIMATE;
            }
        }
    }


    public void updateComms() throws GameActionException {
        // method to update comms
        // gets run every round
        if (rc.getRoundNum() < 200) {
            // currently not using comms for anything during the first 200 rounds
            return;
        }

        listenToOppFlagBroadcast(); // if it's been 100 rounds since last update, fetch new approximate flag locations
        tryCleaningKnownOppFlags(); // try removing records of opponent flag locations if we know they're not valid anymore
        tryAddingKnownOppFlags(); // try adding new records of opponent flag locations based on what we sensed
        tryUpdatingHomeFlagTakenInfo();
        tryUpdateSharedOffensiveTarget();
    }


    public void tryPickingUpOppFlag() throws GameActionException {
        // this method tries to pick up an opponent flag
        // if it can, it picks it up
        // if it can't, it does nothing
        // loop over all the opponent flags and check to see, if they are not null, can you pick them up
        for (FlagInfo flagInfo : sensedNearbyFlags) {
            if (flagInfo.getTeam() == myTeam) continue;
            MapLocation oppFlagLoc = flagInfo.getLocation();

            if (oppFlagLoc != null && rc.canPickupFlag(oppFlagLoc)) {
                rc.pickupFlag(oppFlagLoc);
                comms.writeKnownOppFlagLoc(oppFlagLoc, true);
            }
        }
    }


    public void scanSurroundings() throws GameActionException {
        // this method scans the surroundings of the bot and updates comms if needed
        sensedNearbyFlags = rc.senseNearbyFlags(GameConstants.VISION_RADIUS_SQUARED);

        // TODO: maybe it would be more efficient to call rc.senseNearbyRobots once and generate the arrays ourselves?
        nearbyFriendlies = rc.senseNearbyRobots(GameConstants.VISION_RADIUS_SQUARED, myTeam);
        nearbyActionFriendlies = rc.senseNearbyRobots(GameConstants.ATTACK_RADIUS_SQUARED, myTeam);
        nearbyVisionEnemies = rc.senseNearbyRobots(GameConstants.VISION_RADIUS_SQUARED, oppTeam);
        nearbyActionEnemies = rc.senseNearbyRobots(GameConstants.ATTACK_RADIUS_SQUARED, oppTeam);
    }


    // TODO: improve initial flag placement

    //    public boolean tryPickingCrumbs() throws GameActionException{


//    public boolean tryPickingCrumbs() throws GameActionException{
//
////
////
////        if(crumbTarget != null && roundsChasingCrumb < Constants.MAX_ROUNDS_TO_CHASE_CRUMB){
////            nav.goToBug(crumbTarget);
////        }
////
////        return false;
//    }

}