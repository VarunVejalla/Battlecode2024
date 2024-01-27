package davinci;

import battlecode.common.*;

public class Util {

    static RobotController rc;
    static Robot robot;
    static boolean LOGGING_ALLOWED = true;
    static boolean SUBMISSION_MODE = true; // TODO: Set this to true when submitting.

    public static void resign(){
        if(!SUBMISSION_MODE){
            rc.resign();
        }
    }

    public static void assert_wrapper(boolean bool){
        if(!SUBMISSION_MODE){
            assert(bool);
        }
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

}
