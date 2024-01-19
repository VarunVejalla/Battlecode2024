package neumann;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

import static neumann.OffensiveTargetType.CARRIED;
import static neumann.OffensiveTargetType.DROPPED;
import static neumann.OffensiveTargetType.APPROXIMATE;
import static neumann.OffensiveTargetType.DEFAULT;


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
            if(robot.sharedOffensiveTargetType == CARRIED){
                nav.circle(robot.sharedOffensiveTarget, 3, 8);
                Util.addToIndicatorString("CRC: " + robot.sharedOffensiveTarget);
            }
            else{
                Util.addToIndicatorString("SHRD TGT: " + robot.sharedOffensiveTarget);
                nav.goTo(robot.sharedOffensiveTarget, robot.distToSatisfy);
//                Util.addToIndicatorString("TGTYP: " + robot.sharedOffensiveTargetType.shortString());
            }
        }
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
            case APPROXIMATE:
                typeCost = 3;
                break;
            default:
                typeCost = 4;
                break;
        }

        if(robot.sharedOffensiveTarget == null){
            return typeCost*1000;
        }
        int distCost = Util.minMovesToReach(target.loc, robot.sharedOffensiveTarget);
        return typeCost*1000 + distCost;
    }


    public OffensiveTarget tryGettingNewTarget(OffensiveTarget currentTarget) throws GameActionException{
        boolean gotNewTarget = false;
        int currentTargetCost = getOffensiveTargetCost(currentTarget);

        for(MapLocation flagLoc : robot.knownCarriedOppFlags){
            OffensiveTarget target = new OffensiveTarget(flagLoc, CARRIED);
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
            OffensiveTarget target = new OffensiveTarget(flagLoc, DROPPED);
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
            OffensiveTarget target = new OffensiveTarget(flagLoc, DEFAULT);
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
            OffensiveTarget target = new OffensiveTarget(flagLoc, APPROXIMATE);
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
        OffensiveTarget currentTarget = new OffensiveTarget(robot.sharedOffensiveTarget, robot.sharedOffensiveTargetType);

//        Util.log("currentTarget: " + currentTarget.loc + " " + currentTarget.type);

        OffensiveTarget newTarget = tryGettingNewTarget(currentTarget);
//        Util.log("newTarget: " + newTarget);
//        Util.log("New target: " + newTarget.loc + " " + newTarget.type);

        if(newTarget == null || newTarget.equals(currentTarget)){
            return;
        }
        else{
            currentTarget = newTarget;
            // if we got a new target, update the shared offensive target
            robot.sharedOffensiveTarget = currentTarget.loc;
            robot.sharedOffensiveTargetType = currentTarget.type;
            comms.writeSharedOffensiveTarget(robot.sharedOffensiveTarget);
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
