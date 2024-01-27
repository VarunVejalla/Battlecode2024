package suntzu_stun2_old;

import battlecode.common.*;

public class DamScout {
    RobotController rc;
    Robot robot;
    Comms comms;
    Navigation nav;

    // Dam scouting
    MapLocation centerLoc;
    boolean followRight;
    int[] distsToSpawnCenters;
    Direction adjDir = null;

    // Crumb collection
    int movingToCrumbStepCount = 0;
    MapLocation targetCrumbLoc;
    boolean targetCrumbLocIsRandom = false;
    MapLocation[] crumbQueue = new MapLocation[Constants.CRUMB_REMEMBER_COUNT];

    // Lining up
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

    //////////////////////////
    // DAM SCOUTING METHODS //
    //////////////////////////

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
        }
    }

    public Direction getDamAdjDir() throws GameActionException{
        // this method checks all the adjacent spots to the robot
        // if the robot can move in that direction and the spot is a dam, return that direction

        for(Direction dir : Navigation.movementDirections){
            MapLocation adjLoc = rc.adjacentLocation(dir);
            if(rc.canSenseLocation(adjLoc) && rc.senseMapInfo(adjLoc).isDam()){
                return dir;
            }
        }
        return null;
    }


    public void followDam() throws GameActionException {
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

    //////////////////////////////
    // CRUMB COLLECTION METHODS //
    //////////////////////////////

    public void processCrumbScan(MapLocation[] crumbScan) throws GameActionException {
        // Filter out existing crumb locations that are no longer valid.
        // Also keep track of which indices are empty (so that we can fill them in).
        int[] emptySpots = new int[Constants.CRUMB_REMEMBER_COUNT];
        int numEmpty = 0;
        MapInfo info;
        for(int i = 0; i < Constants.CRUMB_REMEMBER_COUNT; i++){
            if(crumbQueue[i] == null){
                emptySpots[numEmpty] = i;
                numEmpty++;
                continue;
            }
            if(!rc.canSenseLocation(crumbQueue[i])){
                continue;
            }
            info = rc.senseMapInfo(crumbQueue[i]);
            if(info.getCrumbs() == 0){
                emptySpots[numEmpty] = i;
                numEmpty++;
                crumbQueue[i] = null;
            }
        }

        for(MapLocation crumbLoc : crumbScan){
            // Crumb queue is full, can't add in any more locations.
            if(numEmpty == 0){
                break;
            }
            if(Util.checkIfItemInArray(crumbLoc, crumbQueue)){
                continue;
            }
            info = rc.senseMapInfo(crumbLoc);
            if(info.getTeamTerritory() != robot.myTeam){
                continue;
            }
            crumbQueue[numEmpty - 1] = crumbLoc;
            numEmpty--;
        }
    }

    public void setCrumbTargetLoc() {
        // Reset Target
        if (movingToCrumbStepCount >= Constants.CRUMB_GIVE_UP_STEPS) {
            targetCrumbLoc = null;
            targetCrumbLocIsRandom = false;
        }

        // Get a new targetLoc

        if (targetCrumbLoc == null || targetCrumbLocIsRandom) {
            // Get the closest crumb.
            MapLocation prevTargetCrumbLoc = targetCrumbLoc;
            int closestDist = Integer.MAX_VALUE;
            int targetCrumbIdx = -1;
            targetCrumbLoc = null;
            targetCrumbLocIsRandom = false;
            for(int i = 0; i < Constants.CRUMB_REMEMBER_COUNT; i++){
                if(crumbQueue[i] == null){
                    continue;
                }
                int dist = robot.myLoc.distanceSquaredTo(crumbQueue[i]);
                if(dist < closestDist){
                    closestDist = dist;
                    targetCrumbLoc = crumbQueue[i];
                    targetCrumbIdx = i;
                }
            }
            if(targetCrumbLoc != null){
                crumbQueue[targetCrumbIdx] = null;
                targetCrumbLocIsRandom = false;
                movingToCrumbStepCount = 0;
            }
            // If crumb queue is empty, go to random location.
            else if(prevTargetCrumbLoc == null){
                targetCrumbLoc = Util.getRandomLocation();
                targetCrumbLocIsRandom = true;
                movingToCrumbStepCount = 0;
            }
            else{
                // If we were previously using a random location, just stick to that.
                targetCrumbLoc = prevTargetCrumbLoc;
                targetCrumbLocIsRandom = true;
            }
        }
        // If there's a crumb much closer, go to that.
        else{
            int closestDist = Integer.MAX_VALUE;
            int closestIdx = -1;
            MapLocation closestLoc = null;
            for(int i = 0; i < Constants.CRUMB_REMEMBER_COUNT; i++){
                if(crumbQueue[i] == null){
                    continue;
                }
                int dist = robot.myLoc.distanceSquaredTo(crumbQueue[i]);
                if(dist < closestDist){
                    closestDist = dist;
                    closestLoc = crumbQueue[i];
                    closestIdx = i;
                }
            }

            if(closestLoc != null){
                closestDist = Util.minMovesToReach(robot.myLoc, closestLoc);
                int currTargetDist = Util.minMovesToReach(robot.myLoc, targetCrumbLoc);
                if(closestDist <= currTargetDist - Constants.CRUMB_SWITCH_DISTANCE_THRESHOLD){
                    // Keep the current targetCrumbLoc in the queue though.
                    crumbQueue[closestIdx] = targetCrumbLoc;
                    targetCrumbLoc = closestLoc;
                    targetCrumbLocIsRandom = false;
                    movingToCrumbStepCount = 0;
                }
            }
        }
    }

    ///////////////////////
    // LINING UP METHODS //
    ///////////////////////

    public boolean locationIsNextToDam(MapLocation loc) throws GameActionException {
        // this method checks if a location is next to a dam
        // it does this by checking if there is a dam in the location + direction
        // for each direction
        for(Direction dir : Navigation.movementDirections){
            MapLocation adjLoc = loc.add(dir);
            if(rc.canSenseLocation(adjLoc) && rc.senseMapInfo(adjLoc).isDam()){
                return true;
            }
        }
        return false;
    }


    public Direction getLineUpAdjDir() throws GameActionException{
        // this method checks if there is a dir that is adjacent to a bot already in the lineup
        for(Direction dir : Navigation.movementDirections){
            MapLocation adjLoc = rc.adjacentLocation(dir);
            if(rc.canSenseLocation(adjLoc)){
                RobotInfo bot = rc.senseRobotAtLocation(adjLoc);
                if(bot != null && locationIsNextToDam(bot.location)) {
                    return dir;
                }
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


    ////////////////////////
    // MAIN SCOUT METHODS //
    ////////////////////////

    public void runDamScouting() throws GameActionException {
        distsToSpawnCenters = comms.readDistsToSpawnCenters();

        // If you haven't visited the damn yet, go towards it.
        if(adjDir == null){
            nav.pathBF(centerLoc, 100); // go to the center of the map, cuz there's prolly a dam there
            // Check if I'm currently adjacent to the damn.
            adjDir = getDamAdjDir();
        }
        // Otherwise, follow it, either left or right.
        else{
            followDam();
        }

        scanForNearbyDamnLocation();
        comms.writeDistsToSpawnCenters(distsToSpawnCenters);
    }

    public void runCrumbGathering() throws GameActionException {
        movingToCrumbStepCount += 1;

        setCrumbTargetLoc();
        Util.addToIndicatorString("CL: " + targetCrumbLoc);
        // If we have a target

        nav.pathBF(targetCrumbLoc, 100);  // Unrolled bellow

        // Reset target location once reached
        robot.myLoc = rc.getLocation();
        if (robot.myLoc.equals(targetCrumbLoc)) {
            targetCrumbLoc = null;
            targetCrumbLocIsRandom = false;
        }
    }

    public void runLineUpMovement() throws GameActionException {
        switchToLineupModeIfNeeded();

        // if you're adjacent to a dam, don't do anything
        if (getDamAdjDir() != null) { // if we're adjacent to a dam, just stop
            return;
        }

        // first check if you can slide into the lineup. If so, yay ur done. good luck in the fight, u got it bro, don't die xd :D
        for(Direction dir : Navigation.movementDirections){
            if(rc.canMove(dir) && locationIsNextToDam(rc.getLocation().add(dir))){
                rc.move(dir);
                robot.myLoc = rc.getLocation();
                return;
            }
        }

        if(adjDir == null){
            nav.fuzzyNav.goTo(centerLoc, 100);
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

    public void runScout() throws GameActionException {
        processCrumbScan(robot.sensedNearbyCrumbs);
        int roundNum = rc.getRoundNum();
        // Dam scouting mode for the first 70 rounds and the last 40 setup rounds.
        if (roundNum <= Constants.NEW_FLAG_LOC_DECIDED_ROUND){
            runDamScouting();
        } else if(roundNum <= Constants.SCOUT_LINE_UP_DAM_ROUND) {
            // Otherwise in crumb gathering mode.
            runCrumbGathering();
        } else{
            // Otherwise in line up mode.
            runLineUpMovement();
        }
    }

}
