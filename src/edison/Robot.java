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
    MapLocation targetLoc; // where we want to be going
    final Random rng;
    String indicatorString = "";


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


    // list of target locations (same for all the bots) that we'll cycle through
    MapLocation[] allTargetLocs = new MapLocation[15];

    // index representing which targetLoc we should go to (updated every round)
    int targetLocIdx = 0;

    // array containing enemy flag locations (updated every round using comms)
    MapLocation[] opponentFlagLocs = new MapLocation[3];

    FlagInfo[] nearbyFlags;

    Mode mode;

    MapLocation baseLoc;


    public Robot(RobotController rc) throws GameActionException{
        this.rc = rc;
        this.nav = new Navigation(rc, this);
        this.comms = new Comms(rc, this);
        this.rng = new Random(rc.getID());  // seed the random number generator with the id of the bot

        if(rng.nextDouble() < 0.1){
            mode = Mode.DEFENSE;
        }
        else{
            mode = Mode.OFFENSE;
        }

        Util.rc = rc;
        Util.robot = this;
        
        // if the round number is less than 50, set the flags in the shared array to null
        opponentFlagLocs = comms.getAllKnownOppFlagLocs();
        if(rc.getRoundNum() < 50 && opponentFlagLocs[0] != null){
            comms.setKnownOppFlagsToNull();
            comms.setApproxOppFlags(new MapLocation[]{null, null, null});
        }



        baseLoc = rc.getAllySpawnLocations()[0];
    }


    public void spawn() throws GameActionException{

        if(rc.getRoundNum() < 200){
        MapLocation[] spawnLocs = rc.getAllySpawnLocations();
        // Pick a random spawn location to attempt spawning in.
        MapLocation randomLoc = spawnLocs[rng.nextInt(spawnLocs.length)];
        if (rc.canSpawn(randomLoc)) rc.spawn(randomLoc);
        }

        else{
            for(int i = 0; i < 3; i++){
                MapLocation spawnLoc = rc.getAllySpawnLocations()[i];
                    if(rc.canSpawn(spawnLoc)){
                        rc.spawn(spawnLoc);
                        break;
                    }
            }
        }
    }



    public MapLocation getTargetLoc() throws GameActionException{
        // read shared array
        // go to that targetLoc
        return comms.getApproxOppFlag(0);
//        return allTargetLocs[comms.readSharedTargetLocIdx()];
    }


    public void run() throws GameActionException{
        indicatorString = "";
        // this is the main run method that is called every turn
        if (!rc.isSpawned()) spawn();

        else{
            myLoc = rc.getLocation();

            runMovement();
            runAttack();
    }
        rc.setIndicatorString(indicatorString);
}



    public void readComms() throws GameActionException{
        opponentFlagLocs = comms.getAllKnownOppFlagLocs(); // read the opponent flag locs from comms
    }



    public boolean isOppFlagKnown(MapLocation oppFlagLoc){
        // this method checks to see if an opponent flag is known
        // if it is, it returns true
        // if it isn't, it returns false
        for(MapLocation flagLoc: opponentFlagLocs){
            if(flagLoc == oppFlagLoc){
                return true;
            }
        }
        return false;
    }

    
    public void verifyOppFlagLocs() throws GameActionException{
        // this method checks to see if the opponent flag locs in our vision radius of the bot are still valid
        // if they aren't, it sets them to null
        for(int i = 0; i < 3; i++){
            MapLocation flagLoc = opponentFlagLocs[i];
            if(flagLoc != null && rc.canSenseLocation(flagLoc)){

                // loop through nearbyFlags and check to see if the flag is still there
                boolean flagIsStillThere = false;
                for(FlagInfo flagInfo: nearbyFlags){
                    if(flagInfo.getLocation() == flagLoc){
                        flagIsStillThere = true;
                        break;
                    }
                }
                // if the flag is not still there, set it to null
                if(!flagIsStillThere){
                    comms.removeKnownOppFlagLoc(flagLoc);
                }
            }
        }
    }


    public void checkForNewOppFlags() throws GameActionException{
        // this method checks to see if there are any new opponent flags in the vicinity
        // if there are, it comm's them and adds them to the list of opponent flags
        // if there aren't, it does nothing

        // loop through all the flags in the vicinity
        for(FlagInfo flagInfo: nearbyFlags){
            MapLocation flagLoc = flagInfo.getLocation();
            if(flagInfo.getTeam() == rc.getTeam().opponent()  // if it's an opponent flag
                    && !isOppFlagKnown(flagLoc)) // was not previously known// our teammate is not on there
            {
                // check to see if the flag is already being carried by a bot on our team
                // if it is, don't comm it (we only want to comm flags that we don't know about already)
                if(rc.canSenseRobotAtLocation(flagLoc)){
                    RobotInfo botAtFlagLoc = rc.senseRobotAtLocation(flagLoc);
                    if(botAtFlagLoc.getTeam().equals(rc.getTeam())){
                        continue;
                    }
                }

                // if the flag is an opponent flag and it isn't known, comm it and add it to the list of opponent flags
                comms.writeKnownOppFlagLoc(flagLoc, false);
            }
        }
    }



    public void listenToOppFlagBroadcast() throws GameActionException{
        // dropped opponent flags broadcast their location every 100 rounds
        // this method listens to the broadcast and adds the sets those broadcast locations as approximate locations
        if(rc.getRoundNum() >= 200 
            && comms.getApproxOppFlag_LastUpdated() + 100 < rc.getRoundNum()){

            MapLocation[] approximateOppFlagLocs = rc.senseBroadcastFlagLocations();
            comms.setApproxOppFlags(approximateOppFlagLocs);
        }
    }


    public void updateComms() throws GameActionException{
        // method to update comms
        // gets run every round
        if(rc.getRoundNum() < 200){
            // currently not using comms for anything during the first 200 rounds
            return;
        }
        listenToOppFlagBroadcast();
        verifyOppFlagLocs();
        checkForNewOppFlags();
        opponentFlagLocs = comms.getAllKnownOppFlagLocs();
    }


    public void tryPickingUpOppFlag() throws GameActionException{
        // this method tries to pick up an opponent flag
        // if it can, it picks it up
        // if it can't, it does nothing

        // loop over all the opponent flags and check to see, if they are not null, can you pick them up
        for(MapLocation oppFlagLoc: opponentFlagLocs){
            if(oppFlagLoc != null && rc.canPickupFlag(oppFlagLoc)){
                rc.pickupFlag(oppFlagLoc);
                comms.writeKnownOppFlagLoc(oppFlagLoc, true);
            }
        }
    }



    // TOD: improve initial flag placement
    public void runSetupMovement() throws GameActionException{
        // this method contains movement logic during the setup period
        // it is only called during the first 200 rounds

        // if you can pick up a home team flag, do it
        for(FlagInfo flagInfo: nearbyFlags){
            if(flagInfo.getTeam() == rc.getTeam()
                && rc.canPickupFlag(flagInfo.getLocation())
                && flagInfo.getLocation().distanceSquaredTo(baseLoc) > 60){
                rc.pickupFlag(flagInfo.getLocation());
                return;
            }
        }

        if(rc.hasFlag() && rc.getLocation().distanceSquaredTo(baseLoc) > 60){
            indicatorString += "have flag, going to base;";
            nav.goToBug(baseLoc, 60);
        }

        else if(rc.hasFlag()){
            // make sure there is no other flag withing 6 units
            boolean validDropSpot = true;
            for(FlagInfo flagInfo: nearbyFlags){
                if(flagInfo.getTeam() == rc.getTeam()
                    && flagInfo.getLocation().distanceSquaredTo(rc.getLocation()) < 36){
                    validDropSpot = false;
                    // move away from the flag
                    Direction dir = flagInfo.getLocation().directionTo(rc.getLocation());
                    nav.goTo(rc.getLocation().add(dir.opposite()), 0);
                }
            }

            if(validDropSpot && rc.canDropFlag(myLoc)){
                rc.dropFlag(myLoc);
            }
            else{
                nav.moveRandom();
            }
        }

        else{
            indicatorString += "randomly walking around;";
            nav.moveRandom();
        }
    }


    public void runMainMovement() throws GameActionException{
        // this method contains logic for movement and and actions when the setup period has passed

        tryPickingUpOppFlag(); // try to pick up an opponent flag  

        // if you hold a flag, go to the nearest home base
        if(rc.hasFlag()){
            comms.removeKnownOppFlagLoc(myLoc);
            MapLocation nearestHomeSpawnLoc = Util.getNearestHomeSpawnLoc(myLoc);
            indicatorString += "have flag, going to " + nearestHomeSpawnLoc.toString() + ";";
            nav.goToBug(nearestHomeSpawnLoc, 0);
            myLoc = rc.getLocation();
            comms.writeKnownOppFlagLoc(myLoc, true);
            return;
        }

        // if there is a known opponent flag, go to it
        if(opponentFlagLocs[0] != null){
            targetLoc = opponentFlagLocs[0];
            indicatorString += "going to opponent flag at " + targetLoc.toString() + ";";
            nav.goToBug(targetLoc, 4);
            return;
        }



        // if there is no known opponent flag, go to the approximate flag location (if there is one)
        targetLoc = getTargetLoc();
        if(targetLoc != null) {
            indicatorString += "going to approximate opp flag " + targetLoc.toString() + ";";
            nav.goToBug(targetLoc, 4);
        }

        else{
            targetLoc = rc.getAllySpawnLocations()[0];
            nav.circle(targetLoc, 5, 25);
        }
    }


    public void runMovement() throws GameActionException {
        // if the round number is less than 200, walk around randomly
        readComms(); //update  opp flags and the shared target loc index 
        nearbyFlags = rc.senseNearbyFlags(GameConstants.VISION_RADIUS_SQUARED); // sense nearby flags
        updateComms();

        if(rc.getRoundNum() < 200){
            runSetupMovement();
        }

        else{
            runMainMovement();
        }
    }


    public void runAttack() throws GameActionException {
        for(MapLocation loc: rc.getAllLocationsWithinRadiusSquared(myLoc, GameConstants.ATTACK_RADIUS_SQUARED)){
            if(rc.canAttack(loc)){
                rc.attack(loc);
            }
        }
    }
}

