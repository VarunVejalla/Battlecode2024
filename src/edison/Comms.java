package edison;

import battlecode.common.*;


//TODO: need to completely refactor this class to use bit shifting to use less space
// also need to move all constants to a separate class (Constants.java)
public class Comms {

    RobotController rc;
    Robot robot;

    // index for storing the index of the shared target location we want to go to
    // not in use right now
    int sharedTargetLocIdx = 0;

    // indices for storing known, uncaptured enemy flags starts at index 1, goes to 7
    int oppFlagsLocStartIdx = 1;
    int nullVal = 65535;


    // index for storing the last round the approxOppFlags array was updated
    int ApproxOppFlags_lastUpdated_idx = 8;
    // indices for storing the approxOppFlags, starts at index 9, goes to 15
    int ApproxOppFlags_idx = 9;



    public Comms(RobotController rc, Robot robot){
        this.rc = rc;
        this.robot = robot;
    }


    public int getLastUpdatedRound() throws GameActionException{
        return rc.readSharedArray(ApproxOppFlags_lastUpdated_idx);
    }


    public void setApproxOppFlags(MapLocation[] oppFlagsLocs) throws GameActionException{
        // first write the current round to the last updated round index
        rc.writeSharedArray(ApproxOppFlags_lastUpdated_idx, rc.getRoundNum());

        // set all the indices corresponding to approxOppFlags to nullVal
        for(int i = 0; i < 6; i++){
            rc.writeSharedArray(ApproxOppFlags_idx + i, nullVal);
        }
        
        // write the flag locations to the next indices
        for(int i = 0; i < oppFlagsLocs.length; i++){
            int idx = ApproxOppFlags_idx+ i*2;
            rc.writeSharedArray(idx, oppFlagsLocs[i].x);
            rc.writeSharedArray(idx + 1, oppFlagsLocs[i].y);
        }
    }


    public int getLastUpdatd_ApproxOppFlags() throws GameActionException{
        return rc.readSharedArray(ApproxOppFlags_lastUpdated_idx);
    }


    public MapLocation getApproxOppFlag(int idx) throws GameActionException{
        int x = rc.readSharedArray(ApproxOppFlags_idx + idx*2);
        int y = rc.readSharedArray(ApproxOppFlags_idx + idx*2 + 1);

        if (x == nullVal) return null;
        return new MapLocation(x, y);
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
