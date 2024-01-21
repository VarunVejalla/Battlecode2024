package genghis_filler_crumbs;

import battlecode.common.*;
import genghis_nav.utils.FixedSizeQueue;



public class DamScout {
    RobotController rc;
    Robot robot;
    Comms comms;
    Navigation nav;
    MapLocation centerLoc;
    boolean followRight;
    int[] distsToSpawnCenters;
    MapLocation targetLoc = null;
    Direction adjDir = null;

    FixedSizeQueue<MapLocation> crumbs = new FixedSizeQueue<MapLocation>(Constants.CRUMB_REMEMBER_COUNT);
    int movingToCrumbStepCount = 0;

    public DamScout(RobotController rc, Robot robot, Comms comms, Navigation nav) throws GameActionException {
        this.rc = rc;
        this.robot = robot;
        this.comms = comms;
        this.nav = nav;
        centerLoc = new MapLocation(rc.getMapWidth() / 2, rc.getMapHeight() / 2);
        followRight = comms.readScoutCountEven();
        comms.addToScoutCountEven();
    }

    public void scanForNearbyDamnLocation() throws GameActionException{
        MapInfo[] mapInfos = rc.senseNearbyMapInfos();
        for(MapInfo info : mapInfos){
            if(!info.isDam()){
                continue;
            }
            distsToSpawnCenters[0] = Math.min(distsToSpawnCenters[0], Util.minMovesToReach(robot.spawnCenters[0], info.getMapLocation()));
            distsToSpawnCenters[1] = Math.min(distsToSpawnCenters[1], Util.minMovesToReach(robot.spawnCenters[1], info.getMapLocation()));
            distsToSpawnCenters[2] = Math.min(distsToSpawnCenters[2], Util.minMovesToReach(robot.spawnCenters[2], info.getMapLocation()));
        }
    }

    public Direction getDamAdjDir() throws GameActionException{
        for(Direction dir : Robot.movementDirections){
            MapLocation adjLoc = rc.adjacentLocation(dir);
            if(rc.canSenseLocation(adjLoc) && rc.senseMapInfo(adjLoc).isDam()){
                return dir;
            }
        }
        return null;
    }

    public void runScout() throws GameActionException {
        distsToSpawnCenters = comms.readDistsToSpawnCenters();

        // Populate Nearby Crumbs
        MapLocation[] nearbyCrumbs = rc.senseNearbyCrumbs(Constants.CRUMB_SENSE_RADIUS);      // senseNearbyCrumbs() is 0 bytecode??? https://releases.battlecode.org/javadoc/battlecode24/2.0.1/index.html
        for (MapLocation crumb : nearbyCrumbs) {
            crumbs.add(crumb);
        }

        if (rc.getRoundNum() <= Constants.NEW_FLAG_LOC_DECIDED_ROUND + 5 ) {
            // If you haven't visited the damn yet, go towards it.
            if(adjDir == null){
                nav.goToBug(centerLoc, 0);
                // Check if I'm currently adjacent to the damn.
                adjDir = getDamAdjDir();
            }
            // Otherwise, follow it, either left or right.
            else{
                MapLocation locBeforeMoving = rc.getLocation();
                Direction moveDir = adjDir;
                Direction prevDir = moveDir;
                for(int i = 0; i < 8; i++){
                    if(followRight){
                        moveDir = moveDir.rotateRight();
                    }
                    else{
                        moveDir = moveDir.rotateLeft();
                    }
                    if(rc.canMove(moveDir)){
                        rc.move(moveDir);
                        robot.myLoc = rc.getLocation();
                        adjDir = robot.myLoc.directionTo(locBeforeMoving.add(prevDir));
                    }
                    prevDir = moveDir;
                }

                if(!rc.onTheMap(robot.myLoc.add(adjDir))){
                    adjDir = null;
                    followRight = !followRight;
                }
            }

            scanForNearbyDamnLocation();
            comms.writeDistsToSpawnCenters(distsToSpawnCenters);
        } else {        // Grab the crumbs
            movingToCrumbStepCount += 1;

            if (movingToCrumbStepCount >= Constants.CRUMB_GIVE_UP_STEPS) {
                movingToCrumbStepCount = 0;
                // TODO Get a new target
                targetLoc = null;
            }
            
            // Get a new target
            if (targetLoc == null && crumbs.size() > 0) {
                targetLoc = crumbs.poll();
            }
            
            // If we have a target
            if (targetLoc != null) {
                nav.goToBug(targetLoc, 0);

                // Reset target location once reached
                MapLocation curLocation = rc.getLocation();
                if (curLocation.equals(targetLoc)) {
                    targetLoc = null;
                }
            } else { // Can't Do anything
                nav.moveRandom();
            }
        }
    }
}
