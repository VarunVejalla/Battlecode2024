package marconi;

import battlecode.common.*;
public class Comms {

    RobotController rc;
    Robot robot;


    public Comms(RobotController rc, Robot robot){
        this.rc = rc;
        this.robot = robot;
    }


    public MapLocation readSharedTargetLoc() throws GameActionException {
        // note: this is a really crappy use of comms (just storing a single location for all the bots to converge to
        // i did this just to have some sort of semi-intelligent behavior that was not the default behavior
        return new MapLocation(rc.readSharedArray(0), rc.readSharedArray(1));
    }

    public boolean writeSharedTargetLoc(MapLocation targetLoc) throws GameActionException{
        if(rc.canWriteSharedArray(0, targetLoc.x) && rc.canWriteSharedArray(1, targetLoc.y)){
            rc.writeSharedArray(0, targetLoc.x);
            rc.writeSharedArray(1, targetLoc.y);
            return true;
        }
        return false;
    }


}
