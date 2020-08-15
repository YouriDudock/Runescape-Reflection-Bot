package botting.bot.automation.script.ikov.dung;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.tools.condition.Condition;
import botting.bot.automation.tools.filter.Filter;
import botting.bot.debug.DebugPriority;
import botting.game.entity.GameNPC;

import java.util.Optional;

/**
 * @author Youri Dudock
 */
public class EnterDungTask extends BotTask<DungScript> {
    public EnterDungTask(BotInstance bot, DungScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {

        // we have an option dialog! Right now we cannot read what it says, so lets assume its correct or auto fix it by reclicking thok
        if (script.getDialog().hasSelectOptionOpen() && script.getDialog().getOptionCount() == 3) {

            // check dung level
            if (script.getDungLevel() < 500) {
                script.getMouse().click(script.FLOOR_1_OPTION);
            } else {
                script.getMouse().click(script.FLOOR_2_OPTION);
            }

            Condition.waitTill(() -> script.getLocalPlayer().getLocation().equals(script.FLOOR_1_SPAWN) || script.getLocalPlayer().getLocation().equals(script.FLOOR_2_SPAWN), 5000);

        } else { // find thok!
                // get thok, dung master
                Optional<GameNPC> thok = script.getNPCS().getNearest(subject -> subject.getName().contains("Thok"));

                // check if thok is present
                if (thok.isPresent()) {

                    // try to interact
                    boolean hasInteracted = thok.get().interact("Single");
                }
            }





    }

    @Override
    protected boolean shouldActivate() {
        return script.getLocalPlayer().isInArea(script.DUNG_AREA);
    }
}
