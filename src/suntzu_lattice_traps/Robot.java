package suntzu_lattice_traps;

import battlecode.common.*;

import java.util.Random;

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
    boolean potentialFlagMover = true;
    MapLocation myLoc; //current loc of robot
    MapInfo myLocInfo;
    int mapWidth, mapHeight;
    final Random rng;
    String indicatorString = "";
    AttackModule attackModule;
    DefenseModule defenseModule;
    OffenseModule offenseModule;
    Team myTeam;
    Team oppTeam;
    MapLocation centerLoc;

    // array containing enemy flag locations (updated every round using comms)
    MapLocation[] approximateOppFlagLocations;
    MapLocation[] knownDroppedOppFlags;
    MapLocation[] knownCarriedOppFlags;
    int[] knownOppFlagIDs;
    MapLocation[] defaultOppFlagLocs;
    MapLocation[] knownTakenAllyFlags;

    MapLocation homeLocWhenCarryingFlag = null;
    FlagInfo[] sensedNearbyFlags;
    MapInfo[] sensedNearbyMapInfos;
    MapLocation[] sensedNearbyCrumbs;
    RobotInfo[] nearbyFriendlies; // friendly bots within vision radius of bot
    RobotInfo[] nearbyActionFriendlies; // friendly bots within action radius of bot
    RobotInfo[] nearbyActionEnemies; // enemy bots within action radius of bot
    RobotInfo[] nearbyVisionEnemies; // enemy bots within vision radius of bot
    MapLocation[] defaultHomeFlagLocs; // default spots where home flags should be after round 200 (populated after round 200)
    MapLocation[] spawnCenters;
    MapLocation[] allSpawnLocs;

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

        this.comms = new Comms(rc, this);
        this.nav = new Navigation(rc, this.comms, this);
        this.rng = new Random(rc.getID());  // seed the random number generator with the id of the bot
        this.attackModule = new AttackModule(this.rc, this);
        this.defenseModule = new DefenseModule(this.rc, this, this.comms, this.nav);
        this.offenseModule = new OffenseModule(this.rc, this, this.comms, this.nav);
        this.scout = new DamScout(rc, this, this.comms, this.nav);

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
        knownOppFlagIDs = comms.getOppFlagIDArray();

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
            System.out.println("UNKNOWN MODE: " + mode);
            Util.resign();
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
            rc.buyGlobal(GlobalUpgrade.ACTION);
        }

        else if(rc.getRoundNum() > 750 && rc.getRoundNum() < 850 && rc.canBuyGlobal(GlobalUpgrade.ACTION)){
            rc.buyGlobal(GlobalUpgrade.HEALING);
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
//        if(numStationaryDefenders < MIN_NUM_OF_SD){
//            return Mode.STATIONARY_DEFENSE;
//        }

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
        // check to see if you're on the flag and the current number of stationary defenders is less than three
        // if so, override whatever mode was told by determineTypeToSpawn
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
            System.out.println("ROBOT IS UNKNOWN MODE: " + mode);
            Util.resign();
        }
    }


    public void checkToSeeIfIShouldBecomeStationaryDefender() throws GameActionException {
        MapLocation[] spawnLocCenters = Util.getSpawnLocCenters();
        int numberOfStationaryDefenders = comms.getBotCount(Mode.STATIONARY_DEFENSE);
        if(rc.getRoundNum() < Constants.SETUP_ROUNDS && numberOfStationaryDefenders < 3){
            if(Util.checkIfItemInArray(rc.getLocation(), spawnLocCenters)){
                mode = Mode.STATIONARY_DEFENSE;
                comms.incrementBotCount(Mode.STATIONARY_DEFENSE);
                MapLocation[] defaultHomeFlagLocs = comms.getDefaultHomeFlagLocs();
                defenseModule.setup();
                defenseModule.defendingFlagIdx = Util.getItemIndexInArray(rc.getLocation(), defaultHomeFlagLocs);

            }
        }
    }

    public void checkIfInitializationNeeded(){
        if(attackModule.stunTrapInfo == null){
            attackModule.stunTrapInfo = new int[rc.getMapWidth()][rc.getMapHeight()]; // initialized to all zeroes
        }
        else if(attackModule.lastStunnedInfo == null){
            attackModule.lastStunnedInfo = new int[rc.getMapWidth()][rc.getMapHeight()];
        }
        if(defenseModule.trapsMap == null){
            defenseModule.trapsMap = new byte[rc.getMapWidth()][rc.getMapHeight()];
        }
        else if(defenseModule.heuristicMap == null){
            defenseModule.heuristicMap = new int[rc.getMapWidth()][rc.getMapHeight()];
        }
        else if(defenseModule.trapPQ == null){
            defenseModule.trapPQ = new PriorityQueue(defenseModule.NUM_TRAPS_TO_KEEP_TRACK_OF);
        }
    }

    // this is the main run method that is called every turn
    public void run() throws GameActionException {

        if(rc.getRoundNum() > 200){
            rc.resign();
        }

        indicatorString = "";
        checkIfInitializationNeeded();

        idOfFlagImCarrying = -1;
        boolean hasFlagAtBeginningOfTurn = rc.hasFlag();

        Util.addToIndicatorString("Mode:" + mode.toShortString());

        readComms(); // update opp flags and the shared target loc index

        if (!rc.isSpawned()){
            spawn();
        }
        if(rc.isSpawned()){
            tryGlobalUpgrade();

            myLoc = rc.getLocation();
            scanSurroundings();
            updateComms();

            if (rc.getRoundNum() <= Constants.SETUP_ROUNDS) {
                // Scout the dam.
                if(rc.getRoundNum() < 10) {
                    MapLocation[] spawnLocCenters = Util.getSpawnLocCenters();
                    comms.writeDefaultHomeFlagLocs(0, spawnLocCenters[0]);
                    comms.writeOurFlagNewHomeStatus(0, true);

                    comms.writeDefaultHomeFlagLocs(1, spawnLocCenters[1]);
                    comms.writeOurFlagNewHomeStatus(1, true);

                    comms.writeDefaultHomeFlagLocs(2, spawnLocCenters[2]);
                    comms.writeOurFlagNewHomeStatus(2, true);

                    int avgX = 0;
                    int avgY = 0;

                    for(MapLocation loc : spawnLocCenters) {
                        avgX += loc.x;
                        avgY += loc.y;
                    }
                    avgX /= 3;
                    avgY /= 3;
                    comms.writeNewHomeFlagCenter(new MapLocation(avgX, avgY));
                }

                checkToSeeIfIShouldBecomeStationaryDefender();

                if(mode == Mode.STATIONARY_DEFENSE && comms.getOurFlagNewHomeStatus(defenseModule.defendingFlagIdx)) {
//                    System.out.println("RUNNING STATIONARY DEFENSE");
//                    System.out.println("DEFENDING FLAG IDX: " + defenseModule.defendingFlagIdx);
//                    System.out.println("my current location: " + rc.getLocation());
//                    System.out.println("location of flag I'm trying to defend: " + comms.getDefaultHomeFlagLoc(defenseModule.defendingFlagIdx));
                    defenseModule.runStationaryDefense();
                }

                else if(mode == Mode.MOBILE_DEFENSE && comms.getOurFlagNewHomeStatus(defenseModule.defendingFlagIdx)) {
                    defenseModule.runMobileDefense();
                }

                else if(mode != Mode.STATIONARY_DEFENSE){ // If on offense, keep running the scout code.
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
                    comms.removeKnownOppFlagLocFromId(idOfFlagImCarrying);

                    if(offenseModule.sharedOffensiveTarget.equals(myLoc)){
                        offenseModule.sharedOffensiveTarget = rc.getLocation();
                        comms.writeSharedOffensiveTarget(offenseModule.sharedOffensiveTarget);
                    }
                    comms.writeKnownOppFlagLocFromFlagID(rc.getLocation(), true, idOfFlagImCarrying);
                }
            }
            else{
                homeLocWhenCarryingFlag = null;
                attackModule.runSetup();
                attackModule.runStrategy();
                nearbyVisionEnemies = rc.senseNearbyRobots(GameConstants.VISION_RADIUS_SQUARED, oppTeam);
                if(nearbyVisionEnemies.length == 0 && mode == Mode.OFFENSE){
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

//        if(rc.getRoundNum() % 50 == 0){
//            testLog();
//        }
    }


    public void testLog() throws GameActionException {
        Util.logArray("approximateOppFlagLocations: ", approximateOppFlagLocations);
        Util.logArray("knownOppFlagIDs: ", knownOppFlagIDs);
        Util.logArray("knownDroppedOppFlagLocations: ", knownDroppedOppFlags);
        Util.logArray("knownCarriedOppFlagLocations: ", knownCarriedOppFlags);
        Util.logArray("knownDefaultOppFlagLocations: ", defaultOppFlagLocs);
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
        Util.log("Shared offensive target: " + offenseModule.sharedOffensiveTarget);
        Util.log("Shared offensive target type: " + offenseModule.sharedOffensiveTargetType);

        Util.log("--------------------------------");
    }


    public void readComms() throws GameActionException {
        // read opp flag IDs
        knownOppFlagIDs = comms.getOppFlagIDArray();

        // read approximate flag locations
        approximateOppFlagLocations = comms.getAllApproxOppFlags();

        // read carried flag locations
        knownCarriedOppFlags = comms.getCarriedOppFlags();

        // read dropped flag locations
        knownDroppedOppFlags = comms.getDroppedOppFlags();

        // read carried ally flag locations
        knownTakenAllyFlags = comms.getTakenAllyFlags();

        // read shared offensive target
        offenseModule.sharedOffensiveTarget = comms.getSharedOffensiveTarget();

        defenseModule.sharedDefensiveTarget = comms.getSharedDefensiveTarget();

        offenseModule.sharedOffensiveTargetType = null;
        if(Util.checkIfItemInArray(offenseModule.sharedOffensiveTarget, knownCarriedOppFlags)){
            offenseModule.sharedOffensiveTargetType = OffensiveTargetType.CARRIED;
        }
        else if(Util.checkIfItemInArray(offenseModule.sharedOffensiveTarget, knownDroppedOppFlags)){
            offenseModule.sharedOffensiveTargetType = OffensiveTargetType.DROPPED;
        }
        else if(Util.checkIfItemInArray(offenseModule.sharedOffensiveTarget, approximateOppFlagLocations)){
            offenseModule.sharedOffensiveTargetType = OffensiveTargetType.APPROXIMATE;
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
//                    Util.log("Checking if " + knownCarriedOppFlags[i] + " still contains flag");
                    for (FlagInfo flagInfo : sensedNearbyFlags) {
                        if (flagInfo.getLocation().equals(knownCarriedOppFlags[i])) {
                            if (flagInfo.isPickedUp() && flagInfo.getTeam() == oppTeam) {
//                                Util.log("It is! " + flagInfo.getLocation() + ", " + flagInfo.getTeam() + ", " + flagInfo.getID());
                                flagIsStillValid = true;
                            }
                        }
                    }
                    if (!flagIsStillValid) {
//                        Util.log("It's not! Removing " + knownCarriedOppFlags[i] + " from comms");
                        // if we didn't sense the flag at the location in sensedNearbyFlags, it's invalid
                        // remove it from the shared array
                        comms.removeKnownOppFlagLocFromIdx(i);
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
                        comms.removeKnownOppFlagLocFromIdx(i);
                        knownDroppedOppFlags[i] = null;
                    }
                }
            }
        }

        // do the same thing for approx flags.
        for (int i = 0; i < approximateOppFlagLocations.length; i++) {
            if (approximateOppFlagLocations[i] != null) {
                if (rc.getLocation().equals(approximateOppFlagLocations[i])) {
                    boolean flagIsStillValid = false;
                    // check to see if we sensed the flag at the location in sensedNearbyFlags
                    // if we did, and it's being carried, it's valid
                    for (FlagInfo flagInfo : sensedNearbyFlags) {
                        if (flagInfo.getTeam() == oppTeam) {
                            flagIsStillValid = true;
                        }
                    }
                    if (!flagIsStillValid) {
                        System.out.println("SETTING APPROX FLAG LOCATION TO FALSE");
                        approximateOppFlagLocations[i] = null;
                        comms.setApproxOppFlags(approximateOppFlagLocations);
                    }
                }
            }
        }

    }


    public void tryAddingKnownOppFlags() throws GameActionException {
        for (FlagInfo flagInfo : sensedNearbyFlags) {
            if (flagInfo.getTeam() == myTeam) continue;

            if(rc.getLocation().equals(flagInfo.getLocation())){
                Util.log("CARRYING FLAG WITH LOC " + flagInfo.getLocation() + " AND ID " + flagInfo.getID());
                idOfFlagImCarrying = flagInfo.getID();
            }

            comms.writeDefaultOppFlagLocationIfNotSeenBefore(flagInfo.getLocation(), flagInfo.getID());
            knownOppFlagIDs = comms.getOppFlagIDArray();
            if (isOppFlagKnown(flagInfo)) continue;
            if (flagInfo.isPickedUp()) {
                // Update comms.
                comms.writeKnownOppFlagLocFromFlagID(flagInfo.getLocation(), true, flagInfo.getID());
                knownCarriedOppFlags = comms.getCarriedOppFlags();
            } else {
                // Update comms.
                comms.writeKnownOppFlagLocFromFlagID(flagInfo.getLocation(), false, flagInfo.getID());
                knownDroppedOppFlags = comms.getDroppedOppFlags();
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
                            if(Util.checkIfItemInArray(flagInfo.getLocation(), defaultHomeFlagLocs)){
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
                boolean flagTaken = true;

                for(FlagInfo flagInfo: sensedNearbyFlags){
                    if(flagInfo.getTeam() == myTeam && flagInfo.getLocation().equals(defaultHomeFlagLoc)){
                        flagTaken = false;
                    }
                }

                // note: this code is a simplification of the previous two conditionals
                // with this simplication, only one read to comms is needed
                boolean valsDiff = flagTaken != comms.getHomeFlagTakenStatus(i);
                if(valsDiff){
                    comms.writeHomeFlagTakenStatus(i, flagTaken);
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
                comms.writeKnownOppFlagLocFromFlagID(oppFlagLoc, true, flagInfo.getID());
                idOfFlagImCarrying = flagInfo.getID();
            }
        }
    }

    public void scanSurroundings() throws GameActionException {
        // this method scans the surroundings of the bot and updates comms if needed
        sensedNearbyFlags = rc.senseNearbyFlags(GameConstants.VISION_RADIUS_SQUARED);
        sensedNearbyMapInfos = rc.senseNearbyMapInfos();
        sensedNearbyCrumbs = rc.senseNearbyCrumbs(GameConstants.VISION_RADIUS_SQUARED); // senseNearbyCrumbs() is 0 bytecode??? https://releases.battlecode.org/javadoc/battlecode24/2.0.1/index.html

        nearbyFriendlies = rc.senseNearbyRobots(GameConstants.VISION_RADIUS_SQUARED, myTeam);
        nearbyActionFriendlies = rc.senseNearbyRobots(GameConstants.ATTACK_RADIUS_SQUARED, myTeam);
        nearbyVisionEnemies = rc.senseNearbyRobots(GameConstants.VISION_RADIUS_SQUARED, oppTeam);
        nearbyActionEnemies = rc.senseNearbyRobots(GameConstants.ATTACK_RADIUS_SQUARED, oppTeam);

        if(rc.getRoundNum() > 5){
            attackModule.updateStunTrapInfo();
        }

    }

    public int getOppFlagIdx(int flagID){
        if(knownOppFlagIDs[0] == flagID){
            return 0;
        }
        if(knownOppFlagIDs[1] == flagID){
            return 1;
        }
        if(knownOppFlagIDs[2] == flagID){
            return 2;
        }
        return -1;
    }

}