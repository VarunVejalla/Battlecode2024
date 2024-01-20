package sjdev;

import battlecode.common.*;

import java.util.Random;

enum OffensiveTargetType { CARRIED, DROPPED, DEFAULT, APPROXIMATE;

    public String shortString(){
        switch(this){
            case CARRIED:
                return "C";
            case DROPPED:
                return "D";
            case DEFAULT:
                return "DE";
            case APPROXIMATE:
                return "A";
            default:
                return "NULL";
        }

    }
};

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
    TRAPPING;

    public String toShortString(){
        switch(this){
            case MOBILE_DEFENSE:
                return "MD";
            case STATIONARY_DEFENSE:
                return "SD";
            case OFFENSE:
                return "OF";
            case TRAPPING:
                return "TP";
            default:
                return "NULL";
        }
    }
}

public class Robot {

    final int MIN_NUM_OF_SD = 3;

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
    DefenseModule defenseModule;
    OffenseModule offenseModule;
    Team myTeam;
    Team oppTeam;
    MapLocation prevTargetLoc = null; // previous target I travelled to
    int distToSatisfy = 6;
    MapLocation centerLoc;

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
    MapLocation[] knownDroppedOppFlags;
    MapLocation[] knownCarriedOppFlags;
    MapLocation[] knownTakenAllyFlags;

    MapLocation sharedOffensiveTarget;
    OffensiveTargetType sharedOffensiveTargetType;
    MapLocation homeLocWhenCarryingFlag = null;
    FlagInfo[] sensedNearbyFlags;
    MapInfo[] sensedNearbyMapInfos;
    RobotInfo[] nearbyFriendlies; // friendly bots within vision radius of bot
    RobotInfo[] nearbyActionFriendlies; // friendly bots within action radius of bot
    RobotInfo[] nearbyActionEnemies; // enemy bots within action radius of bot
    RobotInfo[] nearbyVisionEnemies; // enemy bots within vision radius of bot
    MapLocation[] defaultHomeFlagLocs; // default spots where home flags should be after round 200 (populated after round 200)
    MapLocation[] spawnCenters;
    MapLocation[] allSpawnLocs;
    MapLocation[] defaultOppFlagLocs;

    int flagProtectingIdx = -1;
    int idOfFlagImCarrying = -1;

    Mode mode;

    MapLocation spawnLoc;

    public Robot(RobotController rc) throws GameActionException {
        this.rc = rc;
        Util.rc = rc;
        Util.robot = this;
        myTeam = rc.getTeam();
        oppTeam = rc.getTeam().opponent();
        this.mapWidth = rc.getMapWidth();
        this.mapHeight = rc.getMapHeight();
        allSpawnLocs = rc.getAllySpawnLocations();
        spawnCenters = Util.getSpawnLocCenters();
        centerLoc = new MapLocation(rc.getMapWidth() / 2, rc.getMapHeight() / 2);
//        Util.logBytecode("After computing all spawn centers");

        this.nav = new Navigation(rc, this);
        this.comms = new Comms(rc, this);
        this.rng = new Random(rc.getID());  // seed the random number generator with the id of the bot
        this.attackModule = new AttackModule(this.rc, this);
        this.movementModule = new MovementModule(this.rc, this, this.comms, this.nav);
        this.defenseModule = new DefenseModule(this.rc, this, this.comms, this.nav);
        this.offenseModule = new OffenseModule(this.rc, this, this.comms, this.nav);
        this.scout = new DamScout(rc, this, this.comms, this.nav);
        this.flagMover = new FlagMover(rc, this, this.comms, this.nav);

//        Util.logBytecode("After creating all the modules");

        // if the round number is less than 50, set all opponent flags in the shared array to null
        // since we don't know anything about them yet
        knownCarriedOppFlags = comms.getAllKnownOppFlagLocs();
        if (rc.getRoundNum() < 50 && knownCarriedOppFlags[0] != null) {
            comms.setKnownOppFlagsToNull();
            comms.setApproxOppFlags(new MapLocation[]{null, null, null});
        }

        defaultOppFlagLocs = comms.getDefaultOppFlagLocations();
        if(rc.getRoundNum() < 200 && defaultOppFlagLocs != null){
            comms.setAllDefaultOppFlagLocsToNull();
        }

        if(!comms.defaultFlagLocationsWritten()) {
            comms.writeDefaultHomeFlagLocs(0, spawnCenters[0]);
            comms.writeDefaultHomeFlagLocs(1, spawnCenters[1]);
            comms.writeDefaultHomeFlagLocs(2, spawnCenters[2]);
            comms.setAllHomeFlags_NotTaken();
        }

        comms.writeRatioVal(Mode.OFFENSE, 13);
        comms.writeRatioVal(Mode.MOBILE_DEFENSE, 2);
        comms.writeRatioVal(Mode.STATIONARY_DEFENSE, 0);

        mode = determineRobotTypeToSpawn();
        comms.incrementBotCount(mode);
        if(mode == Mode.STATIONARY_DEFENSE || mode == Mode.MOBILE_DEFENSE){
            defenseModule.setup();
        }
        else if(mode == Mode.OFFENSE){
            offenseModule.setup();
        }
        else{
            Util.log("UNKNOWN MODE: " + mode);
            rc.resign();
        }

        if(rc.getRoundNum() == 1){
            for(int i = 0; i < 3; i++){
                if(comms.getTakenAllyFlag(i) != null){
                    comms.writeTakenAllyFlagLoc(null, i);
                }
            }
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

        // Always have at least 3 stationary defenders.
        if(numStationaryDefenders < MIN_NUM_OF_SD){
            return Mode.STATIONARY_DEFENSE;
        }

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

        int trapperDiff = (int)Math.ceil((desiredTrapperFrac - currTrapperFrac) * totalNumOfTroops);
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
        if(mode == Mode.STATIONARY_DEFENSE){
            defenseModule.spawnStationary();
        }
        else if(mode == Mode.MOBILE_DEFENSE){
            defenseModule.spawnMobile();
        }
        else if(mode == Mode.OFFENSE){
            offenseModule.spawn();
        }
        else{
            Util.log("ROBOT IS UNKNOWN MODE: " + mode);
            rc.resign();
        }
    }

    public void run() throws GameActionException {
        // this is the main run method that is called every turn


        idOfFlagImCarrying = -1;
        boolean hasFlagAtBeginningOfTurn = rc.hasFlag();

        indicatorString = "";
        Util.addToIndicatorString("Mode:" + mode.toShortString());

        readComms(); // update opp flags and the shared target loc index
//        if(rc.getRoundNum() > Constants.SETUP_ROUNDS && (mode == Mode.STATIONARY_DEFENSE || mode == Mode.MOBILE_DEFENSE)){
//            testLog();
//        }

        if (rc.getRoundNum() % 50 == 0) {
            testLog();
        }

        if (!rc.isSpawned()){
            spawn();
        }
        else {
            tryGlobalUpgrade();

            myLoc = rc.getLocation();
            scanSurroundings();
            updateComms();

            if (rc.getRoundNum() <= Constants.SETUP_ROUNDS) {
                // Scout the dam.
                if(potentialFlagMover){
                    potentialFlagMover = flagMover.runFlagMover();
                }
                else if(mode == Mode.STATIONARY_DEFENSE && comms.getOurFlagNewHomeStatus(defenseModule.defendingFlagIdx)) {
                    defenseModule.runStationaryDefense();
                }
                else if(mode == Mode.MOBILE_DEFENSE && comms.getOurFlagNewHomeStatus(defenseModule.defendingFlagIdx)) {
                    defenseModule.runMobileDefense();
                }
                else{
                    scout.runScout();
                }
            }
            else if(rc.hasFlag()){
                attackModule.runSetup();
                if(attackModule.heuristic.getSafe()){
                    offenseModule.runMovement();
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
                if(mode == Mode.OFFENSE){
                    offenseModule.runMovement();
                }
                else if(mode == Mode.STATIONARY_DEFENSE){
                    defenseModule.runStationaryDefense();
                }
                else if(mode == Mode.MOBILE_DEFENSE){
                    defenseModule.runMobileDefense();
                }
            }
        }
        rc.setIndicatorString(indicatorString);

        if(rc.getRoundNum() > 200
                && hasFlagAtBeginningOfTurn
                && !rc.hasFlag()
                && Util.locIsASpawnLoc(rc.getLocation())){
            comms.setOppFlagToCaptured(idOfFlagImCarrying);
        }
    }


    public void testLog() throws GameActionException {
        Util.logArray("defaultOppFlagLocs: ", defaultOppFlagLocs);
//        Util.logArray("approximateOppFlagLocations: ", approximateOppFlagLocations);
//        Util.logArray("knownDroppedOppFlagLocations: ", knownDroppedOppFlags);
//        Util.logArray("knownCarriedOppFlagLocations: ", knownCarriedOppFlags);
//        Util.logArray("knownTakenAllyFlagLocations: ", knownTakenAllyFlags);
//        Util.logArray("flagBroadcasts: ", rc.senseBroadcastFlagLocations());
//        if(defaultHomeFlagLocs != null){
//            Util.logArray("defaultHomeFlagLocs: ", defaultHomeFlagLocs);
//        }
//        Util.logArray("homeFlagsTaken: ",
//                new Boolean[] {
//                        comms.getHomeFlagTakenStatus(0),
//                        comms.getHomeFlagTakenStatus(1),
//                        comms.getHomeFlagTakenStatus(2)});
//        Util.log("Shared offensive target: " + sharedOffensiveTarget);
//        Util.log("Shared offensive target type: " + sharedOffensiveTargetType);


        Util.log("--------------------------------");
    }


    public void readComms() throws GameActionException {
        // read approximate flag locations
        approximateOppFlagLocations = comms.getAllApproxOppFlags();

        // read carried flag locations
        knownCarriedOppFlags = comms.getCarriedOppFlags();

        // read dropped flag locations
        knownDroppedOppFlags = comms.getDroppedOppFlags();

        // read carried ally flag locations
        knownTakenAllyFlags = comms.getTakenAllyFlags();

        // read shared offensive target
        sharedOffensiveTarget = comms.getSharedOffensiveTarget();

        defenseModule.sharedDefensiveTarget = comms.getSharedDefensiveTarget();

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

        defaultHomeFlagLocs = comms.getDefaultHomeFlagLocs();

        defaultOppFlagLocs = comms.getDefaultOppFlagLocations();
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

    public boolean isTakenAllyFlagKnown(FlagInfo flagInfo) {
        // returns true if a flagInfo object matches our records from shared array
        // this method is used in
        for (MapLocation loc : knownTakenAllyFlags) {
            if (loc != null && flagInfo.getLocation().distanceSquaredTo(loc) <= 2) {
                return true;
            }
        }
        return false;
    }


    public void listenToOppFlagBroadcast() throws GameActionException {
        // dropped opponent flags broadcast their location every 100 rounds
        // this method listens to the broadcast and adds the sets those broadcast locations as approximate locations
        if (rc.getRoundNum() >= Constants.SETUP_ROUNDS && comms.getApproxOppFlag_LastUpdated() + 100 <= rc.getRoundNum()) {
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

            if(rc.getLocation().equals(flagInfo.getLocation())){
                idOfFlagImCarrying = flagInfo.getID();
            }

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
                for(int i = 0; i< Constants.KNOWN_OPP_FLAG_INDICES.length; i++) {
                    if(knownDroppedOppFlags[i] == null){
                        knownDroppedOppFlags[i] = flagInfo.getLocation();
                        break;
                    }
                }
                comms.writeDefaultOppFlagLocationIfNotSeenBefore(flagInfo.getLocation(), flagInfo.getID());
            }
        }
    }

    public void tryCleaningTakenAllyFlags() throws GameActionException {
        // this method tries to remove known opponent flag locations from the shared array if they are no longer valid
        for (int i = 0; i < knownTakenAllyFlags.length; i++) {
            if (knownTakenAllyFlags[i] != null) {
                if (rc.canSenseLocation(knownTakenAllyFlags[i])) {
                    boolean flagIsStillValid = false;
                    // check if we can sense all around the flag (for safety).
                    for(int dx = -1; dx <= 1; dx++){
                        for(int dy = -1; dy <= 1; dy++){
                            MapLocation senseLoc = new MapLocation(knownTakenAllyFlags[i].x + dx, knownTakenAllyFlags[i].y + dy);
                            if(!rc.canSenseLocation(senseLoc) && rc.onTheMap(senseLoc)){
                                flagIsStillValid = true;
                                break;
                            }
                        }
                    }

                    // check to see if we sensed the flag at the location in sensedNearbyFlags
                    if(!flagIsStillValid){
                        for (FlagInfo flagInfo : sensedNearbyFlags) {
                            if(flagInfo.getTeam() == oppTeam){
                                continue;
                            }
                            // Only clean out if it's moved by more than 2 (distance squared) squares.
                            if(flagInfo.getLocation().distanceSquaredTo(knownTakenAllyFlags[i]) <= 2){
                                flagIsStillValid = true;
                                break;
                            }
                        }
                    }
                    if (!flagIsStillValid) {
                        // if we didn't sense the flag at the location in sensedNearbyFlags, it's invalid
                        // remove it from the shared array
                        Util.log("REMOVING TAKEN ALLY FLAG: " + knownTakenAllyFlags[i]);
                        comms.removeTakenAllyFlag(knownTakenAllyFlags[i]);
                        knownTakenAllyFlags[i] = null;
                    }
                }
            }
        }
    }


    public void tryAddingTakenAllyFlags() throws GameActionException {
        for (FlagInfo flagInfo : sensedNearbyFlags) {
            if (flagInfo.getTeam() == oppTeam) continue;
            if(Util.checkIfItemInArray(flagInfo.getLocation(), defaultHomeFlagLocs)) continue;
            if (isTakenAllyFlagKnown(flagInfo)) continue;
            // Update comms.
            comms.writeTakenAllyFlagLoc(flagInfo.getLocation());

            // Update self.
            for(int i = 0; i< knownTakenAllyFlags.length; i++) {
                if(knownTakenAllyFlags[i] == null){
                    knownTakenAllyFlags[i] = flagInfo.getLocation();
                    break;
                }
            }
            Util.log("Found new taken ally flag");
            Util.logArray("KTA is now: ", knownTakenAllyFlags);
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


    public void updateComms() throws GameActionException {
        // method to update comms
        // gets run every round
        if (rc.getRoundNum() < Constants.SETUP_ROUNDS) {
            // currently not using comms for anything during the first 200 rounds
            return;
        }

        listenToOppFlagBroadcast(); // if it's been 100 rounds since last update, fetch new approximate flag locations
        tryCleaningKnownOppFlags(); // try removing records of opponent flag locations if we know they're not valid anymore
        tryAddingKnownOppFlags(); // try adding new records of opponent flag locations based on what we sensed
        tryCleaningTakenAllyFlags();
        tryAddingTakenAllyFlags();
        tryUpdatingHomeFlagTakenInfo();
        offenseModule.tryUpdateSharedOffensiveTarget();
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
        sensedNearbyMapInfos = rc.senseNearbyMapInfos();

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