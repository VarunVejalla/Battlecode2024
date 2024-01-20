package genghis_filler;

import battlecode.common.*;


class AttackHeuristic {
    double friendlyVisionDamage;
    double enemyVisionDamage;
    double friendlyAttackDamage;
    double enemyAttackDamage;
    boolean hasFlag;
    double safetyMultipler; // If this is higher, the robot will be safer.

    public AttackHeuristic(double FVD, double EVD, double FVA, double EVA, boolean HF, double SM){
        friendlyVisionDamage = FVD;
        enemyVisionDamage = EVD;
        friendlyAttackDamage = FVA;
        enemyAttackDamage = EVA;
        hasFlag = HF;
        safetyMultipler = SM;
    }

    public boolean getSafe(){
//        Util.addToIndicatorString("FD:" + (int)friendlyDamage + ",ED:" + (int)enemyDamage);
        if(hasFlag){
            return friendlyAttackDamage >= enemyAttackDamage;
//            return friendlyAttackDamage >= enemyVisionDamage; // TODO: Tune this multiplier.
//            return friendlyVisionDamage >= enemyVisionDamage * 2.0; // TODO: Tune this multiplier.
//            return friendlyVisionDamage >= enemyVisionDamage; // TODO: Tune this multiplier.
        }
        return friendlyVisionDamage >= enemyVisionDamage * safetyMultipler;
    }
}


public class AttackModule {

    Robot robot;
    RobotController rc;
    AttackHeuristic heuristic;
    RobotInfo bestAttackVictim = null;
    MapLocation enemyCOM;

//    MapLocation enemyChaseLoc = null;
//    int turnsSinceChaseLocSet = 0;


    public AttackModule(RobotController rc, Robot robot) throws GameActionException{
        this.rc = rc;
        this.robot = robot;
    }


    public boolean runAttack() throws GameActionException {
        // note: I did not make a runAttackLoop() method that we had last year
        // because I don't think it's ever possible to get the attack cooldown low enough to be able to attack twice

        // the best we could possibly do is a have a level 6 specialized attacker, which would make the action cooldown 12
        // even if we apply two global upgraded to to increase the per-round cooldown rediction, this would get us to reduciton of 18
        // so, we would be able to attack once every round, but not more than that (I think)

        if(!rc.isActionReady()){
            return false;
        }

        if(bestAttackVictim != null){
            MapLocation toAttack = bestAttackVictim.location;
            rc.attack(toAttack);
            return true;
        }
        return false;
    }


    public MapLocation getCenterOfMass(RobotInfo[] nearbyEnemies){
        if(nearbyEnemies.length == 0){
            return null;
        }

        int xSum = 0;
        int ySum = 0;
        for(RobotInfo info: nearbyEnemies){
            xSum += info.location.x;
            ySum += info.location.y;
        }
        return new MapLocation(xSum/ nearbyEnemies.length,  ySum/ nearbyEnemies.length);
    }


    public int compareAttackVictims(RobotInfo x, RobotInfo y){
        // prioritize person with flag?
        // should probably factor in specializations here
        if(x.hasFlag && !y.hasFlag){
            return -1;
        }
        else if(!x.hasFlag && y.hasFlag){
            return 1;
        }
        else{
            return x.getHealth() - y.getHealth();
        }
    }


    public RobotInfo getBestAttackVictim(){
        int toAttackIndex = -1;
        for(int i=0; i < robot.nearbyActionEnemies.length; i++){
            if(rc.canAttack(robot.nearbyActionEnemies[i].location)){
                if(toAttackIndex == -1 ||
                        compareAttackVictims(robot.nearbyActionEnemies[i], robot.nearbyActionEnemies[toAttackIndex])< 0){
                    toAttackIndex = i;
                }
            }
        }
        if(toAttackIndex == -1) return null;
        return robot.nearbyActionEnemies[toAttackIndex];
    }



    // TODO: replace this code (which eats up a lotta bytecode, with DamageFinder class)
    // https://github.com/VarunVejalla/Battlecode2023/blob/main/src/karel/DamageFinder.java
    public double[] calculateDamageArray(MapLocation[] possibleSpots, boolean[] newSpotIsValid){
        //TODO: incorporate action cooldowns (which can vary) into damage calculations

        double[] enemyDamage = new double[9];
        for(RobotInfo enemy: robot.nearbyVisionEnemies){
            for(int i=0; i < enemyDamage.length; i++){
                if(newSpotIsValid[i] &&
                        enemy.getLocation().distanceSquaredTo(possibleSpots[i]) <=  GameConstants.ATTACK_RADIUS_SQUARED){
                    // TODO: incorporate AttackLevel into this calculation
                    enemyDamage[i] += 150.0;    // is there a better way than hard-coding this? I don't see it in GameConstants anywhere
                }
            }
        }
        return enemyDamage;
    }


    public void moveToBestPushLocation() throws GameActionException{
        MapLocation[] possibleSpots = new MapLocation[9];   // list of the possible spots we can go to on our next move
        boolean[] newSpotIsValid = new boolean[9];  // whether or not we can move to each new spot
        double[] enemyDamage = new double[9];   // contains the enemy damage you will receive at each new spot
        int[] sumOfDistanceSquaredToEnemies = new int[9]; //contains the sum of distances to enemies from each new spot
        boolean[] enemyPresentToAttack = new boolean[9];    // contains whether or not there is a

        possibleSpots[0] = robot.myLoc.add(Direction.NORTH);
        possibleSpots[1] = robot.myLoc.add(Direction.NORTHEAST);
        possibleSpots[2] = robot.myLoc.add(Direction.EAST);
        possibleSpots[3] = robot.myLoc.add(Direction.SOUTHEAST);
        possibleSpots[4] = robot.myLoc.add(Direction.SOUTH);
        possibleSpots[5] = robot.myLoc.add(Direction.SOUTHWEST);
        possibleSpots[6] = robot.myLoc.add(Direction.WEST);
        possibleSpots[7] = robot.myLoc.add(Direction.NORTHWEST);
        possibleSpots[8] = robot.myLoc;
        newSpotIsValid[8] = true;   // we know this spot is valid, because we're on it!

        // check if we can sense each new possible location, and that the new location is passable
        for(int i=0; i<8; i++){
            newSpotIsValid[i] = false;
            if(rc.canMove(robot.myLoc.directionTo(possibleSpots[i]))){
                newSpotIsValid[i] = true;
            }
        }

        for(int i = 0; i < 9; i++) {
            if(!newSpotIsValid[i]){
                continue;
            }

            for (RobotInfo enemy : robot.nearbyVisionEnemies) {         //loop over each enemy in vision radius
                if(possibleSpots[i].distanceSquaredTo(enemy.location) <= GameConstants.ATTACK_RADIUS_SQUARED){
                    enemyPresentToAttack[i] = true;
                }
                if(possibleSpots[i].distanceSquaredTo(enemy.location) <= GameConstants.ATTACK_RADIUS_SQUARED){
                    // TODO: make this calculation more accurate by considering attackLevels
                    enemyDamage[i] += 150.0;
//                    enemyDamage[i] += Util.getEnemyDamage(enemy);
                }
                sumOfDistanceSquaredToEnemies[i] += possibleSpots[i].distanceSquaredTo(enemy.location);
            }
        }

        MapLocation bestSpot = null;
        double leastEnemyDamage = robot.nearbyActionEnemies.length;
        int greatestSumDistanceSquared = Integer.MIN_VALUE;

        for(int i=0; i<9; i++){
            // don't consider this new position if there is no enemy at the new location
            // TODO: Hmm but what if you can't move to a square to attack the enemy but you're currently getting attacked, should you really stay there?
            // also don't consider this new position if this spot is not valid
            if(!enemyPresentToAttack[i] || !newSpotIsValid[i]){
                continue;
            }

            // make this spot the new bestSpot if we currently don't have a best spot
            if(bestSpot == null){
                bestSpot = possibleSpots[i];
                leastEnemyDamage = enemyDamage[i];
                greatestSumDistanceSquared = sumOfDistanceSquaredToEnemies[i];
            }

            // make this spot the new bestSpot if
            // 1) we receive less damage at this spot or
            // 2) we receive the same damage as the current best spot but we are further away from the enemies at the new spot
            else if(enemyDamage[i] < leastEnemyDamage || (enemyDamage[i] == leastEnemyDamage &&
                    sumOfDistanceSquaredToEnemies[i] > greatestSumDistanceSquared)){
                bestSpot = possibleSpots[i];
                leastEnemyDamage = enemyDamage[i];
                greatestSumDistanceSquared = sumOfDistanceSquaredToEnemies[i];
            }
        }

        if(bestSpot != null && !robot.myLoc.equals(bestSpot)){
            rc.move(robot.myLoc.directionTo(bestSpot));
        }
    }


    public void moveToSafestSpot() throws GameActionException{
        MapLocation[] possibleSpots = new MapLocation[9];   // list of the possible spots we can go to on our next move
        boolean[] newSpotIsValid = new boolean[9];  // whether we can move to each new spot

        possibleSpots[0] = robot.myLoc.add(Direction.NORTH);
        possibleSpots[1] = robot.myLoc.add(Direction.NORTHEAST);
        possibleSpots[2] = robot.myLoc.add(Direction.EAST);
        possibleSpots[3] = robot.myLoc.add(Direction.SOUTHEAST);
        possibleSpots[4] = robot.myLoc.add(Direction.SOUTH);
        possibleSpots[5] = robot.myLoc.add(Direction.SOUTHWEST);
        possibleSpots[6] = robot.myLoc.add(Direction.WEST);
        possibleSpots[7] = robot.myLoc.add(Direction.NORTHWEST);
        possibleSpots[8] = robot.myLoc;
        newSpotIsValid[8] = true;   // we know this spot is valid, because we're on it!

        // check if we can sense each new possible location, and that the new location is passable
        for(int i=0; i<8; i++){
            newSpotIsValid[i] = false;
            if(rc.canMove(robot.myLoc.directionTo(possibleSpots[i]))){
                newSpotIsValid[i] = true;
            }
        }

        double[] enemyDamage = calculateDamageArray(possibleSpots, newSpotIsValid);

        MapLocation bestSpot = robot.myLoc;
        double leastEnemyDamage = Double.MAX_VALUE;

        for(int i=0; i<9; i++){
            if(!newSpotIsValid[i]){
                continue;
            }
            // if the new spot will give us less enemy damage than the current best spot, make the new spot our best spot
            // if the new spot will give us the same enemy damage, but will move us closer to the enemies, make the new spot our best spot
            if(enemyDamage[i] < leastEnemyDamage){
                bestSpot = possibleSpots[i];
                leastEnemyDamage = enemyDamage[i];
            }

            else if(enemyDamage[i] == leastEnemyDamage
                    && possibleSpots[i].distanceSquaredTo(enemyCOM) < bestSpot.distanceSquaredTo(enemyCOM)){
                bestSpot = possibleSpots[i];
                leastEnemyDamage = enemyDamage[i];
            }
        }
//        Util.log("safest spot: " + bestSpot + ", with " + leastEnemyDamage + " damage ");//with sumDistanceSquared " + smallestSumDistanceSquared);

        if(!bestSpot.equals(robot.myLoc)){
            rc.move(robot.myLoc.directionTo(bestSpot));
        }
    }

    public void updateAllNearbyAttackInfo() throws GameActionException{
        robot.nearbyFriendlies = rc.senseNearbyRobots(GameConstants.VISION_RADIUS_SQUARED, robot.myTeam);
        robot.nearbyVisionEnemies = rc.senseNearbyRobots(GameConstants.VISION_RADIUS_SQUARED, robot.oppTeam);
//        Util.addToIndicatorString(String.valueOf(nearbyVisionEnemies.length)+";");
        enemyCOM = getCenterOfMass(robot.nearbyVisionEnemies);
//        MapLocation enemyActionCOM = getCenterOfMass(robot.nearbyActionEnemies);
//        if(enemyActionCOM == null){
//            enemyActionCOM = enemyCOM;
//        }
//        RobotInfo[] friendliesThatCanAttackEnemyActionCOM = rc.senseNearbyRobots(enemyActionCOM, GameConstants.ATTACK_RADIUS_SQUARED, robot.myTeam);
        RobotInfo[] friendliesThatCanAttackEnemyActionCOM = new RobotInfo[0];
        if(enemyCOM != null && rc.canSenseLocation(enemyCOM)){
            friendliesThatCanAttackEnemyActionCOM = rc.senseNearbyRobots(enemyCOM, GameConstants.ATTACK_RADIUS_SQUARED, robot.myTeam);
        }
        heuristic = getHeuristic(robot.nearbyFriendlies, robot.nearbyVisionEnemies, friendliesThatCanAttackEnemyActionCOM, robot.nearbyActionEnemies, rc.hasFlag());
    }


    public void moveBackFromEnemy() throws GameActionException{
        if(enemyCOM != null){
            int xDisplacement = enemyCOM.x - robot.myLoc.x;
            int yDisplacement = enemyCOM.y - robot.myLoc.y;
            MapLocation target = new MapLocation(robot.myLoc.x - xDisplacement*3, robot.myLoc.y-yDisplacement*3);
            robot.nav.goToFuzzy(target, 0);
        }
    }


    public void runUnsafeStrategy() throws GameActionException{
        // made this it's own method in case we want to add more logic here
        moveBackFromEnemy();
    }

    public void runSafeStrategy() throws GameActionException{
        // TODO: should we consider chasing down enemies???
        // maybe it's not worth since they just respawn
        // maybe something like chase enemies with the flag, but not otherwise?

        if(robot.nearbyActionEnemies.length != 0 ){
            if(rc.isMovementReady()){
                moveToSafestSpot();
            }
        }
        else if(robot.nearbyVisionEnemies.length != 0){
            if(rc.isActionReady() && rc.isMovementReady()){
                moveToBestPushLocation();
            }
            else if(rc.isMovementReady()){
                moveToSafestSpot();
            }
        }
    }

    public MapLocation findBestHealPatient() throws GameActionException{
        // this method finds the best patient to heal
        int worstHealth = 1000;
        MapLocation weakestFriendlyLoc = null;
        for(RobotInfo robot: robot.nearbyFriendlies){
            boolean canHeal = rc.canHeal(robot.getLocation());

            // if the bot carrying the flag is not at 100%, priortize that one
            if(canHeal && robot.getHealth() < worstHealth && robot.hasFlag()){
                return robot.getLocation();
            }

            if(rc.canHeal(robot.location) & robot.getHealth() < worstHealth){
                worstHealth = rc.getHealth();
                weakestFriendlyLoc = robot.getLocation();
            }
        }
        return weakestFriendlyLoc;
    }

    public boolean runHealing() throws GameActionException{
        // note: if we got to this method, it means we didn't attack
        // this method returns true if it heals a bot, and false if it doesn't heal a bot

        // check to see if there are no opp in vision radius
        if(robot.nearbyVisionEnemies.length == 0 && robot.nearbyFriendlies.length > 0)
        {
            // find the weakest friendly to heal
            MapLocation weakestPatientLoc = findBestHealPatient();
            if(weakestPatientLoc != null){

                // heal the boi that needs most help
                rc.heal(weakestPatientLoc);
                return true;
            }
        }
        return false;
    }


    public void runSetup() throws GameActionException {
        // main entry point to this module, which will determine if we're safe or not and will try attacking.
        bestAttackVictim = getBestAttackVictim();
        boolean successfullyAttacked = runAttack(); // try Attacking

        updateAllNearbyAttackInfo();

        Util.addToIndicatorString("SF:" + heuristic.getSafe());
    }

    public void runStrategy() throws GameActionException {
        // main entry point to this module, which will run any attacking strategy (attacking micro).
        if(heuristic.getSafe()){
            runSafeStrategy();
        }
        else{
            runUnsafeStrategy();
        }

        runHealing(); // try healing
    }


    public AttackHeuristic getHeuristic(RobotInfo[] visionFriendlies, RobotInfo[] visionEnemies, RobotInfo[] attackFriendlies, RobotInfo[] attackEnemies, boolean hasFlag) throws GameActionException{
        // TODO: we should calculate the legit damage values here according to bot specializations.
        //  Not sure if there's a way to that without hardcoding in values at the moment.

        // TODO: Move this to constants.
        double safetyMultiplier = 1.0;
        if(robot.mode == Mode.STATIONARY_DEFENSE){
            safetyMultiplier = 0.5;
        }
        return new AttackHeuristic(visionFriendlies.length + 1, visionEnemies.length, attackFriendlies.length + 1, attackEnemies.length, hasFlag, safetyMultiplier);
    }

}
