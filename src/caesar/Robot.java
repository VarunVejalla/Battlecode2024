package caesar;


import battlecode.common.*;

import java.util.Random;


enum SymmetryType { HORIZONTAL, VERTICAL, ROTATIONAL, DIAGONAL_RIGHT, DIAGONAL_LEFT};
enum Mode {DEFENSE, OFFENSE, TRAPPING};

public class Robot {

    RobotController rc;
    Comms comms;
    Navigation nav;
    MapLocation myLoc; //current loc of robot
    MapInfo myLocInfo;
    final Random rng;
    String indicatorString = "";
    String targetLocType = "";
    AttackModule attackModule;
    Team myTeam;
    Team oppTeam;
    MapLocation prevTargetLoc = null; // previous target I travelled to
    int distToSatisfy = 6;


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
    FlagInfo[] sensedNearbyFlags;
    RobotInfo[] nearbyFriendlies; // friendly bots within vision radius of bot
    RobotInfo[] nearbyActionEnemies; // enemy bots within action radius of bot
    RobotInfo[] nearbyVisionEnemies; // enemy bots within vision radius of bot

    int flagProtectingIdx = -1;


    Mode mode;

    MapLocation spawnLoc;


    public Robot(RobotController rc) throws GameActionException {
        this.rc = rc;
        this.nav = new Navigation(rc, this);
        this.comms = new Comms(rc, this);
        this.rng = new Random(rc.getID());  // seed the random number generator with the id of the bot
        this.attackModule = new AttackModule(this.rc, this);
        myTeam = rc.getTeam();
        oppTeam = rc.getTeam().opponent();

        Util.rc = rc;
        Util.robot = this;

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
            if(rng.nextDouble() < 0){
                mode = Mode.DEFENSE;
            }
            else{
                mode = Mode.OFFENSE;
            }
        }

        if(!comms.defaultFlagLocationsWritten()) {
            comms.writeDefaultFlagLocs();
        }
    }


    public void spawn() throws GameActionException {
        if(mode == Mode.TRAPPING) {

            // TODO: what we want to do eventually (if we end up moving flags) is find the spawn location that is closest to the flag, but we're not even properly comming friendly flags yet
            MapLocation myFlagSpawn = comms.getDefaultFlagLoc(flagProtectingIdx);
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
        prevTargetLoc = null;
        MapLocation[] spawnLocs = rc.getAllySpawnLocations();
        // Pick a random spawn location to attempt spawning in.
        MapLocation randomLoc = spawnLocs[rng.nextInt(spawnLocs.length)];
        if (rc.canSpawn(randomLoc)) rc.spawn(randomLoc);
        spawnLoc = randomLoc;
    }


    public void run() throws GameActionException {
        indicatorString = "";
        if (rc.getRoundNum() > 300 && rc.getRoundNum() % 50 == 0) testLog();

        // this is the main run method that is called every turn
        if (!rc.isSpawned()) spawn();

        else {
            myLoc = rc.getLocation();
            readComms(); //update  opp flags and the shared target loc index
            scanSurroundings();
            updateComms();
            readComms();

            if (rc.getRoundNum() > 200) {
                attackModule.run();
            }

            runMovement();

        }
        rc.setIndicatorString(indicatorString);
    }


    public void testLog() throws GameActionException {
        Util.logArray("approximateOppFlagLocations: ", approximateOppFlagLocations);
        Util.logArray("knownDroppedOppFlagLocations: ", knownDroppedOppFlags);
        Util.logArray("knownCarriedOppFlagLocations: ", knownCarriedOppFlags);
        Util.logArray("flagBroadcasts: ", rc.senseBroadcastFlagLocations());
        Util.log("ourFlags: [" +
                comms.getDefaultFlagLoc(0) + ", "
                + comms.getDefaultFlagLoc(1) + ", "
                + comms.getDefaultFlagLoc(2) + "];");
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
                comms.writeKnownOppFlagLoc(flagInfo.getLocation(), true);
            } else {
                comms.writeKnownOppFlagLoc(flagInfo.getLocation(), false);
            }
        }
    }


    public MapLocation getNewSharedOffensiveTarget() throws GameActionException {
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

        // check if the current target is a picked Up flag. if so, not valid, get a new one
        else if (Util.checkIfItemInArray(sharedOffensiveTarget, knownCarriedOppFlags)) {
            needToGetNewTarget = true;
        }

        // if the current target is not in approximate areas or dropped flags, get a new one
        else if (!Util.checkIfItemInArray(sharedOffensiveTarget, approximateOppFlagLocations) &&
                !Util.checkIfItemInArray(sharedOffensiveTarget, knownDroppedOppFlags)) {
            needToGetNewTarget = true;
        }

        // If you are at the current target and there a good number of fellow bots are present, get a new one
        else if (myLoc.distanceSquaredTo(sharedOffensiveTarget) <= distToSatisfy) {
            if (nearbyFriendlies.length >= Constants.BOT_THRESHOLD_TO_MARK_TARGET_AS_COMPLETE) {
//            if(Util.countBotsOfTeam(rc.getTeam(), sensedNearbyRobots) >= Constants.BOT_THRESHOLD_TO_MARK_TARGET_AS_COMPLETE){
                needToGetNewTarget = true;
            }
        }
        indicatorString += "NGST: " + needToGetNewTarget + ";";

        if (needToGetNewTarget) {
            sharedOffensiveTarget = getNewSharedOffensiveTarget();
            comms.writeSharedOffensiveTarget(sharedOffensiveTarget);
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
        nearbyVisionEnemies = rc.senseNearbyRobots(GameConstants.VISION_RADIUS_SQUARED, oppTeam);
        nearbyActionEnemies = rc.senseNearbyRobots(GameConstants.ATTACK_RADIUS_SQUARED, oppTeam);
    }


    // TOD: improve initial flag placement
    public void runSetupMovement() throws GameActionException {
        // this method contains movement logic during the setup period
        // it is only called during the first 200 rounds
        nav.moveRandom();
    }


    public void runDefensiveMovement() throws GameActionException {
        // circle the spawning location you came from?
        nav.circle(spawnLoc, 15, 25);
    }


    public void moveToTarget() throws GameActionException {
        if (rc.hasFlag()) {
            myLoc = rc.getLocation();
            comms.removeKnownOppFlagLoc(myLoc);
            nav.mode = NavigationMode.FUZZYNAV;
            nav.goTo(Util.getNearestHomeSpawnLoc(myLoc), 0);
            myLoc = rc.getLocation();
            comms.writeKnownOppFlagLoc(myLoc, true);
        } else if (sharedOffensiveTarget == null) {
            nav.moveRandom();
        } else {
            nav.mode = NavigationMode.BUGNAV;
            nav.goTo(sharedOffensiveTarget, distToSatisfy);
        }
    }


    public void runOffensiveMovement() throws GameActionException {
        // if you can pick up a flag, pick it up (and update comms)
        tryPickingUpOppFlag();
        if (rc.getRoundNum() % 50 == 0) {
            testLog();
        }

        if (rc.isMovementReady()) {
            moveToTarget();
        }
    }


    public void runTrapperMovement() throws GameActionException{
        // TODO: place bombs around the flag you're defending
        //  TODO: go out and level up your specialization in the beginning of the game
        return;
    }


    public void runMovement() throws GameActionException {
        // if the round number is less than 200, walk around randomly

        if(mode == Mode.TRAPPING){
            runTrapperMovement();
            return;
        }

        if (rc.getRoundNum() < 200) {
            runSetupMovement();
        }

        if (mode == Mode.DEFENSE) {
            runDefensiveMovement();
        } else {
            runOffensiveMovement();
        }

        indicatorString +="hasFlag: "+rc.hasFlag()+";";
        indicatorString +="shareTarg: "+sharedOffensiveTarget +";";
    }
}




