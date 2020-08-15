package botting.bot.automation.script.ikov.frost;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.tools.condition.Condition;
import botting.bot.debug.DebugPriority;
import botting.game.GameGroundItem;

/**
 * @author Youri Dudock
 */
public class LootingTask extends BotTask<FrostDragonScript> {

    // used for short caching
    private GameGroundItem[] foundDrops;

    public LootingTask(BotInstance bot, FrostDragonScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Starting looting..", DebugPriority.HIGH);

        // loop through loots
        for (GameGroundItem loot : foundDrops) {

            // if loot not on screen, but it is nearby
            if (!loot.isOnScreen() && script.getLocalPlayer().distanceFrom(loot.getLocation()) <= 8) {
                // turn camera to loot
                script.getCamera().turnTo(loot);
            }

            // check if loot is on screen again
            if (loot.isOnScreen()) {

                // make room for new drop by eating food
                if (script.getPlayer().getInventory().items().isFull()) {

                    // if we have interacted with an inventory item
                    boolean hasInteracted = false;

                    // check if we can free up space
                    if (script.getPlayer().getInventory().items().contains(script.EMPTY_VIAL)) {
                        // drop empty vials from pots
                        hasInteracted = script.getPlayer().getInventory().items().first(script.EMPTY_VIAL).get().interact("Drop");

                        // else check for food
                    } else if (script.getPlayer().getInventory().items().contains(script.FOOD))
                    {
                        // Eat food
                        hasInteracted = script.getPlayer().getInventory().items().first(script.FOOD).get().interact("Eat");
                    }

                    // if we have done something
                    if (hasInteracted) {
                        // then sleep for a bit to give the client time
                        script.getAntiBan().sleep(200, false);
                    }

                }

                // take the loot
                boolean hasInteracted = loot.interact("Take " + loot.getItem().getName());

                // check if player has interacted
                if (hasInteracted) {
                    // wait till player is done walking or 10 sec
                    Condition.waitTill(() -> !script.getLocalPlayer().isWalking(), 10000);
                    script.bonesLooted += 1;
                }
            }


            // fail safe to check if the player is in combat
            // we dont want him running around trying to loot stuff while being
            // pulled back by auto rel
            // shouldnt happen tho...
            if (script.getLocalPlayer().isInCombat()) {
                // break loop!
                break;
            }


        }
    }

    @Override
    public boolean shouldActivate() {
        debug("Checking looting task..", DebugPriority.MEDIUM);

        // check if in frost area (also prevents being lured away)
        if (!script.getLocalPlayer().isInArea(script.FROST_DRAGONS)) {
            return false;
        }

        // check if currently walking
        if (script.getLocalPlayer().isWalking()) {
            return false;
        }

        // get drops around us
        foundDrops = script.getWorld().getGroundItemManager().findAny(script.DROPS, 8);

        // if we found more than one or more drops: looting!
        if (foundDrops.length > 0) {
            debug("Detected drops: " + foundDrops.length, DebugPriority.MEDIUM);
            return true;
        }


        return false;
    }

}
