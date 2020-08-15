package botting.bot.automation.script.ikov.frost;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.tools.condition.Condition;
import botting.bot.debug.DebugPriority;
import botting.game.entity.GameNPC;

/**
 * @author Youri Dudock
 */
public class CombatTask extends BotTask<FrostDragonScript> {

    public CombatTask(BotInstance bot, FrostDragonScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Combat task executing.. Finding target..", DebugPriority.HIGH);

        // the target of the player
        GameNPC foundTarget = null;

        for (GameNPC target : script.getNPCS().getNpcs()) {

            // unknown npc?
            if (target.getName() == null) {
                continue;
            }

            // check if target is alive
            if (!target.isAlive()) {
                continue;
            }


            // check if target is in combat
            if (target.isInCombat()) {
                continue;
            }


            // check if name matches
            if (!target.getName().equals(script.TARGET_NAME)) {
                continue;
            }

            // check if the target is even in the area
            if (!target.isInArea(script.FROST_DRAGONS)) {
                continue;
            }

            // check if target is nearby
            if (script.getLocalPlayer().distanceFrom(target.getLocation()) > 16) {
                continue;
            }

            // check if target is on the screen
            if (!target.isOnScreen()) {
                // if not, turn to
                script.getCamera().turnTo(target);
            }

            // check if target is now on screen
            if (!target.isOnScreen()) {
                continue;
            }

            // check combat again, because this status could have changed during camera turn
            if (target.isInCombat()) {
                continue;
            }


            // move mouse to target
            script.getMouse().moveMouse(target.getCenterPointOnScreen());

            // wait a bit so the client can process
            script.getAntiBan().sleep(100, false);

            // get amount of attack options from mosue location
            int options = script.getInterfaces().getMenu().countOptions("Attack " + target.getName());

            // if 0, there is no attack option, if higher than 1: we skip the target because we cannot difference between target and other NPCS
            if (options == 0 || options > 1) {
                debug("Cant attack target because there are too many options (or none). Options: " + options, DebugPriority.MEDIUM);
                continue;
            }

            // target found!
            foundTarget = target;
            // break loop because we have a target
            break;
        }


        if (foundTarget == null) {
            debug("Target could not be found.", DebugPriority.HIGH);
            return;
        }


        // attack target
        boolean hasInteracted = foundTarget.interact("Attack " + foundTarget.getName());

        // check if has interacted
        if (hasInteracted) {
            Condition.waitTill(() -> script.getLocalPlayer().isInCombat() || !script.getLocalPlayer().isWalking(), 10000);
        }

    }

    @Override
    public boolean shouldActivate() {
        debug("Checking combat task..", DebugPriority.MEDIUM);

        // we are not in the fighting stage
        if (!script.getLocalPlayer().isInArea(script.FROST_DRAGONS)) {
            debug("Cannot execute combat script because not in area.", DebugPriority.MEDIUM);
            return false;
        }

        // player already fighting
        if (script.getLocalPlayer().isInCombat()) {
            debug("Cannot execute combat script because player in combat", DebugPriority.MEDIUM);
            return false;
        }

        // check if player is walking
        if (script.getLocalPlayer().isWalking()) {
            debug("Cannot execute because player is walking.", DebugPriority.MEDIUM);
            return false;
        }

        // DOUBLE CHECK, BECAUSE WALKING TAKES TIME TO CALCULATE
        // @TODO WALKING CHECK WITHOUT DELAY!
        if (script.getLocalPlayer().isInCombat()) {
            debug("Cannot execute combat script because player in combat", DebugPriority.MEDIUM);
            return false;
        }


        return true;
    }
}
