package botting.bot.automation.script.ikov.frost;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.debug.DebugPriority;
import botting.game.GameItem;
import botting.rs.client.clients.ikov.Ikov;

import java.util.Optional;

/**
 * @author Youri Dudock
 */
public class ConsumingTask extends BotTask<FrostDragonScript> {

    public ConsumingTask(BotInstance bot, FrostDragonScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Consuming...", DebugPriority.HIGH);

        // open inv
        script.getHUD().openTab(Ikov.Game.Tabs.INVENTORY);
        script.getAntiBan().sleep(500);

        if (script.getPlayer().getSkills().getHitpoints() <= script.HITPOINTS_THRESPOINT) {

            // get food item from inventory
            Optional<GameItem> inventoryItem = script.getPlayer().getInventory().items().first(script.FOOD);

            // eat food
            inventoryItem.ifPresent(gameItem -> gameItem.interact("Eat"));

        }

        if (script.getPlayer().getPrayer().getPrayerPoints() <= script.PRAYER_THRESPOINT) {

            // get food item from inventory
            Optional<GameItem> inventoryItem = script.getPlayer().getInventory().items().firstOfAny(script.PRAYER_POTS);

            // drink pot
            inventoryItem.ifPresent(gameItem -> gameItem.interact("Drink"));
        }

        if (script.getPlayer().getSkills().getAttack() <= script.COMBAT_POT_THRESPOINT) {

            // get food item from inventory
            Optional<GameItem> inventoryItem = script.getPlayer().getInventory().items().firstOfAny(script.ATTACK_POTS);

            // drink pot
            inventoryItem.ifPresent(gameItem -> gameItem.interact("Drink"));
        }

        if (script.getPlayer().getSkills().getStrength() <= script.COMBAT_POT_THRESPOINT) {

            // get food item from inventory
            Optional<GameItem> inventoryItem = script.getPlayer().getInventory().items().firstOfAny(script.STRENGTH_POTS);

            // drink pot
            inventoryItem.ifPresent(gameItem -> gameItem.interact("Drink"));
        }

    }

    @Override
    public boolean shouldActivate() {
        debug("Checking consuming task..", DebugPriority.MEDIUM);

        // we are not in the fighting stage
        if (!script.getLocalPlayer().isInArea(script.FROST_DRAGONS)) {
            return false;
        }

        // check hitpoints
        if (script.getPlayer().getSkills().getHitpoints() <= script.HITPOINTS_THRESPOINT && script.getPlayer().getInventory().items().contains(script.FOOD)) {
            debug("Detected low hitpoints.", DebugPriority.MEDIUM);
            return true;
        }

         if (script.getPlayer().getPrayer().getPrayerPoints() <= script.PRAYER_THRESPOINT && script.getPlayer().getInventory().items().containsAny(script.PRAYER_POTS)) {
             debug("Detected low prayer.", DebugPriority.MEDIUM);
             return true;
         }

        if (script.getPlayer().getSkills().getAttack() <= script.COMBAT_POT_THRESPOINT && script.getPlayer().getInventory().items().containsAny(script.ATTACK_POTS)) {
            debug("Detected low attack.", DebugPriority.MEDIUM);
            return true;
        }

        if (script.getPlayer().getSkills().getStrength() <= script.COMBAT_POT_THRESPOINT && script.getPlayer().getInventory().items().containsAny(script.STRENGTH_POTS)) {
            debug("Detected low strength.", DebugPriority.MEDIUM);
            return true;
        }


        return false;
    }
}
