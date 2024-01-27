package davinci;

import battlecode.common.*;

public class Navigation {

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

    static final Direction[] cardinalDirections = {
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST,
    };

    // array containing all directions
    static final Direction[] allDirections = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
            Direction.CENTER
    };

    static final int BFS_CENTER_X = 5;
    static final int BFS_CENTER_Y = 4;

    RobotController rc;
    Robot robot;
    Comms comms;

    FuzzyNav fuzzyNav;
    BugNav bugNav;
    BFS bfs;

    int[][] heuristicMap;
    int lastRoundHeuristicMapCalculated = 0;

    boolean[][] locsToIgnore;
    final int RECENTLY_VISITED_LENGTH = 5;
    MapLocation[] recentlyVisited = new MapLocation[RECENTLY_VISITED_LENGTH]; // Used for circling
    int recentlyVisitedIdx = 0; // Used for circling
    boolean prevCircleDir = false;
    boolean wasRunningBug = false;
    boolean waterFillingAllowed = false;

    public Navigation(RobotController rc, Comms comms, Robot robot) throws GameActionException {
        this.rc = rc;
        this.comms = comms;
        this.robot = robot;
        fuzzyNav = new FuzzyNav(rc, comms, robot);
        bugNav = new BugNav(rc, comms, robot);
        bfs = new BFS20(rc, robot);
        locsToIgnore = new boolean[rc.getMapWidth()][rc.getMapHeight()];
    }

    public void resetBFS(){
        recentlyVisited = new MapLocation[RECENTLY_VISITED_LENGTH];
    }

    public void pathBF(MapLocation target, int minCrumbsForNavigation) throws GameActionException {
        if(!rc.isMovementReady()){
            return;
        }

        waterFillingAllowed = rc.getCrumbs() >= minCrumbsForNavigation;

        if(!target.equals(bugNav.prevTarget)){
            wasRunningBug = false;
            bugNav.resetBug0(target, waterFillingAllowed);
        }
        bugNav.closestDistBug0 = Math.min(bugNav.closestDistBug0, rc.getLocation().distanceSquaredTo(target));
        if(rc.getRoundNum() > bugNav.lastUpdatedRoundNum){
            bugNav.updateBug0CurrLocation(robot.myLoc);
            bugNav.lastUpdatedRoundNum = rc.getRoundNum();
        }
        if(wasRunningBug && bugNav.currWallLocation == null) { // If we were previously going towards target.
            // Try running BFS
            Direction moveDir = bfs.getBestDir(target, getHeuristicMapBFS());
            if(moveDir != null){
                Util.tryMove(moveDir);
                recentlyVisited[recentlyVisitedIdx] = rc.getLocation();
                recentlyVisitedIdx = (recentlyVisitedIdx + 1) % recentlyVisited.length;
                wasRunningBug = false;
                Util.addToIndicatorString("SW_BFS");
                return;
            }
            else { // If that doesn't work, try going towards target.
                wasRunningBug = true;
                if(bugNav.tryMovingCloserToGoal(target, waterFillingAllowed)) {
                    Util.addToIndicatorString("CT_MCTG");
                    return;
                }
                // If that doesn't work, try going around obstacle.
                bugNav.needToChooseBugDirection = true;
                bugNav.tryGoingAroundWall(target, waterFillingAllowed);
                Util.addToIndicatorString("SW_BUG");
                return;
            }
        }
        else if(wasRunningBug && bugNav.currWallLocation != null) { // If we were previously going around an obstacle.
            wasRunningBug = true;
            // Try moving closer to goal.
            if(bugNav.tryMovingCloserToGoal(target, waterFillingAllowed)) {
                Util.addToIndicatorString("SW_MCTG");
                return;
            }

            // If that fails, go around obstacle.
            bugNav.tryGoingAroundWall(target, waterFillingAllowed);
            Util.addToIndicatorString("CT_BUG");
            return;
        }
        else { // If was previously running BFS.
            // Try continuing BFS.
            Direction moveDir = bfs.getBestDir(target, getHeuristicMapBFS());
            if(moveDir != null){
                Util.tryMove(moveDir);
                recentlyVisited[recentlyVisitedIdx] = rc.getLocation();
                recentlyVisitedIdx = (recentlyVisitedIdx + 1) % recentlyVisited.length;
                wasRunningBug = false;
                Util.addToIndicatorString("CT_BFS");
            }
            else { // If that doesn't work, try going towards target.
                wasRunningBug = true;
                bugNav.resetBug0(target, waterFillingAllowed);
                resetBFS();
                if(bugNav.tryMovingCloserToGoal(target, waterFillingAllowed)) {
                    Util.addToIndicatorString("SW_MCTG");
                    return;
                }
                // If that doesn't work, try going around obstacle.
                bugNav.needToChooseBugDirection = true;
                bugNav.tryGoingAroundWall(target, waterFillingAllowed);
                Util.addToIndicatorString("SW_BUG");
                return;
            }
        }
    }

    public void update() throws GameActionException {
        if(!bfs.vars_are_reset){
            bfs.resetVars(getHeuristicMapBFS());
        }
    }

    public void moveRandom() throws GameActionException {
        int randomIdx = robot.rng.nextInt(8);
        for(int i = 0; i < movementDirections.length; i++){
            if(Util.tryMove(movementDirections[(randomIdx + i) % movementDirections.length])){
                return;
            }
        }
    }

    public int[][] getHeuristicMapBFS() throws GameActionException {
        if(rc.getRoundNum() == lastRoundHeuristicMapCalculated){
            return heuristicMap;
        }
        MapInfo[] infos = rc.senseNearbyMapInfos();
        heuristicMap = new int[10][10];
        int ourX = rc.getLocation().x;
        int ourY = rc.getLocation().y;

        // NOTE: This loop takes ~3000 bytecode (slowest part of the nav code).
        for(int i = infos.length; i-- > 0;){
            MapLocation infoLoc = infos[i].getMapLocation();
            int bfsX = infoLoc.x - ourX + BFS_CENTER_X;
            int bfsY = infoLoc.y - ourY + BFS_CENTER_Y;
            if(waterFillingAllowed && infos[i].isWater()){
                heuristicMap[bfsX][bfsY] = 3;
            }
            else if(infos[i].isPassable()){
                heuristicMap[bfsX][bfsY] = 1;
            }
        }
        for(int i = recentlyVisited.length; i-- > 0;){
            MapLocation loc = recentlyVisited[i];
            if(loc == null){
                continue;
            }
            int bfsX = loc.x - ourX + BFS_CENTER_X;
            int bfsY = loc.y - ourY + BFS_CENTER_Y;
            if(bfsX >= 0 && bfsX < heuristicMap.length && bfsY >= 0 && bfsY < heuristicMap[0].length){
                heuristicMap[bfsX][bfsY] = 0;
            }
        }
        heuristicMap[BFS_CENTER_X][BFS_CENTER_Y] = 1;

        lastRoundHeuristicMapCalculated = rc.getRoundNum();
        return heuristicMap;
    }


    public boolean circle(MapLocation center, int minDist, int maxDist, int minCrumbsForNavigation) throws GameActionException {
        if(!rc.isMovementReady()){
            return false;
        }
        MapLocation myLoc = robot.myLoc;
        if(Util.minMovesToReach(myLoc, center) > maxDist){
//            Util.log("Moving closer!");
            pathBF(center, minCrumbsForNavigation);
            return true;
        }
        if(Util.minMovesToReach(myLoc, center) < minDist){
//            Util.log("Moving away!");
            Direction centerDir = myLoc.directionTo(center);
            if(centerDir == Direction.CENTER){
                centerDir = robot.centerLoc.directionTo(myLoc);
            }
            MapLocation target = myLoc.subtract(centerDir).subtract(centerDir).subtract(centerDir).subtract(centerDir).subtract(centerDir);
            if(bugNav.goToBug0(target, minCrumbsForNavigation)){
                return true;
            }
            return fuzzyNav.goTo(target, minCrumbsForNavigation);
        }

//        Util.log("Tryna circle CCW? " + prevCircleDir);
        if(circle(center, minDist, maxDist, prevCircleDir)){
            return true;
        }
//        Util.log("Tryna circle CW");
        if(circle(center, minDist, maxDist, !prevCircleDir)){
            return true;
        }
        return false;
    }

    // from: https://github.com/srikarg89/Battlecode2022/blob/main/src/cracked4BuildOrder/Navigation.java
    public boolean circle(MapLocation center, int minDist, int maxDist, boolean ccw) throws GameActionException {
        if(!rc.isMovementReady()){
            return false;
        }
        Util.addToIndicatorString("CRC" + ccw);

        if(ccw != prevCircleDir){
            recentlyVisited = new MapLocation[RECENTLY_VISITED_LENGTH];
            prevCircleDir = ccw;
        }

        MapLocation myLoc = robot.myLoc;
        int dx = myLoc.x - center.x;
        int dy = myLoc.y - center.y;
        double theta = Math.atan2(dy, dx);
        theta += (ccw ? 0.5 : -0.5);
        int avgDist = (minDist + maxDist) / 2;

        int x = (int)((double)avgDist * Math.cos(theta));
        int y = (int)((double)avgDist * Math.sin(theta));
        MapLocation target = center.translate(x, y);
        Util.addToIndicatorString("TGT " + target);
        Direction targetDir = myLoc.directionTo(target);

        Direction[] options = {targetDir, targetDir.rotateRight(), targetDir.rotateLeft(), targetDir.rotateRight().rotateRight(), targetDir.rotateLeft().rotateLeft()};
        Direction bestDirection = null;
        int bestHeuristic = Integer.MAX_VALUE;
        for(int i = 0; i < options.length; i++){
            if(!rc.canMove(options[i])){
                continue;
            }
            MapLocation newLoc = myLoc.add(options[i]);
            if(Util.checkIfItemInArray(newLoc, recentlyVisited)){
                continue;
            }
            int heuristic = i * 10;
            heuristic += locsToIgnore[newLoc.x][newLoc.y] ? 1000 : 0;
            int centerDist = Util.minMovesToReach(center, newLoc);
            heuristic += Math.abs(centerDist - avgDist) * 15;
            if(Util.minMovesToReach(center, newLoc) < minDist){
                continue;
            }
            if(Util.minMovesToReach(center, newLoc) > maxDist){
                continue;
            }
            if(heuristic < bestHeuristic){
                bestDirection = options[i];
                bestHeuristic = heuristic;
            }
        }
        if(bestDirection != null){
            rc.move(bestDirection);
            recentlyVisited[recentlyVisitedIdx] = rc.getLocation();
            recentlyVisitedIdx = (recentlyVisitedIdx + 1) % recentlyVisited.length;
            return true;
        }
        return false;
    }
}
