package botting.bot.automation.script.ikov.smithing;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.script.ikov.PracticeScript;
import botting.bot.automation.script.ikov.frost.FrostDragonScript;
import botting.bot.automation.tools.condition.Condition;
import botting.bot.debug.DebugPriority;
import botting.bot.manager.impl.BankManager;
import botting.game.GameObject;

import java.util.Optional;

/**
 * @author Yoeri Leijdekker
 */
public class VisitBankTask extends BotTask<PracticeScript> {

    public VisitBankTask(BotInstance bot, PracticeScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Visiting bank..", DebugPriority.HIGH);


        Optional<GameObject> bankBooth = script.getObjects().getNearest(BankManager.BANKS);


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
        return script.getLocalPlayer().isInArea(script.SMITHING_AREA) && !script.getInterfaces().getBank().isOpen();
    }
}
