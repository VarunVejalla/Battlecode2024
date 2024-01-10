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
        Direction bestDir = toTarget;

        Direction[] moveOptions = {
                toTarget,
                toTarget.rotateLeft(),
                toTarget.rotateRight(),
                toTarget.rotateLeft().rotateLeft(),
                toTarget.rotateRight().rotateRight(),
        };

        double bestCost = Double.MAX_VALUE;

        for(int i = moveOptions.length; i--> 0;){
            Direction dir = moveOptions[i];
            MapLocation newLoc = robot.myLoc.add(dir);

            if(!rc.canSenseLocation(newLoc) || !rc.canMove(dir)){   // skip checking this cell if we can't see / move to it
                continue;
            }

            if(!rc.sensePassability(newLoc)) continue;  // don't consider if the new location is not passable

            int cost = Util.minMovesToReach(newLoc, target) * 10;

            if(cost < bestCost){
                bestCost = cost;
                bestDir = dir;
            }
        }
        return bestDir;
    }



    public boolean goTo(MapLocation target, int minDistToSatisfy) throws GameActionException {
        if (robot.myLoc.distanceSquaredTo(target) <= minDistToSatisfy){
            return true;
        }

        if(!rc.isMovementReady()){
            return false;
        }

        while(rc.isMovementReady()){
            Direction toGo = fuzzyNav(target);
            Util.tryMove(toGo); // Should always return true since fuzzyNav checks if rc.canMove(dir)
            if (robot.myLoc.distanceSquaredTo(target) <= minDistToSatisfy){
                return true;
            }
        }
        return true;
    }


}
