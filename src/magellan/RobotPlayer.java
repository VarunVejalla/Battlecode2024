package magellan;

import battlecode.common.Clock;
import battlecode.common.RobotController;


/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function: this is what we'll call once your robot
 * is created!
 */
public strictfp class RobotPlayer {

    /**
     * We will use this variable to count the number of turns this robot has been alive.
     * You can use static variables like this to save any information you want. Keep in mind that even though
     * these variables are static, in Battlecode they aren't actually shared between your robots.
     */

    /**
     * A random number generator.
     * We will use this RNG to make some random moves. The Random class is provided by the java.util.Random
     * import at the top of this file. Here, we *seed* the RNG with a constant number (6147); this makes sure
     * we get the same sequence of numbers every time this code is run. This is very useful for debugging!
     */



    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * It is like the main function for your robot. If this method returns, the robot dies!
     *
     * @param rc  The RobotController object. You use it to perform actions from this robot, and to get
     *            information on its current status. Essentially your portal to interacting with the world.
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws Exception {
        int startTurn = rc.getRoundNum();
        Robot robot = new Robot(rc);
        if(rc.getRoundNum() != startTurn){
            Util.log("BYTECODE EXCEEDED");
            rc.resign();
        }
        while (true) {
            startTurn = rc.getRoundNum();

            try{
                robot.run();
                if(rc.getRoundNum() != startTurn){
                    Util.log("BYTECODE EXCEEDED");
//                    rc.resign();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                Clock.yield();
            }
        }
    }



}
