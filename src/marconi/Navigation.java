package marconi;

import battlecode.common.*;

public class Navigation {

    RobotController rc;
    Robot robot;

    public Navigation(RobotController rc, Robot robot){
        this.rc = rc;
        this.robot = robot;
    }

    public Direction fuzzyNav(MapLocation target) throws GameActionException{
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

            if(!rc.canSenseLocation(newLoc) || !rc.canMove(dir)){
                continue;
            }

            if(!rc.sensePassability(newLoc)) continue;

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
        return bestDir;
    }



    public boolean goTo(MapLocation target, int minDistToSatisfy) throws GameActionException {
        if (robot.myLoc.distanceSquaredTo(target) <= minDistToSatisfy) {
            return true;
        }

        if (!rc.isMovementReady()) {
            return false;
        }

        while (rc.isMovementReady()) {
            Direction toGo = null;
            toGo = fuzzyNav(target);

            if (toGo == null) return false;
            Util.tryMove(toGo);

        if (robot.myLoc.distanceSquaredTo(target) <= minDistToSatisfy) {
            return true;
        }
    }
        return true;
    }

}
