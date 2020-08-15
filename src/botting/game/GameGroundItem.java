package botting.game;

import botting.bot.BotInstance;
import botting.bot.automation.tools.condition.Condition;
import botting.game.data.InventoryType;
import botting.game.types.Interactable;
import botting.game.types.Locatable;
import botting.reflection.accessors.items.GroundItem;

import java.awt.*;

/**
 * An item on the ground
 *
 * @author Youri Dudock
 */
public class GameGroundItem implements Locatable, Interactable {

    // amount in this ground item stack
    private int amount;

    private GroundItem accessor;

    private BotInstance bot;

    private GameItem item;

    private int localRegionX, localRegionY;

    public GameGroundItem(BotInstance bot, GroundItem accessor, int localRegionX, int localRegionY) {
        this.bot = bot;
        this.localRegionX = localRegionX;
        this.localRegionY = localRegionY;
        this.accessor = accessor;
    }

    public GameItem getItem()
    {
        if (item == null) {
            item = new GameItem(bot, accessor.getItemID());
        }

        return item;
    }


    /**
     * Calculates center point on screen of this grounditem
     *
     * @return point on screen
     */
    public final Point getCenterPointOnScreen() {
        return bot.getManagers().getCalculations().tileToScreen(localRegionX, localRegionY,
                0.5D, 0.5D, 0);
    }

    @Override
    public boolean interact(String option) {
        return bot.getManagers().getInterfaces().getMenu().interact(option, getCenterPointOnScreen());
    }

    @Override
    public boolean findAndInteract(String option) {
        // is on screen?
        if (!isOnScreen()) {

            // if pretty close by
            if (bot.getManagers().getPlayer().getLocalPlayer().distanceFrom(this) <= 10) {
                // then turn camera
                bot.getManagers().getCameraManager().turnTo(this);
            }

            // check if on screen now
            if (!isOnScreen()) {
                // is far away maybe?
                if (bot.getManagers().getPlayer().getLocalPlayer().distanceFrom(this) > 5) {

                    // is on minimap?
                    if (getLocation().isOnMinimap()) {
                        // click minimap
                        getLocation().clickOnMinimap();

                        // wait till walking has finished
                        Condition.waitTill(() -> !bot.getManagers().getPlayer().getLocalPlayer().isWalking(), 10000);

                        // check if on screen after minimap click
                        if (!isOnScreen()) {
                            // turn one last time
                            bot.getManagers().getCameraManager().turnTo(this);
                        }
                    }
                }
            }


        }

        return bot.getManagers().getInterfaces().getMenu().interact(option, getCenterPointOnScreen());
    }

    @Override
    public String toString() {
        return "GameGroundItem{" +
                "amount=" + amount +
                ", accessor=" + accessor +
                ", bot=" + bot +
                ", item=" + item +
                ", localRegionX=" + localRegionX +
                ", localRegionY=" + localRegionY +
                '}';
    }

    public int getLocalRegionX() {
        return localRegionX;
    }

    public int getLocalRegionY() {
        return localRegionY;
    }

    public boolean isOnScreen() {
        return bot.getManagers().getCalculations().isOnScreen(getCenterPointOnScreen());
    }

    @Override
    public GameTile getLocation() {
        return new GameTile(bot, bot.getAccessors().getClient().getBaseX() + getLocalRegionX(), bot.getAccessors().getClient().getBaseY() + getLocalRegionY());
    }
}
