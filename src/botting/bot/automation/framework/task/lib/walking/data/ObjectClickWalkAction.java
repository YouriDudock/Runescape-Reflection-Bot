package botting.bot.automation.framework.task.lib.walking.data;

import botting.bot.automation.tools.condition.Condition;
import botting.bot.manager.ManagerRepository;
import botting.game.GameObject;
import botting.game.GameTile;

import java.util.Optional;

/**
 * @author Youri Dudock
 */
public class ObjectClickWalkAction implements WalkAction {

    private int objectID;

    private GameTile destination;
    private String interaction;

    public ObjectClickWalkAction(GameTile destination, int objectID, String interaction) {
        this.objectID= objectID;
        this.destination = destination;
        this.interaction = interaction;
    }

    @Override
    public void perform(ManagerRepository managers) {
        Optional<GameObject> target = managers.getWorld().getObjectManager().getNearest(objectID);

        if (target.isPresent()) {

            if (!target.get().isOnScreen()) {
                managers.getCameraManager().turnTo(target.get());
            }

            if (target.get().isOnScreen()) {
                boolean hasInteracted = target.get().interact(interaction);

                if (hasInteracted) {
                    Condition.waitTill(() -> !managers.getPlayer().getLocalPlayer().isWalking() || managers.getPlayer().getLocalPlayer().getLocation().equals(destination), 10000);
                }

            }

        }

    }

    @Override
    public boolean hasArrived(ManagerRepository managers) {
        return managers.getPlayer().getLocalPlayer().getLocation().equals(destination);
    }
}
