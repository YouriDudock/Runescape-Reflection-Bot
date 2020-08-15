package botting.bot.automation.script.ikov.frost;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.tools.condition.Condition;
import botting.bot.debug.DebugPriority;
import botting.bot.manager.impl.BankManager;
import botting.game.GameObject;

import java.util.Optional;

/**
 * @author Youri Dudock
 */
public class VisitBankTask extends BotTask<FrostDragonScript> {

    public VisitBankTask(BotInstance bot, FrostDragonScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Visiting bank..", DebugPriority.HIGH);

        // get reju box
        Optional<GameObject> bankBooth = script.getObjects().getNearest(BankManager.BANKS);

        // check if the reju box was found
        if (bankBooth.isPresent()) {

            // are we nearby enough?
            if (script.getLocalPlayer().distanceFrom(bankBooth.get()) <= 15) {

                // is the box already on screen?
                if (!bankBooth.get().isOnScreen()) {
                    script.getCamera().turnTo(bankBooth.get());
                }

                // is it now on screen?
                if (bankBooth.get().isOnScreen()) {

                    // use the box
                    boolean hasInteracted = bankBooth.get().interact("Use bank");

                    // check if we have interacted
                    if (hasInteracted) {
                        // wait till we are done walking
                        Condition.waitTill(() -> !script.getLocalPlayer().isWalking() || script.getInterfaces().getBank().isOpen(), 5000);
                    }
                }

            }

        }

    }

    @Override
    public boolean shouldActivate() {
        return script.getLocalPlayer().isInArea(script.HOME) && !script.getInterfaces().getBank().isOpen() && !script.getLocalPlayer().getLocation().equals(script.HOME_TELEPORT_TILE);
    }
}
