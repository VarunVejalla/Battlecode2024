package suntzu_stun2;

import battlecode.common.*;

public class DefenseModule {

    RobotController rc;
    Robot robot;
    Comms comms;
    Navigation nav;
    int defendingFlagIdx = -1;
    MapLocation flagDefaultLoc;
    MapLocation sharedDefensiveTarget;
    int sharedDefensiveTargetPriority = Integer.MAX_VALUE;
    MapLocation trapPlacementTarget = null;
    int trapPlacementHeuristic = Integer.MAX_VALUE;
    MapLocation[] allFlagDefaultLocs = new MapLocation[3];
    final int NUM_TRAPS_TO_KEEP_TRACK_OF = 200;
    PriorityQueue trapPQ = null;
    int[][] heuristicMap = null;
    byte[][] trapsMap = null; // 0 means nothing, 1 means I placed a trap, 2 means someone else placed a trap, 3 means its currently in the trap PQ.
    int trapCount = 0;
    boolean initializedPotTrapsArray = false;
    MapLocation nearestCornerToFlag = null;

    public DefenseModule(RobotController rc, Robot robot, Comms comms, Navigation nav) throws GameActionException {
        this.rc = rc;
        this.robot = robot;
        this.comms = comms;
        this.nav = nav;
        // NOTE: Changed these to be initialized on different round nums cuz initialization takes so much mf bytecode.
//        trapsMap = new byte[rc.getMapWidth()][rc.getMapHeight()];
//        heuristicMap = new int[rc.getMapWidth()][rc.getMapHeight()];
//        trapPQ = new PriorityQueue(NUM_TRAPS_TO_KEEP_TRACK_OF);
    }

    // Helper methods to manage trap count.
    public void updateTrapCountValue() throws GameActionException {
        MapInfo info;
        int x, y;
        for (int i = robot.sensedNearbyMapInfos.length; --i >= 0; ) {
            info = robot.sensedNearbyMapInfos[i];
            x = info.getMapLocation().x;
            y = info.getMapLocation().y;

            // If trap removed.
            if(info.getTrapType() != TrapType.NONE){ // If trap sensed.
                if(trapsMap[x][y] == 0 || trapsMap[x][y] == 3){ // If we've never interacted with this location, or its in the queue.
                    trapsMap[x][y] = 2; // Mark it down as someone placed a trap there.
                }
            }
            else{
                if(trapsMap[x][y] == 1){ // If I placed the trap, then decrement count, and add it back in the queue.
                    assert(heuristicMap[x][y] != 0);
                    trapCount--;
                    trapsMap[x][y] = 3;
                    trapPQ.insert(heuristicMap[x][y], info.getMapLocation());
                }
            }
        }
    }

    public void updatePotTrapLocs(MapLocation trapPacedLoc) throws GameActionException {
        MapInfo info;
        for(Direction dir : Navigation.movementDirections){
            MapLocation adjLoc = trapPacedLoc.add(dir);
            if(!rc.onTheMap(adjLoc)){
                continue;
            }
            if(trapsMap[adjLoc.x][adjLoc.y] == 1 || trapsMap[adjLoc.x][adjLoc.y] == 2){ // A trap has already been placed here by someone.
                continue;
            }
            if(trapsMap[adjLoc.x][adjLoc.y] == 3){ // Already in PQ.
                continue;
            }
            if(heuristicMap[adjLoc.x][adjLoc.y] == -2){ // This location is bad (not passable or some shit like that).
                continue;
            }
            Util.assert_wrapper(rc.canSenseLocation(adjLoc));
            info = rc.senseMapInfo(adjLoc);
            if(!info.isPassable() && !info.isWater()){
                heuristicMap[adjLoc.x][adjLoc.y] = -2;
                continue;
            }
            else if(info.getTrapType() != TrapType.NONE){
                int heuristic = getTrapHeuristic(adjLoc);
                heuristicMap[adjLoc.x][adjLoc.y] = heuristic;
                trapsMap[adjLoc.x][adjLoc.y] = 2; // Someone else must've placed a trap there.
            }
            else{
                // TODO: make this dynamic and factor in how many traps offense needs at the moment
                int heuristic = getTrapHeuristic(adjLoc);
                heuristicMap[adjLoc.x][adjLoc.y] = heuristic;
                trapPQ.insert(heuristic, adjLoc);
                trapsMap[adjLoc.x][adjLoc.y] = 3;
            }

        }
    }

    public int getTrapHeuristic(MapLocation trapLoc){
//        if(nearestCornerToFlag == null){
//            nearestCornerToFlag = Util.getNearestCorner(flagDefaultLoc);
//        }
        Direction flagToCenter = flagDefaultLoc.directionTo(robot.centerLoc);
        int heuristic = trapLoc.distanceSquaredTo(flagDefaultLoc) * 10;
        Direction flagToSpot = flagDefaultLoc.directionTo(trapLoc);
        heuristic += Util.directionDistance(flagToSpot, flagToCenter) * 10;
        return heuristic;
    }

    public void updateBestTrapPlacementTarget(){
        MapLocation bestLoc = trapPQ.mapLocs[0];
        while(trapPQ.size >= 0 && trapsMap[bestLoc.x][bestLoc.y] != 3){ // If trap has bene placed there, remove it from the queue.
            trapPQ.extractMin();
            bestLoc = trapPQ.mapLocs[0];
        }

        // Reset trapPlacementTarget if needed.
        if(trapPlacementTarget != null && trapsMap[trapPlacementTarget.x][trapPlacementTarget.y] != 3){
            trapPlacementTarget = null;
            trapPlacementHeuristic = Integer.MAX_VALUE;
        }

        // Check if the PQ has any better spot.
        if(trapPQ.peekPriority() >= trapPlacementHeuristic){
            return;
        }

        // Insert the current target back in.
        if(trapPlacementTarget != null){
            trapPQ.insert(trapPlacementHeuristic, trapPlacementTarget);
        }

        // Get the lowest heuristic location as the new target.
        trapPlacementHeuristic = trapPQ.peekPriority();
        trapPlacementTarget = trapPQ.extractMin();
    }

    // Movement methods
    public void placeTrapsAroundFlag() throws GameActionException {
        updateBestTrapPlacementTarget();
        Util.addToIndicatorString("TPT: " + trapPlacementTarget);
        Util.addToIndicatorString("TPTH: " + trapPlacementHeuristic);
        Util.addToIndicatorString("TC: " + trapCount);

        // If you don't have enough crumbs for a trap, just circle.
        int numHomies = getNumHomiesWithLowerTrapCount();
        int minCrumbsNeeded = numHomies * TrapType.EXPLOSIVE.buildCost + TrapType.EXPLOSIVE.buildCost;
        if(trapCount > 10){
            // TODO: Change this from a constant 10 to some dynamic commed value.
            minCrumbsNeeded += 10 * TrapType.STUN.buildCost; // Leave some room for offensive guys to make traps
        }
        if(trapPlacementTarget == null || rc.getCrumbs() < minCrumbsNeeded){
            Util.addToIndicatorString("CRC: " + flagDefaultLoc);
            nav.circle(flagDefaultLoc, 2, 5, 0);
        }
        else if(rc.canSenseLocation(trapPlacementTarget) && rc.canFill(trapPlacementTarget)){
            rc.fill(trapPlacementTarget);
        }
        else if(!rc.canBuild(TrapType.EXPLOSIVE, trapPlacementTarget)){
            nav.pathBF(trapPlacementTarget, 0);
        }
        else{
            rc.build(TrapType.EXPLOSIVE, trapPlacementTarget);
            trapsMap[trapPlacementTarget.x][trapPlacementTarget.y] = 1; // I placed a trap there.
            trapCount++;
            updatePotTrapLocs(trapPlacementTarget);
            trapPlacementTarget = null;
            trapPlacementHeuristic = Integer.MAX_VALUE;
        }
    }

    public int getNumHomiesWithLowerTrapCount() throws GameActionException {
        int numHomies = 0;
        for(int i = 0; i < 3; i++){
            if(i == defendingFlagIdx){
                continue;
            }
            if(comms.getHomeFlagTakenStatus(i)){
                continue;
            }
            int otherFlagTraps = comms.readNumTrapsForFlag(i);
            if(otherFlagTraps != Constants.MAX_NUM_OF_TRAPS_COMMABLE && otherFlagTraps < trapCount){
                numHomies += 1;
            }
        }
        return numHomies;
    }

    // Spawning / setup methods

    public void spawnStationary() throws GameActionException {
        if(defendingFlagIdx == -1){
            System.out.println("SETUP NOT YET CALLED??");
            Util.resign();
        }
        flagDefaultLoc = comms.getDefaultHomeFlagLoc(defendingFlagIdx);
        MapLocation[] spawnLocs = rc.getAllySpawnLocations();
        int bestIdx = -1;
        int bestDist = Integer.MAX_VALUE;
        for(int i = 0; i < spawnLocs.length; i++){
            if(!rc.canSpawn(spawnLocs[i])){
                continue;
            }

            int dist = Util.minMovesToReach(flagDefaultLoc, spawnLocs[i]);
            if(dist < bestDist){
                bestDist = dist;
                bestIdx = i;
            }
        }
        if(bestIdx != -1){
            rc.spawn(spawnLocs[bestIdx]);
        }
    }

    public void spawnMobile() throws GameActionException {
        if(defendingFlagIdx == -1){
            System.out.println("SETUP NOT YET CALLED??");
            Util.resign();
        }
        if(sharedDefensiveTarget == null){
            spawnStationary();
        }
        MapLocation[] spawnLocs = rc.getAllySpawnLocations();
        int bestIdx = -1;
        int bestDist = Integer.MAX_VALUE;
        for(int i = 0; i < spawnLocs.length; i++){
            if(!rc.canSpawn(spawnLocs[i])){
                continue;
            }

            int dist = Util.minMovesToReach(sharedDefensiveTarget, spawnLocs[i]);
            if(dist < bestDist){
                bestDist = dist;
                bestIdx = i;
            }
        }
        if(bestIdx != -1){
            rc.spawn(spawnLocs[bestIdx]);
        }
    }

    public void setup() throws GameActionException {
        int flag0_defenders = comms.readNumDefendersForFlag(0);
        int flag1_defenders = comms.readNumDefendersForFlag(1);
        int flag2_defenders = comms.readNumDefendersForFlag(2);

        if(flag0_defenders <= flag1_defenders && flag0_defenders <= flag2_defenders){
            defendingFlagIdx = 0;
        }
        else if(flag1_defenders <= flag0_defenders && flag1_defenders <= flag2_defenders){
            defendingFlagIdx = 1;
        }
        else if(flag2_defenders <= flag0_defenders && flag2_defenders <= flag1_defenders){
            defendingFlagIdx = 2;
        }
        flagDefaultLoc = comms.getDefaultHomeFlagLoc(defendingFlagIdx);
        comms.incrementNumDefendersForFlag(defendingFlagIdx);
    }

    public void runStationaryDefense() throws GameActionException {
        // If your flag was taken, run the mobile defense code.
        if(comms.getHomeFlagTakenStatus(defendingFlagIdx)){
            Util.addToIndicatorString("RMD");
            runMobileDefense();
            return;
        }

        allFlagDefaultLocs[0] = comms.getDefaultHomeFlagLoc(0);
        allFlagDefaultLocs[1] = comms.getDefaultHomeFlagLoc(1);
        allFlagDefaultLocs[2] = comms.getDefaultHomeFlagLoc(2);

        Util.assert_wrapper(defendingFlagIdx != -1);
        flagDefaultLoc = comms.getDefaultHomeFlagLoc(defendingFlagIdx);
        Util.addToIndicatorString("FL: " + flagDefaultLoc);
        boolean targetChanged = checkSharedDefensiveTargetStillValid();
        targetChanged |= updateSharedDefensiveTarget();
        if(targetChanged){
            comms.writeSharedDefensiveTarget(sharedDefensiveTarget);
        }

        if(!initializedPotTrapsArray){
            updatePotTrapLocs(flagDefaultLoc);
            initializedPotTrapsArray = true;
        }
        updateTrapCountValue();
        comms.writeNumTrapsForFlag(defendingFlagIdx, trapCount);
        placeTrapsAroundFlag();
    }

    public void runMobileDefense() throws GameActionException {
        allFlagDefaultLocs[0] = comms.getDefaultHomeFlagLoc(0);
        allFlagDefaultLocs[1] = comms.getDefaultHomeFlagLoc(1);
        allFlagDefaultLocs[2] = comms.getDefaultHomeFlagLoc(2);
        flagDefaultLoc = comms.getDefaultHomeFlagLoc(defendingFlagIdx);

        boolean targetChanged = checkSharedDefensiveTargetStillValid();
        targetChanged |= updateSharedDefensiveTarget();
        if(targetChanged){
            comms.writeSharedDefensiveTarget(sharedDefensiveTarget);
        }
        if(sharedDefensiveTarget != null){
            Util.addToIndicatorString("SDT:" + sharedDefensiveTarget);
            Util.addToIndicatorString("SDTP: " + sharedDefensiveTargetPriority);
            nav.pathBF(sharedDefensiveTarget, 0);
        }
        else if(comms.getHomeFlagTakenStatus(defendingFlagIdx) == false){ // If our home flag is still there, circle that.
            Util.addToIndicatorString("FL");
            nav.circle(flagDefaultLoc, 2, 5, 0);
        }
        else if(comms.getHomeFlagTakenStatus(0) == false){ // Otherwise check if flag Idx 0 is still there, and circle that.
            Util.addToIndicatorString("F0");
            nav.circle(allFlagDefaultLocs[0], 2, 5, 0);
        }
        else if(comms.getHomeFlagTakenStatus(1) == false){ // Otherwise check if flag Idx 1 is still there, and circle that.
            Util.addToIndicatorString("F1");
            nav.circle(allFlagDefaultLocs[1], 2, 5, 0);
        }
        else if(comms.getHomeFlagTakenStatus(2) == false){ // Otherwise check if flag Idx 2 is still there, and circle that.
            Util.addToIndicatorString("F2");
            nav.circle(allFlagDefaultLocs[2], 2, 5, 0);
        }
        else if(robot.offenseModule.sharedOffensiveTarget != null){ // Otherwise default to offense? Idk wtf to do here T_T.
            Util.log("RUNNING OFFENSE AS A DEFENDER CUZ ALL FLAGS ARE TAKEN T_T");
            Util.addToIndicatorString("OF");
            nav.pathBF(robot.offenseModule.sharedOffensiveTarget, 100);
        }
    }

    // Strategy methods
//    public int getDefensiveTargetPriority(MapLocation defensiveTarget){
//        // if there is a spotted captured flag, that's a priority of 1.
//        if(Util.checkIfItemInArray(defensiveTarget, robot.knownCarriedAllyFlags)){
//            return 1;
//        }
//
//        // if there is a known dropped flag that is not the current target, that's a priority of 2.
//        if(Util.checkIfItemInArray(defensiveTarget, robot.knownCarriedAllyFlags)){
//            return 2;
//        }
//
//        return Integer.MAX_VALUE;
//    }

    public boolean checkSharedDefensiveTargetStillValid() throws GameActionException {
        if(sharedDefensiveTarget == null){
            sharedDefensiveTargetPriority = Integer.MAX_VALUE;
            return false;
        }

        for (MapLocation loc : robot.knownTakenAllyFlags) {
            if (loc != null && loc.distanceSquaredTo(sharedDefensiveTarget) <= 2) {
                sharedDefensiveTargetPriority = 1;
                return false;
            }
        }

        Util.log("Resetting shared defensive target to null " + sharedDefensiveTarget.toString());
        Util.logArray("KTA: ", robot.knownTakenAllyFlags);
        sharedDefensiveTarget = null;
        sharedDefensiveTargetPriority = Integer.MAX_VALUE;
        return true;
    }

    // TODO: Comm when enemies are nearby a flag.
    public boolean updateSharedDefensiveTarget() throws GameActionException {
        // if there is a spotted captured flag, go to that
        MapLocation bestDefensiveTargetLoc = null;
        int bestPriority = Integer.MAX_VALUE;
        for (MapLocation loc : robot.knownTakenAllyFlags) {
            if (loc != null) {
                bestDefensiveTargetLoc = loc;
                bestPriority = 1;
                break;
            }
        }

        if(bestPriority < sharedDefensiveTargetPriority){
            sharedDefensiveTarget = bestDefensiveTargetLoc;
            sharedDefensiveTargetPriority = bestPriority;
            return true;
        }
        return false;
    }


}
