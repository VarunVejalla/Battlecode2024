package genghis_nav_health;

import genghis_nav.utils.FixedSizeQueue;
import battlecode.common.*;



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
    int reachCrumbCount = 0;
    boolean moveBackFlag = true;

    FixedSizeQueue<MapLocation> crumbs = new FixedSizeQueue<MapLocation>(Constants.CRUMB_REMEMBER_COUNT);


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

    public void runScout(MapLocation[] nearbyCrumbs) throws GameActionException {
        distsToSpawnCenters = comms.readDistsToSpawnCenters();

        for (MapLocation crumb : nearbyCrumbs) {
            crumbs.add(crumb);
        }

        if (rc.getRoundNum() <= Constants.NEW_FLAG_LOC_DECIDED_ROUND + 5 ) {
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
        }
        else {

            reachCrumbCount += 1;

            if (reachCrumbCount >= Constants.CRUMB_GIVE_UP_STEPS) {
                if (moveBackFlag) {
                    targetLoc = robot.spawnLoc;
                }
                else {
                    targetLoc = null;
                }
                moveBackFlag = !moveBackFlag;
                reachCrumbCount = 0; 
            }

            if (targetLoc == null  && crumbs.size() > 0) {
                targetLoc = crumbs.poll();
                reachCrumbCount = 0;
            }

            if (targetLoc != null) {
                nav.goToBug(targetLoc, 0);
                MapLocation curLocation = rc.getLocation();
                if (curLocation.equals(targetLoc)) {
                    targetLoc = null;
                }
            }
            else {
                nav.moveRandom();
            }
        }
    }
}

// class FixedSizeQueue<T> extends LinkedList<T> {
//     private final int maxSize;

//     public FixedSizeQueue(int maxSize) {
//         this.maxSize = maxSize;
//     }

//     @Override
//     public boolean add(T element) {
//         boolean added = super.add(element);
//         if (size() > maxSize) {
//             // Remove the oldest element if the size exceeds the limit
//             super.removeFirst();
//         }
//         return added;
//     }
// }
