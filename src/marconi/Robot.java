package marconi;


import battlecode.common.*;

import java.util.Random;


enum SymmetryType { HORIZONTAL, VERTICAL, ROTATIONAL, DIAGONAL_RIGHT, DIAGONAL_LEFT};


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


    public Robot(RobotController rc) throws GameActionException{
        this.rc = rc;
        this.nav = new Navigation(rc, this);
        this.comms = new Comms(rc, this);
        this.rng = new Random(rc.getID());  // seed the random number generator with the id of the bot

        Util.rc = rc;
        Util.robot = this;

        for(int i=0; i < 3; i++){ // looping over spawnLocs
            int j=0;
            for(SymmetryType symmetryType: SymmetryType.values()){ // looping over symmetries
                allTargetLocs[i*5 + j] = Util.applySymmetry(rc.getAllySpawnLocations()[i], symmetryType);
                Util.log(rc.getAllySpawnLocations()[i] + " " + symmetryType + " --> " + allTargetLocs[i*5 + j]);

                j++;
            }
        }


        // if the round number is less than 50, set the flags in the shared array to null
        opponentFlagLocs = comms.readOpponentFlagLocs();
        if(rc.getRoundNum() < 50 && opponentFlagLocs[0] != null){
            comms.setOppFlagsToNull();
        }
    }


    public void spawn() throws GameActionException{
        MapLocation[] spawnLocs = rc.getAllySpawnLocations();
        // Pick a random spawn location to attempt spawning in.
        MapLocation randomLoc = spawnLocs[rng.nextInt(spawnLocs.length)];
        if (rc.canSpawn(randomLoc)) rc.spawn(randomLoc);
    }



    public MapLocation getTargetLoc() throws GameActionException{
        // read shared array
        // go to that targetLoc
        return allTargetLocs[targetLocIdx];
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
        opponentFlagLocs = comms.readOpponentFlagLocs(); // read the opponent flag locs from comms
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
        // this method checks to see if the opponent flag locs are still valid
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
                    comms.removeOppFlagLoc(flagLoc);
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
                comms.writeOppFlagLoc(flagLoc);
            }
        }
    }


    public void updateComms() throws GameActionException{
        verifyOppFlagLocs();
        checkForNewOppFlags();
        opponentFlagLocs = comms.readOpponentFlagLocs();
    }



    public void tryPickingUpOppFlag() throws GameActionException{
        // this method tries to pick up an opponent flag
        // if it can, it picks it up
        // if it can't, it does nothing

        // loop over all the opponent flags and check to see, if they are not null, can you pick them up
        for(MapLocation oppFlagLoc: opponentFlagLocs){
            if(oppFlagLoc != null && rc.canPickupFlag(oppFlagLoc)){
                rc.pickupFlag(oppFlagLoc);
                comms.removeOppFlagLoc(oppFlagLoc); // remove it from comms because you've picked it up
            }
        }
    }



    public void runMovement() throws GameActionException {
        // if the round number is less than 200, walk around randomly
        if(rc.getRoundNum() < 200){
            nav.moveRandom();
            indicatorString += "moving around randomly :D;";
            return;
        }

        readComms(); //update  opp flags and the shared target loc index 
        nearbyFlags = rc.senseNearbyFlags(GameConstants.VISION_RADIUS_SQUARED); // sense nearby flags
        updateComms();

        tryPickingUpOppFlag(); // try to pick up an opponent flag  

        // if you hold a flag, go to the nearest home base
        if(rc.hasFlag()){
            MapLocation nearestHomeSpawnLoc = Util.getNearestHomeSpawnLoc(myLoc);
            indicatorString += "have flag, going to " + nearestHomeSpawnLoc.toString() + ";";
            nav.goTo(nearestHomeSpawnLoc, 0);
            return;
        }

        // if you've reached the target loc, update the target loc
        if(myLoc.distanceSquaredTo(getTargetLoc()) <= 4){
            targetLocIdx = (targetLocIdx + 1) % allTargetLocs.length;
//            comms.writeSharedTargetLocIdx((sharedtargetLocIdx + 1) % allTargetLocs.length);
        }

        // if there is a known opponent flag, go to it
        if(opponentFlagLocs[0] != null){
            targetLoc = opponentFlagLocs[0];
            indicatorString += "going to opponent flag at " + targetLoc.toString() + ";";
            nav.goTo(targetLoc, 4);

        }

        // if there is no known opponent flag, go to the shared target loc
        else{
            targetLoc = getTargetLoc();
            indicatorString += "going to shared target loc at " + targetLoc.toString() + ";";
            nav.goTo(targetLoc, 4);
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

