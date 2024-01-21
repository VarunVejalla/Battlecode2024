package navtesting;

import battlecode.common.*;

public class FlagMover {

    final int MIN_DIST_TO_CENTER_SQUARED = 50;

    RobotController rc;
    Robot robot;
    Comms comms;
    Navigation nav;
    MapLocation targetLoc;
    boolean placedFlag = false;
    boolean circleCCW = false;
    int flagIdx = -1;
    MapLocation[] alreadyPlacedFlags = new MapLocation[3];

    public FlagMover(RobotController rc, Robot robot, Comms comms, Navigation nav) throws GameActionException {
        this.rc = rc;
        this.robot = robot;
        this.comms = comms;
        this.nav = nav;
    }

    public void tryPlacingFlag() throws GameActionException {
        for(int i = 0; i < 3; i++){
            if(alreadyPlacedFlags[i] != null){
                continue;
            }
            if(comms.getOurFlagNewHomeStatus(i)){
                alreadyPlacedFlags[i] = comms.getDefaultHomeFlagLoc(i);
                Util.fillTrue(nav.locsToIgnore, alreadyPlacedFlags[i], 36);
            }
        }

        MapInfo[] infos = rc.senseNearbyMapInfos(GameConstants.VISION_RADIUS_SQUARED);
        for(MapInfo info : infos){
            MapLocation loc = info.getMapLocation();
            // Check something
            if(loc.distanceSquaredTo(targetLoc) > MIN_DIST_TO_CENTER_SQUARED){
                continue;
            }

            // Check if it's too close to another flag that's been placed down.
            boolean tooClose = false;
            for(int i = 0; i < 3; i++){
                if(alreadyPlacedFlags[i] != null && loc.distanceSquaredTo(alreadyPlacedFlags[i]) < 36){
                    tooClose = true;
                    break;
                }
            }
            if(tooClose){
                continue;
            }

            if(rc.canDropFlag(loc)){
                rc.dropFlag(loc);
                placedFlag = true;
                if(flagIdx == -1){
                    rc.resign();
                }
                System.out.println("Setting new default flag loc for " + flagIdx + " to " + loc);
                comms.writeDefaultHomeFlagLocs(flagIdx, loc);
                comms.writeOurFlagNewHomeStatus(flagIdx, true);
            }
        }
    }

    public void chooseTargetLoc() throws GameActionException {
        int[] distsToSpawnCenters = comms.readDistsToSpawnCenters();
        int bestIdx = 0;
        for(int i = 1; i < robot.spawnCenters.length; i++){
            if(distsToSpawnCenters[i] > distsToSpawnCenters[bestIdx]){
                bestIdx = i;
            }
        }
        targetLoc = robot.spawnCenters[bestIdx];
    }

    public boolean runFlagMover() throws GameActionException {
        if(rc.hasFlag()){
            if(rc.getRoundNum() > Constants.NEW_FLAG_LOC_DECIDED_ROUND){
                targetLoc = comms.readNewHomeFlagCenter();
                if(targetLoc == null){
                    chooseTargetLoc();
                    comms.writeNewHomeFlagCenter(targetLoc);
                }
                Util.addToIndicatorString("CT: " + targetLoc.toString());
                Util.addToIndicatorString("CW: " + circleCCW);
                boolean circled = nav.circle(targetLoc, 4, 8, circleCCW, 0);
                if(rc.isMovementReady() && !circled){
                    circleCCW = !circleCCW;
                    nav.recentlyVisited = new MapLocation[10];
                    circled = nav.circle(targetLoc, 4, 8, circleCCW, 0);
                    if(!circled){
                        circleCCW = !circleCCW;
                        nav.recentlyVisited = new MapLocation[10];
                    }
                }
                tryPlacingFlag();
            }
            return true;
        }
        else if(!placedFlag){
            // Search for nearby flags.
            FlagInfo[] nearbyFlags = rc.senseNearbyFlags(GameConstants.VISION_RADIUS_SQUARED);
            for(int i = 0; i < nearbyFlags.length; i++){
                if(nearbyFlags[i].isPickedUp()){
                    continue;
                }
                MapLocation flagLoc = nearbyFlags[i].getLocation();
                if(!rc.canPickupFlag(flagLoc)){
                    Util.log("Why tf can i not pick it up");
                    nav.fuzzyNav.goTo(flagLoc, 0);
                    return true;
                }
                // Determine the index of the flag.
                flagIdx = Util.getItemIndexInArray(flagLoc, robot.spawnCenters);
                if(flagIdx == -1){
                    Util.log("Flag is not at its center???");
                    rc.resign();
                }
                rc.pickupFlag(flagLoc);
                return true;
            }
        }
        return false;
    }

}
