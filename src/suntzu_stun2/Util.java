package suntzu_stun2;

import battlecode.common.*;

public class Util {

    static RobotController rc;
    static Robot robot;
    static boolean LOGGING_ALLOWED = true;
    static boolean SUBMISSION_MODE = false; // TODO: Set this to true when submitting.

    public static void resign(){
        if(!SUBMISSION_MODE){
            System.out.println("Resigning " + rc.getID() + ", " + robot.mode + ", " + rc.getLocation());
            rc.resign();
        }
    }

    public static void assert_wrapper(boolean bool){
        if(!SUBMISSION_MODE){
            if(!bool){
                System.out.println("Assert failed " + rc.getID() + ", " + robot.mode + ", " + rc.getLocation());
            }
            assert(bool);
        }
    }

    public static MapLocation getClosestHomeFlag() throws GameActionException {
        if(robot.defaultHomeFlagLocs == null){
            return null;
        }
        MapLocation closest = null;
        int closestDist = Integer.MAX_VALUE;
        for(int i = 0; i < robot.defaultHomeFlagLocs.length; i++){
            if(robot.comms.getHomeFlagTakenStatus(i)){
                continue;
            }
            if(robot.defaultHomeFlagLocs[i] == null){
                continue;
            }
            int dist = robot.myLoc.distanceSquaredTo(robot.defaultHomeFlagLocs[i]);
            if(dist < closestDist){
                closestDist = dist;
                closest = robot.defaultHomeFlagLocs[i];
            }
        }
        return closest;
    }

    public static int minMovesToReach(MapLocation a, MapLocation b){
        int dx = a.x - b.x;
        int dy = a.y - b.y;
        return Math.max(Math.abs(dx), Math.abs(dy));
    }

    public static MapLocation getRandomLocation(){
        return new MapLocation(robot.rng.nextInt(rc.getMapWidth()), robot.rng.nextInt(rc.getMapHeight()));
    }

    public static boolean tryMove(Direction dir, int minCrumbsToFill) throws GameActionException{
        if(rc.getCrumbs() >= minCrumbsToFill + Constants.FILL_CRUMB_COST && rc.canFill(rc.adjacentLocation(dir))) {
            rc.fill(rc.adjacentLocation(dir));
        }
        if(rc.canMove(dir)) {
            rc.move(dir);
            robot.myLoc = rc.getLocation();
            robot.myLocInfo = rc.senseMapInfo(robot.myLoc);
            return true;
        }
        return false;
    }

    public static boolean tryMove(Direction dir, boolean waterFillingAllowed) throws GameActionException{
        if(waterFillingAllowed && rc.canFill(rc.adjacentLocation(dir))) {
            rc.fill(rc.adjacentLocation(dir));
        }
        if(rc.canMove(dir)) {
            rc.move(dir);
            robot.myLoc = rc.getLocation();
            robot.myLocInfo = rc.senseMapInfo(robot.myLoc);
            return true;
        }
        return false;
    }

    public static boolean canMove(Direction dir, boolean waterFillingAllowed) throws GameActionException{
        MapLocation adjLoc = rc.getLocation().add(dir);
        if(!rc.isMovementReady()){
            return false;
        }
        if(!rc.canSenseLocation(adjLoc)){
            return false;
        }
        if(rc.isLocationOccupied(adjLoc)){
            return false;
        }
        MapInfo adjInfo = rc.senseMapInfo(adjLoc);
        if(adjInfo.isWater() && waterFillingAllowed && rc.canFill(adjLoc)) {
            return true;
        }
        if(adjInfo.isPassable()){
            return true;
        }
        return false;
    }

    public static boolean tryMove(Direction dir) throws GameActionException{
        return tryMove(dir, 0);
    }

    public static void addToIndicatorString(String str){
        robot.indicatorString += str + ";";
    }

    public static void printBytecode(String prefix){
        Util.log(prefix + ": " + Clock.getBytecodesLeft());
    }

    public static int countBotsOfTeam(Team team, RobotInfo[] bots){
        int count = 0;
        for(RobotInfo bot : bots){
            if(bot.getTeam() == team){
                count++;
            }
        }
        return count;
    }

    public static <T> int getItemIndexInArray(T item, T[] array){
        // helper method to get the index of an item in an array
        for(int i = 0; i < array.length; i++){
            T arrayItem = array[i];
            if(arrayItem != null && arrayItem.equals(item)){
                return i;
            }
        }
        return -1;
    }

    public static int getItemIndexInArray(int item, int[] array){
        // helper method to get the index of an item in an array
        for(int i = 0; i < array.length; i++){
            int arrayItem = array[i];
            if(arrayItem == item){
                return i;
            }
        }
        return -1;
    }


    public static <T> boolean checkIfItemInArray(T item, T[] array){
        return getItemIndexInArray(item, array) != -1;
    }


    public static <T> void logArray(String name, T[] array){
        if(!LOGGING_ALLOWED){
            return;
        }
        // helper method to display array of any type to the logs
        String out = "";
        out += name + ": ";
        for(int i=0; i<array.length; i++){
            if(i == 0){ // first element
                out += "["+array[i] + ", ";
            }
            else if(i==array.length-1){ // last element
                out += array[i] + "]";
            }
            else{   // other elements
                out += array[i] + ", ";
            }
        }
        System.out.println(out);
    }

    public static void logArray(String name, int[] arr){
        logArray(name, intToIntegerArray(arr));
    }

    public static Integer[] intToIntegerArray(int[] arr){
        Integer[] ret = new Integer[arr.length];
        for(int i = 0; i < ret.length; i++){
            ret[i] = arr[i];
        }
        return ret;
    }

    public static void fillTrue(boolean[][] arr, MapLocation center, int radiusSquared) {
        int ceiledRadius = (int) Math.ceil(Math.sqrt(radiusSquared)) + 1; // add +1 just to be safe
        int minX = Math.max(center.x - ceiledRadius, 0);
        int minY = Math.max(center.y - ceiledRadius, 0);
        int maxX = Math.min(center.x + ceiledRadius, rc.getMapWidth() - 1);
        int maxY = Math.min(center.y + ceiledRadius, rc.getMapHeight() - 1);
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                MapLocation newLocation = new MapLocation(x, y);
                if(center.isWithinDistanceSquared(newLocation, radiusSquared)){
                    arr[x][y] = true;
                }
            }
        }
    }

    public static MapLocation getNearestHomeSpawnLoc(MapLocation loc) throws GameActionException{
        MapLocation[] homeSpawnLocs = rc.getAllySpawnLocations();
        int minDist = Integer.MAX_VALUE;
        MapLocation nearestHomeSpawnLoc = null;
        for(MapLocation homeSpawnLoc : homeSpawnLocs){
            int dist = loc.distanceSquaredTo(homeSpawnLoc);
            if(dist < minDist){
                minDist = dist;
                nearestHomeSpawnLoc = homeSpawnLoc;
            }
        }
        return nearestHomeSpawnLoc;
    }

    public static void spawnClosestToLocation(MapLocation targetLoc) throws GameActionException {
        MapLocation spawnLoc = null;
        int bestDist = Integer.MAX_VALUE;
        for(MapLocation potentialSpawnLoc : robot.allSpawnLocs){
            if(!rc.canSpawn(potentialSpawnLoc)){
                continue;
            }
            int dist = potentialSpawnLoc.distanceSquaredTo(targetLoc);
            if(dist < bestDist){
                spawnLoc = potentialSpawnLoc;
                bestDist = dist;
            }
        }
        if(spawnLoc != null){
            rc.spawn(spawnLoc);
        }
    }

    // TODO: fix the right and left diagonal symmetry cases
    public static MapLocation applySymmetry(MapLocation loc, SymmetryType type){
        int width = rc.getMapWidth();
        int height = rc.getMapHeight();
        switch(type){
            case HORIZONTAL:
                return new MapLocation(width - loc.x - 1, loc.y);
            case VERTICAL:
                return new MapLocation(loc.x, height - loc.y - 1);
            case ROTATIONAL:
                return new MapLocation(width - loc.x - 1, height - loc.y - 1);
            case DIAGONAL_RIGHT:
                int newY = Math.min(loc.x, height - 1);
                int newX = Math.min(loc.y, width - 1);
                return new MapLocation(newX, newY);
            case DIAGONAL_LEFT:
                return new MapLocation(
                        Math.min(height - loc.y - 1, width - 1),
                        Math.min(width - loc.x - 1, height - 1));
        }
        return null;
    }

    public static int encodeMapLocation(MapLocation loc){
        return loc.x * (robot.mapHeight + 1) + loc.y;
    }

    public static int encodeMapLocation(int x, int y){
        return x * (robot.mapHeight + 1) + y;
    }

    public static MapLocation decodeMapLocation(int code){
        return new MapLocation(code / (robot.mapHeight + 1), code % (robot.mapHeight + 1));
    }

    public static int directionToInt(Direction dir){
        switch(dir){
            case NORTH:
                return 0;
            case NORTHEAST:
                return 1;
            case EAST:
                return 2;
            case SOUTHEAST:
                return 3;
            case SOUTH:
                return 4;
            case SOUTHWEST:
                return 5;
            case WEST:
                return 6;
            case NORTHWEST:
                return 7;
            default:
                return Integer.MAX_VALUE;
        }
    }

    // Assumes neither one is center.
    public static int directionDistance(Direction a, Direction b){
        return Math.min(directionDistanceLeft(a, b), directionDistanceRight(a, b));
    }

    public static int directionDistanceLeft(Direction a, Direction b){
        return 8 - directionDistanceRight(a, b);
    }

    public static int directionDistanceRight(Direction a, Direction b){
        int ai = directionToInt(a);
        int bi = directionToInt(b);
        int dist = bi - ai;
        if(dist < 0){
            dist += 8;
        }
        return dist;
    }

    // NOTE: Takes a worst-case of 10,000 bytecode to run.
    public static MapLocation[] getSpawnLocCenters(){
        int spawnCenterIdx = 0;
        MapLocation[] spawnCenters = new MapLocation[3];

        boolean[][] spawnLocMap = new boolean[rc.getMapWidth()][rc.getMapHeight()];
        MapLocation[] spawnLocs = robot.allSpawnLocs;

        for(int i = 0; i < spawnLocs.length; i++){
            spawnLocMap[spawnLocs[i].x][spawnLocs[i].y] = true;
        }

        for(int i = 0; i < spawnLocs.length; i++){
            int x = spawnLocs[i].x;
            int y = spawnLocs[i].y;

            // check if moving in any direction will go off the map
            // without this check, bot errors out on maps where spawn locs are on the edge
            if(x == 0 || x == rc.getMapWidth() - 1 || y == 0 || y == rc.getMapHeight() - 1){
                continue;
            }

            if(spawnLocMap[x - 1][y]
                && spawnLocMap[x + 1][y]
                && spawnLocMap[x][y - 1]
                && spawnLocMap[x][y + 1]){
                spawnCenters[spawnCenterIdx] = spawnLocs[i];
                spawnCenterIdx++;
            }
        }

        if(spawnCenterIdx < 3){
            System.out.println("Not all spawn centers found in getSpawnLocCenters");
            Util.resign();
        }

        return spawnCenters;
    }

    public static void log(String str){
        if(LOGGING_ALLOWED){
            System.out.println(str);
        }
    }

    public static void logBytecode(String str){
        Util.log(str + ": " + Clock.getBytecodesLeft());
    }

    public static void logBytecodeUsed(String str){
        Util.log(str + ": " + Clock.getBytecodeNum());
    }


    public static Direction[] closeDirections(Direction dir){
        return new Direction[]{
                dir,
                dir.rotateLeft(),
                dir.rotateRight(),
                dir.rotateLeft().rotateLeft(),
                dir.rotateRight().rotateRight(),
                dir.rotateLeft().rotateLeft().rotateLeft(),
                dir.rotateRight().rotateRight().rotateRight(),
                dir.opposite()
        };
    }

    public static boolean locIsASpawnLoc(MapLocation loc) throws GameActionException{
        // this method checks if the robot is on a spawn location
        for(MapLocation spawnCenter: robot.spawnCenters){
            if(Util.minMovesToReach(loc, spawnCenter) <= 1){
                return true;
            }
        }
        return false;
    }


    public static boolean checkIfDirIsCardinal(Direction dir){
        return dir == Direction.CENTER
                || dir == Direction.NORTH
                || dir == Direction.EAST
                || dir == Direction.SOUTH
                || dir == Direction.WEST;
    }


    // this method is a helper method to get the currentTarget of a robot based on its mode
    public static MapLocation getCurrentTarget() throws GameActionException {
        if(robot.mode == Mode.OFFENSE){
            return robot.offenseModule.sharedOffensiveTarget;
        }
        else if(robot.mode == Mode.MOBILE_DEFENSE){
            return robot.defenseModule.sharedDefensiveTarget;
        }

        else if(robot.mode == Mode.STATIONARY_DEFENSE && robot.defenseModule.defendingFlagIdx != -1){
            return robot.comms.getDefaultHomeFlagLoc(robot.defenseModule.defendingFlagIdx);
        }
        return null;
    }

    public static double getAttackDamage(RobotInfo robotInfo) throws GameActionException{
        // TODO: implement this method to take into account attack specializations
        // this method returns the attack damage of the robot given its specialization
        return 150.0;
    }


    public static double getAttackCooldown(RobotInfo robotInfo) throws GameActionException{
        // TODO: implement this method to take into account attack specializations
        return 20.0;
    }

    public static int getPositionDeltaToIdx(int xDelta, int yDelta){
        // converts position delta to index in array
        switch (xDelta) {
            case -5:
                switch (yDelta) {
                }
            case -4:
                switch (yDelta) {
                    case -2:
                        return 0;
                    case -1:
                        return 1;
                    case 0:
                        return 2;
                    case 1:
                        return 3;
                    case 2:
                        return 4;
                }
            case -3:
                switch (yDelta) {
                    case -3:
                        return 5;
                    case -2:
                        return 6;
                    case -1:
                        return 7;
                    case 0:
                        return 8;
                    case 1:
                        return 9;
                    case 2:
                        return 10;
                    case 3:
                        return 11;
                }
            case -2:
                switch (yDelta) {
                    case -4:
                        return 12;
                    case -3:
                        return 13;
                    case -2:
                        return 14;
                    case -1:
                        return 15;
                    case 0:
                        return 16;
                    case 1:
                        return 17;
                    case 2:
                        return 18;
                    case 3:
                        return 19;
                    case 4:
                        return 20;
                }
            case -1:
                switch (yDelta) {
                    case -4:
                        return 21;
                    case -3:
                        return 22;
                    case -2:
                        return 23;
                    case -1:
                        return 24;
                    case 0:
                        return 25;
                    case 1:
                        return 26;
                    case 2:
                        return 27;
                    case 3:
                        return 28;
                    case 4:
                        return 29;
                }
            case 0:
                switch (yDelta) {
                    case -4:
                        return 30;
                    case -3:
                        return 31;
                    case -2:
                        return 32;
                    case -1:
                        return 33;
                    case 0:
                        return 34;
                    case 1:
                        return 35;
                    case 2:
                        return 36;
                    case 3:
                        return 37;
                    case 4:
                        return 38;
                }
            case 1:
                switch (yDelta) {
                    case -4:
                        return 39;
                    case -3:
                        return 40;
                    case -2:
                        return 41;
                    case -1:
                        return 42;
                    case 0:
                        return 43;
                    case 1:
                        return 44;
                    case 2:
                        return 45;
                    case 3:
                        return 46;
                    case 4:
                        return 47;
                }
            case 2:
                switch (yDelta) {
                    case -4:
                        return 48;
                    case -3:
                        return 49;
                    case -2:
                        return 50;
                    case -1:
                        return 51;
                    case 0:
                        return 52;
                    case 1:
                        return 53;
                    case 2:
                        return 54;
                    case 3:
                        return 55;
                    case 4:
                        return 56;
                }
            case 3:
                switch (yDelta) {
                    case -3:
                        return 57;
                    case -2:
                        return 58;
                    case -1:
                        return 59;
                    case 0:
                        return 60;
                    case 1:
                        return 61;
                    case 2:
                        return 62;
                    case 3:
                        return 63;
                }
            case 4:
                switch (yDelta) {
                    case -2:
                        return 64;
                    case -1:
                        return 65;
                    case 0:
                        return 66;
                    case 1:
                        return 67;
                    case 2:
                        return 68;
                }
        }
    return -1;
    }


    public int idxToXDelta(int idx){
        switch (idx) {
            case 0:
                return -4;
            case 1:
                return -4;
            case 2:
                return -4;
            case 3:
                return -4;
            case 4:
                return -4;
            case 5:
                return -3;
            case 6:
                return -3;
            case 7:
                return -3;
            case 8:
                return -3;
            case 9:
                return -3;
            case 10:
                return -3;
            case 11:
                return -3;
            case 12:
                return -2;
            case 13:
                return -2;
            case 14:
                return -2;
            case 15:
                return -2;
            case 16:
                return -2;
            case 17:
                return -2;
            case 18:
                return -2;
            case 19:
                return -2;
            case 20:
                return -2;
            case 21:
                return -1;
            case 22:
                return -1;
            case 23:
                return -1;
            case 24:
                return -1;
            case 25:
                return -1;
            case 26:
                return -1;
            case 27:
                return -1;
            case 28:
                return -1;
            case 29:
                return -1;
            case 30:
                return 0;
            case 31:
                return 0;
            case 32:
                return 0;
            case 33:
                return 0;
            case 34:
                return 0;
            case 35:
                return 0;
            case 36:
                return 0;
            case 37:
                return 0;
            case 38:
                return 0;
            case 39:
                return 1;
            case 40:
                return 1;
            case 41:
                return 1;
            case 42:
                return 1;
            case 43:
                return 1;
            case 44:
                return 1;
            case 45:
                return 1;
            case 46:
                return 1;
            case 47:
                return 1;
            case 48:
                return 2;
            case 49:
                return 2;
            case 50:
                return 2;
            case 51:
                return 2;
            case 52:
                return 2;
            case 53:
                return 2;
            case 54:
                return 2;
            case 55:
                return 2;
            case 56:
                return 2;
            case 57:
                return 3;
            case 58:
                return 3;
            case 59:
                return 3;
            case 60:
                return 3;
            case 61:
                return 3;
            case 62:
                return 3;
            case 63:
                return 3;
            case 64:
                return 4;
            case 65:
                return 4;
            case 66:
                return 4;
            case 67:
                return 4;
            case 68:
                return 4;
        }
        return -1;
    }



    public int idxToYDelta(int idx){
        switch (idx) {
            case 0:
                return -2;
            case 1:
                return -1;
            case 2:
                return 0;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return -3;
            case 6:
                return -2;
            case 7:
                return -1;
            case 8:
                return 0;
            case 9:
                return 1;
            case 10:
                return 2;
            case 11:
                return 3;
            case 12:
                return -4;
            case 13:
                return -3;
            case 14:
                return -2;
            case 15:
                return -1;
            case 16:
                return 0;
            case 17:
                return 1;
            case 18:
                return 2;
            case 19:
                return 3;
            case 20:
                return 4;
            case 21:
                return -4;
            case 22:
                return -3;
            case 23:
                return -2;
            case 24:
                return -1;
            case 25:
                return 0;
            case 26:
                return 1;
            case 27:
                return 2;
            case 28:
                return 3;
            case 29:
                return 4;
            case 30:
                return -4;
            case 31:
                return -3;
            case 32:
                return -2;
            case 33:
                return -1;
            case 34:
                return 0;
            case 35:
                return 1;
            case 36:
                return 2;
            case 37:
                return 3;
            case 38:
                return 4;
            case 39:
                return -4;
            case 40:
                return -3;
            case 41:
                return -2;
            case 42:
                return -1;
            case 43:
                return 0;
            case 44:
                return 1;
            case 45:
                return 2;
            case 46:
                return 3;
            case 47:
                return 4;
            case 48:
                return -4;
            case 49:
                return -3;
            case 50:
                return -2;
            case 51:
                return -1;
            case 52:
                return 0;
            case 53:
                return 1;
            case 54:
                return 2;
            case 55:
                return 3;
            case 56:
                return 4;
            case 57:
                return -3;
            case 58:
                return -2;
            case 59:
                return -1;
            case 60:
                return 0;
            case 61:
                return 1;
            case 62:
                return 2;
            case 63:
                return 3;
            case 64:
                return -2;
            case 65:
                return -1;
            case 66:
                return 0;
            case 67:
                return 1;
            case 68:
                return 2;
        }
        return -1;
    }

}
