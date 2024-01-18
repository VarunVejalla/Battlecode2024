package genghis;

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

    public void getBestSpawnLoc(){

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
