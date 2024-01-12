package edison;


import battlecode.common.*;

import java.util.Random;


enum SymmetryType { HORIZONTAL, VERTICAL, ROTATIONAL, DIAGONAL_RIGHT, DIAGONAL_LEFT};
enum Mode {DEFENSE, OFFENSE};

public class Robot {

    RobotController rc;
    Comms comms;
    Navigation nav;
    MapLocation myLoc; //current loc of robot
    MapInfo myLocInfo;
    final Random rng;
    String indicatorString = "";
    String targetLocType = "";


    MapLocation prevTargetLoc = null; // previous target I travelled to
    int distToSatisfy = 6;


    /** Array containing all the possible movement directions. */
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
    RobotInfo[] sensedNearbyRobots;

    Mode mode;

    MapLocation spawnLoc;


    public Robot(RobotController rc) throws GameActionException{
        this.rc = rc;
        this.nav = new Navigation(rc, this);
        this.comms = new Comms(rc, this);
        this.rng = new Random(rc.getID());  // seed the random number generator with the id of the bot

        if(rng.nextDouble() < 0){
            mode = Mode.DEFENSE;
        }
        else{
            mode = Mode.OFFENSE;
        }

        Util.rc = rc;
        Util.robot = this;
        
        // if the round number is less than 50, set all opponent flags in the shared array to null
        // since we don't know anything about them yet
        knownCarriedOppFlags = comms.getAllKnownOppFlagLocs();
        if(rc.getRoundNum() < 50 && knownCarriedOppFlags[0] != null){
            comms.setKnownOppFlagsToNull();
            comms.setApproxOppFlags(new MapLocation[]{null, null, null});
        }
    }


    public void spawn() throws GameActionException{
        sharedOffensiveTarget = null;
        prevTargetLoc = null;
        MapLocation[] spawnLocs = rc.getAllySpawnLocations();
        // Pick a random spawn location to attempt spawning in.
        MapLocation randomLoc = spawnLocs[rng.nextInt(spawnLocs.length)];
        if (rc.canSpawn(randomLoc)) rc.spawn(randomLoc);
        spawnLoc = randomLoc;
    }


    public void run() throws GameActionException{
        indicatorString = "";
        if(rc.getRoundNum() > 300 && rc.getRoundNum() % 50 == 0) testLog();

        // this is the main run method that is called every turn
        if (!rc.isSpawned()) spawn();

        else{
            myLoc = rc.getLocation();
            runMovement();
            runAttack();
    }
        rc.setIndicatorString(indicatorString);
}


public void testLog(){
    Util.logArray("approximateOppFlagLocations: ", approximateOppFlagLocations);
    Util.logArray("knownDroppedOppFlagLocations: ", knownDroppedOppFlags);
    Util.logArray("knownCarriedOppFlagLocations: ", knownCarriedOppFlags);
    Util.log("--------------------------------");
}


    public void readComms() throws GameActionException{
        // read approximate flag locations
        approximateOppFlagLocations = comms.getAllApproxOppFlags();

        // read carried flag locations
        knownCarriedOppFlags = comms.getCarriedOppFlags();

        // read dropped flag locations
        knownDroppedOppFlags = comms.getDroppedOppFlags();

        // read shared offensive target
        sharedOffensiveTarget = comms.getSharedOffensiveTarget();
    }



    public boolean isOppFlagKnown(FlagInfo flagInfo){
        // returns true if a flagInfo object matches our records from shared array
        // this method is used in
        MapLocation[] arrToCheck;
        if(flagInfo.isPickedUp()){
            arrToCheck = knownCarriedOppFlags;
        }
        else{
            arrToCheck = knownDroppedOppFlags;
        }
        for(MapLocation loc: arrToCheck){
            if(flagInfo.getLocation().equals(loc)){
                return true;
            }
        }
        return false;
    }


    public void listenToOppFlagBroadcast() throws GameActionException{
        // dropped opponent flags broadcast their location every 100 rounds
        // this method listens to the broadcast and adds the sets those broadcast locations as approximate locations
        if(rc.getRoundNum() >= 200 && comms.getApproxOppFlag_LastUpdated() + 100 <= rc.getRoundNum()){
            approximateOppFlagLocations = rc.senseBroadcastFlagLocations();
            comms.setApproxOppFlags(approximateOppFlagLocations);
            Util.log("Approximate Flag Broadcast!");
            Util.logArray("approximateFlagLocs: " , approximateOppFlagLocations);
            Util.log("approximateFlagLos.length: " + approximateOppFlagLocations.length);
        }
    }



    public void tryCleaningKnownOppFlags() throws GameActionException{
        // this method tries to remove known opponent flag locations from the shared array if they are no longer valid
        for(int i=0; i<knownCarriedOppFlags.length; i++){
            if(knownCarriedOppFlags[i] != null){
                if(rc.canSenseLocation(knownCarriedOppFlags[i])){
                    // check to see if we sensed the flag at the location in sensedNearbyFlags
                    // if we did, and it's being carried, it's valid
                    for(FlagInfo flagInfo: sensedNearbyFlags){
                        if(flagInfo.getLocation().equals(knownCarriedOppFlags[i])){
                            if(flagInfo.isPickedUp()){
                                return;
                            }
                        }
                    }
                    // if we didn't sense the flag at the location in sensedNearbyFlags, it's invalid
                    // remove it from the shared array
                    comms.removeKnownOppFlagLoc(knownCarriedOppFlags[i]);
                }
            }
        }

        // do the same thing for knownDroppedFlags
        for(int i=0; i<knownDroppedOppFlags.length; i++){
            if(knownDroppedOppFlags[i] != null){
                if(rc.canSenseLocation(knownDroppedOppFlags[i])){
                    // check to see if we sensed the flag at the location in sensedNearbyFlags
                    // if we did, and it's not being carried, it's valid
                    for(FlagInfo flagInfo: sensedNearbyFlags){
                        if(flagInfo.getLocation().equals(knownDroppedOppFlags[i])){
                            if(!flagInfo.isPickedUp()){
                                return;
                            }
                        }
                    }
                    // if we didn't sense the flag at the location in sensedNearbyFlags, it's invalid
                    // remove it from the shared array
                    comms.removeKnownOppFlagLoc(knownDroppedOppFlags[i]);
                }
            }
        }
    }


    public void tryAddingKnownOppFlags() throws GameActionException{
        for(FlagInfo flagInfo: sensedNearbyFlags){
            if(flagInfo.getTeam() == rc.getTeam()) continue;
            if(isOppFlagKnown(flagInfo)) continue;
            if(flagInfo.isPickedUp()){
                comms.writeKnownOppFlagLoc(flagInfo.getLocation(), true);
            }
            else{
                comms.writeKnownOppFlagLoc(flagInfo.getLocation(), false);
            }
        }
    }


    public MapLocation getNewSharedOffensiveTarget() throws GameActionException{
        // if there is a known dropped flag that is not the current target, go to that
        for(MapLocation loc: knownDroppedOppFlags){
            if(loc != null && !loc.equals(sharedOffensiveTarget)){
                return loc;
            }
        }

        // if there is an approximate location of a flag that is not the current target, go to that
        for(MapLocation loc: approximateOppFlagLocations){
            if(loc != null && !loc.equals(sharedOffensiveTarget)){
                return loc;
            }
        }

        return null;
    }


    public void tryUpdateSharedOffensiveTarget() throws GameActionException{
        // this method updates the sharedOffensiveTarget if the current target is no longer valid
        boolean needToGetNewTarget = false;

        // if we currently don't have a shared offensive target
        if(sharedOffensiveTarget == null){
            needToGetNewTarget = true;
        }

        // check if the current target is a picked Up flag. if so, not valid, get a new one
        else if(Util.checkIfItemInArray(sharedOffensiveTarget, knownCarriedOppFlags)){
            needToGetNewTarget = true;
        }

        // if the current target is not in approximate areas or dropped flags, get a new one
        else if(!Util.checkIfItemInArray(sharedOffensiveTarget, approximateOppFlagLocations) &&
                !Util.checkIfItemInArray(sharedOffensiveTarget, knownDroppedOppFlags)){
            needToGetNewTarget = true;
        }

        // If you are at the current target and there a good number of fellow bots are present, get a new one
        else if(myLoc.distanceSquaredTo(sharedOffensiveTarget) <= distToSatisfy){
            if(Util.countBotsOfTeam(rc.getTeam(), sensedNearbyRobots) >= Constants.BOT_THRESHOLD_TO_MARK_TARGET_AS_COMPLETE){
                needToGetNewTarget = true;
            }
        }
        indicatorString += "NGST: " + needToGetNewTarget + ";";

        if(needToGetNewTarget){
            sharedOffensiveTarget = getNewSharedOffensiveTarget();
            comms.writeSharedOffensiveTarget(sharedOffensiveTarget);
        }
    }


    public void updateComms() throws GameActionException{
        // method to update comms
        // gets run every round
        if(rc.getRoundNum() < 200){
            // currently not using comms for anything during the first 200 rounds
            return;
        }
        
        listenToOppFlagBroadcast(); // if it's been 100 rounds since last update, fetch new approximate flag locations
        tryCleaningKnownOppFlags(); // try removing records of opponent flag locations if we know they're not valid anymore
        tryAddingKnownOppFlags(); // try adding new records of opponent flag locations based on what we sensed
        tryUpdateSharedOffensiveTarget();
    }


    public void tryPickingUpOppFlag() throws GameActionException{
        // this method tries to pick up an opponent flag
        // if it can, it picks it up
        // if it can't, it does nothing
        // loop over all the opponent flags and check to see, if they are not null, can you pick them up
        for(FlagInfo flagInfo: sensedNearbyFlags){
            if(flagInfo.getTeam() == rc.getTeam()) continue;
            MapLocation oppFlagLoc = flagInfo.getLocation();

            if(oppFlagLoc != null && rc.canPickupFlag(oppFlagLoc)){
                rc.pickupFlag(oppFlagLoc);
                comms.writeKnownOppFlagLoc(oppFlagLoc, true);
            }
        }
    }


    public void scanSurroundings() throws GameActionException{
        // this method scans the surroundings of the bot and updates comms if needed
        sensedNearbyFlags = rc.senseNearbyFlags(GameConstants.VISION_RADIUS_SQUARED);
        sensedNearbyRobots = rc.senseNearbyRobots(GameConstants.VISION_RADIUS_SQUARED);

    }



    // TOD: improve initial flag placement
    public void runSetupMovement() throws GameActionException {
        // this method contains movement logic during the setup period
        // it is only called during the first 200 rounds
        nav.moveRandom();
    }


    public void runDefensiveMovement() throws GameActionException{
        // circle the spawning location you came from?
        nav.circle(spawnLoc, 15, 25);
    }


    public void moveToTarget() throws GameActionException{
        if(rc.hasFlag()){
            myLoc = rc.getLocation();
            comms.removeKnownOppFlagLoc(myLoc);
            nav.goTo(Util.getNearestHomeSpawnLoc(myLoc), 0);
            myLoc = rc.getLocation();
            comms.writeKnownOppFlagLoc(myLoc, true);
        }
        else if(sharedOffensiveTarget == null){
            nav.moveRandom();
        }
        else{
            nav.goTo(sharedOffensiveTarget, distToSatisfy);
        }
    }


    public void runOffensiveMovement() throws GameActionException{
        // if you can pick up a flag, pick it up (and update comms)
        tryPickingUpOppFlag();
        if(rc.getRoundNum() % 50 == 0){
            testLog();
        }

        moveToTarget();
    }


    public void runMovement() throws GameActionException {
        // if the round number is less than 200, walk around randomly
        readComms(); //update  opp flags and the shared target loc index
        scanSurroundings();
        updateComms();
        readComms();

        if(rc.getRoundNum() < 200){
            runSetupMovement();
        }
        else{
            if(mode == Mode.DEFENSE){
                runDefensiveMovement();
            }
            else {
                runOffensiveMovement();
            }
        }
        indicatorString += "hasFlag: " + rc.hasFlag() + ";";
        indicatorString += "shareTarg: " + sharedOffensiveTarget + ";";
    }


    public void runAttack() throws GameActionException {
        for(MapLocation loc: rc.getAllLocationsWithinRadiusSquared(myLoc, GameConstants.ATTACK_RADIUS_SQUARED)){
            if(rc.canAttack(loc)){
                rc.attack(loc);
            }
        }
    }
}

