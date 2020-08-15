package botting.bot.automation.script.ikov.dung;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.framework.task.lib.CombatTask;
import botting.bot.automation.script.Script;
import botting.bot.debug.DebugPriority;
import botting.game.GameTile;
import botting.game.data.GameArea;
import botting.game.data.GameSkill;
import botting.rs.client.clients.ikov.Ikov;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public class DungScript extends Script {

    public final GameArea DUNG_AREA = new GameArea(createTile(3448,3712 ), createTile(3458, 3736));

    public final GameArea FLOOR_1_AREA = new GameArea(createTile(3034, 9133), createTile(3334, 9422));

    public final GameArea FLOOR_1_BOSS_AREA = new GameArea(createTile(2850, 9725), createTile(2870, 9745));

    public GameTile FLOOR_1_SPAWN = createTile(3233, 9315);
    public GameTile FLOOR_2_SPAWN = createTile(2618, 9796);

    public final Point FLOOR_1_OPTION = new Point(262, 393);
    public final Point FLOOR_2_OPTION = new Point(259, 411);



    public BotTask[] dungOneTasks = {
            new EnterDungTask(getBot(), this),
            new WalkDungOneTask(getBot(), this),

            new CombatTask(getBot(), this) {
                @Override
                public String getTargetName() {
                    return "Icy Bones";
                }

                @Override
                public GameArea getKillArea() {
                    return FLOOR_1_BOSS_AREA;
                }
            }
    };

    public BotTask[] dungTwoTasks = {
            new EnterDungTask(getBot(), this)
    };

    public DungScript(BotInstance bot) {
        super(bot);


    }

    @Override
    public void onStart() {

        setDismissRandomInterfaces(true);
        setDismissCastOn(true);
        setAutoLogin(true);
        setDismissUseItemOnItem(true);
    }

    @Override
    public void onTick() {

        // perform room one!
        if (getDungLevel() < 500) {

            for (BotTask task : dungOneTasks) {
                if (task.shouldRun()) {
                    task.execute();
                }
            }


        } else { // room two

            for (BotTask task : dungTwoTasks) {
                if (task.shouldRun()) {
                    task.execute();
                }
            }

        }
    }

    @Override
    public void onStop() {

    }

    @Override
    public boolean shouldStop() {
        return false;
    }

    public int getDungLevel() {
        return getSkills().getStat(Ikov.Game.Skills.DUNGENEERING);
    }


}
