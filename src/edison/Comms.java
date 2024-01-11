package edison;

import battlecode.common.*;


//TODO: need to completely refactor this class to use bit shifting to use less space
// also need to move all constants to a separate class (Constants.java)
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
        for(int i=0; i<oppFlagsLocs.length; i++){
            setApproxOppFlag(i, oppFlagsLocs[i]);
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

    private MapLocation readKnownOppFlag(int idx) throws GameActionException{
        // this method returns the location of a single known opponent flag (specified by idx)
        int trueIndex = constants.KNOWN_OPP_FLAG_INDICES[idx];
        int x = extractVal(trueIndex, constants.KNOWN_OPP_FLAG_X_MASK, constants.KNOWN_OPP_FLAG_X_SHIFT);
        int y = extractVal(trueIndex, constants.KNOWN_OPP_FLAG_Y_MASK, constants.KNOWN_OPP_FLAG_Y_SHIFT);
        if(x == constants.LOCATION_NULL_VAL || y == constants.LOCATION_NULL_VAL){
            return null;
        } 
        return new MapLocation(x, y);
    }

    private boolean readCarriedStatus_KnownOppFlag(int idx) throws GameActionException{
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
            MapLocation knownFlagLoc = readKnownOppFlag(i);
            if(knownFlagLoc != null && knownFlagLoc.equals(newFlagLoc)){
                writeCarriedStatus_KnownOppFlag(i, carried);
                return;
            }
        }

        // if we get here, then we didn't find the flag in the broadcast
        // so we need to add it
        for(int i=0; i<constants.KNOWN_OPP_FLAG_INDICES.length; i++){
            MapLocation knownFlagLoc = readKnownOppFlag(i);
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
            knownFlags[i] = readKnownOppFlag(i);
        }
        return knownFlags;
    }
    

    private MapLocation[] getKnownOppFlags(boolean carried) throws GameActionException {
        // this method returns an array of locations of opponent flags that are being carried by friendly robots
        MapLocation[] knownFlags = new MapLocation[3];
        
        for(int i=0; i<constants.KNOWN_OPP_FLAG_INDICES.length; i++){
            MapLocation flagLoc = readKnownOppFlag(i);
            if(flagLoc != null){
                if(readCarriedStatus_KnownOppFlag(i) == carried){
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
            MapLocation knownFlagLoc = readKnownOppFlag(i);
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

}
