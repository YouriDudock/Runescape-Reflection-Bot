package botting.game.entity;

import botting.bot.BotInstance;
import botting.bot.automation.tools.condition.Condition;
import botting.game.GameTile;
import botting.game.data.GameArea;
import botting.game.types.Drawable;
import botting.game.types.Interactable;
import botting.game.types.Locatable;
import botting.reflection.accessors.entity.Entity;

import java.awt.*;

/**
 * An entity in game
 *
 * @author Youri Dudock
 */
public abstract class GameEntity implements Interactable, Locatable, Drawable {

    // instance of the bot
    protected BotInstance bot;

    public GameEntity(BotInstance bot) {
        this.bot = bot;
    }

    public int getInteractingID() {
        return getAccessor().getInteractingID();
    }

    public void setInteractingID(int ID) {
        getAccessor().setInteractingID(ID);
    }

    public int getHealth() {
        return getAccessor().getHealth();
    }

    public int getHeight() {
        return getAccessor().getHeight();
    }


    public boolean isInCombat() {
        return getAccessor().getLoopCycleStatus() > bot.getAccessors().getClient().getLoopCycle();
    }


    public int getLocalRegionX() {
        return getAccessor().getX() >> 7;
    }


    public int getLocalRegionY() {
        return getAccessor().getY() >> 7;
    }


    /**
     * Checks if an entity is in the given area
     *
     * @param area area to check in
     *
     * @return if the entity is in this area
     */
    public boolean isInArea(GameArea area) {
        GameTile location = getLocation();

        if (location.getX() >= area.getSouthWestTile().getX() &&
                location.getX() <= area.getNorthEastTile().getX() &&
                location.getY() >= area.getSouthWestTile().getY() &&
                location.getY() <= area.getNorthEastTile().getY()) {

            return true;
        }

        return false;
    }



    public int getX() {
        return getAccessor().getX();
    }

    public int getY() {
        return getAccessor().getY();
    }

    /**
     * Checks if an entity is alive
     *
     * @return alive or not
     */
    public boolean isAlive() {
        return getHealth() > 0;
    }

    public boolean isOnScreen() {
        return bot.getManagers().getCalculations().isOnScreen(getCenterPointOnScreen());
    }


    /**
     * Calculates center point on screen of this character
     * @return point on screen
     */
    public final Point getCenterPointOnScreen() {
        return bot.getManagers().getCalculations().tileToScreen(getLocalRegionX(), getLocalRegionY(), 0.5D, 0.5D, 100);
    }

    /**
     *
     * @return if the player is walking
     */
    public boolean isWalking() {
        GameTile oldLocation = getLocation();

        bot.getManagers().getAntiBan().sleep(1000);

        return !getLocation().equals(oldLocation);
    }

    public int distanceFrom(GameTile tile) {
        GameTile myLocation = getLocation();

        int xDistance = tile.getX() - myLocation.getX();
        int yDistance = tile.getY() - myLocation.getY();

        return (int) Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }

    public int distanceFrom(Locatable locatable) {
        return this.distanceFrom(locatable.getLocation());
    }

    /**
     * Returns the character this character is interacting with
     * @return interacting character
     */
    public final GameEntity getInteractingEntity() {
        int index = this.getAccessor().getInteractingID();
        if (index != -1 && index < 32768) {

            return new GameNPC(bot, bot.getAccessors().getClient().getNPCS()[index]);
        } else if (index >= 32768) {
            index -= 32768;
            // TODO GET PLAYER LOCAL INDEX
            if (index == 2449) {
                return null;
            }

            return new GamePlayer(bot, bot.getAccessors().getClient().getPlayers()[index]);
        }
        return null;
    }

    public int getAnimation() {
        return getAccessor().getAnimation();
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
    public boolean interact(String option) {
        return bot.getManagers().getInterfaces().getMenu().interact(option, getCenterPointOnScreen());
    }

    @Override
    public GameTile getLocation() {
        int tileX = bot.getAccessors().getClient().getBaseX() + getLocalRegionX();
        int tileY = bot.getAccessors().getClient().getBaseY() + getLocalRegionY();

        return new GameTile(bot, tileX, tileY);
    }

    public abstract Entity getAccessor();



}
