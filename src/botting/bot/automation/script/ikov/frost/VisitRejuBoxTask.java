package botting.bot.automation.script.ikov.frost;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.tools.condition.Condition;
import botting.bot.debug.DebugPriority;
import botting.game.GameObject;

import java.util.Optional;

/**
 * @author Youri Dudock
 */
public class VisitRejuBoxTask extends BotTask<FrostDragonScript> {

    public VisitRejuBoxTask(BotInstance bot, FrostDragonScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Visiting reju..", DebugPriority.HIGH);

        // get reju box
        Optional<GameObject> rejuBox = script.getObjects().getNearest(script.REJU_BOX_ID, 10);

        // check if the reju box was found
        if (rejuBox.isPresent()) {

            // are we nearby enough?
            if (script.getLocalPlayer().distanceFrom(rejuBox.get()) <= 12) {

                // is the box already on screen?
                if (!rejuBox.get().isOnScreen()) {
                    script.getCamera().turnTo(rejuBox.get());
                }

                // is it now on screen?
                if (rejuBox.get().isOnScreen()) {

                    // use the box
                    boolean hasInteracted = rejuBox.get().interact("Use Reju");

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
        return script.getLocalPlayer().getLocation().equals(script.HOME_TELEPORT_TILE);
    }
}
