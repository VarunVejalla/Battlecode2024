package genghis_nav;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class Comms {

    RobotController rc;
    Robot robot;
    Constants constants;
    private int trapperCountShift;

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

        int x0 = extractVal(Constants.DEFAULT_FLAG_LOC_0_IDX, Constants.DEFAULT_FLAG_LOC_X_MASK, Constants.DEFAULT_FLAG_LOC_X_SHIFT); // TODO: Make these constants.
        int y0 = extractVal(Constants.DEFAULT_FLAG_LOC_0_IDX, Constants.DEFAULT_FLAG_LOC_Y_MASK, Constants.DEFAULT_FLAG_LOC_Y_SHIFT);

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


    public void writeDefaultHomeFlagLocs(int flagIdx, MapLocation flagLoc) throws GameActionException {
        // Note: a defaultFlagLocation is a location where our flags will be by default after round 200 (if they are not taken)
        insertVal(Constants.DEFAULT_FLAG_LOCS_INDICES[flagIdx], Constants.DEFAULT_FLAG_LOC_X_MASK, Constants.DEFAULT_FLAG_LOC_X_SHIFT, flagLoc.x);
        insertVal(Constants.DEFAULT_FLAG_LOCS_INDICES[flagIdx], Constants.DEFAULT_FLAG_LOC_Y_MASK, Constants.DEFAULT_FLAG_LOC_Y_SHIFT, flagLoc.y);
    }


    public MapLocation getDefaultHomeFlagLoc(int flagIdx) throws GameActionException {
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

    public void writeHomeFlagTakenStatus(int flagIndex, boolean taken) throws GameActionException {
        int valToWrite = 0;
        if(taken) valToWrite = 1;
        insertVal(
                Constants.DEFAULT_FLAG_LOCS_INDICES[flagIndex],
                Constants.DEFAULT_FLAG_TAKEN_MASK,
                Constants.DEFAULT_FLAG_TAKEN_SHIFT,
                valToWrite);
    }

    public boolean getOurFlagNewHomeStatus(int flagIndex) throws GameActionException{
        // this method returns a boolean
        return extractVal(Constants.DEFAULT_FLAG_LOCS_INDICES[flagIndex],
                Constants.FLAG_PLACED_NEW_HOME_MASK,
                Constants.FLAG_PLACED_NEW_HOME_SHIFT) == 1;
    }

    public void writeOurFlagNewHomeStatus(int flagIndex, boolean taken) throws GameActionException {
        int valToWrite = 0;
        if(taken) valToWrite = 1;
        insertVal(
                Constants.DEFAULT_FLAG_LOCS_INDICES[flagIndex],
                Constants.FLAG_PLACED_NEW_HOME_MASK,
                Constants.FLAG_PLACED_NEW_HOME_SHIFT,
                valToWrite);
    }


    public void setAllHomeFlags_NotTaken() throws GameActionException{
        for(int i=0; i < 3; i++){
            writeHomeFlagTakenStatus(i, false);
        }
    }


    public int[] readDistsToSpawnCenters() throws GameActionException{
        int[] dists = new int[3];
        for(int i = 0; i < 3; i++){
            dists[i] = extractVal(Constants.DIST_TO_SPAWN_CENTERS_IDX, Constants.DIST_TO_SPAWN_CENTER_MASKS[i], Constants.DIST_TO_SPAWN_CENTER_SHIFTS[i]);
            if(dists[i] == 0){
                dists[i] = Integer.MAX_VALUE;
            }
            else{
                dists[i] *= 2;
            }
        }

        return dists;
    }

    public void writeDistsToSpawnCenters(int[] dists) throws GameActionException{
        for(int i = 0; i < 3; i++){
            int val = 0;
            if(dists[i] != Integer.MAX_VALUE){
                val = dists[i] / 2;
            }
            insertVal(Constants.DIST_TO_SPAWN_CENTERS_IDX, Constants.DIST_TO_SPAWN_CENTER_MASKS[i], Constants.DIST_TO_SPAWN_CENTER_SHIFTS[i], val);
        }
    }

    public boolean readScoutCountEven() throws GameActionException{
        return extractVal(Constants.DIST_TO_SPAWN_CENTERS_IDX, Constants.SCOUT_EVEN_MASK, Constants.SCOUT_EVEN_SHIFT) == 0;
    }

    public void addToScoutCountEven() throws GameActionException{
        int countEven = readScoutCountEven() ? 0 : 1;
        insertVal(Constants.DIST_TO_SPAWN_CENTERS_IDX, Constants.SCOUT_EVEN_MASK, Constants.SCOUT_EVEN_SHIFT, 1 - countEven);
    }

    public MapLocation readNewHomeFlagCenter() throws GameActionException {
        boolean setHomeCenter = extractVal(Constants.NEW_HOME_FLAG_CENTER_IDX, Constants.NEW_HOME_FLAG_CENTER_SET_BIT_MASK, Constants.NEW_HOME_FLAG_CENTER_SET_BIT_SHIFT) == 1;
        if(!setHomeCenter){
            return null;
        }
        int x = extractVal(Constants.NEW_HOME_FLAG_CENTER_IDX, Constants.NEW_HOME_FLAG_CENTER_X_MASK, Constants.NEW_HOME_FLAG_CENTER_X_SHIFT);
        int y = extractVal(Constants.NEW_HOME_FLAG_CENTER_IDX, Constants.NEW_HOME_FLAG_CENTER_Y_MASK, Constants.NEW_HOME_FLAG_CENTER_Y_SHIFT);
        return new MapLocation(x, y);
    }

    public void writeNewHomeFlagCenter(MapLocation loc) throws GameActionException {
        insertVal(Constants.NEW_HOME_FLAG_CENTER_IDX, Constants.NEW_HOME_FLAG_CENTER_SET_BIT_MASK, Constants.NEW_HOME_FLAG_CENTER_SET_BIT_SHIFT, 1);
        insertVal(Constants.NEW_HOME_FLAG_CENTER_IDX, Constants.NEW_HOME_FLAG_CENTER_X_MASK, Constants.NEW_HOME_FLAG_CENTER_X_SHIFT, loc.x);
        insertVal(Constants.NEW_HOME_FLAG_CENTER_IDX, Constants.NEW_HOME_FLAG_CENTER_Y_MASK, Constants.NEW_HOME_FLAG_CENTER_Y_SHIFT, loc.y);
    }

    //---------------------------------------------------------
    //////////////////////////////////////////////////////////////////////////////////////////
    // methods for reading and writing soldier type ratios and

    public int getBotCount(Mode mode) throws GameActionException {
        if(mode == Mode.OFFENSE){
            return extractVal(
                    Constants.OFFENSIVE_COUNT_INDEX,
                    Constants.OFFENSIVE_COUNT_MASK,
                    Constants.OFFENSIVE_COUNT_SHIFT);
        }

        if(mode == Mode.MOBILE_DEFENSE){
            return extractVal(
                    Constants.MOBILE_DEFENDER_COUNT_INDEX,
                    Constants.MOBILE_DEFENDER_COUNT_MASK,
                    Constants.MOBILE_DEFENDER_COUNT_SHIFT);
        }

        else if(mode == Mode.STATIONARY_DEFENSE){
            return extractVal(
                    Constants.STATIONARY_DEFENDER_COUNT_INDEX,
                    Constants.STATIONARY_DEFENDER_COUNT_MASK,
                    Constants.STATIONARY_DEFENDER_COUNT_SHIFT);
        }

        else if(mode == Mode.TRAPPING){
            return extractVal(
                    Constants.TRAPPER_COUNT_INDEX,
                    Constants.TRAPPER_COUNT_MASK,
                    Constants.TRAPPER_COUNT_SHIFT);
        }

        return -1;
    }


    public void writeBotCount(Mode mode, int count) throws GameActionException {
        if(mode == Mode.OFFENSE){
            insertVal(
                    Constants.OFFENSIVE_COUNT_INDEX,
                    Constants.OFFENSIVE_COUNT_MASK,
                    Constants.OFFENSIVE_COUNT_SHIFT,
                    count);
        }

        else if(mode == Mode.MOBILE_DEFENSE){
            insertVal(
                    Constants.MOBILE_DEFENDER_COUNT_INDEX,
                    Constants.MOBILE_DEFENDER_COUNT_MASK,
                    Constants.MOBILE_DEFENDER_COUNT_SHIFT,
                    count);
        }

        else if(mode == Mode.STATIONARY_DEFENSE){
            insertVal(
                    Constants.STATIONARY_DEFENDER_COUNT_INDEX,
                    Constants.STATIONARY_DEFENDER_COUNT_MASK,
                    Constants.STATIONARY_DEFENDER_COUNT_SHIFT,
                    count);
        }

        else if(mode == Mode.TRAPPING){
            insertVal(
                    Constants.TRAPPER_COUNT_INDEX,
                    Constants.TRAPPER_COUNT_MASK,
                    Constants.TRAPPER_COUNT_SHIFT,
                    count);
        }
    }

    public void incrementBotCount(Mode mode) throws GameActionException{
        int count = getBotCount(mode);
        writeBotCount(mode, count+1);
    }

    public void decrementBotCount(Mode mode) throws GameActionException {
        int count = getBotCount(mode);
        writeBotCount(mode, count-1);
    }


    public void writeRatioVal(Mode mode, int ratioVal) throws GameActionException{
        // Note: ratioVal argument should be between 0 - 15
        if(mode == Mode.OFFENSE){
            insertVal(
                    Constants.BOT_RATIO_INDEX,
                    Constants.OFFENSIVE_RATIO_MASK,
                    Constants.OFFENSIVE_RATIO_SHIFT,
                    ratioVal);
        }

        else if(mode == Mode.MOBILE_DEFENSE){
            insertVal(
                    Constants.BOT_RATIO_INDEX,
                    Constants.MOBILE_DEFENDER_RATIO_MASK,
                    Constants.MOBILE_DEFENDER_RATIO_SHIFT,
                    ratioVal);
        }

        else if(mode == Mode.STATIONARY_DEFENSE){
            insertVal(
                    Constants.BOT_RATIO_INDEX,
                    Constants.STATIONARY_DEFENDER_RATIO_MASK,
                    Constants.STATIONARY_DEFENDER_RATIO_SHIFT,
                    ratioVal);
        }

        else if(mode == Mode.TRAPPING){
            insertVal(
                    Constants.BOT_RATIO_INDEX,
                    Constants.TRAPPER_RATIO_MASK,
                    Constants.TRAPPER_RATIO_SHIFT,
                    ratioVal);
        }
    }

    public int readRatioVal(Mode mode) throws GameActionException {
        if(mode == Mode.OFFENSE){
            return extractVal(
                    Constants.BOT_RATIO_INDEX,
                    Constants.OFFENSIVE_RATIO_MASK,
                    Constants.OFFENSIVE_RATIO_SHIFT);
        }

        else if(mode == Mode.MOBILE_DEFENSE){
            return extractVal(
                    Constants.BOT_RATIO_INDEX,
                    Constants.MOBILE_DEFENDER_RATIO_MASK,
                    Constants.MOBILE_DEFENDER_RATIO_SHIFT);
        }

        else if(mode == Mode.STATIONARY_DEFENSE){
            return extractVal(
                    Constants.BOT_RATIO_INDEX,
                    Constants.STATIONARY_DEFENDER_RATIO_MASK,
                    Constants.STATIONARY_DEFENDER_RATIO_SHIFT);
        }

        else if(mode == Mode.TRAPPING){
            return extractVal(
                    Constants.BOT_RATIO_INDEX,
                    Constants.TRAPPER_RATIO_MASK,
                    Constants.TRAPPER_RATIO_SHIFT);
        }
        return -1;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // comms for shared defensive target
    public MapLocation getSharedDefensiveTarget() throws GameActionException {
        // this method returns the location of the shared offensive target
        int x = extractVal(
                constants.SHARED_DEFENSIVE_TARGET_IDX,
                constants.SHARED_DEFENSIVE_TARGET_X_MASK,
                constants.SHARED_DEFENSIVE_TARGET_Y_MASK);

        int y = extractVal(
                constants.SHARED_DEFENSIVE_TARGET_IDX,
                constants.SHARED_DEFENSIVE_TARGET_X_MASK,
                constants.SHARED_DEFENSIVE_TARGET_Y_MASK);

        if(x == constants.LOCATION_NULL_VAL || y == constants.LOCATION_NULL_VAL){
            return null;
        }

        else {
            return new MapLocation(x, y);
        }
    }


    public void writeSharedDefensiveTarget(MapLocation loc) throws GameActionException {
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
                constants.SHARED_DEFENSIVE_TARGET_IDX,
                constants.SHARED_DEFENSIVE_TARGET_X_MASK,
                constants.SHARED_DEFENSIVE_TARGET_X_SHIFT,
                x);

        insertVal(
                constants.SHARED_DEFENSIVE_TARGET_IDX,
                constants.SHARED_DEFENSIVE_TARGET_Y_MASK,
                constants.SHARED_DEFENSIVE_TARGET_Y_SHIFT,
                y);
    }

    public int readNumDefendersForFlag(int flagIdx) throws GameActionException{
        // Max value of 31.
        return extractVal(Constants.NUM_DEFENDERS_FOR_FLAG_IDX, Constants.NUM_DEFENDERS_FOR_FLAG_MASKS[flagIdx], Constants.NUM_DEFENDERS_FOR_FLAG_SHIFTS[flagIdx]);
    }

    public void writeNumDefendersForFlag(int flagIdx, int count) throws GameActionException{
        // Max value of 31.
        insertVal(Constants.NUM_DEFENDERS_FOR_FLAG_IDX, Constants.NUM_DEFENDERS_FOR_FLAG_MASKS[flagIdx], Constants.NUM_DEFENDERS_FOR_FLAG_SHIFTS[flagIdx], count);
    }

    public void incrementNumDefendersForFlag(int flagIdx) throws GameActionException{
        writeNumDefendersForFlag(flagIdx, readNumDefendersForFlag(flagIdx) + 1);
    }

    public void decrementNumDefendersForFlag(int flagIdx) throws GameActionException{
        writeNumDefendersForFlag(flagIdx, readNumDefendersForFlag(flagIdx) - 1);
    }

}
