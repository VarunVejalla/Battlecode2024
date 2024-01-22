package hannibal;

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

        if(!rc.onTheMap(robot.myLoc.add(adjDir))){
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
            Util.log("Closest crumb target loc: " + targetCrumbLoc);
            if(targetCrumbLoc != null){
                crumbQueue[targetCrumbIdx] = null;
                targetCrumbLocIsRandom = false;
                movingToCrumbStepCount = 0;
            }
            // If crumb queue is empty, go to random location.
            else if(prevTargetCrumbLoc == null){
                targetCrumbLoc = Util.getRandomLocation();
                targetCrumbLocIsRandom = true;
                Util.log("Choosing random location: " + targetCrumbLoc);
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
    // MAIN SCOUT METHOD //
    ///////////////////////

    public void runScout() throws GameActionException {
        processCrumbScan(robot.sensedNearbyCrumbs);

        int roundNum = rc.getRoundNum();

        // Dam scouting mode for the first 70 rounds and the last 40 setup rounds.
        if (roundNum <= Constants.NEW_FLAG_LOC_DECIDED_ROUND || roundNum >= Constants.SCOUT_LINE_UP_DAM_ROUND ) {
            distsToSpawnCenters = comms.readDistsToSpawnCenters();

            // If you haven't visited the damn yet, go towards it.
            if(adjDir == null){
                nav.pathBF(centerLoc, 100);
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

        // Otherwise in crumb gathering mode.
        else{
            adjDir = null;
            movingToCrumbStepCount += 1;

            setCrumbTargetLoc();
            Util.addToIndicatorString("CL: " + targetCrumbLoc);
            // If we have a target
//            boolean isDam = true;
//            robot.myLoc = rc.getLocation();

//            while (isDam) {
//                isDam = false;
//                Direction togo = robot.myLoc.directionTo(targetCrumbLoc);
//                MapLocation adjToCrumbDirection = rc.adjacentLocation(togo);
//                if (rc.canSenseLocation(adjToCrumbDirection) && rc.senseMapInfo(adjToCrumbDirection).isDam()) {
//                    // Get new targetLoc if Dam is encountered
//                    targetCrumbLoc = null;
//                    targetCrumbLocIsRandom = false;
//                    setCrumbTargetLoc();
//                    isDam = true;
//                }
//            }

            nav.pathBF(targetCrumbLoc, 0);  // Unrolled bellow

            // Reset target location once reached
            robot.myLoc = rc.getLocation();
            if (robot.myLoc.equals(targetCrumbLoc)) {
                targetCrumbLoc = null;
                targetCrumbLocIsRandom = false;
            }
        }
    }

}
