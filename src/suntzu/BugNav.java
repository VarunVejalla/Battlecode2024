package suntzu;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class BugNav {

    RobotController rc;
    Comms comms;
    Robot robot;

    // General bug stuff
    MapLocation prevTarget = null;
    MapLocation currWallLocation = null;
    boolean bugFollowRight = true;
    int closestDistBug0 = Integer.MAX_VALUE;
    boolean needToChooseBugDirection = true;
    int lastUpdatedRoundNum = 0;
    MapLocation[] visitedList = new MapLocation[100];
    int visitedStart = 0;
    int visitedEnd = 0;

    public BugNav(RobotController rc, Comms comms, Robot robot) throws GameActionException {
        this.rc = rc;
        this.comms = comms;
        this.robot = robot;
    }

    public int firstFreeSpotIdx(MapLocation wallLoc, MapLocation target, boolean checkRight, boolean waterFillingAllowed) throws GameActionException {
        Direction targetDir = rc.getLocation().directionTo(wallLoc);
        targetDir = checkRight ? targetDir.rotateRight() : targetDir.rotateLeft();
        for(int i = 0; i < 8; i++){
            // Only cut into the water if it'll help you get there faster, otherwise just go around it.
            MapLocation adjLoc = rc.getLocation().add(targetDir);
            boolean isWater = rc.canSenseLocation(adjLoc) && rc.senseMapInfo(adjLoc).isWater();
            if(isWater && targetDir != rc.getLocation().directionTo(target)){
                // In water
            }
            else if(!rc.onTheMap(adjLoc)){
                // Not on map
            }
            else if(checkInVisited(adjLoc)){
                // In visited
            }
            else if(Util.canMove(targetDir, waterFillingAllowed)){
                return i;
            }
            else{
                // Can't move there.
            }
            targetDir = checkRight ? targetDir.rotateRight() : targetDir.rotateLeft();
        }
        return 8;
    }

    public void chooseBugDirection(MapLocation target, boolean waterFillingAllowed) throws GameActionException {
        // Otherwise, follow the wall?
        MapLocation tempWallLoc = currWallLocation;
        if(tempWallLoc == null){
            Direction towardsTarget = robot.myLoc.directionTo(target);
            tempWallLoc = robot.myLoc.add(towardsTarget);
        }

        int goingLeft = firstFreeSpotIdx(tempWallLoc, target, false, waterFillingAllowed);
        int goingRight = firstFreeSpotIdx(tempWallLoc, target, true, waterFillingAllowed);
        if(goingLeft < goingRight){
            bugFollowRight = false;
        }
        else{
            bugFollowRight = true;
        }
    }

    public void resetBug0(MapLocation target, boolean waterFillingAllowed) throws GameActionException {
        prevTarget = target;
        currWallLocation = null;
        closestDistBug0 = Integer.MAX_VALUE;
        resetVisited();
        chooseBugDirection(target, waterFillingAllowed);
    }

    public void resetVisited(){
        visitedStart = 0;
        visitedEnd = 0;
    }

    public void addToVisited(MapLocation loc){
        visitedList[visitedEnd] = loc;
        visitedEnd = (visitedEnd + 1) % visitedList.length;
        if(visitedStart == visitedEnd){
            visitedStart = (visitedStart + 1) % visitedList.length;
        }
    }

    public boolean checkInVisited(MapLocation loc){
        for(int i = visitedStart; i < visitedEnd; i++){
            if(loc.equals(visitedList[i])){
                return true;
            }
        }
        return false;
    }

    public boolean tryMovingCloserToGoal(MapLocation target, boolean waterFillingAllowed) throws GameActionException {
        int closestDist = closestDistBug0;
        Direction bestDir = null;
        for(Direction dir : Navigation.movementDirections){
            MapLocation adjLoc = rc.getLocation().add(dir);
            boolean canMove = rc.canMove(dir);
            canMove |= rc.canSenseLocation(adjLoc) && rc.senseMapInfo(adjLoc).isWater() && waterFillingAllowed;
            if(!canMove){
                continue;
            }
            int dist = adjLoc.distanceSquaredTo(target);
            if(dist < closestDist){
                closestDist = dist;
                bestDir = dir;
            }
        }
        if(bestDir != null){
            if(Util.tryMove(bestDir, waterFillingAllowed)){
                closestDistBug0 = rc.getLocation().distanceSquaredTo(target);
                currWallLocation = null;
                resetVisited();
                needToChooseBugDirection = true;
                return true;
            }
        }
        return false;
    }

    public void updateBug0CurrLocation(MapLocation loc){
        addToVisited(loc);
    }

    public void chooseWallFollowingDirection(MapLocation target, MapLocation locBeforeMoving, Direction targetDir, boolean isWater) throws GameActionException {
        Direction oneOffOfTargetDir = bugFollowRight ? targetDir.rotateLeft() : targetDir.rotateRight();
        currWallLocation = locBeforeMoving.add(oneOffOfTargetDir);

        if(isWater){
            closestDistBug0 = rc.getLocation().distanceSquaredTo(target);
        }

        if(rc.canSenseLocation(currWallLocation) && rc.sensePassability(currWallLocation)){
            Direction potWallDir = rc.getLocation().directionTo(currWallLocation);
            MapLocation potWallLoc = rc.getLocation().add(potWallDir);
            for(int j = 0; j < 8; j++){
                if(rc.sensePassability(potWallLoc) || checkInVisited(potWallLoc)){
                    potWallDir = bugFollowRight ? potWallDir.rotateRight() : potWallDir.rotateLeft();
                }
            }
            if(potWallDir == rc.getLocation().directionTo(currWallLocation)){
                closestDistBug0 = rc.getLocation().distanceSquaredTo(target);
                currWallLocation = null;
                resetVisited();
            }
            else{
                closestDistBug0 = rc.getLocation().distanceSquaredTo(target);
                currWallLocation = rc.getLocation().add(potWallDir);
                resetVisited();
            }
        }
    }

    public boolean tryGoingAroundWall(MapLocation target, boolean waterFillingAllowed) throws GameActionException {
        if(needToChooseBugDirection){
            chooseBugDirection(target, waterFillingAllowed);
            needToChooseBugDirection = false;
        }

        if(bugFollowRight){
            Util.addToIndicatorString("BFR");
        }
        else{
            Util.addToIndicatorString("BFL");
        }

        if(currWallLocation == null){
            Direction towardsTarget = robot.myLoc.directionTo(target);
            currWallLocation = robot.myLoc.add(towardsTarget);
        }

        Direction targetDir = robot.myLoc.directionTo(currWallLocation);
        MapLocation locBeforeMoving = robot.myLoc;

        // TODO: Remove this??
        if(rc.onTheMap(currWallLocation) && rc.canSenseLocation(currWallLocation) && rc.isLocationOccupied(currWallLocation)){
            return true;
        }
        targetDir = bugFollowRight ? targetDir.rotateRight() : targetDir.rotateLeft();
        Direction worstCaseDir = null;
        for(int i = 0; i < 8; i++){
            // Only cut into the water if it'll help you get there faster, otherwise just go around it.
            MapLocation adjLoc = rc.getLocation().add(targetDir);
            boolean isWater = rc.canSenseLocation(adjLoc) && rc.senseMapInfo(adjLoc).isWater();
            if(isWater && targetDir != rc.getLocation().directionTo(target)){
            }
            else if(!rc.onTheMap(adjLoc)){
                boolean prevFollowDir = bugFollowRight;
                resetVisited();
                bugFollowRight = !prevFollowDir;
                return true;
            }
//            else if(rc.onTheMap(adjLoc) && rc.canSenseLocation(adjLoc) && rc.isLocationOccupied(adjLoc)){
//                return true;
//            }
            else if(!Util.tryMove(targetDir, waterFillingAllowed)) {
                // Can't move there.
            }
            else if(checkInVisited(adjLoc)){
                // Don't move to a visited location.
                if(worstCaseDir == null){
                    worstCaseDir = targetDir;
                }
            }
            else{
                chooseWallFollowingDirection(target, locBeforeMoving, targetDir, isWater);
                return true;
            }
            targetDir = bugFollowRight ? targetDir.rotateRight() : targetDir.rotateLeft();
        }
        // If no other direction works, then just go back in the direction you came from.
        if(worstCaseDir != null){
            Util.addToIndicatorString("WCD: " + worstCaseDir);
            MapLocation adjLoc = rc.getLocation().add(worstCaseDir);
            boolean isWater = rc.canSenseLocation(adjLoc) && rc.senseMapInfo(adjLoc).isWater();
            chooseWallFollowingDirection(target, locBeforeMoving, worstCaseDir, isWater);
            return true;
        }
        return false;
    }

    public boolean goToBug0(MapLocation target, int minCrumbsForNavigation) throws GameActionException {
        if(!rc.isMovementReady()){
            return false;
        }

        boolean waterFillingAllowed = rc.getCrumbs() >= minCrumbsForNavigation;

        if(!target.equals(prevTarget)){
            resetBug0(target, waterFillingAllowed);
        }

        if(rc.getRoundNum() > lastUpdatedRoundNum){
            updateBug0CurrLocation(robot.myLoc);
            lastUpdatedRoundNum = rc.getRoundNum();
        }

        // If I can move towards the target, do so.
        if(tryMovingCloserToGoal(target, waterFillingAllowed)){
            return true;
        }

        // Otherwise, follow the wall?
        return tryGoingAroundWall(target, waterFillingAllowed);
    }

}
