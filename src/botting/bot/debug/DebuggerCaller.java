package botting.bot.debug;

/**
 * @author Youri Dudock
 */
public interface DebuggerCaller {

    public void debug(String log, DebugPriority priority);
    public void debug(String log);

}
