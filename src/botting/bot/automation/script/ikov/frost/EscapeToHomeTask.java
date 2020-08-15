package botting.bot.automation.script.ikov.frost;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.tools.condition.Condition;
import botting.bot.debug.DebugPriority;
import botting.game.data.GameTab;
import botting.rs.client.clients.ikov.Ikov;

/**
 * @author Youri Dudock
 */
public class EscapeToHomeTask extends BotTask<FrostDragonScript> {

    public EscapeToHomeTask(BotInstance bot, FrostDragonScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Teleporting home..", DebugPriority.MEDIUM);

        // open mage tab
        script.getHUD().openTab(Ikov.Game.Tabs.MAGIC_BOOK);

        // wait a bit
        script.getAntiBan().sleep(500);

        // click location of home spell
        script.getMouse().click(script.HOME_TELEPORT_SPELL_POINT);

        // wait till player is at home teleport tile AND is not doing any animations (like the home teleport animation)
        Condition.waitTill(() -> script.getLocalPlayer().getLocation().equals(script.HOME_TELEPORT_TILE) && script.getLocalPlayer().getAnimation() <= 0, 6000);

    }

    @Override
    public boolean shouldActivate() {
        debug("Checking teleport home task..", DebugPriority.MEDIUM);

        // unknown location?
        if (!script.getLocalPlayer().isInArea(script.HOME) && !script.getLocalPlayer().isInArea(script.FROST_DRAGONS) && !script.getLocalPlayer().getLocation().equals(script.FROST_TELEPORT_TILE)) {
            debug("Detected unknown location.", DebugPriority.MEDIUM);
            return true;
        }

        // check low hitpoints without food at frost dragons
        if (script.getLocalPlayer().isInArea(script.FROST_DRAGONS) &&
                script.getPlayer().getSkills().getHitpoints() <= script.HITPOINTS_THRESPOINT &&
                !script.getPlayer().getInventory().items().contains(script.FOOD)) {

            debug("Detected low hitpoints without food.", DebugPriority.MEDIUM);
            return true;
        }

        // check full inv without any food left at frosts
        if (script.getLocalPlayer().isInArea(script.FROST_DRAGONS) &&
                script.getPlayer().getInventory().items().isFull() &&
                !script.getPlayer().getInventory().items().contains(script.FOOD)) {

            debug("Detected full inventory without food.", DebugPriority.MEDIUM);
            return true;
        }

        return false;
    }

}
