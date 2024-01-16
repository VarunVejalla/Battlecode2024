package tesla;

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


    public static <T> boolean checkIfItemInArray(T item, T[] array){
        // helper method to check if an item is in an array
        for(T arrayItem : array){
            if(arrayItem != null && arrayItem.equals(item)){
                return true;
            }
        }
        return false;
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

    // TODO: Maybe improve bytecode of this??
    public static MapLocation[] getSpawnLocCenters(){
        int spawnCenterIdx = 0;
        MapLocation[] spawnCenters = new MapLocation[3];

        MapLocation[] spawnLocs = rc.getAllySpawnLocations();
        int count;
        for(MapLocation potentialFlag : spawnLocs) {
            count = 0;
            for(MapLocation loc : spawnLocs) {
                if(potentialFlag.distanceSquaredTo(loc) <= 2) {
                    count += 1;
                }
            }
            if(count > 9) {
                Util.log("Count > 9 in getSpawnLocCenters");
                rc.resign();
            }
            if(count == 9) {
                spawnCenters[spawnCenterIdx] = potentialFlag;
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
}
