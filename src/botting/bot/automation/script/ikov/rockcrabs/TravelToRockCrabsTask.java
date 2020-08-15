package botting.bot.automation.script.ikov.rockcrabs;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.tools.condition.Condition;
import botting.bot.debug.DebugPriority;
import botting.game.data.GameTab;
import botting.rs.client.clients.ikov.Ikov;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Youri Dudock
 */
public class TravelToRockCrabsTask extends BotTask<RockCrabsScript> {

    public TravelToRockCrabsTask(BotInstance bot, RockCrabsScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Teleporting to rock crabs..", DebugPriority.HIGH);

        script.getHUD().openTab(Ikov.Game.Tabs.MAGIC_BOOK);
        script.getAntiBan().sleep(500);

        // click teleport spell from normal spellbook
        script.getMouse().click(new Point(569, 274));
        script.getAntiBan().sleep(2000);

        script.getKeyboard().pressKey(KeyEvent.VK_BACK_SPACE);
        script.getAntiBan().sleep(100);

        script.getKeyboard().pressKey(KeyEvent.VK_BACK_SPACE);
        script.getAntiBan().sleep(100);

        script.getKeyboard().pressKey('a');
        script.getAntiBan().sleep(200);

        script.getKeyboard().pressKey('b');
        script.getAntiBan().sleep(200);

        Condition.waitTill(() -> script.getLocalPlayer().getLocation().equals(script.ROCK_CRAB_TELEPORT_TILE) && script.getLocalPlayer().getAnimation() <= 0, 6000);
    }

    @Override
    protected boolean shouldActivate() {
        return !script.getLocalPlayer().isInArea(script.ROCK_CRABS);
    }
}
