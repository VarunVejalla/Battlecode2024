package magellan;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class FuzzyNav {

    RobotController rc;
    Comms comms;
    Robot robot;

    MapLocation prevTarget = null;
    final int RECENTLY_VISITED_LENGTH = 15;
    MapLocation[] recentlyVisited = new MapLocation[RECENTLY_VISITED_LENGTH];
    int recentlyVisitedIdx = 0;
    int lastUpdatedRoundNum = 0;

    public FuzzyNav(RobotController rc, Comms comms, Robot robot) throws GameActionException {
        this.rc = rc;
        this.comms = comms;
        this.robot = robot;
    }

    public void resetFuzzyNav(){
        prevTarget = null;
        recentlyVisitedIdx = 0;
        recentlyVisited = new MapLocation[RECENTLY_VISITED_LENGTH];
    }

    public void updateFuzzyCurrLocation(MapLocation loc){
        if(Util.checkIfItemInArray(loc, recentlyVisited)){
            loc = null;
        }
        recentlyVisited[recentlyVisitedIdx] = loc;
        recentlyVisitedIdx = (recentlyVisitedIdx + 1) % RECENTLY_VISITED_LENGTH;
    }

    public boolean goTo(MapLocation target, int minCrumbsForNavigation) throws GameActionException {
        Util.addToIndicatorString("FZN");
        if(!target.equals(prevTarget)){
            resetFuzzyNav();
        }
        prevTarget = target;

        if(rc.getRoundNum() > lastUpdatedRoundNum){
            updateFuzzyCurrLocation(robot.myLoc);
            lastUpdatedRoundNum = rc.getRoundNum();
        }

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

        Direction bestDir = null;
        int leastNumMoves = Integer.MAX_VALUE;
        int leastDistanceSquared = Integer.MAX_VALUE;

        for(int i = moveOptions.length; i-- > 0;){
            Direction dir = moveOptions[i];
            MapLocation newLoc = robot.myLoc.add(dir);

            if(Util.checkIfItemInArray(newLoc, recentlyVisited)){
                continue;
            }

            boolean canMove = rc.canMove(dir);
            // Water is fine if we have enough crumbs
            // TODO: 30 shouldn't be this constant value since it may be able to fill for cheaper
            canMove |= (rc.canSenseLocation(newLoc) && rc.senseMapInfo(newLoc).isWater() && rc.getCrumbs() >= Math.max(minCrumbsForNavigation, 30) && rc.canFill(newLoc));
            if(!canMove){
                continue;
            }

            int numMoves = Util.minMovesToReach(newLoc, target);
            int distanceSquared = newLoc.distanceSquaredTo(target);

            if(numMoves < leastNumMoves ||
                    (numMoves == leastNumMoves && distanceSquared < leastDistanceSquared)){
                leastNumMoves = numMoves;
                leastDistanceSquared = distanceSquared;
                bestDir = dir;
            }
        }
        if(bestDir != null){
            return Util.tryMove(bestDir, minCrumbsForNavigation);
        }
        return false;
    }



}
