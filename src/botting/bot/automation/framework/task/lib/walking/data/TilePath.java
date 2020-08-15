package botting.bot.automation.framework.task.lib.walking.data;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;

/**
 * @author Youri Dudock
 */
public class TilePath {

    private WalkAction[] path;

    private int nextTile = 0;

    private BotInstance bot;

    public WalkAction getNextDestination() {
        return path[nextTile];
    }

    public int getPathLength() {
        return path.length;
    }

    public boolean hasReachedDestination() {
        return getPathLength() == nextTile;
    }

    public TilePath(BotInstance bot, WalkAction[] path) {
        this.bot = bot;
        this.path = path;
    }

    public WalkAction[] getPath() {
        return path;
    }



    public void step() {
        WalkAction action = getNextDestination();
        Debugger.write(getClass(), "Trying to reach next tile: " + nextTile, DebugPriority.HIGH);
        action.perform(bot.getManagers());

        if (action.hasArrived(bot.getManagers())) {
            Debugger.write(getClass(), "Has arrived at temp destination: " + nextTile, DebugPriority.HIGH);
            nextTile += 1;
        }

        if (hasReachedDestination()) {
            Debugger.write(getClass(), "Has arrived at final destination!", DebugPriority.HIGH);
        }


    }

    public void resetWalkingProgress() {
        nextTile = 0;
    }
}
