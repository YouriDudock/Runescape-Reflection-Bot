package botting.game;

import botting.bot.BotInstance;
import botting.bot.automation.tools.condition.Condition;
import botting.game.types.Drawable;
import botting.game.types.Interactable;
import botting.game.types.Locatable;
import botting.reflection.accessors.objects.SceneObjectTile;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public class GameObject implements Drawable, Locatable, Interactable {

    private BotInstance bot;

    private SceneObjectTile accessor;

    //private String name;

    public GameObject(BotInstance bot, SceneObjectTile accessor) {
        this.bot = bot;
        this.accessor = accessor;
    }

    public int getLocalRegionX() {
        return accessor.getX();
    }

    public int getLocalRegionY() {
        return accessor.getY();
    }

    /**
     * Calculates center point on screen
     * @return point
     */
    public final Point getCenterPointOnScreen() {
        double offsetX =0.5D;

        // somehow this appears wrong? Cave entrance @TODO fix
        if (getID() == 2) {
            offsetX = 2D;
        }

        return bot.getManagers().getCalculations().tileToScreen(getLocalRegionX(), getLocalRegionY(), offsetX, 0.5D, 0);
    }

    /**
     *  Determines if this object is on screen
     */
    public final boolean isOnScreen() {
        return bot.getManagers().getCalculations().isOnScreen(this.getCenterPointOnScreen());
    }

    public int getID() {
        return (int) (getHash() >> 32);
    }


    public long getHash() {
        return accessor.getHash();
    }

//    public String getName() {
//        if (name == null) {
//            name = accessor.getName();
//        }
//
//        return name;
//    }

    @Override
    public void draw(Graphics g) {
        if (!isOnScreen()) {
            return;
        }

        Point p = getCenterPointOnScreen();
        g.setColor(Color.RED);
        g.fillRect(p.x - 2, p.y - 2, 4, 4);
        g.setColor(Color.yellow);
        g.drawString("[" + getID() + "]", p.x + 5, p.y - 2);
    }


    @Override
    public GameTile getLocation() {
        return new GameTile(bot, bot.getAccessors().getClient().getBaseX() + getLocalRegionX(), bot.getAccessors().getClient().getBaseY() + getLocalRegionY());
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
}
