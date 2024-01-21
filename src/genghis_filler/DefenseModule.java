package genghis_filler;

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
    int mobileDefendingFlagIdx = -1;
    MapLocation trapPlacementTarget = null;
    int trapPlacementHeuristic = Integer.MAX_VALUE;
    MapLocation[] trapsList = new MapLocation[31];
    boolean[][] trapsMap;
    int trapCount = 0;
    MapLocation[] allFlagDefaultLocs = new MapLocation[3];

    public DefenseModule(RobotController rc, Robot robot, Comms comms, Navigation nav) throws GameActionException {
        this.rc = rc;
        this.robot = robot;
        this.comms = comms;
        this.nav = nav;
        trapsMap = new boolean[rc.getMapWidth()][rc.getMapHeight()];
    }

    // Helper methods to manage trap count.

    public void updateTrapInfo(MapInfo info){
        MapLocation infoLoc = info.getMapLocation();
        if(info.getTrapType() == TrapType.NONE){ // If there's actually no trap at that location.
            if(trapsMap[infoLoc.x][infoLoc.y]){
                // We think there's a trap there, but there really isn't one.
                trapsMap[infoLoc.x][infoLoc.y] = false;
                for(int i = 0; i < trapsList.length; i++){
                    if(infoLoc.equals(trapsList[i])){
                        trapsList[i] = null;
                        trapCount -= 1;
                        return;
                    }
                }
            }
        }
        else{ // If there actually is a trap at that location.
            if(!trapsMap[infoLoc.x][infoLoc.y]){
                // We think there's no trap but there actually is one.
                for(int i = 0; i < trapsList.length; i++){
                    if(trapsList[i] == null){
                        trapsMap[infoLoc.x][infoLoc.y] = true;
                        trapsList[i] = infoLoc;
                        trapCount += 1;
                        return;
                    }
                }
            }
        }
    }

    public void updateTrapCountValue() throws GameActionException {
        for(MapInfo info : robot.sensedNearbyMapInfos){
            MapLocation infoLoc = info.getMapLocation();
            // We alr know about this trap.
            if(trapsMap[infoLoc.x][infoLoc.y] && info.getTrapType() != TrapType.NONE){
                continue;
            }

            // We alr know that there's no trap.
            if(!trapsMap[infoLoc.x][infoLoc.y] && info.getTrapType() == TrapType.NONE){
                continue;
            }

            // Check if our flag is closest to the location compared to other flags.
            int ourFlagDist = flagDefaultLoc.distanceSquaredTo(info.getMapLocation());
            int minDist = Math.min(Math.min(allFlagDefaultLocs[0].distanceSquaredTo(info.getMapLocation()),
                    allFlagDefaultLocs[1].distanceSquaredTo(info.getMapLocation())),
                    allFlagDefaultLocs[2].distanceSquaredTo(info.getMapLocation()));

            if(minDist != ourFlagDist){
                continue;
            }

            // Only count the location if it's closest to us.
            updateTrapInfo(info);
        }
    }

    public boolean checkIfLowestTrapCount() throws GameActionException {
        for(int i = 0; i < 3; i++){
            if(i == defendingFlagIdx){
                continue;
            }
            if(comms.getHomeFlagTakenStatus(i)){
                continue;
            }
            if(comms.readNumTrapsForFlag(i) < trapCount){
                return false;
            }
        }
        return true;
    }

    // Spawning / setup methods

    public void spawnStationary() throws GameActionException {
        if(defendingFlagIdx == -1){
            Util.log("SETUP NOT YET CALLED??");
            rc.resign();
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
            Util.log("SETUP NOT YET CALLED??");
            rc.resign();
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

    // TODO: Make this more bytecode efficient.
    public void updateBestTrapPlacementTarget(){
        int bestHeuristic = trapPlacementHeuristic;
        MapLocation bestTrapLoc = trapPlacementTarget;
        Direction flagToCenter = flagDefaultLoc.directionTo(robot.centerLoc);
        for(MapInfo info : robot.sensedNearbyMapInfos){
            if(info.getTrapType() != TrapType.NONE){
                continue;
            }
            if(info.isWater() || info.isWall() || info.isDam() || !info.isPassable()){
                continue;
            }
            if(info.getMapLocation().equals(flagDefaultLoc)){
                continue;
            }
            if(Util.checkLocsSameLattice(info.getMapLocation(), flagDefaultLoc)){ // This is to only place traps on lattice.
                continue;
            }
            int heuristic = info.getMapLocation().distanceSquaredTo(flagDefaultLoc) * 10;
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
        Util.addToIndicatorString("TPT: " + trapPlacementTarget);
        Util.addToIndicatorString("TPTH: " + trapPlacementHeuristic);
        TrapType targetTrapType = null;
        if(trapPlacementTarget != null){
//            targetTrapType = Util.checkLocsSameLattice(flagDefaultLoc, trapPlacementTarget) ? TrapType.EXPLOSIVE : TrapType.STUN;
            targetTrapType = TrapType.STUN;
        }

        // If you don't have enough crumbs for a trap, just circle.
        boolean isOurTurnToTrap = checkIfLowestTrapCount();
        if((trapPlacementTarget == null) || (rc.getCrumbs() < targetTrapType.buildCost) || !isOurTurnToTrap){
            Util.addToIndicatorString("CRC: " + flagDefaultLoc);
            nav.circle(flagDefaultLoc, 2, 5);
        }
        else if(!rc.canBuild(targetTrapType, trapPlacementTarget)){
            nav.goToBug(trapPlacementTarget, 0);
            return;
        }
        else{
            rc.build(targetTrapType, trapPlacementTarget);
            trapPlacementTarget = null;
            trapPlacementHeuristic = Integer.MAX_VALUE;
        }
    }

    // TODO: This method takes up so much ducking bytecode T_T.
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

        assert(defendingFlagIdx != -1);
        Util.addToIndicatorString("FL: " + flagDefaultLoc);
        flagDefaultLoc = comms.getDefaultHomeFlagLoc(defendingFlagIdx);
        boolean targetChanged = checkSharedDefensiveTargetStillValid();
        targetChanged |= updateSharedDefensiveTarget();
        if(targetChanged){
            comms.writeSharedDefensiveTarget(sharedDefensiveTarget);
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
            Util.addToIndicatorString("MDFI" + mobileDefendingFlagIdx);
            nav.mode = NavigationMode.FUZZYNAV;
            nav.goTo(sharedDefensiveTarget, 0);
        }
        else if(comms.getHomeFlagTakenStatus(defendingFlagIdx) == false){ // If our home flag is still there, circle that.
            Util.addToIndicatorString("FL");
            nav.circle(flagDefaultLoc, 2, 5);
        }
        else if(comms.getHomeFlagTakenStatus(0) == false){ // Otherwise check if flag Idx 0 is still there, and circle that.
            Util.addToIndicatorString("F0");
            nav.circle(allFlagDefaultLocs[0], 2, 5);
        }
        else if(comms.getHomeFlagTakenStatus(1) == false){ // Otherwise check if flag Idx 1 is still there, and circle that.
            Util.addToIndicatorString("F1");
            nav.circle(allFlagDefaultLocs[1], 2, 5);
        }
        else if(comms.getHomeFlagTakenStatus(2) == false){ // Otherwise check if flag Idx 2 is still there, and circle that.
            Util.addToIndicatorString("F2");
            nav.circle(allFlagDefaultLocs[2], 2, 5);
        }
        else if(robot.offenseModule.sharedOffensiveTarget != null){ // Otherwise default to offense? Idk wtf to do here T_T.
            Util.log("RUNNING OFFENSE AS A DEFENDER CUZ ALL FLAGS ARE TAKEN T_T");
            Util.addToIndicatorString("OF");
            nav.goTo(robot.offenseModule.sharedOffensiveTarget, 0);
        }
    }

    // Strategy methods
    final int HOME_DANGER_ENTER_THRESHOLD = 3;
    final int HOME_DANGER_EXIT_THRESHOLD = 1;
    final int HOME_DANGER_SWITCH_THRESHOLD = 3;
    public boolean checkSharedDefensiveTargetStillValid() throws GameActionException {
        if(sharedDefensiveTarget == null){
            sharedDefensiveTargetPriority = Integer.MAX_VALUE;
            mobileDefendingFlagIdx = -1;
            return false;
        }

        for (MapLocation loc : robot.knownTakenAllyFlags) {
            if (loc != null && loc.distanceSquaredTo(sharedDefensiveTarget) <= 2) {
                sharedDefensiveTargetPriority = 1;
                mobileDefendingFlagIdx = -1;
                return false;
            }
        }

        // Check if base is still under attack.
        if(Util.checkIfItemInArray(sharedDefensiveTarget, allFlagDefaultLocs)){
            int flagIdx = Util.getItemIndexInArray(sharedDefensiveTarget, allFlagDefaultLocs);
            if(comms.getEnemyCountNearFlagPrevRound(flagIdx) > HOME_DANGER_EXIT_THRESHOLD){
                sharedDefensiveTargetPriority = 2;
                mobileDefendingFlagIdx = Util.getItemIndexInArray(sharedDefensiveTarget, allFlagDefaultLocs);
                return false;
            }
        }

        Util.log("Resetting shared defensive target to null " + sharedDefensiveTarget.toString());
        Util.logArray("KTA: ", robot.knownTakenAllyFlags);
        sharedDefensiveTarget = null;
        sharedDefensiveTargetPriority = Integer.MAX_VALUE;
        mobileDefendingFlagIdx = -1;
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

        if(bestPriority >= 2){
            int[] enemyCounts = comms.getEnemyCountsNearFlagsPrevRound();
            int mostInDangerFlagIdx = Util.maxIndexInArray(enemyCounts);
            if(enemyCounts[mostInDangerFlagIdx] >= HOME_DANGER_ENTER_THRESHOLD){
                if(mobileDefendingFlagIdx == -1 || enemyCounts[mobileDefendingFlagIdx] - enemyCounts[mostInDangerFlagIdx] >= HOME_DANGER_SWITCH_THRESHOLD){
                    bestDefensiveTargetLoc = allFlagDefaultLocs[mostInDangerFlagIdx];
                    bestPriority = 2;
                }
            }
        }

        if(bestPriority < sharedDefensiveTargetPriority){
            sharedDefensiveTarget = bestDefensiveTargetLoc;
            sharedDefensiveTargetPriority = bestPriority;
            if(sharedDefensiveTargetPriority == 2){
                mobileDefendingFlagIdx = Util.getItemIndexInArray(sharedDefensiveTarget, allFlagDefaultLocs);
            }
            return true;
        }
        return false;
    }


}
