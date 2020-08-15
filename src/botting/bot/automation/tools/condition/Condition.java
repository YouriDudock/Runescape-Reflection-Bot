package botting.bot.automation.tools.condition;

import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;

import static java.lang.Thread.sleep;

/**
 * @author Youri Dudock
 */
public class Condition {

    private static int sleepTime = 100;

    public static void waitTill(WaitCondition condition, int waitTime) {
        int shouldStop = waitTime / sleepTime;
        int iteration = 0;

        Debugger.write(Condition.class, "Waiting: " + waitTime, DebugPriority.MEDIUM);
        while (true) {
            if (condition.till()) {
                Debugger.write(Condition.class, "Wait Condition is MET " + waitTime, DebugPriority.MEDIUM);
                break;
            }
            if (iteration >= shouldStop) {
                Debugger.write(Condition.class, "Wait Condition has FAILED " + waitTime, DebugPriority.MEDIUM);
                break;
            }

            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            iteration += 1;
        }

    }

    public static void waitTill(WaitCondition condition, GameAction action, int waitTime) {
        int shouldStop = waitTime / sleepTime;
        int iteration = 0;

        Debugger.write(Condition.class, "Waiting: " + waitTime, DebugPriority.MEDIUM);
        while (true) {
            action.perform();

            if (condition.till()) {
                Debugger.write(Condition.class, "Wait Condition is MET " + waitTime, DebugPriority.MEDIUM);
                break;
            }
            if (iteration >= shouldStop) {
                Debugger.write(Condition.class, "Wait Condition has FAILED " + waitTime, DebugPriority.MEDIUM);
                break;
            }


            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            iteration += 1;

        }
    }

}
