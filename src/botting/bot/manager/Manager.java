package botting.bot.manager;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.bot.debug.DebuggerCaller;
import botting.environment.BotSession;
import botting.reflection.hooking.HookProvider;

/**
 * @author Youri Dudock
 */
public abstract class Manager implements DebuggerCaller {

    protected BotInstance bot;

    public Manager(BotInstance bot) {
        this.bot = bot;
    }

    protected ManagerRepository getManagers() {
        return bot.getManagers();
    }


    protected HookProvider getHooks() {
        return bot.getHooks();
    }

    protected BotSession getBotSession() {
        return bot.getSession();
    }

    @Override
    public void debug(String log, DebugPriority priority) {
        Debugger.write(getClass(), log, priority);
    }

    @Override
    public void debug(String log) {
        Debugger.write(getClass(), log, DebugPriority.MEDIUM);
    }
}
