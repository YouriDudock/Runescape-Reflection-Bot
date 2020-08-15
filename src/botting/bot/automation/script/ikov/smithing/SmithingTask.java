package botting.bot.automation.script.ikov.smithing;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.script.ikov.PracticeScript;
import botting.bot.automation.tools.condition.Condition;
import botting.bot.debug.DebugPriority;
import botting.game.GameObject;

import java.awt.*;
import java.util.Optional;

/**
 * @author Yoeri Leijdekker
 */
public class SmithingTask extends BotTask<PracticeScript> {

    public SmithingTask(BotInstance bot, PracticeScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Making Platebodies", DebugPriority.HIGH);

        script.getInterfaces().getMenu().interact("Smith All", new Point(258, 230));

    }

    @Override
    public boolean shouldActivate() {
        return script.getLocalPlayer().isInArea(script.SMITHING_AREA) && !script.getInterfaces().getBank().isOpen();
    }
}