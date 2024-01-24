package siddev;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class Bug2Nav {

    RobotController rc;
    Comms comms;
    Robot robot;

    // General bug stuff
    MapLocation prevTarget = null;
    MapLocation currWallLocation = null;
    boolean bugFollowRight = true; // TODO: Figure out how to make this a smart decision.

    // Bug 2 specific stuff
    double line_m;
    double line_b;
    int closestDistAlongLine = Integer.MAX_VALUE;
    final int RECENTLY_VISITED_LENGTH = 10;
    MapLocation[] recentlyVisited = new MapLocation[RECENTLY_VISITED_LENGTH];
    int recentlyVisitedIdx = 0;
    int lastUpdatedRoundNum = 0;

    public Bug2Nav(RobotController rc, Comms comms, Robot robot) throws GameActionException {
        this.rc = rc;
        this.comms = comms;
        this.robot = robot;
    }

    public void computeLineValues(MapLocation start, MapLocation target){
        double dx = target.x - start.x;
        double dy = target.y - start.y;
        if(dx == 0){
            if(dy > 0){
                line_m = Double.MAX_VALUE;
            }
            else{
                line_m = Double.MIN_VALUE;
            }
            line_b = start.y;
        }
        else{
            line_m = dy / dx;
            line_b = (double)start.y - line_m * (double)start.x;
        }
    }

    public void resetBug2(MapLocation start, MapLocation target){
        prevTarget = null;
        currWallLocation = null;
        computeLineValues(start, target);
        closestDistAlongLine = start.distanceSquaredTo(target);
        recentlyVisitedIdx = 0;
        recentlyVisited = new MapLocation[RECENTLY_VISITED_LENGTH];
    }

    public boolean checkOnLine(MapLocation loc){
        double line_y = (double)loc.x * line_m + line_b;
        return (loc.y == (int)Math.floor(line_y)) || (loc.y == (int)Math.ceil(line_y));
    }

    public boolean tryFollowingLine(MapLocation target, int minCrumbsForNavigation, int maxDist) throws GameActionException {
        Direction toTarget = robot.myLoc.directionTo(target);
        Direction[] moveOptions = {
                toTarget,
                toTarget.rotateLeft(),
                toTarget.rotateRight(),
                toTarget.rotateLeft().rotateLeft(),
                toTarget.rotateRight().rotateRight(),
                toTarget.rotateLeft().rotateLeft().rotateLeft(),
                toTarget.rotateRight().rotateRight().rotateRight(),
                toTarget.opposite(),
        };

        for(Direction dir : moveOptions){
            MapLocation adjLoc = rc.getLocation().add(dir);
            if(!checkOnLine(adjLoc)){
                continue;
            }
            if(adjLoc.distanceSquaredTo(target) > maxDist){
                continue;
            }
            if(!rc.canMove(dir)){
                continue;
            }
//            if(Util.checkIfItemInArray(adjLoc, recentlyVisited)){
//                continue;
//            }
            if(Util.tryMove(dir, minCrumbsForNavigation)){
                return true;
            }
        }
        return false;
    }

    public void updateBug2CurrLocation(MapLocation loc){
        if(Util.checkIfItemInArray(loc, recentlyVisited)){
            loc = null;
        }
        recentlyVisited[recentlyVisitedIdx] = loc;
        recentlyVisitedIdx = (recentlyVisitedIdx + 1) % RECENTLY_VISITED_LENGTH;
    }

    public boolean goToBug2(MapLocation target, int minCrumbsForNavigation) throws GameActionException {
        if(!target.equals(prevTarget)){
            resetBug2(rc.getLocation(), target);
        }
        prevTarget = target;

        if(!rc.isMovementReady()){
            return false;
        }

        if(rc.getRoundNum() > lastUpdatedRoundNum){
            updateBug2CurrLocation(robot.myLoc);
            lastUpdatedRoundNum = rc.getRoundNum();
        }

        // If on line and closer than previously travelled, try following line.
        if(checkOnLine(rc.getLocation()) && rc.getLocation().distanceSquaredTo(target) <= closestDistAlongLine){
            if(tryFollowingLine(target, minCrumbsForNavigation, closestDistAlongLine)){
                closestDistAlongLine = Math.min(closestDistAlongLine, rc.getLocation().distanceSquaredTo(target));
                currWallLocation = null;
                return true;
            }
        }
//        System.out.println("On line: " + checkOnLine(rc.getLocation()));
//        System.out.println(line_m + ", " + line_b);
//        System.out.println(rc.getLocation().x + ", " + rc.getLocation().y);

        // Otherwise, follow the wall?
        if(currWallLocation == null){
            Direction towardsTarget = robot.myLoc.directionTo(target);
            currWallLocation = robot.myLoc.add(towardsTarget);
        }

        Direction targetDir = robot.myLoc.directionTo(currWallLocation);
        MapLocation locBeforeMoving = robot.myLoc;
        for(int i = 0; i < 8; i++){
            if(Util.tryMove(targetDir, minCrumbsForNavigation)){
                Direction oneOffOfTargetDir = bugFollowRight ? targetDir.rotateLeft() : targetDir.rotateRight();
                currWallLocation = locBeforeMoving.add(oneOffOfTargetDir);
                if(checkOnLine(rc.getLocation())){
                    closestDistAlongLine = Math.min(closestDistAlongLine, rc.getLocation().distanceSquaredTo(target));
                }
                return true;
            }
            targetDir = bugFollowRight ? targetDir.rotateRight() : targetDir.rotateLeft();
        }
        return false;
    }

}
