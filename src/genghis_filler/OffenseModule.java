package genghis_filler;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

enum OffensiveTargetType { CARRIED, DROPPED, DEFAULT, APPROXIMATE;

    public String shortString(){
        switch(this){
            case CARRIED:
                return "C";
            case DROPPED:
                return "DR";
            case DEFAULT:
                return "DE";
            case APPROXIMATE:
                return "A";
            default:
                return "NULL";
        }

    }
};

class OffensiveTarget {
    MapLocation loc;
    OffensiveTargetType type;

    public OffensiveTarget(MapLocation loc, OffensiveTargetType type) {
        this.loc = loc;
        this.type = type;
    }
}


public class OffenseModule {

    RobotController rc;
    Robot robot;
    Comms comms;
    Navigation nav;

    MapLocation sharedOffensiveTarget;
    OffensiveTargetType sharedOffensiveTargetType;

    public OffenseModule(RobotController rc, Robot robot, Comms comms, Navigation nav) throws GameActionException {
        this.rc = rc;
        this.robot = robot;
        this.comms = comms;
        this.nav = nav;
    }

    public int getOffensiveTargetCost(OffensiveTarget target){
        // this method returns the cost of the target
        int typeCost;
        if(target == null || target.loc == null || target.type==null){
            return 4000;
        }

        switch(target.type){
            case CARRIED:
                typeCost = 0;
                break;
            case DROPPED:
                typeCost = 1;
                break;
            case DEFAULT:
                typeCost = 2;
                break;
            case APPROXIMATE:
                typeCost = 3;
                break;
            default:
                typeCost = 4;
                break;
        }

        if(sharedOffensiveTarget == null){
            return typeCost*1000;
        }
        int distCost = Util.minMovesToReach(target.loc, sharedOffensiveTarget);
        return typeCost*1000 + distCost;
    }


    public OffensiveTarget tryGettingNewTarget(OffensiveTarget currentTarget) throws GameActionException{
        boolean gotNewTarget = false;
        int currentTargetCost = getOffensiveTargetCost(currentTarget);

        for(MapLocation flagLoc : robot.knownCarriedOppFlags){
            OffensiveTarget target = new OffensiveTarget(flagLoc, OffensiveTargetType.CARRIED);
            int targetCost = getOffensiveTargetCost(target);
            if (targetCost < currentTargetCost){
                currentTarget = target;
                currentTargetCost = targetCost;
                gotNewTarget = true;
            }
        }
        if (gotNewTarget){
            return currentTarget;
        }

        // loop over dropped flags
        for(MapLocation flagLoc : robot.knownDroppedOppFlags){
            OffensiveTarget target = new OffensiveTarget(flagLoc, OffensiveTargetType.DROPPED);
            int targetCost = getOffensiveTargetCost(target);
            if (targetCost < currentTargetCost){
                currentTarget = target;
                currentTargetCost = targetCost;
                gotNewTarget = true;
            }
        }
        if (gotNewTarget){
            return currentTarget;
        }

        // loop over default opp flag locations
        for(MapLocation flagLoc : robot.defaultOppFlagLocs){
            OffensiveTarget target = new OffensiveTarget(flagLoc, OffensiveTargetType.DEFAULT);
            int targetCost = getOffensiveTargetCost(target);
            if (targetCost < currentTargetCost){
                currentTarget = target;
                currentTargetCost = targetCost;
                gotNewTarget = true;
            }
        }
        if (gotNewTarget){
            return currentTarget;
        }


        // loop over approximate flag locations
        for(MapLocation flagLoc : robot.approximateOppFlagLocations){
            OffensiveTarget target = new OffensiveTarget(flagLoc, OffensiveTargetType.APPROXIMATE);
            int targetCost = getOffensiveTargetCost(target);
            if (targetCost < currentTargetCost){
                currentTarget = target;
                currentTargetCost = targetCost;
                gotNewTarget = true;
            }
        }
        if (gotNewTarget){
            return currentTarget;
        }
        return currentTarget;
    }

    public void tryUpdateSharedOffensiveTarget() throws GameActionException {
        // loop over carried flags
        OffensiveTarget currentTarget = new OffensiveTarget(sharedOffensiveTarget, sharedOffensiveTargetType);
//        Util.log("Current Target: " + currentTarget.loc + " " + currentTarget.type);

        OffensiveTarget newTarget = tryGettingNewTarget(currentTarget);
//        Util.log("New Target: " + newTarget.loc + " " + newTarget.type);

        if(newTarget == null || newTarget.equals(currentTarget)){
            return;
        }
        else{
            currentTarget = newTarget;
            // if we got a new target, update the shared offensive target
            sharedOffensiveTarget = currentTarget.loc;
            sharedOffensiveTargetType = currentTarget.type;
            comms.writeSharedOffensiveTarget(sharedOffensiveTarget);
        }
    }

    public void spawn() throws GameActionException {
        if(sharedOffensiveTarget != null){
            Util.spawnClosestToLocation(sharedOffensiveTarget);
        }
        // Pick a random spawn location to attempt spawning in.
        else{
            MapLocation randomLoc = robot.allSpawnLocs[robot.rng.nextInt(robot.allSpawnLocs.length)];
            if (rc.canSpawn(randomLoc)) rc.spawn(randomLoc);
            robot.spawnLoc = randomLoc;
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
            if(sharedOffensiveTarget.equals(robot.myLoc)){
                sharedOffensiveTarget = rc.getLocation();
                comms.writeSharedOffensiveTarget(sharedOffensiveTarget);
            }
            robot.myLoc = rc.getLocation();
            comms.writeKnownOppFlagLoc(robot.myLoc, true);
        } else if (sharedOffensiveTarget == null) {
            nav.moveRandom();
            Util.addToIndicatorString("RND");
        } else {
            nav.mode = NavigationMode.BUGNAV;
            if(sharedOffensiveTargetType == OffensiveTargetType.CARRIED){
                nav.circle(sharedOffensiveTarget, 3, 8);
                Util.addToIndicatorString("CRC: " + sharedOffensiveTarget);
            }
            else{
                Util.addToIndicatorString("SHRD TGT: " + sharedOffensiveTarget);
                nav.goTo(sharedOffensiveTarget, 0);
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
