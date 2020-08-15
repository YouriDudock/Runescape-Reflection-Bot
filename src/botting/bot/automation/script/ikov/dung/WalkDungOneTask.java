package botting.bot.automation.script.ikov.dung;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.framework.task.lib.walking.WalkTask;
import botting.bot.automation.framework.task.lib.walking.data.ObjectClickWalkAction;
import botting.bot.automation.framework.task.lib.walking.data.TileWalkAction;
import botting.bot.automation.framework.task.lib.walking.data.WalkAction;

/**
 * @author Youri Dudock
 */
public class WalkDungOneTask  extends BotTask<DungScript> {

    private final int doorWay = 6553, portal = 2156;
    private String boss = "Icy Bones";

    private final WalkAction[] floorOnePath = {
            new TileWalkAction(script.createTile(3234, 9323)),
            new ObjectClickWalkAction(script.createTile(3234, 9325), doorWay, "Open"),
            new TileWalkAction(script.createTile(3244, 9332)),
            new TileWalkAction(script.createTile(3236, 9334)),
            new TileWalkAction(script.createTile(3227, 9334)),
            new TileWalkAction(script.createTile(3220, 9328)),
            new TileWalkAction(script.createTile(3211, 9334)),
            new TileWalkAction(script.createTile(3210, 9322)),
            new TileWalkAction(script.createTile(3221, 9317)),
            new TileWalkAction(script.createTile(3221, 9309)),
            new TileWalkAction(script.createTile(3220, 9298)),
            new TileWalkAction(script.createTile(3231, 9296)),
            new ObjectClickWalkAction(script.createTile(2860, 9739), portal, "Enter")

    };


    private WalkTask walker;

    public WalkDungOneTask(BotInstance bot, DungScript script) {
        super(bot, script);

        walker = new WalkTask(bot, script);
    }

    @Override
    public void execute() {
        // setup path
        if (script.getLocalPlayer().getLocation().equals(script.FLOOR_1_SPAWN)) {
            walker.setPath(floorOnePath);
        }

        if (walker.shouldRun()) {
            walker.execute();
        }

    }

    @Override
    protected boolean shouldActivate() {
        return script.getLocalPlayer().isInArea(script.FLOOR_1_AREA);
    }
}
