package botting.game;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.game.types.Drawable;
import botting.game.types.Locatable;

import java.awt.*;

/**
 * A game tile represent a tile in the runescape map
 *
 * @author Youri Dudock
 */
public class GameTile implements Locatable, Drawable {

    private int x, y;

    private BotInstance bot;

    public GameTile(BotInstance bot, int x, int y) {
        this.x = x;
        this.y = y;
        this.bot = bot;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Calculates local region x
     *
     * @return local region x
     */
    public final int getLocalRegionX() {
        return x - bot.getAccessors().getClient().getBaseX();
    }

    /**
     * Calculates local region y
     *
     * @return local region y
     */
    public final int getLocalRegionY() {
        return y - bot.getAccessors().getClient().getBaseY();
    }

    public final Point toScreen() {
        return bot.getManagers().getCalculations().tileToScreen(this);
    }

    public final Point toMinimap() {
        return bot.getManagers().getCalculations().tileToMinimap(this, true);
    }

    public final boolean isOnScreen() {
        return bot.getManagers().getCalculations().isOnScreen(toScreen());
    }

    /**
     * Determines if this tile is on minimap
     *
     * @return whether this tile is on minimap
     */
    public final boolean isOnMinimap() {
        return bot.getManagers().getPlayer().getLocalPlayer().distanceFrom(this) <= 16;
    }


    public final void clickOnMinimap() {
        Debugger.write(getClass(), "Clicking tile on minimap: " + this.toString(), DebugPriority.HIGH);
        bot.getManagers().getMouse().click(toMinimap());

    }





    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == GameTile.class) {

            GameTile tile = (GameTile)obj;

            if (tile.getX() == x && tile.getY() == y) {
                return true;
            }

        }

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "bot.game.RSTile{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public GameTile getLocation() {
        return new GameTile(bot, x, y);
    }

    @Override
    public void draw(Graphics g) {

    }
}
