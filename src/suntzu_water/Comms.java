package suntzu_water;

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


    public void writeKnownOppFlagLocFromFlagID(MapLocation newFlagLoc, boolean carried, int flagID) throws GameActionException{
        int flagIdx = Util.getItemIndexInArray(flagID, getOppFlagIDArray());
        Util.assert_wrapper(flagIdx != -1);
        writeKnownOppFlagLoc(newFlagLoc, carried, flagIdx);
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

    public void removeKnownOppFlagLocFromIdx(int flagIdx) throws GameActionException{
        // this method removes a known opponent flag location from the broadcast
        // used if we notice a flag is no longer there
        writeKnownOppFlagLoc(null, false, flagIdx);
    }

    public void removeKnownOppFlagLocFromId(int flagId) throws GameActionException{
        // this method removes a known opponent flag location from the broadcast
        // used if we notice a flag is no longer there
        int flagIdx = Util.getItemIndexInArray(flagId, getOppFlagIDArray());
        if(flagIdx == -1){
            Util.LOGGING_ALLOWED = true;
            robot.testLog();
            Util.logArray("Comms opp flag array:", getOppFlagIDArray());
            //Util.log("Flag ID:" + flagId);
            System.out.println("Failed while removing flag ID: " + flagId);
            Util.resign();
        }
        Util.assert_wrapper(flagIdx != -1);
        writeKnownOppFlagLoc(null, false, flagIdx);
    }

    public void setKnownOppFlagsToNull() throws GameActionException{
        // this method sets all known opponent flag locations to null (at the beginning of the game)
        for(int i=0; i<constants.KNOWN_OPP_FLAG_INDICES.length; i++){
            writeKnownOppFlagLoc(null, false, i);
        }
    }

    public MapLocation getTakenAllyFlag(int idx) throws GameActionException{
        // this method returns the location of a single known opponent flag (specified by idx)
        int trueIndex = constants.TAKEN_ALLY_FLAG_INDICES[idx];
        int x = extractVal(trueIndex, constants.TAKEN_ALLY_FLAG_X_MASK, constants.TAKEN_ALLY_FLAG_X_SHIFT);
        int y = extractVal(trueIndex, constants.TAKEN_ALLY_FLAG_Y_MASK, constants.TAKEN_ALLY_FLAG_Y_SHIFT);
        if(x == constants.LOCATION_NULL_VAL || y == constants.LOCATION_NULL_VAL){
            return null;
        }
        return new MapLocation(x, y);
    }

    public MapLocation[] getTakenAllyFlags() throws GameActionException{
        // this method returns the location of a single known opponent flag (specified by idx)
        return new MapLocation[]{
                getTakenAllyFlag(0),
                getTakenAllyFlag(1),
                getTakenAllyFlag(2),
        };
    }

    public void writeTakenAllyFlagLoc(MapLocation newFlagLoc, int idx) throws GameActionException{
        // this method writes the location of a single known opponent flag (specified by idx)
        // this method is used internally to the Comms class
        //Util.log("Writing ally flag loc " + newFlagLoc + " to index " + idx);
        int trueIndex = constants.TAKEN_ALLY_FLAG_INDICES[idx];
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
        insertVal(trueIndex, constants.TAKEN_ALLY_FLAG_X_MASK, constants.TAKEN_ALLY_FLAG_X_SHIFT, x);

        // set the y value
        insertVal(trueIndex, constants.TAKEN_ALLY_FLAG_Y_MASK, constants.TAKEN_ALLY_FLAG_Y_SHIFT, y);
    }

    public void writeTakenAllyFlagLoc(MapLocation newFlagLoc) throws GameActionException{
        for(int i = 0; i < constants.TAKEN_ALLY_FLAG_INDICES.length; i++){
            if(getTakenAllyFlag(i) == null){
                writeTakenAllyFlagLoc(newFlagLoc, i);
                return;
            }
        }

//        // Worst-case scenario T_T. TODO: Instead of doing this, keep track of the flag IDs
//        writeTakenAllyFlagLoc(newFlagLoc, 2);
    }

    public void removeTakenAllyFlag(MapLocation flagLoc) throws GameActionException{
        // this method removes a known opponent flag location from the broadcast
        // used if we notice a flag is no longer there
        for(int i=0; i<constants.TAKEN_ALLY_FLAG_INDICES.length; i++){
            MapLocation takenFlagLoc = getTakenAllyFlag(i);
            if(takenFlagLoc != null && takenFlagLoc.equals(flagLoc)){
                writeTakenAllyFlagLoc(null, i);
            }
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

        int x0 = extractVal(Constants.DEFAULT_FLAG_LOC_0_IDX, Constants.DEFAULT_FLAG_LOC_X_MASK, Constants.DEFAULT_FLAG_LOC_X_SHIFT);
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
                constants.SHARED_DEFENSIVE_TARGET_X_SHIFT);

        int y = extractVal(
                constants.SHARED_DEFENSIVE_TARGET_IDX,
                constants.SHARED_DEFENSIVE_TARGET_Y_MASK,
                constants.SHARED_DEFENSIVE_TARGET_Y_SHIFT);

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

    public int readNumTrapsForFlag(int flagIdx) throws GameActionException{
        // Max value of 31.
        return extractVal(Constants.TRAP_COUNT_IDX, Constants.TRAP_COUNT_MASKS[flagIdx], Constants.TRAP_COUNT_SHIFTS[flagIdx]);
    }

    public void writeNumTrapsForFlag(int flagIdx, int count) throws GameActionException{
        // Max value of 31.
        count = Math.min(count, Constants.MAX_NUM_OF_TRAPS_COMMABLE);
        insertVal(Constants.TRAP_COUNT_IDX, Constants.TRAP_COUNT_MASKS[flagIdx], Constants.TRAP_COUNT_SHIFTS[flagIdx], count);
    }

    /////////// comms methods for default opp flag locations//////////////////////////////////////////////////////
    public void setAllDefaultOppFlagLocsToNull() throws GameActionException{
        // this method sets all the default opp flag locs to null at the beginning of the game
        // (since we can have no idea where the opp flags are until round 200)
        for(int i=0; i<3; i++){

            // insert nullValue for the flag ID
            insertVal(Constants.OPP_FLAG_ID_INDICES[i],
                    Constants.MASK_FOR_OPP_FLAG_ID,
                    Constants.SHIFT_FOR_OPP_FLAG_ID,
                    Constants.NULL_FLAG_ID_VAL);

            insertVal(Constants.DEFAULT_OPP_FLAG_INFO_INDICES[i],
                    Constants.DEFAULT_OPP_FLAG_X_MASK,
                    Constants.DEFAULT_OPP_FLAG_X_SHIFT,
                    Constants.LOCATION_NULL_VAL);

            insertVal(Constants.DEFAULT_OPP_FLAG_INFO_INDICES[i],
                    Constants.DEFAULT_OPP_FLAG_Y_MASK,
                    Constants.DEFAULT_OPP_FLAG_Y_SHIFT,
                    Constants.LOCATION_NULL_VAL);
        }
    }


    public int[] getOppFlagIDArray() throws GameActionException{
        int[] oppFlagIDs = new int[3];
        for(int i=0; i<3; i++){
            oppFlagIDs[i] = extractVal(
                    Constants.OPP_FLAG_ID_INDICES[i],
                    Constants.MASK_FOR_OPP_FLAG_ID,
                    Constants.SHIFT_FOR_OPP_FLAG_ID);
        }
        return oppFlagIDs;
    }

    public void writeDefaultOppFlagLocationIfNotSeenBefore(MapLocation defaultFlagLoc, int flagID) throws GameActionException {
        // this method saves the default location of an opponent flag to comms
        int firstNullIndex = -1;

        for(int i=0; i<3; i++){
            int currFlagId = extractVal(
                    Constants.OPP_FLAG_ID_INDICES[i],
                    Constants.MASK_FOR_OPP_FLAG_ID,
                    Constants.SHIFT_FOR_OPP_FLAG_ID);
            if(currFlagId == flagID) return;    // we've already seen this flagID, so we should already have its default location
            else if(currFlagId == Constants.NULL_FLAG_ID_VAL && firstNullIndex == -1) firstNullIndex = i;
        }

        System.out.println("First time flag " + flagID + " was spotted at location " + defaultFlagLoc);

        // if we get here, then we haven't seen this flagID before, so we need to add it
        if(firstNullIndex != -1){
            // we have a null index, so we can write the default location to that index
            insertVal(Constants.OPP_FLAG_ID_INDICES[firstNullIndex],
                    Constants.MASK_FOR_OPP_FLAG_ID,
                    Constants.SHIFT_FOR_OPP_FLAG_ID,
                    flagID);

            insertVal(Constants.DEFAULT_OPP_FLAG_INFO_INDICES[firstNullIndex],
                    Constants.DEFAULT_OPP_FLAG_X_MASK,
                    Constants.DEFAULT_OPP_FLAG_X_SHIFT,
                    defaultFlagLoc.x);

            insertVal(Constants.DEFAULT_OPP_FLAG_INFO_INDICES[firstNullIndex],
                    Constants.DEFAULT_OPP_FLAG_Y_MASK,
                    Constants.DEFAULT_OPP_FLAG_Y_SHIFT,
                    defaultFlagLoc.y);
        }
    }


    public MapLocation[] getDefaultOppFlagLocations() throws GameActionException{
        // this method will read all the default Opp flag locations and return them an array in size 3
        // (with nulls if we don't know them yet)
        MapLocation[] defaultOppFlagLocations = new MapLocation[3];
        for(int i = 0; i < 3; i++){
            int x = extractVal(Constants.DEFAULT_OPP_FLAG_INFO_INDICES[i],
                    Constants.DEFAULT_OPP_FLAG_X_MASK,
                    Constants.DEFAULT_OPP_FLAG_X_SHIFT);

            int y = extractVal(Constants.DEFAULT_OPP_FLAG_INFO_INDICES[i],
                    Constants.DEFAULT_OPP_FLAG_Y_MASK,
                    Constants.DEFAULT_OPP_FLAG_Y_SHIFT);

            int captured = extractVal(Constants.DEFAULT_OPP_FLAG_INFO_INDICES[i],
                    Constants.OPP_FLAG_CAPTURED_MASK,
                    Constants.OPP_FLAG_CAPTURED_SHIFT);

            if(x == Constants.LOCATION_NULL_VAL || y == Constants.LOCATION_NULL_VAL || captured == 1){
                defaultOppFlagLocations[i] = null;
            }
            else{
                defaultOppFlagLocations[i] = new MapLocation(x, y);
            }
        }
        return defaultOppFlagLocations;
    }


    public void setOppFlagToCaptured(int flagID) throws GameActionException {
        // this sets the captured bit corresponding to the opponenet flag that has its ID as flagID to true
        // used by a robot as it walks into the spawnLocation
        //Util.log("Setting flag " + flagID + " to captured");
        int index = 0;
        while(index < 3){
            int currFlagID = extractVal(
                    Constants.OPP_FLAG_ID_INDICES[index],
                    Constants.MASK_FOR_OPP_FLAG_ID,
                    Constants.SHIFT_FOR_OPP_FLAG_ID);

            if(currFlagID == flagID){
                insertVal(
                        Constants.DEFAULT_OPP_FLAG_INFO_INDICES[index],
                        Constants.OPP_FLAG_CAPTURED_MASK,
                        Constants.OPP_FLAG_CAPTURED_SHIFT,
                        1);
                return;
            }
            index++;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // comms for flag snapback
    public boolean getFlagSnapbackAllowed() throws GameActionException {
        return extractVal(Constants.FLAG_SNAPBACK_IDX, Constants.FLAG_SNAPBACK_MASK, Constants.FLAG_SNAPBACK_SHIFT) == 1;
    }
    public void writeFlagSnapBackAllowed(int isAllowed) throws GameActionException {
        insertVal(Constants.FLAG_SNAPBACK_IDX, Constants.FLAG_SNAPBACK_MASK, Constants.FLAG_SNAPBACK_SHIFT, isAllowed);
    }

}
