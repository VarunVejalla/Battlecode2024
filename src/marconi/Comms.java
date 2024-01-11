package marconi;

import battlecode.common.*;
public class Comms {

    RobotController rc;
    Robot robot;


    int oppFlagsLocStartIdx = 1;
    int nullVal = 65535;

    public Comms(RobotController rc, Robot robot){
        this.rc = rc;
        this.robot = robot;
    }


    public int readSharedTargetLocIdx() throws GameActionException {
        return rc.readSharedArray(0);
    }

    public void writeSharedTargetLocIdx(int newIndex) throws GameActionException{
        rc.writeSharedArray(0, newIndex);
    }



    public void writeOppFlagLoc(MapLocation newFlagLoc) throws GameActionException{
        // first check if the flag is already in the array
        for(int i = 0; i < 3; i++){
            int idx = oppFlagsLocStartIdx + i*2;
            int x = rc.readSharedArray(idx);
            int y = rc.readSharedArray(idx + 1);
            if(x == newFlagLoc.x && y == newFlagLoc.y){
                return;
            }
        }

        // find the first index that is nullVal, and write the x and y coordinates to the next two indices
        for(int i = 0; i < 3; i++){
            int idx = oppFlagsLocStartIdx + i*2;
            int x = rc.readSharedArray(idx);
            if(x == nullVal){
                rc.writeSharedArray(idx, newFlagLoc.x);
                rc.writeSharedArray(idx + 1, newFlagLoc.y);
                return;
            }
        }
    }

    public void removeOppFlagLoc(MapLocation flagLoc) throws GameActionException{
        for(int i = 0; i < 3; i++){
            int idx = oppFlagsLocStartIdx + i*2;
            int x = rc.readSharedArray(idx);
            int y = rc.readSharedArray(idx + 1);
            if(x == flagLoc.x && y == flagLoc.y){
                rc.writeSharedArray(idx, nullVal);
                rc.writeSharedArray(idx + 1, nullVal);
                return;
            }
        }
    }


    public void setOppFlagsToNull() throws GameActionException{
        // write fakeVal to all the indices
        for(int i = 1; i < 7; i++){
            rc.writeSharedArray(i, nullVal);
        }
    }


    // need to refactor the indices using Constants, will do soon
    public MapLocation[] readOpponentFlagLocs() throws GameActionException{
        MapLocation[] opponentFlagLocs = new MapLocation[3];
        // if the corresponding index is nullVal, then the flag is not there, so set the location to null
        for(int i = 0; i < 3; i++){
            int idx = oppFlagsLocStartIdx + i*2;
            int x = rc.readSharedArray(idx);
            if(x == nullVal){
                opponentFlagLocs[i] = null;
            }
            else{
                int y = rc.readSharedArray(idx + 1);
                opponentFlagLocs[i] = new MapLocation(x, y);
            }
        }

        return opponentFlagLocs;
    }

}
