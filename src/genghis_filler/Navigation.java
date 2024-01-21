package genghis_filler;

import battlecode.common.*;
enum NavigationMode{
    FUZZYNAV, BUGNAV;
}

public class Navigation {

    RobotController rc;
    Robot robot;
    Comms comms;

    NavigationMode mode = NavigationMode.BUGNAV;

    // Bugnav variables
    int closestDistToTarget = Integer.MAX_VALUE;
    MapLocation lastWallFollowed = null;
    Direction lastDirectionMoved = null;
    int roundsSinceClosestDistReset = 0;
    MapLocation prevTarget = null;
    boolean[][] locsToIgnore;
    final int RECENTLY_VISITED_LENGTH = 5;
    MapLocation[] recentlyVisited = new MapLocation[RECENTLY_VISITED_LENGTH]; // Used for circling
    int recentlyVisitedIdx = 0; // Used for circling
    boolean prevCircleDir = false;
    boolean bugFollowRight = true; // TODO: Figure out how to make this a smart decision.

    final int ROUNDS_TO_RESET_BUG_CLOSEST = 15;

    public Navigation(RobotController rc, Comms comms, Robot robot) throws GameActionException {
        this.rc = rc;
        this.comms = comms;
        this.robot = robot;
        bugFollowRight = comms.readScoutCountEven();
        locsToIgnore = new boolean[rc.getMapWidth()][rc.getMapHeight()];
    }

    public boolean goToBug(MapLocation target, int minDistToSatisfy) throws GameActionException {
        if(mode != NavigationMode.BUGNAV){
            mode = NavigationMode.BUGNAV;
            resetBugNav();
        }
        if(!target.equals(prevTarget)){
            resetBugNav();
        }
        prevTarget = target;
        return goTo(target, minDistToSatisfy);
    }

    public boolean goToFuzzy(MapLocation target, int minDistToSatisfy) throws GameActionException {
        mode = NavigationMode.FUZZYNAV;
        return goTo(target, minDistToSatisfy);
    }

    public void resetBugNav() {
        closestDistToTarget = Integer.MAX_VALUE;
        lastWallFollowed = null;
        lastDirectionMoved = null;
        roundsSinceClosestDistReset = 0;
    }

    public Direction bugNav(MapLocation target) throws GameActionException {
        Util.addToIndicatorString("BGN");
        // Every 20 turns reset the closest distance to target
        if(roundsSinceClosestDistReset >= ROUNDS_TO_RESET_BUG_CLOSEST){
            closestDistToTarget = Integer.MAX_VALUE;
            roundsSinceClosestDistReset = 0;
        }
        roundsSinceClosestDistReset++;

        Direction closestDir = null;
        Direction wallDir = null;
        Direction dir = null;

        if(lastWallFollowed != null){

            // Check if the last wall still exists and update
            Direction toLastWallFollowed = robot.myLoc.directionTo(lastWallFollowed);
            if (toLastWallFollowed == Direction.CENTER || (robot.myLoc.isAdjacentTo(lastWallFollowed) && rc.canMove(toLastWallFollowed))) {
                lastWallFollowed = null;
            }
            else {
                dir = robot.myLoc.directionTo(lastWallFollowed);
            }
        }
        if (dir == null) {
            dir = robot.myLoc.directionTo(target);
        }

        // This should never happen theoretically, but in case it does, just reset and continue.
        if(dir == Direction.CENTER){
            resetBugNav();
            return Direction.CENTER;
        }

        for (int i = 0; i < 8; i++) {
            MapLocation newLoc = rc.adjacentLocation(dir);
//            if(!rc.isActionReady()) {
//                if(!rc.canMove(dir)) {
//                    continue;
//                }
//            } else {
//                // water is fine if we have enough crumbs
//                // TODO: 30 shouldn't be this constant value since it may be able to fill for cheaper
//                if(rc.getCrumbs() < 30) {
//                    if(!rc.canMove(dir)) {
//                        continue;
//                    }
//                } else {
//                    if(!rc.canMove(dir) && !rc.canFill(newLoc)) {
//                        continue;
//                    }
//                }
//            }

            // TODO: improve these cases
            if(rc.canMove(dir) || (rc.isActionReady() && rc.getCrumbs() >= 30 && rc.canFill(newLoc))){
                // If we can get closer to the target than we've ever been before, do that.
                int dist = newLoc.distanceSquaredTo(target);
                if(dist < closestDistToTarget){
                    closestDistToTarget = dist;
                    closestDir = dir;
                }

                // Check if wall-following is viable
                if(wallDir == null){
                    wallDir = dir;
                }
            }

            // If we canot move in direction
            else {
                if (wallDir == null) {
                    // Count as wall if its not outer boundary or another robot
                    if (rc.onTheMap(newLoc) && !rc.canSenseRobotAtLocation(newLoc)){
                        lastWallFollowed = newLoc;
                    }
                }
            }
            dir = bugFollowRight ? dir.rotateRight() : dir.rotateLeft();
        }

        if(closestDir != null){
            return closestDir;
        }
        return wallDir;
    }

    // TODO: Keep track of recently visited squares and don't revisit.
    public Direction fuzzyNav(MapLocation target) throws GameActionException{
        Util.addToIndicatorString("FZN");
        Direction toTarget = robot.myLoc.directionTo(target);
        Direction[] moveOptions = {
                toTarget,
                toTarget.rotateLeft(),
                toTarget.rotateRight(),
                toTarget.rotateLeft().rotateLeft(),
                toTarget.rotateRight().rotateRight()
        };

        Direction bestDir = null;
        int leastNumMoves = Integer.MAX_VALUE;
        int leastDistanceSquared = Integer.MAX_VALUE;

        MapLocation bestNewLoc = robot.myLoc;

        for(int i= moveOptions.length; i--> 0;){
            Direction dir = moveOptions[i];
            MapLocation newLoc = robot.myLoc.add(dir);

            if(!rc.isActionReady()) {
                if(!rc.canMove(dir)) {
                    continue;
                }
            } else {
                // water is fine if we have enough crumbs
                // TODO: 30 shouldn't be this constant value since it may be able to fill for cheaper
                if(rc.getCrumbs() < 30) {
                    if(!rc.canMove(dir)) {
                        continue;
                    }
                } else {
                    if(!rc.canMove(dir) && !rc.canFill(newLoc)) {
                        continue;
                    }
                }
            }

            int numMoves = Util.minMovesToReach(newLoc, target);
            int distanceSquared = newLoc.distanceSquaredTo(target);

            if(numMoves < leastNumMoves ||
                    (numMoves == leastNumMoves && distanceSquared < leastDistanceSquared)){
                leastNumMoves = numMoves;
                leastDistanceSquared = distanceSquared;
                bestDir = dir;
                bestNewLoc = newLoc;
            }
        }
//        if(rc.canFill(bestNewLoc)) {
//            rc.fill(bestNewLoc);
//        }
        return bestDir;
    }


    public void moveRandom() throws GameActionException {
        int randomIdx = robot.rng.nextInt(8);
        for(int i = 0; i < Robot.movementDirections.length; i++){
            if(Util.tryMove(Robot.movementDirections[(randomIdx + i) % Robot.movementDirections.length])){
                return;
            }
        }
    }


    public boolean goTo(MapLocation target, int minDistToSatisfy) throws GameActionException{
        // thy journey hath been completed
        if (robot.myLoc.distanceSquaredTo(target) <= minDistToSatisfy) {
            return true;
        }

        if (!rc.isMovementReady()) {
            return false;
        }

        while (rc.isMovementReady()) {
            Direction toGo = null;
            switch (mode) {
                case FUZZYNAV:
                    toGo = fuzzyNav(target);
                    break;
                case BUGNAV:
                    toGo = bugNav(target);
                    break;
            }
            if(toGo == null) return false;
            Util.tryMove(toGo); // Should always return true since fuzzyNav checks if rc.canMove(dir)
            if (robot.myLoc.distanceSquaredTo(target) <= minDistToSatisfy){
                return true;
            }
        }
        return true;
    }

    public boolean circle(MapLocation center, int minDist, int maxDist) throws GameActionException {
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
        MapLocation myLoc = robot.myLoc;
        if(Util.minMovesToReach(myLoc, center) > maxDist){
//            Util.log("Moving closer!");
            return goTo(center, minDist);
        }
        if(Util.minMovesToReach(myLoc, center) < minDist){
//            Util.log("Moving away!");
            Direction centerDir = myLoc.directionTo(center);
            if(centerDir == Direction.CENTER){
                centerDir = robot.centerLoc.directionTo(myLoc);
            }
            MapLocation target = myLoc.subtract(centerDir).subtract(centerDir).subtract(centerDir).subtract(centerDir).subtract(centerDir);
            boolean moved = goToBug(target, minDist);
            if(moved){
                return true;
            }
            moved = goToFuzzy(target, minDist);
            if(moved) {
                return true;
            }
            return false;
        }

        if(ccw != prevCircleDir){
            recentlyVisited = new MapLocation[RECENTLY_VISITED_LENGTH];
            prevCircleDir = ccw;
        }

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
