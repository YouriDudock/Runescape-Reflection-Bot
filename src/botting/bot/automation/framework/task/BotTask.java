package botting.bot.automation.framework.task;

import botting.bot.BotInstance;
import botting.bot.automation.script.Script;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.bot.debug.DebuggerCaller;

public abstract class  BotTask<S extends Script> implements DebuggerCaller {

    public abstract void execute();

    protected abstract boolean shouldActivate();

    private BotInstance bot;

    protected S script;

    public BotTask(BotInstance bot, S script) {
        this.script = script;
        this.bot = bot;
    }

    public boolean shouldRun() {
        if (script.isRunning() && !script.shouldStop()) {
            return shouldActivate();
        }

        return false;
    }


    @Override
    public void debug(String log, DebugPriority priority) {
        Debugger.write(getClass(), log, priority);
    }

    @Override
    public void debug(String log) {
        debug(log, DebugPriority.HIGH);
    }
}
