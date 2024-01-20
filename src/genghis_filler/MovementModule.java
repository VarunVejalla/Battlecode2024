package genghis_filler;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class MovementModule {

    Robot robot;
    RobotController rc;
    Navigation nav;
    Comms comms;

    public MovementModule(RobotController rc, Robot robot, Comms comms, Navigation nav) {
        this.rc = rc;
        this.robot = robot;
        this.comms = comms;
        this.nav = nav;
    }

    public void runSetupMovement() throws GameActionException {
        // this method contains movement logic during the setup period
        // it is only called during the first 200 rounds
        nav.moveRandom();
    }


    public void runDefensiveMovement() throws GameActionException {
        // circle the spawning location you came from?
        nav.circle(robot.spawnLoc, 5, 10);
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
            if(robot.offenseModule.sharedOffensiveTarget.equals(robot.myLoc)){
                robot.offenseModule.sharedOffensiveTarget = rc.getLocation();
                comms.writeSharedOffensiveTarget(robot.offenseModule.sharedOffensiveTarget);
            }
            robot.myLoc = rc.getLocation();
            comms.writeKnownOppFlagLoc(robot.myLoc, true);
        } else if (robot.offenseModule.sharedOffensiveTarget == null) {
            nav.moveRandom();
            Util.addToIndicatorString("RND");
        } else {
            nav.mode = NavigationMode.BUGNAV;
            if(robot.offenseModule.sharedOffensiveTargetType == OffensiveTargetType.CARRIED){
                nav.circle(robot.offenseModule.sharedOffensiveTarget, 3, 8);
                Util.addToIndicatorString("CRC: " + robot.offenseModule.sharedOffensiveTarget);
            }
            else{
                Util.addToIndicatorString("SHRD TGT: " + robot.offenseModule.sharedOffensiveTarget);
                nav.goTo(robot.offenseModule.sharedOffensiveTarget, robot.distToSatisfy);
            }
        }
    }


    public void runOffensiveMovement() throws GameActionException {
        // if you can pick up a flag, pick it up (and update comms)
        robot.tryPickingUpOppFlag();
        if (rc.getRoundNum() % 50 == 0) {
//            testLog();
        }

        if (rc.isMovementReady()) {
            moveToTarget();
        }
    }


    public void runTrapperMovement() throws GameActionException{
        // TODO: place bombs around the flag you're defending
        //  TODO: go out and level up your specialization in the beginning of the game
        return;
    }


    public void runMovement() throws GameActionException {
        // if the round number is less than 200, walk around randomly

        if(robot.mode == Mode.TRAPPING){
            runTrapperMovement();
            return;
        }

//        tryPickingCrumbs();

        if (rc.getRoundNum() < Constants.SETUP_ROUNDS) {
            runSetupMovement();
        }

        if (robot.mode == Mode.MOBILE_DEFENSE) {
            runDefensiveMovement();
        } else {
            runOffensiveMovement();
        }
    }
}
