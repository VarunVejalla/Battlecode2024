package tesla;

import battlecode.common.*;

public class DamScout {
    RobotController rc;
    Robot robot;
    Comms comms;
    Navigation nav;
    MapLocation centerLoc;
    boolean followRight;
    int[] distsToSpawnLocs;
    MapLocation[] spawnLocs;
    MapLocation targetLoc = null;
    Direction adjDir = null;

    public DamScout(RobotController rc, Robot robot, Comms comms, Navigation nav) throws GameActionException {
        this.rc = rc;
        this.robot = robot;
        this.comms = comms;
        this.nav = nav;
        centerLoc = new MapLocation(rc.getMapWidth() / 2, rc.getMapHeight() / 2);
        followRight = comms.readScoutCountEven();
        comms.addToScoutCountEven();
        spawnLocs = Util.getSpawnLocCenters();
    }

    public void scanForNearbyDamnLocation() throws GameActionException{
        MapInfo[] mapInfos = rc.senseNearbyMapInfos();
        for(MapInfo info : mapInfos){
            if(!info.isDam()){
                continue;
            }
            distsToSpawnLocs[0] = Math.min(distsToSpawnLocs[0], Util.minMovesToReach(spawnLocs[0], info.getMapLocation()));
            distsToSpawnLocs[1] = Math.min(distsToSpawnLocs[1], Util.minMovesToReach(spawnLocs[1], info.getMapLocation()));
            distsToSpawnLocs[2] = Math.min(distsToSpawnLocs[2], Util.minMovesToReach(spawnLocs[2], info.getMapLocation()));
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
        distsToSpawnLocs = comms.readDistsToSpawnCenters(); // TODO: Make sure to default to Integer.MAX_VALUE.

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
        comms.writeDistsToSpawnCenters(distsToSpawnLocs);
    }

}
