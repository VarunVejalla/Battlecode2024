package sjdev;

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

    MapLocation lastSeenDamLoc = null;

    boolean switchedToLineUpMode = false;

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
        // this method checks nearby map infos for locations with a dam
        // for each of those locations, it checks if distance
        // from that location to each of the spawn centers is less than the current minimum

        MapInfo[] mapInfos = rc.senseNearbyMapInfos();
        for(MapInfo info : mapInfos){
            if(!info.isDam()){
                continue;
            }
            distsToSpawnCenters[0] = Math.min(distsToSpawnCenters[0], Util.minMovesToReach(robot.spawnCenters[0], info.getMapLocation()));
            distsToSpawnCenters[1] = Math.min(distsToSpawnCenters[1], Util.minMovesToReach(robot.spawnCenters[1], info.getMapLocation()));
            distsToSpawnCenters[2] = Math.min(distsToSpawnCenters[2], Util.minMovesToReach(robot.spawnCenters[2], info.getMapLocation()));

            lastSeenDamLoc = info.getMapLocation();
        }
    }

    public Direction getDamAdjDir() throws GameActionException{
        // this method checks all the adjacent spots to the robot
        // if the robot can move in that direction and the spot is a dam, return that direction
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

        // If you haven't visited the damn yet, go towards it.
        if(adjDir == null){
            nav.goToBug(centerLoc, 0); // go to the center of the map, cuz there's prolly a dam there
            // Check if I'm currently adjacent to the damn.
            adjDir = getDamAdjDir();  // contains
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

            if(!rc.onTheMap(robot.myLoc.add(adjDir)) || getDamAdjDir() == null){
                adjDir = null;
                followRight = !followRight;
            }
        }

        scanForNearbyDamnLocation();
        comms.writeDistsToSpawnCenters(distsToSpawnCenters);
    }

    // ----------------------------------- LINEUP STUFF -----------------------------------------

    public boolean locationIsNextToDam(MapLocation loc) throws GameActionException {
        // this method checks if a location is next to a dam
        // it does this by checking if there is a dam in the location + direction
        // for each direction
        for(Direction dir : Robot.movementDirections){
            MapLocation adjLoc = loc.add(dir);
            if(rc.canSenseLocation(adjLoc) && rc.senseMapInfo(adjLoc).isDam()){
                return true;
            }
        }
        return false;
    }


    public Direction getLineUpAdjDir() throws GameActionException{
        // this method checks if there is a dir that is adjacent to a bot already in the lineup
        for(Direction dir : Robot.movementDirections){
            MapLocation adjLoc = rc.adjacentLocation(dir);
            RobotInfo bot = rc.senseRobotAtLocation(adjLoc);
            if(rc.canSenseLocation(adjLoc)
                    && bot != null && locationIsNextToDam(bot.location)){
                return dir;
            }
        }
        return null;
    }


    public void switchToLineupModeIfNeeded(){
        if(!switchedToLineUpMode){
            adjDir = null;
            switchedToLineUpMode = true;
        }
    }


    public void runLineUpMovement() throws GameActionException {
        switchToLineupModeIfNeeded();

        // if you're adjacent to a dam, don't do anything
        if (getDamAdjDir() != null) { // if we're adjacent to a dam, just stop
            return;
        }

        // first check if you can slide into the lineup. If so, yay ur done. good luck in the fight, u got it bro, don't die xd :D
        for(Direction dir : Robot.movementDirections){
            if(rc.canMove(dir) && locationIsNextToDam(rc.getLocation().add(dir))){
                rc.move(dir);
                robot.myLoc = rc.getLocation();
                return;
            }
        }

        if(adjDir == null){
            nav.goToFuzzy(centerLoc, 0);
            robot.indicatorString += "CENTER;";
            adjDir = getLineUpAdjDir();
        }

        else {
            // otherwise, follow the wall of bots/dam until you're adjacent
            MapLocation locBeforeMoving = rc.getLocation();
            Direction moveDir = adjDir; // direction to move in
            Direction prevDir = moveDir; // direction you moved in last turn

            for (int i = 0; i < 8; i++) {
                if (followRight) {
                    moveDir = moveDir.rotateRight();
                } else {
                    moveDir = moveDir.rotateLeft();
                }
                if (rc.canMove(moveDir)) {
                    rc.move(moveDir);
                    robot.myLoc = rc.getLocation();
                    adjDir = robot.myLoc.directionTo(locBeforeMoving.add(prevDir));
                }
                prevDir = moveDir;
            }

            if (!rc.onTheMap(robot.myLoc.add(adjDir))) {
                adjDir = null;
                followRight = !followRight;
            }
        }
    }

}
