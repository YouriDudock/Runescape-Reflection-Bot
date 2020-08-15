package botting.bot.automation.script.ikov.smithing;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.script.ikov.PracticeScript;
import botting.bot.automation.tools.condition.Condition;
import botting.bot.debug.DebugPriority;
import botting.game.GameObject;

import java.util.Optional;

/**
 * @author Yoeri Leijdekker
 */
public class VisitAnvilTask extends BotTask<PracticeScript> {

    public VisitAnvilTask(BotInstance bot, PracticeScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Visiting anvil", DebugPriority.HIGH);

        // get anvil
        Optional<GameObject> anvil = script.getObjects().getNearest(2783);

        if (anvil.isPresent()) {

            // are we nearby enough?
            if (script.getLocalPlayer().distanceFrom(anvil.get()) <= 15) {

                // is the box already on screen?
                if (!anvil.get().isOnScreen()) {
                    script.getCamera().turnTo(anvil.get());
                }

                // is it now on screen?
                if (anvil.get().isOnScreen()) {

                    // use the box
                    boolean hasInteracted = anvil.get().interact("Smith");

                    // check if we have interacted
                    if (hasInteracted) {
                        // wait till we are done walking
                        Condition.waitTill(() -> !script.getLocalPlayer().isWalking(), 5000);
                    }
                }

            }

        }

    }

    @Override
    public boolean shouldActivate() {
        return script.getLocalPlayer().isInArea(script.SMITHING_AREA) && !script.getInterfaces().getBank().isOpen()
                ;
    }
}
