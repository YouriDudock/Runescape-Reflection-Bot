package botting.bot.automation.framework.task.lib.walking;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.framework.task.lib.walking.data.TilePath;
import botting.bot.automation.framework.task.lib.walking.data.WalkAction;
import botting.bot.automation.script.Script;
import botting.bot.debug.DebugPriority;

/**
 * @author Youri Dudock
 */
public class WalkTask extends BotTask {

    private TilePath tilePath;


    public WalkTask(BotInstance bot, Script script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Trying to walk..", DebugPriority.HIGH);

        if (!tilePath.hasReachedDestination()) {
            tilePath.step();
        }

    }

    @Override
    protected boolean shouldActivate() {
        if (script.getLocalPlayer().isWalking()) {
            return false;
        }


        return true;
    }

    public void setPath(WalkAction[] tiles) {
        tilePath = new TilePath(script.getBot(), tiles);
    }
}
