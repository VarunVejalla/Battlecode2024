package genghis;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class DefenseModule {

    RobotController rc;
    Robot robot;
    Comms comms;
    Navigation nav;
    int defendingFlagIdx = -1;
    MapLocation flagDefaultLoc;

    public DefenseModule(RobotController rc, Robot robot, Comms comms, Navigation nav) throws GameActionException {
        this.rc = rc;
        this.robot = robot;
        this.comms = comms;
        this.nav = nav;
    }

    public void getBestSpawnLoc(){

    }

    public void spawn() throws GameActionException {
        if(defendingFlagIdx == -1){
            Util.log("SETUP NOT YET CALLED??");
            rc.resign();
        }
        flagDefaultLoc = comms.getDefaultHomeFlagLoc(defendingFlagIdx);
        MapLocation[] spawnLocs = rc.getAllySpawnLocations();
        int bestIdx = -1;
        int bestDist = Integer.MAX_VALUE;
        for(int i = 0; i < spawnLocs.length; i++){
            if(!rc.canSpawn(spawnLocs[i])){
                continue;
            }

            int dist = Util.minMovesToReach(flagDefaultLoc, spawnLocs[i]);
            if(dist < bestDist){
                bestDist = dist;
                bestIdx = i;
            }
        }
        if(bestIdx != -1){
            rc.spawn(spawnLocs[bestIdx]);
        }
    }

    public void setup() throws GameActionException {
        int flag0_defenders = comms.readNumDefendersForFlag(0);
        int flag1_defenders = comms.readNumDefendersForFlag(1);
        int flag2_defenders = comms.readNumDefendersForFlag(2);

        if(flag0_defenders <= flag1_defenders && flag0_defenders <= flag2_defenders){
            defendingFlagIdx = 0;
        }
        else if(flag1_defenders <= flag0_defenders && flag1_defenders <= flag2_defenders){
            defendingFlagIdx = 1;
        }
        else if(flag2_defenders <= flag0_defenders && flag2_defenders <= flag1_defenders){
            defendingFlagIdx = 2;
        }
        flagDefaultLoc = comms.getDefaultHomeFlagLoc(defendingFlagIdx);
        comms.incrementNumDefendersForFlag(defendingFlagIdx);
    }

    public void runDefense() throws GameActionException {
        assert(defendingFlagIdx != -1);
        flagDefaultLoc = comms.getDefaultHomeFlagLoc(defendingFlagIdx);
        Util.addToIndicatorString("FL:" + flagDefaultLoc);
        nav.circle(flagDefaultLoc, 2, 5);
    }


}
