package caesar;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class Comms {

    RobotController rc;
    Robot robot;
    Constants constants;



    public Comms(RobotController rc, Robot robot){
        this.rc = rc;
        this.robot = robot;
        this.constants = new Constants();
    }

    // general methods for extracting and inserting values into the comms array

    private int extractVal(int commsIdx, int mask, int shift) throws GameActionException {
        return (rc.readSharedArray(commsIdx) & mask) >> shift;
    }

    private void insertVal(int commsIdx, int mask, int shift, int value) throws GameActionException {
        // Clear out the existing value in that position
        int clearMask = constants.FULL_MASK - mask;
        int newCommsVal = rc.readSharedArray(commsIdx) & clearMask;

        // Insert the new value
        newCommsVal = newCommsVal | (value << shift);
        rc.writeSharedArray(commsIdx, newCommsVal);
    }

    /////////////////////////////////////////////////////////////////////////////
    // methods for reading and writing the approximate opponent flag locations

    public int getApproxOppFlag_LastUpdated() throws GameActionException{
        // this method returns the last round that the approximate opponent flag locations were updated from the broadcast
        return extractVal(
            constants.APPROX_OPP_FLAG_INFO_LAST_UPDATED_IDX, 
            constants.APPROX_OPP_FLAG_INFO_LAST_UPDATED_MASK, 
            constants.APPROX_OPP_FLAG_INFO_LAST_UPDATED_SHIFT) * 10;
    }

    public void setApproxOppFlag_LastUpdated(int lastUpdated) throws GameActionException{
        // this method sets the last round that the approximate opponent flag locations were updated from the broadcast
        lastUpdated /= 10; // we divide the round number by 10, since the trailing zero is always present
        insertVal(
            constants.APPROX_OPP_FLAG_INFO_LAST_UPDATED_IDX, 
            constants.APPROX_OPP_FLAG_INFO_LAST_UPDATED_MASK, 
            constants.APPROX_OPP_FLAG_INFO_LAST_UPDATED_SHIFT, 
            lastUpdated);
    }

    public MapLocation[] getAllApproxOppFlags() throws GameActionException{
        // this method returns an array of all approximate opponent flag locations
        MapLocation[] approxFlags = new MapLocation[3];
        for(int i=0; i< constants.APPROX_OPP_FLAG_INDICES.length; i++){
            approxFlags[i] = getApproxOppFlag(i);
        }
        return approxFlags;
    }

    private void setApproxOppFlag(int idx, MapLocation flagLoc) throws GameActionException{
        // this method sets the approximate location of an opponent flag
        int x;
        int y;
        
        if(flagLoc == null){
            x = constants.LOCATION_NULL_VAL;
            y = constants.LOCATION_NULL_VAL;
        } else {
            x = flagLoc.x;
            y = flagLoc.y;
        }

        // set the x value
        insertVal(idx, constants.APPROX_OPP_FLAG_X_MASK, constants.APPROX_OPP_FLAG_X_SHIFT, x);

        // set the y value
        insertVal(idx, constants.APPROX_OPP_FLAG_Y_MASK, constants.APPROX_OPP_FLAG_Y_SHIFT, y);
    }


    public void setApproxOppFlags(MapLocation[] oppFlagsLocs) throws GameActionException{
        // this method sets the approximate locations of all opponent flags from the broadcast

        // set the last updated value
        int lastUpdated = rc.getRoundNum();
        setApproxOppFlag_LastUpdated(lastUpdated);

        // set the flag locations
        for(int i=0; i<3; i++){
            if (i < oppFlagsLocs.length) {
                setApproxOppFlag(i, oppFlagsLocs[i]);
            }
            else{
                setApproxOppFlag(i, null);
            }
        }
    }

    public MapLocation getApproxOppFlag(int idx) throws GameActionException{
        int trueIndex = constants.APPROX_OPP_FLAG_INDICES[idx];
        // this method returns the approximate location of a single opponent flag (specified by idx)
        int x = extractVal(
            trueIndex, 
            constants.APPROX_OPP_FLAG_X_MASK, 
            constants.APPROX_OPP_FLAG_X_SHIFT);

        int y = extractVal(
            trueIndex, 
            constants.APPROX_OPP_FLAG_Y_MASK, 
            constants.APPROX_OPP_FLAG_Y_SHIFT);

        if(x == constants.LOCATION_NULL_VAL || y == constants.LOCATION_NULL_VAL){
            return null;
        } else {
            return new MapLocation(x, y);
        }
    }


    /////////////////////////////////////////////////////////////////////////////
    // methods for reading and writing the known opponent flag locations

    public MapLocation getKnownOppFlag(int idx) throws GameActionException{
        // this method returns the location of a single known opponent flag (specified by idx)
        int trueIndex = constants.KNOWN_OPP_FLAG_INDICES[idx];
        int x = extractVal(trueIndex, constants.KNOWN_OPP_FLAG_X_MASK, constants.KNOWN_OPP_FLAG_X_SHIFT);
        int y = extractVal(trueIndex, constants.KNOWN_OPP_FLAG_Y_MASK, constants.KNOWN_OPP_FLAG_Y_SHIFT);
        if(x == constants.LOCATION_NULL_VAL || y == constants.LOCATION_NULL_VAL){
            return null;
        } 
        return new MapLocation(x, y);
    }


    public boolean getCarriedStatus_KnownOppFlag(int idx) throws GameActionException{
        // this method returns whether a single known opponent flag (specified by idx) is being carried or not
        int trueIndex = constants.KNOWN_OPP_FLAG_INDICES[idx];
        int carried = extractVal(trueIndex, constants.KNOWN_OPP_FLAG_CARRIED_MASK, constants.KNOWN_OPP_FLAG_CARRIED_SHIFT);
        return carried == 1;
    }


    private void writeCarriedStatus_KnownOppFlag(int idx, boolean carried) throws GameActionException{
        // this method writes the carried status of a single known opponent flag (specified by idx)
        int trueIndex = constants.KNOWN_OPP_FLAG_INDICES[idx];
        int carriedVal = 0;
        if(carried){
            carriedVal = 1;
        }
        insertVal(trueIndex, constants.KNOWN_OPP_FLAG_CARRIED_MASK, constants.KNOWN_OPP_FLAG_CARRIED_SHIFT, carriedVal);
    }


    private void writeKnownOppFlagLoc(MapLocation newFlagLoc, boolean carried, int idx) throws GameActionException{
        // this method writes the location of a single known opponent flag (specified by idx)
        // this method is used internally to the Comms class
        int trueIndex = constants.KNOWN_OPP_FLAG_INDICES[idx];
        int x;
        int y;

        if(newFlagLoc == null){
            x = constants.LOCATION_NULL_VAL;
            y = constants.LOCATION_NULL_VAL;
        } else {
            x = newFlagLoc.x;
            y = newFlagLoc.y;
        }

        // set the x value
        insertVal(trueIndex, constants.KNOWN_OPP_FLAG_X_MASK, constants.KNOWN_OPP_FLAG_X_SHIFT, x);

        // set the y value
        insertVal(trueIndex, constants.KNOWN_OPP_FLAG_Y_MASK, constants.KNOWN_OPP_FLAG_Y_SHIFT, y);

        // set the carried value
        int carriedVal = 0;
        if(carried){
            carriedVal = 1;
        }
        insertVal(trueIndex, constants.KNOWN_OPP_FLAG_CARRIED_MASK, constants.KNOWN_OPP_FLAG_CARRIED_SHIFT, carriedVal);
    }


    public void writeKnownOppFlagLoc(MapLocation newFlagLoc, boolean carried) throws GameActionException{
        for(int i=0; i<constants.KNOWN_OPP_FLAG_INDICES.length; i++){
            MapLocation knownFlagLoc = getKnownOppFlag(i);
            if(knownFlagLoc != null && knownFlagLoc.equals(newFlagLoc)){
                writeCarriedStatus_KnownOppFlag(i, carried);
                return;
            }
        }

        // if we get here, then we didn't find the flag in the broadcast
        // so we need to add it
        for(int i=0; i<constants.KNOWN_OPP_FLAG_INDICES.length; i++){
            MapLocation knownFlagLoc = getKnownOppFlag(i);
            if(knownFlagLoc == null){
                writeKnownOppFlagLoc(newFlagLoc, carried, i);
                return;
            }
        }
    }


    public MapLocation[] getAllKnownOppFlagLocs() throws GameActionException{
        // this method returns an array of all known opponent flag locations
        MapLocation[] knownFlags = new MapLocation[3];
        for(int i=0; i< constants.KNOWN_OPP_FLAG_INDICES.length; i++){
            knownFlags[i] = getKnownOppFlag(i);
        }
        return knownFlags;
    }
    

    private MapLocation[] getKnownOppFlags(boolean carried) throws GameActionException {
        // this method returns an array of locations of opponent flags that are being carried by friendly robots
        MapLocation[] knownFlags = new MapLocation[3];
        
        for(int i=0; i<constants.KNOWN_OPP_FLAG_INDICES.length; i++){
            MapLocation flagLoc = getKnownOppFlag(i);
            if(flagLoc != null){
                if(getCarriedStatus_KnownOppFlag(i) == carried){
                    knownFlags[i] = flagLoc;
                }
            }
        }
        return knownFlags;
    }


    public MapLocation[] getCarriedOppFlags() throws GameActionException{
        // this method returns an array of locations of opponent flags that are being carried by friendly robots
        return getKnownOppFlags(true);
    }


    public MapLocation[] getDroppedOppFlags() throws GameActionException{
        // this method returns the locations of opponent flags that are not being carried
        return getKnownOppFlags(false);
    }



    public void removeKnownOppFlagLoc(MapLocation flagLoc) throws GameActionException{
        // this method removes a known opponent flag location from the broadcast
        // used if we notice a flag is no longer there
        for(int i=0; i<constants.KNOWN_OPP_FLAG_INDICES.length; i++){
            MapLocation knownFlagLoc = getKnownOppFlag(i);
            if(knownFlagLoc != null && knownFlagLoc.equals(flagLoc)){
                    writeKnownOppFlagLoc(null, false, i);
            }
        }
    }


    public void setKnownOppFlagsToNull() throws GameActionException{
        // this method sets all known opponent flag locations to null (at the beginning of the game)
        for(int i=0; i<constants.KNOWN_OPP_FLAG_INDICES.length; i++){
            writeKnownOppFlagLoc(null, false, i);
        }
    }


    /////////////////////////////////////////////////////////////////////////////
    // methods for reading and writing shared offensive target
    public MapLocation getSharedOffensiveTarget() throws GameActionException{
        // this method returns the location of the shared offensive target
            int x = extractVal(
                constants.SHARED_OFFENSIVE_TARGET_IDX, 
                constants.SHARED_OFFENSIVE_TARGET_X_MASK, 
                constants.SHARED_OFFENSIVE_TARGET_X_SHIFT);

            int y = extractVal(
                constants.SHARED_OFFENSIVE_TARGET_IDX, 
                constants.SHARED_OFFENSIVE_TARGET_Y_MASK, 
                constants.SHARED_OFFENSIVE_TARGET_Y_SHIFT);

            if(x == constants.LOCATION_NULL_VAL || y == constants.LOCATION_NULL_VAL){
                return null;
            } 
            
            else {
                return new MapLocation(x, y);
            }        
    }


    public void writeSharedOffensiveTarget(MapLocation loc) throws GameActionException {
        // this method writes the location of the shared offensive target
        int x, y;
        if(loc == null){
            x = constants.LOCATION_NULL_VAL;
            y = constants.LOCATION_NULL_VAL;
        } 
        else {
            x = loc.x;
            y = loc.y;
        }

        insertVal(
            constants.SHARED_OFFENSIVE_TARGET_IDX, 
            constants.SHARED_OFFENSIVE_TARGET_X_MASK, 
            constants.SHARED_OFFENSIVE_TARGET_X_SHIFT, 
            x);
        
        insertVal(
            constants.SHARED_OFFENSIVE_TARGET_IDX, 
            constants.SHARED_OFFENSIVE_TARGET_Y_MASK, 
            constants.SHARED_OFFENSIVE_TARGET_Y_SHIFT, 
            y);
    }


    public boolean defaultFlagLocationsWritten() throws GameActionException {
        // this method checks if we have written defaultFlagLocations
        // these are the default locations of the home team flags after round 200

        // TODO: make this method generalizable if we move flags to different locations
        // Note, this method may fail if we move flags to a different location, since that location will be (0,0),
        // in which case this method returns False

        // the assumption for this is that all default flag locations are written at the same time to valid locations
        // so you only need to check the first flag

        int x0 = extractVal(Constants.DEFAULT_FLAG_LOC_0_IDX, Constants.DEFAULT_FLAG_LOC_X_MASK, 0);
        int y0 = extractVal(Constants.DEFAULT_FLAG_LOC_0_IDX, Constants.DEFAULT_FLAG_LOC_Y_MASK, 6);

        if(x0 >= 61 || y0 >= 61) {
            return false;
        }
        else if (x0 > 0 && y0 > 0) {
            return true;
        }
        else {
            //x0 == 0 and y0 == 0
            return false;
        }
    }


    public void writeDefaultHomeFlagLocs() throws GameActionException {
        // this method determines the center of each spawn zone and sets that as a defaultFlagLocation
        // Note: a defaultFlagLocation is a location where our flags will be by default after round 200 (if they are not taken)

        // TODO: need to make this method more general in case we move flags before rounds
        // TODO: the bytecode for this can probably be reduced, but it's only executed one time
        MapLocation[] spawnLocs = rc.getAllySpawnLocations();
        MapLocation[] flags = new MapLocation[3];
        int count;
        for(MapLocation potentialFlag : spawnLocs) {
            count = 0;
            for(MapLocation loc : spawnLocs) {
                if(potentialFlag.distanceSquaredTo(loc) <= 2) {
                    count += 1;
                }
            }
//            if(count > 9) {
//                //TODO: something went wrong
//            }
            if(count == 9) {
                if(flags[0] == null) {
                    flags[0] = potentialFlag;
                    continue;
                }
                if(flags[1] == null) {
                    flags[1] = potentialFlag;
                    continue;
                }
                if(flags[2] == null) {
                    flags[2] = potentialFlag;
                    continue;
                }
                // TODO: if it got here, something went wrong
            }
        }

        for(int i = 0; i < 3; i += 1) {
            insertVal(Constants.DEFAULT_FLAG_LOCS_INDICES[i], Constants.DEFAULT_FLAG_LOC_X_MASK, Constants.DEFAULT_FLAG_LOC_X_SHIFT, flags[i].x);
            insertVal(Constants.DEFAULT_FLAG_LOCS_INDICES[i], Constants.DEFAULT_FLAG_LOC_Y_MASK, Constants.DEFAULT_FLAG_LOC_Y_SHIFT, flags[i].y);
        }
    }


    public MapLocation getDefaultHomeFlagLoc(int flagIdx) throws GameActionException {
//        if(flagIdx < 0 || flagIdx >= 3)
        int x = extractVal(Constants.DEFAULT_FLAG_LOCS_INDICES[flagIdx], Constants.DEFAULT_FLAG_LOC_X_MASK, Constants.DEFAULT_FLAG_LOC_X_SHIFT);
        int y = extractVal(Constants.DEFAULT_FLAG_LOCS_INDICES[flagIdx], Constants.DEFAULT_FLAG_LOC_Y_MASK, Constants.DEFAULT_FLAG_LOC_Y_SHIFT);
        return new MapLocation(x, y);
    }

    public MapLocation[] getDefaultHomeFlagLocs() throws GameActionException {
        // returns an array of map locations representing the default locations of home flags after round 200
        return new MapLocation[]{
                getDefaultHomeFlagLoc(0),
                getDefaultHomeFlagLoc(1),
                getDefaultHomeFlagLoc(2)};
    }


    public void writeTrapper(int flagIndex, int spawned) throws GameActionException {
        // this method writes a bit signifying that we have spawned a trapper bot at the specified flag
        insertVal(Constants.TRAPPERS_SPAWNED_IDX, 1 << flagIndex, flagIndex, spawned);
    }


    public int readTrapper(int flagIndex) throws GameActionException {
        // this method reads a bit signifying, if true, that we have spawned a trapper bot at the specified flag
        return extractVal(Constants.TRAPPERS_SPAWNED_IDX, 1 << flagIndex, flagIndex);
    }


    public boolean getHomeFlagTakenStatus(int flagIndex) throws GameActionException{
        // this method returns a boolean
        return extractVal(Constants.DEFAULT_FLAG_LOCS_INDICES[flagIndex],
                Constants.DEFAULT_FLAG_TAKEN_MASK,
                Constants.DEFAULT_FLAG_TAKEN_SHIFT) == 1;
    }


//    private void insertVal(int commsIdx, int mask, int shift, int value) throws GameActionException {
        public void writeHomeFlagTakenStatus(int flagIndex, boolean taken) throws GameActionException {
        int valToWrite = 0;
        if(taken) valToWrite = 1;
        insertVal(
                Constants.DEFAULT_FLAG_LOCS_INDICES[flagIndex],
                Constants.DEFAULT_FLAG_TAKEN_MASK,
                Constants.DEFAULT_FLAG_TAKEN_SHIFT,
                valToWrite);
    }


    public void setAllHomeFlags_NotTaken() throws GameActionException{
        for(int i=0; i < 3; i++){
            writeHomeFlagTakenStatus(i, false);
        }
    }
}
