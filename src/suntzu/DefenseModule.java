package suntzu;

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
    MapLocation[] trapsList = new MapLocation[100];
//    boolean[][] trapsMap;
    int trapCount = 0;
    MapLocation[] allFlagDefaultLocs = new MapLocation[3];

    public DefenseModule(RobotController rc, Robot robot, Comms comms, Navigation nav) throws GameActionException {
        this.rc = rc;
        this.robot = robot;
        this.comms = comms;
        this.nav = nav;
//        trapsMap = new boolean[rc.getMapWidth()][rc.getMapHeight()];
    }

    // Helper methods to manage trap count.
//    public void updateTrapInfo(MapInfo info){
//        MapLocation infoLoc = info.getMapLocation();
//        if(info.getTrapType() == TrapType.NONE){ // If there's actually no trap at that location.
//            if(trapsMap[infoLoc.x][infoLoc.y]){
//                // We think there's a trap there, but there really isn't one.
//                trapsMap[infoLoc.x][infoLoc.y] = false;
//                for(int i = 0; i < trapsList.length; i++){
//                    if(infoLoc.equals(trapsList[i])){
//                        trapsList[i] = null;
//                        trapCount -= 1;
//                        return;
//                    }
//                }
//            }
//        }
//        else{ // If there actually is a trap at that location.
//            if(!trapsMap[infoLoc.x][infoLoc.y]){
//                // We think there's no trap but there actually is one.
//                for(int i = 0; i < trapsList.length; i++){
//                    if(trapsList[i] == null){
//                        trapsMap[infoLoc.x][infoLoc.y] = true;
//                        trapsList[i] = infoLoc;
//                        trapCount += 1;
//                        return;
//                    }
//                }
//            }
//        }
//    }

    public void updateTrapCountValue() throws GameActionException {
//        for(MapInfo info : robot.sensedNearbyMapInfos){
//            MapLocation infoLoc = info.getMapLocation();
//            // We alr know about this trap.
//            if(trapsMap[infoLoc.x][infoLoc.y] && info.getTrapType() != TrapType.NONE){
//                continue;
//            }
//
//            // We alr know that there's no trap.
//            if(!trapsMap[infoLoc.x][infoLoc.y] && info.getTrapType() == TrapType.NONE){
//                continue;
//            }
//
//            // Check if our flag is closest to the location compared to other flags.
//            int ourFlagDist = flagDefaultLoc.distanceSquaredTo(info.getMapLocation());
//            int minDist = Math.min(Math.min(allFlagDefaultLocs[0].distanceSquaredTo(info.getMapLocation()),
//                    allFlagDefaultLocs[1].distanceSquaredTo(info.getMapLocation())),
//                    allFlagDefaultLocs[2].distanceSquaredTo(info.getMapLocation()));
//
//            if(minDist != ourFlagDist){
//                continue;
//            }
//
//            // Only count the location if it's closest to us.
//            updateTrapInfo(info);
//        }
        for(int i = 0; i < trapsList.length; i++){
            if(trapsList[i] == null){
                continue;
            }
            MapLocation infoLoc = trapsList[i];
            if(!rc.canSenseLocation(infoLoc)){
                continue;
            }
            MapInfo info = rc.senseMapInfo(infoLoc);
            // Check if trap still exists.
            if(info.getTrapType() == TrapType.NONE){
                trapsList[i] = null;
                trapCount -= 1;
            }
        }

        // Check if trap placement target is still available.
        if(trapPlacementTarget != null && rc.canSenseLocation(trapPlacementTarget)){
            if(rc.senseMapInfo(trapPlacementTarget).getTrapType() != TrapType.NONE){
                trapPlacementTarget = null;
                trapPlacementHeuristic = Integer.MAX_VALUE;
            }
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

    public void updateBestTrapPlacementTarget(){
        int bestHeuristic = trapPlacementHeuristic;
        MapLocation bestTrapLoc = trapPlacementTarget;
        Direction flagToCenter = flagDefaultLoc.directionTo(robot.centerLoc);
        for(MapInfo info : robot.sensedNearbyMapInfos){
            MapLocation infoLoc = info.getMapLocation();
            if(info.getTrapType() != TrapType.NONE){
                continue;
            }
            if(!info.isPassable()){
                continue;
            }
            if(infoLoc.equals(flagDefaultLoc)){
                continue;
            }
            int heuristic = infoLoc.distanceSquaredTo(flagDefaultLoc) * 10;
            Direction flagToSpot = flagDefaultLoc.directionTo(info.getMapLocation());
            heuristic += Util.directionDistance(flagToSpot, flagToCenter) * 10;
            if(heuristic < bestHeuristic){
                bestHeuristic = heuristic;
                bestTrapLoc = info.getMapLocation();
            }
        }

        trapPlacementTarget = bestTrapLoc;
        trapPlacementHeuristic = bestHeuristic;
    }

    // Movement methods
    public void placeTrapsAroundFlag() throws GameActionException {
        updateBestTrapPlacementTarget();
        Util.logBytecode("After updating TPT");
        Util.addToIndicatorString("TPT: " + trapPlacementTarget);
        Util.addToIndicatorString("TPTH: " + trapPlacementHeuristic);

        // If you don't have enough crumbs for a trap, just circle.
        int numHomies = getNumHomiesWithLowerTrapCount();
        Util.logBytecode("After computing # of homies");
        int minCrumbsNeeded = numHomies * TrapType.EXPLOSIVE.buildCost + TrapType.EXPLOSIVE.buildCost;
        if((trapPlacementTarget == null) || rc.getCrumbs() < minCrumbsNeeded){
            Util.addToIndicatorString("CRC: " + flagDefaultLoc);
            nav.circle(flagDefaultLoc, 2, 5, 0);
            Util.logBytecode("After circling location");
        }
        else if(!rc.canBuild(TrapType.EXPLOSIVE, trapPlacementTarget)){
            nav.pathBF(trapPlacementTarget, 0);
            Util.logBytecode("After going towards TPT");
        }
        else{
            rc.build(TrapType.EXPLOSIVE, trapPlacementTarget);
            for(int i = 0; i < trapsList.length; i++){
                if(trapsList[i] == null){
                    trapsList[i] = trapPlacementTarget;
                    trapCount += 1;
                    break;
                }
            }
            trapPlacementTarget = null;
            trapPlacementHeuristic = Integer.MAX_VALUE;
        }
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
        Util.logBytecode("Start of stationary defense method");
        boolean targetChanged = checkSharedDefensiveTargetStillValid();
        Util.logBytecode("After checking defensive target still valid");
        targetChanged |= updateSharedDefensiveTarget();
        Util.logBytecode("After updating defensive target");
        if(targetChanged){
            comms.writeSharedDefensiveTarget(sharedDefensiveTarget);
        }
        updateTrapCountValue();
        Util.logBytecode("After updating trap count");
        comms.writeNumTrapsForFlag(defendingFlagIdx, trapCount);
        placeTrapsAroundFlag();
        Util.logBytecode("After placing traps around flag");
    }

    public void runMobileDefense() throws GameActionException {
        Util.logBytecode("Beginning of mobile defense");
        allFlagDefaultLocs[0] = comms.getDefaultHomeFlagLoc(0);
        allFlagDefaultLocs[1] = comms.getDefaultHomeFlagLoc(1);
        allFlagDefaultLocs[2] = comms.getDefaultHomeFlagLoc(2);
        flagDefaultLoc = comms.getDefaultHomeFlagLoc(defendingFlagIdx);

        boolean targetChanged = checkSharedDefensiveTargetStillValid();
        Util.logBytecode("Checked if defensive target still valid");
        targetChanged |= updateSharedDefensiveTarget();
        Util.logBytecode("Updated shared defensive target");
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
