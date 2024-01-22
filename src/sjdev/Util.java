package sjdev;

import battlecode.common.*;

public class Util {

    static RobotController rc;
    static Robot robot;

    public static int minMovesToReach(MapLocation a, MapLocation b){
        int dx = a.x - b.x;
        int dy = a.y - b.y;
        return Math.max(Math.abs(dx), Math.abs(dy));
    }

    
    public static boolean tryMove(Direction dir) throws GameActionException{
        if(rc.canFill(rc.adjacentLocation(dir))) {
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


    public static <T> boolean checkIfItemInArray(T item, T[] array){
        return getItemIndexInArray(item, array) != -1;
    }


    public static <T> void logArray(String name, T[] array){
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
        int ai = directionToInt(a);
        int bi = directionToInt(b);
        return Math.min(Math.min(Math.abs(ai - bi), Math.abs(ai - bi - 8)), Math.abs(ai - bi + 8));
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
            Util.log("Not all spawn centers found in getSpawnLocCenters");
            rc.resign();
        }

        return spawnCenters;
    }

    public static void log(String str){
        System.out.println(str);
    }

    public static void logBytecode(String str){
        System.out.println(str + ": " + Clock.getBytecodesLeft());
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



    // this method checks if a direction is cardinal
    // used in AttackModule when prioritizing to move in cardinal directions to maintain attack formation
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


    public static double getAttackDamage(RobotInfo robotInfo) throws GameActionException {
        // this method returns the attack damage of the robot given its specialization
        // and also considers the global upgrade selected by the other team
        if (robotInfo == null) return 0;

        GlobalUpgrade[] globalUpgrades = (robotInfo.getTeam().equals(robot.myTeam))
                ? robot.myTeamGlobalUpgrades
                : robot.oppTeamGlobalUpgrades;

        boolean isGlobalUpgradeAttack = Util.checkIfItemInArray(GlobalUpgrade.ATTACK, globalUpgrades);
        int attackLevelSpecialization = robotInfo.getAttackLevel();

        switch (attackLevelSpecialization) {
            case 0: // 0% damage boost for attack specialization level 0
                // 225 = (150 + 75)
                // 150 =  base attack
                return isGlobalUpgradeAttack ? 225.0 : 150.0;

            case 1: // 5% damage boost for attack specialization level 1
                // 236.25 = (150 + 75) * 1.05
                // 157.5 = 150 * 1.05
                return isGlobalUpgradeAttack ? 236.25 : 157.5;

            case 2: // 7% damage boost for attack specialization level 2
                // 240.75 = (150 + 75) * 1.07
                // 160.5 = 150 * 1.07
                return isGlobalUpgradeAttack ? 240.75 : 160.5;

            case 3: // 10% damage boost for attack specialization level 3
                // 247.5 = (150 + 75) * 1.10
                // 165.0 = 150 * 1.10
                return isGlobalUpgradeAttack ? 247.5 : 165.0;

            case 4: // 30% damage boost for attack specialization level 4
                // 292.5 = (150 + 75) * 1.30
                // 195.0 = 150 *  1.30
                return isGlobalUpgradeAttack ? 292.5 : 195.0;

            case 5: // 35% damage boost for attack specialization level 5
                // 303.75 = (150 + 75) * 1.35
                // 202.5 = 150 * 1.35
                return isGlobalUpgradeAttack ? 303.75 : 202.5;

            case 6: // 60% damage boost for attack specialization level 6
                // 360 = (150 + 75) * 1.60
                // 240 = 150 * 1.60
                return isGlobalUpgradeAttack ? 360 : 240;

            default:
                return 150.0;
        }
    }


    public static double getAttackCooldown(RobotInfo robotInfo) throws GameActionException{
        // this method returns the attack
        if (robotInfo == null) return 0;
        int attackLevelSpecialization = robotInfo.getAttackLevel();
        switch(attackLevelSpecialization){
            case 0:
                // 0% reduction in cooldown
                return 20.0;

            case 1:
                // 5% reduction in cooldwon
                return 19.0;

            case 2:
                // 7% reduction in cooldown
                return 18.6;

            case 3:
                // 10% reduction in cooldown
                return 18.0;

            case 4:
                // 20% reduction in cooldown
                return 16.0;

            case 5:
                // 35% reduction in cooldown
                return 13.0;

            case 6:
                // 60% reduction in cooldown
                return 8.0;
        }
        return 20.0;
    }
}
