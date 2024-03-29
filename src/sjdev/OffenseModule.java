package sjdev;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
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

    public int getOffensiveTargetCost(OffensiveTarget target, MapLocation offensiveCOM){
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


        int distCost = 0;

        // if offensiveCOM is not null, use it to calculate distance cost
        // idea is we want to select targets that are closer to the current offensiveCOM
        if(offensiveCOM != null){
            distCost = Util.minMovesToReach(target.loc, offensiveCOM);
//            Util.log("Offensive COM: " + offensiveCOM);
//            Util.log("Target: " + target.loc);
//            Util.log("Dist Cost: " + distCost);
        }

        // if we don't have an offensiveCOM (which we should after round 200, but just in case)
        // use the sharedOffensiveTarget
        else if(sharedOffensiveTarget != null){
            distCost = Util.minMovesToReach(target.loc, sharedOffensiveTarget);
        }

        // if we have neither offensiveCOM nor sharedOffensiveTarget, just return 0 for distCost
        else{
            distCost = 0;
        }
        return typeCost*1000 + distCost;
    }


    public OffensiveTarget tryGettingNewTarget(OffensiveTarget currentTarget) throws GameActionException{
        boolean gotNewTarget = false;

        MapLocation offensiveCOM = comms.getPreviousOffensiveCOM(); // offensiveCOM in previous round. variable is reused in
        int currentTargetCost = getOffensiveTargetCost(currentTarget, offensiveCOM);

        // loop over known carried flags (bros + flags >> flags)
        for(MapLocation flagLoc : robot.knownCarriedOppFlags){
            OffensiveTarget target = new OffensiveTarget(flagLoc, OffensiveTargetType.CARRIED);
            int targetCost = getOffensiveTargetCost(target, offensiveCOM);
//            Util.log("Carried flag target: " + target.loc + " " + target.type);
//            Util.log("Cost: " + targetCost);
//            Util.log("");

            if (targetCost < currentTargetCost){
                currentTarget = target;
                currentTargetCost = targetCost;
                gotNewTarget = true;
            }
        }
        if (gotNewTarget){
//            Util.log("Got carried flag target: " + currentTarget.loc + " " + currentTarget.type);
//            Util.log("Cost: " + currentTargetCost);
            return currentTarget;
        }

        // loop over dropped flags
        for(MapLocation flagLoc : robot.knownDroppedOppFlags){
            OffensiveTarget target = new OffensiveTarget(flagLoc, OffensiveTargetType.DROPPED);
            int targetCost = getOffensiveTargetCost(target, offensiveCOM);
//            Util.log("Dropped flag target: " + target.loc + " " + target.type);
//            Util.log("Cost: " + targetCost);
//            Util.log("");

            if (targetCost < currentTargetCost){
                currentTarget = target;
                currentTargetCost = targetCost;
                gotNewTarget = true;
            }
        }
        if (gotNewTarget){
//            Util.log("Got dropped flag target: " + currentTarget.loc + " " + currentTarget.type);
//            Util.log("Cost: " + currentTargetCost);
            return currentTarget;
        }

        // loop over default opp flag locations
        for(MapLocation flagLoc : robot.defaultOppFlagLocs){
            OffensiveTarget target = new OffensiveTarget(flagLoc, OffensiveTargetType.DEFAULT);
            int targetCost = getOffensiveTargetCost(target, offensiveCOM);
//            Util.log("Default flag target: " + target.loc + " " + target.type);
//            Util.log("Cost: " + targetCost);
//            Util.log("");
            if (targetCost < currentTargetCost){
                currentTarget = target;
                currentTargetCost = targetCost;
                gotNewTarget = true;
            }
        }
        if (gotNewTarget){
//            Util.log("Got default flag target: " + currentTarget.loc + " " + currentTarget.type);
//            Util.log("Cost: " + currentTargetCost);
            return currentTarget;
        }

        // loop over approximate flag locations
        for(MapLocation flagLoc : robot.approximateOppFlagLocations){
            OffensiveTarget target = new OffensiveTarget(flagLoc, OffensiveTargetType.APPROXIMATE);
            int targetCost = getOffensiveTargetCost(target, offensiveCOM);
//            Util.log("Approximate flag target: " + target.loc + " " + target.type);
//            Util.log("Cost: " + targetCost);
//            Util.log("");
            if (targetCost < currentTargetCost){
                currentTarget = target;
                currentTargetCost = targetCost;
                gotNewTarget = true;
            }
        }
        if (gotNewTarget){
//            Util.log("Got approximate flag target: " + currentTarget.loc + " " + currentTarget.type);
//            Util.log("Cost: " + currentTargetCost);
            return currentTarget;
        }
        return currentTarget;
    }

    public void tryUpdateSharedOffensiveTarget() throws GameActionException {
        // loop over carried flags
        OffensiveTarget currentTarget = new OffensiveTarget(sharedOffensiveTarget, sharedOffensiveTargetType);
//        Util.log("Current Target: " + currentTarget.loc + " " + currentTarget.type);

//        Util.log("Current Target: " + currentTarget.loc + " " + currentTarget.type);

        OffensiveTarget newTarget = tryGettingNewTarget(currentTarget);
//        Util.log("New Target: " + newTarget.loc + " " + newTarget.type);
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
            comms.removeKnownOppFlagLocFromId(robot.idOfFlagImCarrying);
            nav.pathBF(robot.homeLocWhenCarryingFlag, 0);
            Util.addToIndicatorString("HL: " + robot.homeLocWhenCarryingFlag);
            if(sharedOffensiveTarget.equals(robot.myLoc)){
                sharedOffensiveTarget = rc.getLocation();
                comms.writeSharedOffensiveTarget(sharedOffensiveTarget);
            }
            robot.myLoc = rc.getLocation();
            comms.writeKnownOppFlagLocFromFlagID(robot.myLoc, true, robot.idOfFlagImCarrying);
        } else if (sharedOffensiveTarget == null) { // this should not happen after round 200
            nav.moveRandom();
            Util.addToIndicatorString("RND");
        } else {
            if(sharedOffensiveTargetType == OffensiveTargetType.CARRIED){
                nav.circle(sharedOffensiveTarget, 3, 8, 0);
                Util.addToIndicatorString("CRC: " + sharedOffensiveTarget);
            }
            else if(sharedOffensiveTargetType == OffensiveTargetType.APPROXIMATE){
                // note: broadcasts are accurate up to raidus of sqrt(100) = 10
                // and the radius of bot is sqrt(20) ~ 4.5
                // so i think this circle range gurantees we'll find the flag
                nav.circle(sharedOffensiveTarget, 3, 7, 0);
                Util.addToIndicatorString("CRC: " + sharedOffensiveTarget);

            }
            else{
                Util.addToIndicatorString("SHRD TGT: " + sharedOffensiveTarget);
                Util.logBytecode("Beginning of pathBF");
                nav.pathBF(sharedOffensiveTarget, 100);
            }
        }
    }

    public void runMovement() throws GameActionException {
        // if you can pick up a flag, pick it up (and update comms)
        Util.logBytecode("Beginning of run movement");
        robot.tryPickingUpOppFlag();

        if (rc.isMovementReady()) {
            Util.logBytecode("Beginning move to target");
            moveToTarget();
        }
    }

    public void setup(){

    }

}
