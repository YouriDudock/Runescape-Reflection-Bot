package botting.bot.debug;

import botting.environment.ui.BotUI;

import javax.swing.*;

/**
 * Deals with the debugging of information from the botting.bot
 *
 * @author Youri Dudock
 */
public class Debugger {

    public static boolean isDebugging = false;

    /**
     * This field controls what level of log priority is shown in the debugger
     *
     * LOW = Show getNearest logs
     * MEDIUM = Show MEDIUM and HIGH logs
     * HIGH = only show HIGH logs
     */
    private final static DebugPriority priorityFilter = DebugPriority.LOW;

    /**
     * Writes log to the the console
     *
     * @param caller
     * @param priority priority of the log
     * @param log
     */
    public static void write(Class caller, String log, DebugPriority priority) {
        System.out.println("["+ caller.getName()+"] " + log);
        //JOptionPane.showMessageDialog(BotUI.getInstance(), log);
    }

}
