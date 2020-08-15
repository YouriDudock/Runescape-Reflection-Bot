package botting.bot.automation.framework.task.lib.walking.data;

import botting.bot.automation.tools.condition.Condition;
import botting.bot.manager.ManagerRepository;
import botting.game.GameTile;

/**
 * @author Youri Dudock
 */
public class TileWalkAction implements WalkAction {

    private GameTile destination;

    public TileWalkAction(GameTile destination) {
        this.destination = destination;
    }

    @Override
    public void perform(ManagerRepository managers) {
        destination.clickOnMinimap();


        Condition.waitTill(() -> !managers.getPlayer().getLocalPlayer().isWalking() || managers.getPlayer().getLocalPlayer().distanceFrom(destination) <= 5, 10000);
    }

    @Override
    public boolean hasArrived(ManagerRepository managers) {
        if (managers.getPlayer().getLocalPlayer().distanceFrom(destination) <= 5) {
            return true;
        }

        return false;
    }

}
