package botting.bot.automation.script.ikov.smithing;


import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.script.ikov.PracticeScript;
import botting.bot.automation.tools.condition.Condition;
import botting.bot.debug.DebugPriority;
import botting.game.data.GameTab;
import botting.rs.client.clients.ikov.Ikov;

/**
 * @author Yoeri Leijdekker
 */
public class TeleportTask extends BotTask<PracticeScript> {

    public TeleportTask(BotInstance bot, PracticeScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Teleporting to smithing area..", DebugPriority.MEDIUM);

        // open mage tab
        script.getHUD().openTab(Ikov.Game.Tabs.MAGIC_BOOK);

        // wait a bit
        script.getAntiBan().sleep(500);

        // click location of home spell
        script.getMouse().click(script.SMITHING_TELEPORT_SPELL_POINT);
        script.getAntiBan().sleep(200);

        script.getKeyboard().pressKey('h');
        script.getAntiBan().sleep(200);

        script.getKeyboard().pressKey('b');
        script.getAntiBan().sleep(200);

        // wait till player is at smithing teleport tile AND is not doing any animations (like the home teleport animation)
        Condition.waitTill(() -> script.getLocalPlayer().getLocation().equals(script.SMITHING_TELEPORT_TILE) && script.getLocalPlayer().getAnimation() <= 0, 6000);

    }

    @Override
    public boolean shouldActivate() {
        debug("Checking teleport to smithing area task..", DebugPriority.MEDIUM);

        // unknown location?
        if (!script.getLocalPlayer().isInArea(script.SMITHING_AREA)) {
            debug("Detected unknown location.", DebugPriority.MEDIUM);
            return true;
        }

        return false;
    }

}