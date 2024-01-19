package genghis;

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

    public DefenseModule(RobotController rc, Robot robot, Comms comms, Navigation nav) throws GameActionException {
        this.rc = rc;
        this.robot = robot;
        this.comms = comms;
        this.nav = nav;
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

    public void updateBestTrapPlacementTarget(){
        MapInfo[] nearbyInfos = rc.senseNearbyMapInfos();
        int bestHeuristic = trapPlacementHeuristic;
        MapLocation bestTrapLoc = trapPlacementTarget;
        for(MapInfo info : nearbyInfos){
            if(info.getTrapType() != TrapType.NONE){
                continue;
            }
            if(info.isWater() || info.isWater() || info.isDam() || !info.isPassable()){
                continue;
            }
            int heuristic = info.getMapLocation().distanceSquaredTo(flagDefaultLoc);
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

        // If you don't have enough crumbs for a trap, just circle.
        if(rc.getCrumbs() < TrapType.EXPLOSIVE.buildCost){
            Util.addToIndicatorString("CRC: " + flagDefaultLoc);
            nav.circle(flagDefaultLoc, 2, 5);
        }
        else if(!rc.canBuild(TrapType.EXPLOSIVE, trapPlacementTarget)){
            nav.goToBug(trapPlacementTarget, 0);
            return;
        }
        else{
            rc.build(TrapType.EXPLOSIVE, trapPlacementTarget);
            trapPlacementTarget = null;
            trapPlacementHeuristic = Integer.MAX_VALUE;
        }
    }

    public void runStationaryDefense() throws GameActionException {
        assert(defendingFlagIdx != -1);
        flagDefaultLoc = comms.getDefaultHomeFlagLoc(defendingFlagIdx);
        boolean targetChanged = checkSharedDefensiveTargetStillValid();
        targetChanged |= updateSharedDefensiveTarget();
        if(targetChanged){
            comms.writeSharedDefensiveTarget(sharedDefensiveTarget);
        }
        placeTrapsAroundFlag();
//        nav.circle(flagDefaultLoc, 2, 5);
    }

    public void runMobileDefense() throws GameActionException {
        checkSharedDefensiveTargetStillValid();
        updateSharedDefensiveTarget();
        comms.writeSharedDefensiveTarget(sharedDefensiveTarget);
        if(sharedDefensiveTarget != null){
            Util.addToIndicatorString("SDT:" + sharedDefensiveTarget);
            Util.addToIndicatorString("SDTP: " + sharedDefensiveTargetPriority);
            nav.mode = NavigationMode.FUZZYNAV;
            nav.goTo(sharedDefensiveTarget, robot.distToSatisfy);
        }
        else{
            runStationaryDefense();
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
