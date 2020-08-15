package botting.bot.automation.script.ikov.rockcrabs;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.lib.CombatTask;
import botting.bot.automation.script.Script;
import botting.game.GameTile;
import botting.game.data.GameArea;

/**
 * @author Youri Dudock
 */
public class RockCrabsScript extends Script {

    public final GameArea ROCK_CRABS = new GameArea(createTile(2664, 3714), createTile(2687, 3737));

    public final GameTile ROCK_CRAB_TELEPORT_TILE = createTile(2673, 3712);

    public CombatTask combatTask;

    public TravelToRockCrabsTask travelTask;

    public RockCrabsScript(BotInstance bot) {
        super(bot);

        combatTask = new CombatTask(bot, this);
        travelTask = new TravelToRockCrabsTask(bot, this);
    }

    @Override
    public void onStart() {
        combatTask.setTargetName("Rock Crab");
        combatTask.setKillArea(ROCK_CRABS);

    }

    @Override
    public void onTick() {

        if (travelTask.shouldRun()) {
            travelTask.execute();
        }

        if (combatTask.shouldRun()) {
            combatTask.execute();
        }

        getAntiBan().sleep(50);
    }

    @Override
    public void onStop() {

    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
