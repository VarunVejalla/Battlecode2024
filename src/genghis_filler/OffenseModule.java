package genghis_filler;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class OffenseModule {

    RobotController rc;
    Robot robot;
    Comms comms;
    Navigation nav;

    public OffenseModule(RobotController rc, Robot robot, Comms comms, Navigation nav) throws GameActionException {
        this.rc = rc;
        this.robot = robot;
        this.comms = comms;
        this.nav = nav;
    }

    public void spawnClosestToAllyFlags() throws GameActionException {
        MapLocation spawnLoc = null;
        int bestDist = Integer.MAX_VALUE;
        for(MapLocation potentialSpawnLoc : rc.getAllySpawnLocations()){
            if(!rc.canSpawn(potentialSpawnLoc)){
                continue;
            }
            for(MapLocation flagLoc : robot.defaultHomeFlagLocs){
                int dist = potentialSpawnLoc.distanceSquaredTo(flagLoc);
                if(dist < bestDist){
                    spawnLoc = potentialSpawnLoc;
                    bestDist = dist;
                }
            }
        }
        if(spawnLoc != null){
            rc.spawn(spawnLoc);
        }
    }


    public MapLocation getNewSharedOffensiveTarget() throws GameActionException {
        // if there is a known carried flag that is not the current target, go to that
        for (MapLocation loc : robot.knownCarriedOppFlags) {
            if (loc != null) {
                return loc;
            }
        }
        // if there is a known dropped flag that is not the current target, go to that
        for (MapLocation loc : robot.knownDroppedOppFlags) {
            if (loc != null && !loc.equals(robot.sharedOffensiveTarget)) {
                return loc;
            }
        }
        // if there is an approximate location of a flag that is not the current target, go to that
        for (MapLocation loc : robot.approximateOppFlagLocations) {
            if (loc != null && !loc.equals(robot.sharedOffensiveTarget)) {
                return loc;
            }
        }
        return null;
    }


    public void tryUpdateSharedOffensiveTarget() throws GameActionException {
        // this method updates the sharedOffensiveTarget if the current target is no longer valid
        boolean needToGetNewTarget = false;

        // if we currently don't have a shared offensive target
        if (robot.sharedOffensiveTarget == null) {
            needToGetNewTarget = true;
        }

        // if the current target is not in approximate areas or dropped flags, get a new one
        else if (!Util.checkIfItemInArray(robot.sharedOffensiveTarget, robot.approximateOppFlagLocations) &&
                !Util.checkIfItemInArray(robot.sharedOffensiveTarget, robot.knownDroppedOppFlags) &&
                !Util.checkIfItemInArray(robot.sharedOffensiveTarget, robot.knownCarriedOppFlags)) {
            needToGetNewTarget = true;
        }

        // If you are at the current target and there a good number of fellow bots are present, get a new one
        else if (robot.sharedOffensiveTargetType != OffensiveTargetType.CARRIED
                && robot.myLoc.distanceSquaredTo(robot.sharedOffensiveTarget) <= robot.distToSatisfy) {
            if (robot.nearbyFriendlies.length >= Constants.BOT_THRESHOLD_TO_MARK_TARGET_AS_COMPLETE) {
//            if(Util.countBotsOfTeam(rc.getTeam(), sensedNearbyRobots) >= Constants.BOT_THRESHOLD_TO_MARK_TARGET_AS_COMPLETE){
                needToGetNewTarget = true;
            }
        }
//        indicatorString += "NGST: " + needToGetNewTarget + ";";

        if (needToGetNewTarget) {
            robot.sharedOffensiveTarget = getNewSharedOffensiveTarget();
            comms.writeSharedOffensiveTarget(robot.sharedOffensiveTarget);

            robot.sharedOffensiveTargetType = null;
            if(Util.checkIfItemInArray(robot.sharedOffensiveTarget, robot.knownCarriedOppFlags)){
                robot.sharedOffensiveTargetType = OffensiveTargetType.CARRIED;
            }
            else if(Util.checkIfItemInArray(robot.sharedOffensiveTarget, robot.knownDroppedOppFlags)){
                robot.sharedOffensiveTargetType = OffensiveTargetType.DROPPED;
            }
            else if(Util.checkIfItemInArray(robot.sharedOffensiveTarget, robot.approximateOppFlagLocations)){
                robot.sharedOffensiveTargetType = OffensiveTargetType.APPROXIMATE;
            }
        }
    }

    // TODO: Change this to spawn closest to opponent flags?
    public void spawn() throws GameActionException {
        robot.sharedOffensiveTarget = null;
        robot.sharedOffensiveTargetType = null;
        robot.prevTargetLoc = null;
        // Pick a random spawn location to attempt spawning in.
        if(robot.defaultHomeFlagLocs == null){
            MapLocation randomLoc = robot.allSpawnLocs[robot.rng.nextInt(robot.allSpawnLocs.length)];
            if (rc.canSpawn(randomLoc)) rc.spawn(randomLoc);
            robot.spawnLoc = randomLoc;
        }
        else{
            spawnClosestToAllyFlags();
        }
    }

    public void moveToTarget() throws GameActionException {
        if (rc.hasFlag()) {
            if(robot.homeLocWhenCarryingFlag == null){
                robot.homeLocWhenCarryingFlag = Util.getNearestHomeSpawnLoc(robot.myLoc);
            }
            robot.myLoc = rc.getLocation();
            comms.removeKnownOppFlagLoc(robot.myLoc);
            nav.mode = NavigationMode.BUGNAV;
            nav.goTo(robot.homeLocWhenCarryingFlag, 0);
            Util.addToIndicatorString("HL: " + robot.homeLocWhenCarryingFlag);
            if(robot.sharedOffensiveTarget.equals(robot.myLoc)){
                robot.sharedOffensiveTarget = rc.getLocation();
                comms.writeSharedOffensiveTarget(robot.sharedOffensiveTarget);
            }
            robot.myLoc = rc.getLocation();
            comms.writeKnownOppFlagLoc(robot.myLoc, true);
        } else if (robot.sharedOffensiveTarget == null) {
            nav.moveRandom();
            Util.addToIndicatorString("RND");
        } else {
            nav.mode = NavigationMode.BUGNAV;
            if(robot.sharedOffensiveTargetType == OffensiveTargetType.CARRIED){
                nav.circle(robot.sharedOffensiveTarget, 3, 8);
                Util.addToIndicatorString("CRC: " + robot.sharedOffensiveTarget);
            }
            else{
                Util.addToIndicatorString("SHRD TGT: " + robot.sharedOffensiveTarget);
                nav.goTo(robot.sharedOffensiveTarget, robot.distToSatisfy);
            }
        }
    }

    public void runMovement() throws GameActionException {
        // if you can pick up a flag, pick it up (and update comms)
        robot.tryPickingUpOppFlag();

        if (rc.isMovementReady()) {
            moveToTarget();
        }
    }

    public void setup(){

    }

}
