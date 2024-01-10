package marconi;


import battlecode.common.*;

import java.util.Random;

public class Robot {

    RobotController rc;
    Comms comms;
    Navigation nav;
    MapLocation myLoc; //current loc of robot
    MapInfo myLocInfo;
    MapLocation targetLoc; // where we want to be going
    final Random rng = new Random(6147);

    /** Array containing all the possible movement directions. */
    Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };


    public Robot(RobotController rc) throws GameActionException{
        this.rc = rc;
        this.nav = new Navigation(rc, this);
        this.comms = new Comms(rc, this);

        Util.rc = rc;
        Util.robot = this;
    }


    public void spawn() throws GameActionException{
        MapLocation[] spawnLocs = rc.getAllySpawnLocations();
        // Pick a random spawn location to attempt spawning in.
        MapLocation randomLoc = spawnLocs[rng.nextInt(spawnLocs.length)];
        if (rc.canSpawn(randomLoc)) rc.spawn(randomLoc);
    }


    public MapLocation generateSharedTarget() throws GameActionException {
        // generate a target that everyone should go to
        // gets written to the shared comms array and everyone will start travelling to this location
        int x_pos = rng.nextInt(rc.getMapWidth());
        int y_pos = rng.nextInt(rc.getMapHeight());
        MapLocation sharedTargetLoc = new MapLocation(x_pos, y_pos);
        if(comms.writeSharedTargetLoc(sharedTargetLoc)) {
            return sharedTargetLoc;
        }
        return null;
    }


    public MapLocation getSharedTargetLoc() throws GameActionException{
        // read shared array
        // go to that targetLoc
        return comms.readSharedTargetLoc();
    }


    public void run() throws GameActionException{
        if (!rc.isSpawned()) spawn();

        else{
            myLoc = rc.getLocation();

            if (rc.canPickupFlag(rc.getLocation())){
                rc.pickupFlag(rc.getLocation());
                rc.setIndicatorString("Holding a flag!");
            }

            runMovement();
            runAttack();
    }
}


    public void runMovement() throws GameActionException {
        // if you have a flag, head back home
        if (rc.hasFlag() && rc.getRoundNum() >= GameConstants.SETUP_ROUNDS){
            MapLocation[] spawnLocs = rc.getAllySpawnLocations();
            targetLoc= spawnLocs[0];
        }


        else {
            targetLoc = comms.readSharedTargetLoc();
            nav.goTo(targetLoc, 3);
            myLoc = rc.getLocation();

            // generate a new targetLoc (for everyone) if we've reached the destination
            if(myLoc.distanceSquaredTo(targetLoc) < 3) {
                targetLoc = generateSharedTarget();
            }
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

