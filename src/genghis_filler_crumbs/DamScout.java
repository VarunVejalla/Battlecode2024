package genghis_filler_crumbs;

import battlecode.common.*;
import genghis_filler_crumbs.utils.FixedSizeQueue;



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
    boolean moveRandomLoc = true;

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

    public void setCrumbTargetLoc() {
        // Reset Target
        if (movingToCrumbStepCount >= Constants.CRUMB_GIVE_UP_STEPS) {
            movingToCrumbStepCount = 0;
            if (moveRandomLoc) {
                targetLoc = Util.getRandomLocation();
            } else {
                targetLoc = null;
            }
            moveRandomLoc = !moveRandomLoc;
            // targetLoc = Util.getRandomLocation();
        }
        
        // Get a new targetLoc
        if (targetLoc == null) {
            if (crumbs.size() > 0) {
                targetLoc = crumbs.poll();
            } else {
                targetLoc = Util.getRandomLocation();
            }    
            // targetLoc = Util.getRandomLocation();            
        }
    }

    public void runScout() throws GameActionException {
        distsToSpawnCenters = comms.readDistsToSpawnCenters();

        // Populate Nearby Crumbs
        MapLocation[] nearbyCrumbs = rc.senseNearbyCrumbs(Constants.CRUMB_SENSE_RADIUS);      // senseNearbyCrumbs() is 0 bytecode??? https://releases.battlecode.org/javadoc/battlecode24/2.0.1/index.html
        for (MapLocation crumb : nearbyCrumbs) {
            crumbs.add(crumb);
        }
        int roundNum = rc.getRoundNum();
        if (roundNum <= Constants.NEW_FLAG_LOC_DECIDED_ROUND || roundNum >= Constants.SCOUT_LINE_UP_DAM_ROUND ) {
            // If you haven't visited the damn yet, go towards it.
            if(adjDir == null){
                nav.goToBug(centerLoc, 0);
                // Check if I'm currently adjacent to the damn.
                adjDir = getDamAdjDir();
            }
            // Otherwise, follow it, either left or right.      rc.senseMapInfo(MapLocation loc)
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
            adjDir = null;
            movingToCrumbStepCount += 1;

            setCrumbTargetLoc();
            
            // If we have a target
            if (targetLoc != null) {
                
                boolean isDam = true;
                robot.myLoc = rc.getLocation();
                
                while (isDam) {
                    isDam = false;
                    Direction togo = robot.myLoc.directionTo(targetLoc);
                    if (!rc.canMove(togo)) {    
                        // Get new targetLoc if Dam is encountered
                        if (rc.senseMapInfo(rc.adjacentLocation(togo)).isDam()) {
                            targetLoc = null;
                            setCrumbTargetLoc();
                            isDam = true;
                        } 
                    }
                }
  
                nav.goToBug(targetLoc, 0);  // Unrolled bellow

                // nav.resetBugNav();
                // while (rc.isMovementReady()) {
                    
                //     // Move in Direction of target
                //     robot.myLoc = rc.getLocation();
                //     Direction togo = robot.myLoc.directionTo(targetLoc);
                //     Direction bug_nav_togo = nav.bugNav(targetLoc);

                //     if (!rc.canMove(togo)) {    
                //         // Get new targetLoc if Dam is encountered
                //         if (rc.senseMapInfo(rc.adjacentLocation(togo)).isDam()) {
                //             targetLoc = null;
                //             setCrumbTargetLoc();
                //         } 
                //         // else {
                //         //     togo = null;
                //         //     togo = nav.bugNav(targetLoc);
                //         // }
                //     }
                //     if (bug_nav_togo != null) {
                //         Util.tryMove(bug_nav_togo);
                //     }
                //     if (robot.myLoc.equals(targetLoc)) {
                //         break;
                //     }
                // }

                // Reset target location once reached
                robot.myLoc = rc.getLocation();
                if (robot.myLoc.equals(targetLoc)) {
                    targetLoc = null;
                }
            } else { // Can't Do anything, should never happen
                nav.moveRandom();
                Util.log("HELLOOOOOOOOOOOOOO");
            }
        }
    }
}
