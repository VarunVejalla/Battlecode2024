package suntzu_lattice_fill;

import battlecode.common.*;

class AttackHeuristic {
    double friendlyHP;
    double friendlyDamage;
    double enemyHP;
    double enemyDamage;
    double safetyMultipler; // If this is higher, the robot will be safer.
    boolean hasFlag;


    public AttackHeuristic(double friendlyHP, double friendlyDamage, double enemyHP, double enemyDamage, boolean hasFlag, double safetyMultipler){
        this.friendlyHP = friendlyHP;
        this.friendlyDamage = friendlyDamage;
        this.enemyHP = enemyHP;
        this.enemyDamage = enemyDamage;
        this.safetyMultipler = safetyMultipler;
        this.hasFlag = hasFlag;
    }

    public boolean getSafe() {

        if(enemyDamage == 0){
            return true;
        }

        double myTurnsNeeded = enemyHP / friendlyDamage;
        double enemyTurnsNeeded = friendlyHP / enemyDamage;

//        Util.addToIndicatorString("MTN:" + (int)myTurnsNeeded + ",ETN:" + (int)enemyTurnsNeeded);

        if(hasFlag){
            return myTurnsNeeded <= enemyTurnsNeeded;
        }
        else{
            // TODO: tune the safetyMultiplier parameter and the 1.3 constant
            return myTurnsNeeded < enemyTurnsNeeded * safetyMultipler * 1.3;
        }

////        Util.addToIndicatorString("FD:" + (int)friendlyDamage + ",ED:" + (int)enemyDamage);
//        if(hasFlag){
//            return friendlyAttackDamage >= enemyAttackDamage;
//        }
//        return friendlyVisionDamage >= enemyVisionDamage * safetyMultipler;
    }
}


public class AttackModule {

    Robot robot;
    RobotController rc;
    AttackHeuristic heuristic;
    RobotInfo bestAttackVictim = null;
    MapLocation enemyCOM;
    int lastRetreatRound = -1;
    int[][] stunTrapInfo;
    int[][] lastStunnedInfo;
    boolean previouslySafe = true;


//    MapLocation enemyChaseLoc = null;
//    int turnsSinceChaseLocSet = 0;


    public AttackModule(RobotController rc, Robot robot) throws GameActionException {
        this.rc = rc;
        this.robot = robot;
    }


    public boolean runAttack() throws GameActionException {
        // note: I did not make a runAttackLoop() method that we had last year
        // because I don't think it's ever possible to get the attack cooldown low enough to be able to attack twice

        if (!rc.isActionReady()) {
            return false;
        }

        if (bestAttackVictim != null) {
            MapLocation toAttack = bestAttackVictim.location;
            rc.attack(toAttack);
            Util.addToIndicatorString("ATK");
            return true;
        }
        return false;
    }


    public MapLocation getCenterOfMass(RobotInfo[] nearbyEnemies) {
        if (nearbyEnemies.length == 0) {
            return null;
        }

        int xSum = 0;
        int ySum = 0;
        for (RobotInfo info : nearbyEnemies) {
            xSum += info.location.x;
            ySum += info.location.y;
        }
        return new MapLocation(xSum / nearbyEnemies.length, ySum / nearbyEnemies.length);
    }

    public static int getSpecializationHeuristic(RobotInfo robotInfo) {
        // this method returns a heuristic value for the specialization of the robot
        // a bot with a lower specialization should be prioritized in attack order

        int attackLevel = robotInfo.getAttackLevel();
        int buildLevel = robotInfo.getBuildLevel();
        int healLevel = robotInfo.getHealLevel();

        // TODO: tune these parameters
        return -(attackLevel * 5 + buildLevel * 3 + healLevel * 1);
    }

    public int compareAttackVictims(RobotInfo x, RobotInfo y) throws GameActionException {
        // what should this method do:
        // prioritize getting the flag
        // prioritize getting a kill
        // prioritize specializations (attackers >> builders >> healers)

        // if one bot has a flag and the other doesn't, prioritize the one with the flag
        if (x.hasFlag && !y.hasFlag) {
            return -1;
        } else if (!x.hasFlag && y.hasFlag) {
            return 1;
        }

        RobotInfo myRobotInfo = rc.senseRobot(rc.getID());

        // prioritizing getting a kill
        boolean xCanKill = x.getHealth() <= Util.getAttackDamage(myRobotInfo);
        boolean yCanKill = y.getHealth() <= Util.getAttackDamage(myRobotInfo);

        if (xCanKill && !yCanKill) {
            return -1;
        } else if (!xCanKill && yCanKill) {
            return 1;
        }

        // prioritize specializations
        int xSpecializationHeuristic = getSpecializationHeuristic(x);
        int ySpecializationHeuristic = getSpecializationHeuristic(y);

        if (xSpecializationHeuristic < ySpecializationHeuristic) {
            return -1;
        } else if (xSpecializationHeuristic > ySpecializationHeuristic) {
            return 1;
        } else { // default to prioritizing the bot with the lowest health
            return x.getHealth() - y.getHealth();
        }
    }


    public RobotInfo getBestAttackVictim() throws GameActionException {
        // this method loops over all enemies in action radius and finds the best one to attack
        // see compareAttackVictims() for how we compare two victims
        robot.nearbyActionEnemies = rc.senseNearbyRobots(GameConstants.ATTACK_RADIUS_SQUARED, robot.oppTeam);
        int toAttackIndex = -1;
        for (int i = 0; i < robot.nearbyActionEnemies.length; i++) {
            if (rc.canAttack(robot.nearbyActionEnemies[i].location)) {
                if (toAttackIndex == -1 ||
                        compareAttackVictims(robot.nearbyActionEnemies[i], robot.nearbyActionEnemies[toAttackIndex]) < 0) {
                    toAttackIndex = i;
                }
            }
        }
        if (toAttackIndex == -1) return null;
        return robot.nearbyActionEnemies[toAttackIndex];
    }


    public void moveToBestPushLocation() throws GameActionException {
        // TODO: maybe make decisions based on a heuristic ith constant multipliers instead of if-statements for blending constraints?
        // TODO: maybe consider filling water? not sure if this is a good idea though cuz of high incurred action cooldown
        // this method moves to the best push location to attack the enemy

        // this method priortizes:
        //      - prioritize getting closer to AN enemy, but avoid other enemies
        //      - minimize average distance to enemies
        //      - prioritize horizontal/vertical movement over diagonal movement to maintain troop formation
        //      - minimize distance to the target you're trying to reach (based on your mode)
        Direction bestDirToMove = null;
        int bestNumEnemies = 0;
        int minAvgDistanceToEnemies = Integer.MAX_VALUE; // some arbitrarily large number
        boolean bestDirIsCardinal = false; // it's better to move in horizontal/vertical direction rather than diagonal direction, so we use the direction as a tiebreaker
        int minDistanceToCurrTarget = Integer.MAX_VALUE;
        int minDistanceToClosestEnemy = Integer.MAX_VALUE;
        MapLocation currentTarget = Util.getCurrentTarget();


        for (int i = 9; --i >= 0; ) {
            Direction dir = robot.nav.allDirections[i];
            if (!rc.canMove(dir)) continue;  // if we can't move in this direction, don't consider it

            int currNumEnemies = 0;
            int currAvgDistanceToEnemies = 0;
            boolean currDirIsCardinal = Util.checkIfDirIsCardinal(dir);
            int currDistanceToCurrTarget = 0;
            int currDistanceToClosestEnemy = Integer.MAX_VALUE;

            if (currentTarget != null) {
                currDistanceToCurrTarget = robot.myLoc.distanceSquaredTo(currentTarget);
            }

            MapLocation newLoc = robot.myLoc.add(dir);

            // loop over all the enemies in our vision radius and check if they can attack this spot
            for (int x = robot.nearbyVisionEnemies.length; --x >= 0; ) {
                RobotInfo enemy = robot.nearbyVisionEnemies[x];
                int distanceToEnemy = enemy.location.distanceSquaredTo(newLoc);

                if (distanceToEnemy <= GameConstants.ATTACK_RADIUS_SQUARED) {
                    currNumEnemies++;
                    currAvgDistanceToEnemies += enemy.location.distanceSquaredTo(newLoc);
                }
                if (distanceToEnemy < currDistanceToClosestEnemy) {
                    currDistanceToClosestEnemy = distanceToEnemy;
                }
            }


            // if we haven't found a direction to move in yet that gets us into attack range of someone
            // but this direction minimizes the distance to the closest enemy, then this is the best direction
            if (bestNumEnemies == 0 && currDistanceToClosestEnemy < minDistanceToClosestEnemy) {
                bestNumEnemies = currNumEnemies;
                minAvgDistanceToEnemies = currAvgDistanceToEnemies;
                bestDirToMove = dir;
                bestDirIsCardinal = currDirIsCardinal;
                minDistanceToCurrTarget = currDistanceToCurrTarget;
                minDistanceToClosestEnemy = currDistanceToClosestEnemy;
            }

            if (currNumEnemies == 0) {
                // if there are no enemies that can attack this spot, don't consider it
                continue;
            } else {
                // if there are enemies that can attack this spot, compute the average distance to them
                currAvgDistanceToEnemies /= currNumEnemies;
            }

            // see if this is the best direction to move in
            if ((currNumEnemies < bestNumEnemies) ||    // if this direction gets us into fewer enemies that current best, but still more than 0, then this is the best direction
                    (currNumEnemies == bestNumEnemies && currAvgDistanceToEnemies < minAvgDistanceToEnemies) || // if this direction gets us into the same number of enemies as the current best, but the average distance to them is less, then this is the best direction
                    (currNumEnemies == bestNumEnemies && currAvgDistanceToEnemies == minAvgDistanceToEnemies && currDirIsCardinal && !bestDirIsCardinal) || // prioritize horizontal/vertical movements to maintain troop formation
                    (currNumEnemies == bestNumEnemies && currAvgDistanceToEnemies == minAvgDistanceToEnemies && (currDirIsCardinal == bestDirIsCardinal) && currDistanceToCurrTarget < minDistanceToCurrTarget)) {

                bestNumEnemies = currNumEnemies;
                minAvgDistanceToEnemies = currAvgDistanceToEnemies;
                bestDirToMove = dir;
                bestDirIsCardinal = currDirIsCardinal;
                minDistanceToCurrTarget = currDistanceToCurrTarget;
                minDistanceToClosestEnemy = currDistanceToClosestEnemy;
            }
        }

        if (bestDirToMove != null && bestDirToMove != Direction.CENTER) {
            Util.addToIndicatorString("MV");
            rc.move(bestDirToMove);
            robot.myLoc = rc.getLocation();
        }
    }


    public void moveToSafestSpot() throws GameActionException {
        // TODO: maybe make decisions based on a heuristic ith constant multipliers instead of if-statements for blending constraints?
        // TODO: consider filling up water when running away?
        // not sure if above is a good idea though, because you don't want to increase action cooldown if you're fighting?
        // maybe we factor water in some kind of heuristic calculation

        // this method moves to the safest spot,
        // used when
        //      - not safe,
        //      - safe, but can't attack this round (kiting behavior)
        // the method prioritizes:
        //    - minimizing the number of enemies that can attack you
        //    - prioritizing horizontal/vertical movement over diagonal movement to maintain troop formation
        //    - minimizing the distance to the target you're trying to reach (based on your mode)

        // if we started retreating in the last x rounds and there are no enemies in vision radius,
        // but there are friendlies in vision radius, go to the friendlies
        if (lastRetreatRound != -1 &&
                (rc.getRoundNum() - lastRetreatRound) < Constants.NUM_ROUNDS_TO_RETREAT_FOR) { // If in retreating mode.
            if (robot.nearbyVisionEnemies.length == 0) {
                if (robot.nearbyFriendlies.length > 0) { // If no enemies nearby, go to nearest friendly.
                    // find the closest friendly
                    RobotInfo nearestFriendly = getClosestBot(robot.nearbyFriendlies);

                    // fuzzy nav to the closest friendly
                    if (nearestFriendly != null) {
                        robot.nav.fuzzyNav.goTo(nearestFriendly.location, 0);
                    }
                    return;
                }

                // if we started retreating in the last x rounds and there are no enemies in
                // vision radius and no friendlies in vision radius, move away from enemyCOM if that's not null
                else if (enemyCOM != null) { // If no one nearby,
                    // fuzzy nav away from enemyCOM
                    Direction awayFromEnemyCOM = robot.myLoc.directionTo(enemyCOM).opposite();

                    robot.nav.fuzzyNav.goTo(robot.myLoc.add(awayFromEnemyCOM).add(awayFromEnemyCOM).add(awayFromEnemyCOM), 0);
                    return;
                } else {
                    System.out.println("No one nearby, but enemyCOM is null");
                    Util.resign();
                }
            }
        }

        Direction bestDirToMove = null;
        int minNumEnemiesThatCanAttack = Integer.MAX_VALUE;
        boolean bestDirIsCardinal = false; // it's better to move in horizontal/vertical direction rather than diagonal direction, so we use the direction as a tiebreaker

        // this is the distance to the target this bot is trying to reach
        // gonna use this as a tiebreaker for selecting the best spot to the move from
        int minDistanceToCurrTarget = Integer.MAX_VALUE;
        MapLocation currentTarget = Util.getCurrentTarget();    // the current target that the robot is going to (based on its mode)

        enemyCOM = getCenterOfMass(robot.nearbyVisionEnemies);
        for (int i = 9; --i >= 0; ) {
            Direction dir = robot.nav.allDirections[i];
            int currNumEnemiesThatCanAttack = 0;
            int currentDistanceToTarget = 0;
            boolean currDirIsCardinal = Util.checkIfDirIsCardinal(dir);

            if (currentTarget != null) {
                currentDistanceToTarget = robot.myLoc.distanceSquaredTo(currentTarget);
            }

            // don't consider this direction if we can't move in it
            if (!rc.canMove(dir)) continue;
            MapLocation loc = robot.myLoc.add(dir);

            // loop over all the enemies in our vision radius and check if they can attack this spot
            for (int x = robot.nearbyVisionEnemies.length; --x >= 0; ) {
                RobotInfo enemy = robot.nearbyVisionEnemies[x];
                if (enemy.location.distanceSquaredTo(loc) <= GameConstants.ATTACK_RADIUS_SQUARED) {
                    currNumEnemiesThatCanAttack++;
                }
            }

            // see if this is the best direction to move in
            if ((currNumEnemiesThatCanAttack < minNumEnemiesThatCanAttack) ||   // minimies the number of enemies that can see you in the new location
                    (currNumEnemiesThatCanAttack == minNumEnemiesThatCanAttack && currDirIsCardinal && !bestDirIsCardinal) ||  // prioritize movements that are in horizontal/vertical directions to keep formation
                    (currNumEnemiesThatCanAttack == minNumEnemiesThatCanAttack && (currDirIsCardinal == bestDirIsCardinal) && currentDistanceToTarget < minDistanceToCurrTarget)  // minimize distance to target
            ) {
                minNumEnemiesThatCanAttack = currNumEnemiesThatCanAttack;
                bestDirToMove = dir;
                bestDirIsCardinal = currDirIsCardinal;
                minDistanceToCurrTarget = currentDistanceToTarget;
            }
        }


        if (bestDirToMove != null && bestDirToMove != Direction.CENTER) {
            Util.addToIndicatorString("MV");
            rc.move(bestDirToMove);
            robot.myLoc = rc.getLocation();
        }
    }


    public void updateAllNearbyAttackInfo() throws GameActionException {
        robot.nearbyFriendlies = rc.senseNearbyRobots(GameConstants.VISION_RADIUS_SQUARED, robot.myTeam);
        robot.nearbyActionFriendlies = rc.senseNearbyRobots(GameConstants.ATTACK_RADIUS_SQUARED, robot.myTeam);
        robot.nearbyVisionEnemies = rc.senseNearbyRobots(GameConstants.VISION_RADIUS_SQUARED, robot.oppTeam);
        robot.nearbyActionEnemies = rc.senseNearbyRobots(GameConstants.ATTACK_RADIUS_SQUARED, robot.oppTeam);
//        Util.addToIndicatorString(String.valueOf(nearbyVisionEnemies.length)+";");
//        enemyCOM = getCenterOfMass(robot.nearbyVisionEnemies);
//        MapLocation enemyActionCOM = getCenterOfMass(robot.nearbyActionEnemies);
//        if(enemyActionCOM == null){
//            enemyActionCOM = enemyCOM;
//        }
//        RobotInfo[] friendliesThatCanAttackEnemyActionCOM = rc.senseNearbyRobots(enemyActionCOM, GameConstants.ATTACK_RADIUS_SQUARED, robot.myTeam);
        RobotInfo[] friendliesThatCanAttackEnemyActionCOM = new RobotInfo[0];
        if (enemyCOM != null && rc.canSenseLocation(enemyCOM)) {
            friendliesThatCanAttackEnemyActionCOM = rc.senseNearbyRobots(enemyCOM, GameConstants.ATTACK_RADIUS_SQUARED, robot.myTeam);
        }
        heuristic = getHeuristic(robot.nearbyFriendlies, robot.nearbyVisionEnemies, robot.nearbyActionFriendlies, robot.nearbyActionEnemies, rc.hasFlag());
    }


    public void updateEnemyStunnedLocs(int centerX, int centerY, int roundLastStunned){
        int lowerX = Math.max(centerX - 3, 0);
        int upperX = Math.min(centerX + 3, rc.getMapWidth());
        int lowerY = Math.max(centerY - 3, 0);
        int upperY = Math.min(centerY + 3, rc.getMapHeight());

        for(int x = lowerX; x < upperX; x++){
            for(int y = lowerY; y < upperY; y++){
                if(roundLastStunned > lastStunnedInfo[x][y]){
                    lastStunnedInfo[x][y] = roundLastStunned;
                }
            }
        }
    }


    public void updateStunTrapInfo() throws GameActionException {
        // this method will scan nearby squares and update stun trap info
        // the 2D matrix will be updated with the current round number if we sense a stun trap at the corresponding location

        // this method is called in the scanSurroundings method of Robot
        // it should be called by all Robot types
        int currRoundNum = rc.getRoundNum();
        for(MapInfo info: robot.sensedNearbyMapInfos){
            int x = info.getMapLocation().x; int y = info.getMapLocation().y;
            if(info.getTrapType() == TrapType.STUN){
                stunTrapInfo[x][y] = currRoundNum;
            }
            else if(stunTrapInfo[x][y] != 0){
                // If the stun trap went off in the last few rounds, compute enemy stunned locs.
                System.out.println("Stun trap went off " + (currRoundNum - stunTrapInfo[x][y]) + " rounds ago at " + info.getMapLocation() + "!");
                if(currRoundNum - stunTrapInfo[x][y] < Constants.NUM_ROUNDS_OF_STUN){
                    updateEnemyStunnedLocs(x, y, stunTrapInfo[x][y]);
                }
                stunTrapInfo[x][y] = 0;
            }
        }
    }

    public void tryPlacingStunTrap() throws GameActionException {
        // this tries to place a stun trap in the direction of the enemyCOM
        // compute enemyCOM
        enemyCOM = getCenterOfMass(robot.nearbyVisionEnemies);
        if(enemyCOM == null){
            return;
        }

        // compute the direction to enemyCOM
        Direction dirToEnemyCOM = robot.myLoc.directionTo(enemyCOM);
        int roundNum = rc.getRoundNum();
        for(Direction direction : Util.closeDirections(dirToEnemyCOM)){
            MapLocation potentialBuildLocation = robot.myLoc.add(direction);
            if(!rc.canBuild(TrapType.STUN, potentialBuildLocation)) {
                continue;
            }

            boolean adjacentStunTrap = false;
            for(Direction dir : robot.nav.cardinalDirections){
                MapLocation adjacentLoc = potentialBuildLocation.add(dir);
                if(rc.canSenseLocation(adjacentLoc) && (stunTrapInfo[adjacentLoc.x][adjacentLoc.y] != 0)){
                    adjacentStunTrap = true;
                    break;
                }
                }


            if(!adjacentStunTrap && rc.canBuild(TrapType.STUN, potentialBuildLocation)){
                rc.build(TrapType.STUN, potentialBuildLocation);

                Util.LOGGING_ALLOWED = true;
                Util.logBytecode("after placing trap");
                Util.LOGGING_ALLOWED = false;
                return;
            }
        }
    }


    public void runUnsafeStrategy() throws GameActionException {
        // if we have been retreated in the last 3 rounds, either retreat again or move towards your enemies

        // made this it's own method in case we want to add more logic here
        moveToSafestSpot();
    }

    public void runSafeStrategy() throws GameActionException {
        // TODO: should we consider chasing down enemies???
        // TODO: max attack specialization can attack twice in one round. We need to factor that in
        // maybe it's not worth since they just respawn
        // maybe something like chase enemies with the flag, but not otherwise?

        robot.tryPickingUpOppFlag();
        if (robot.nearbyActionEnemies.length != 0) {
            if (rc.isMovementReady()) {
                moveToSafestSpot();
            }
        } else if (robot.nearbyVisionEnemies.length != 0) {
            if (rc.isActionReady() && rc.isMovementReady()) {
                moveToBestPushLocation();
            } else if (rc.isMovementReady()) {
                moveToSafestSpot();
            }
        }
    }

    public MapLocation findBestHealPatient() throws GameActionException {
        // this method finds the best patient to heal
        int worstHealth = GameConstants.DEFAULT_HEALTH;
        MapLocation weakestFriendlyLoc = null;
        for (RobotInfo robot : robot.nearbyFriendlies) {
            boolean canHeal = rc.canHeal(robot.getLocation());

            // if the bot carrying the flag is not at 100%, priortize that one
            if (canHeal && robot.getHealth() < GameConstants.DEFAULT_HEALTH && robot.hasFlag()) {
                return robot.getLocation();
            }

            if (rc.canHeal(robot.location) && robot.getHealth() < worstHealth) {
                worstHealth = rc.getHealth();
                weakestFriendlyLoc = robot.getLocation();
            }
        }
        return weakestFriendlyLoc;
    }

    public boolean runHealing() throws GameActionException {
        // note: if we got to this method, it means we didn't attack
        // this method returns true if it heals a bot, and false if it doesn't heal a bot

        // check to see if there are no opp in vision radius
        if (robot.nearbyActionEnemies.length == 0 && robot.nearbyFriendlies.length > 0) {
            // find the weakest friendly to heal
            MapLocation weakestPatientLoc = findBestHealPatient();
            if (weakestPatientLoc != null) {
                // heal the boi that needs most help
                Util.addToIndicatorString("HL");
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

        if(previouslySafe && !heuristic.getSafe()){
            Util.addToIndicatorString("UNSAFE");
            tryPlacingStunTrap(); // tries to place stun trap in direction of enemyCOM
            Util.logBytecode("After placing stun trap");
        }
        previouslySafe = heuristic.getSafe();


        updateAllNearbyAttackInfo();
        Util.addToIndicatorString("SF:" + heuristic.getSafe());
    }


    public boolean haveLotOfCrumbs(){
        return rc.getCrumbs() >= 1000;
    }

    public void runStrategy() throws GameActionException {
        // main entry point to this module, which will run any attacking strategy (attacking micro).

        // keep retreating if we started retreating in the last x rounds
        boolean amSafe = heuristic.getSafe();
        if (amSafe) {
            // if we've been reatreating in the last x rounds, keep retreating
            if (lastRetreatRound != -1 && (rc.getRoundNum() - lastRetreatRound) < Constants.NUM_ROUNDS_TO_RETREAT_FOR) {
                moveToSafestSpot();
            } else {
                lastRetreatRound = -1;
                runSafeStrategy();
            }
        } else {
            lastRetreatRound = rc.getRoundNum(); // keep track of the round you started retreat in
            runUnsafeStrategy();
        }

        bestAttackVictim = getBestAttackVictim();
        boolean successfullyAttacked = runAttack(); // try Attacking
        runHealing(); // try healing

        if(!amSafe || haveLotOfCrumbs()) {
            tryPlacingStunTrap(); // tries to place stun trap in direction of enemyCOM if possible
        }
    }

    public double getHeuristicSafetyMultiplier() {
        switch (robot.mode) {
            case OFFENSE:
                return Constants.OFFENSE_ATTACK_SAFETY_FACTOR;
            case STATIONARY_DEFENSE:
                return Constants.STATIONARY_DEFENSE_ATTACK_SAFETY_FACTOR;
            case MOBILE_DEFENSE:
                return Constants.MOBILE_DEFENSE_ATTACK_SAFETY_FACTOR;
            default:
                throw new RuntimeException("Unkonwn mode when computing heuristic " + robot.mode.toShortString() + "!");
        }
    }


    public RobotInfo getClosestBot(RobotInfo[] arr) throws GameActionException {
        // this method finds the closest enemy in the vision radius of the current bot
        int distanceToClosestBot = Integer.MAX_VALUE;
        RobotInfo closestBot = null;

        if (robot.nearbyFriendlies.length == 0) {
            return null;
        }

        for (int i = robot.nearbyFriendlies.length; --i >= 0; ) {
            RobotInfo enemy = robot.nearbyFriendlies[i];
            int distanceToEnemy = robot.myLoc.distanceSquaredTo(enemy.location);
            if (distanceToEnemy < distanceToClosestBot) {
                distanceToClosestBot = distanceToEnemy;
                closestBot = enemy;
            }
        }
        return closestBot;
    }


    public AttackHeuristic getHeuristic(RobotInfo[] visionFriendlies, RobotInfo[] visionEnemies, RobotInfo[] attackFriendlies, RobotInfo[] attackEnemies, boolean hasFlag) throws GameActionException {
        // TODO: we should calculate the legit damage values here according to bot specializations.
        //  Not sure if there's a way to that without hardcoding in values at the moment.


        RobotInfo[] nearbyEnemies = rc.senseNearbyRobots(9, robot.oppTeam);

        // factor in rounds to kill
        // factor in enemy HP
        double safetyMultiplier = getHeuristicSafetyMultiplier();
        double friendlyDamage = 0.0;
        double enemyDamage = 0.0;
        double friendlyHP = 0.0;
        double enemyHP = 0.0;

//        RobotInfo nearestEnemy = getClosestBot(visionEnemies);
        RobotInfo nearestEnemy = getBestAttackVictim();
        if(nearestEnemy == null){
            nearestEnemy = getClosestBot(visionEnemies);
        }

        for (int i = nearbyEnemies.length; --i >= 0; ) {
            RobotInfo enemy = nearbyEnemies[i];
            double attackDamage = Util.getAttackDamage(enemy);
            enemyDamage += attackDamage / Util.getAttackCooldown(enemy);
            enemyHP += enemy.getHealth();
        }

        // calculate friendlies attacking the enemy
        for (int i = visionFriendlies.length; --i >= 0; ) {
            RobotInfo friendly = visionFriendlies[i];

            // if this friendly can't attack the closest enemy to me, don't consider the friendly
//            if (nearestEnemy != null &&
//                    friendly.getLocation().distanceSquaredTo(nearestEnemy.getLocation()) > 9) {
            if (nearestEnemy != null &&
                    friendly.getLocation().distanceSquaredTo(nearestEnemy.getLocation()) > 9) {
                continue;
            }

            double attackDamage = Util.getAttackDamage(friendly);
            friendlyDamage += attackDamage / Util.getAttackCooldown(friendly);
            friendlyHP += friendly.getHealth();
        }

//        friendlyHP = 0.0;
//        enemyHP = 0.0;

        if(nearestEnemy != null){
            enemyHP += nearestEnemy.getHealth();
        }

        RobotInfo myRobotInfo = rc.senseRobot(rc.getID());
        // factor in the damage that you can do
        double myAttackDamage = Util.getAttackDamage(myRobotInfo);
        friendlyDamage += myAttackDamage / Util.getAttackCooldown(myRobotInfo);
        friendlyHP += myRobotInfo.getHealth();
//        Util.addToIndicatorString("FD:" + (int) friendlyDamage + ",ED:" + (int) enemyDamage);
//        Util.addToIndicatorString("FHP:" + (int) friendlyHP + ",EHP:" + (int) enemyHP);

        return new AttackHeuristic(friendlyHP, friendlyDamage, enemyHP, enemyDamage, hasFlag, safetyMultiplier);
    }
}
