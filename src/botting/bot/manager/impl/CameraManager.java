package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.manager.Manager;
import botting.game.GameTile;
import botting.game.types.Locatable;

import java.awt.event.KeyEvent;

/**
 * @author Youri Dudock
 */
public class CameraManager extends Manager {

    public CameraManager(BotInstance bot) {
        super(bot);
    }

    /**
     * Rotates the camera to the given locatable
     *
     * @param locatable
     * @param pitchUp
     */
    public final void turnTo(final Locatable locatable) {
        debug("Turning to: " + locatable.getLocation(), DebugPriority.HIGH);
        int angle = getTileAngle(locatable.getLocation());
        setRotation(angle);
        setPitch(false);
    }

    /**
     * Moves camera fully up or down
     *
     * @param up
     * @return <b>true</b> if camera was moved successfully
     */
    public final boolean setPitch(boolean up) {
        try {
            int action = up ? KeyEvent.VK_UP : KeyEvent.VK_DOWN;
            bot.getManagers().getKeyboard().pressKey(action);
            bot.getManagers().getAntiBan().sleep(1000, false);
            bot.getManagers().getKeyboard().releaseKey(action);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Calculates angle to given tile
     *
     * @param tile
     * @return angle
     */
    private final int getTileAngle(GameTile t) {
        int a = (bot.getManagers().getCalculations().angleToTile(t) - 90) % 360;
        return a < 0 ? a + 360 : a;
    }


    /**
     * Moves the camera to a given amount of degrees
     *
     * @param degrees
     */
    public final void setRotation(int degrees) {
        int left = KeyEvent.VK_LEFT;
        int right = KeyEvent.VK_RIGHT;
        int whichDir = left;
        int start = getAngle();
        if (start < 180)
            start += 360;
        if (degrees < 180)
            degrees += 360;
        if (degrees > start) {
            if (degrees - 180 < start)
                whichDir = right;
        } else if (start > degrees && start - 180 >= degrees)
            whichDir = right;
        degrees %= 360;
        bot.getManagers().getKeyboard().pressKey(whichDir);
        int timeWaited = 0;
        while (getAngle() > degrees + 5 || getAngle() < degrees - 5) {
            bot.getManagers().getAntiBan().sleep(10, false);
            if ((timeWaited += 10) > 500) {
                int time = timeWaited - 500;
                if (time == 0)
                    bot.getManagers().getKeyboard().pressKey(whichDir);

                else if (time % 40 == 0)
                    bot.getManagers().getKeyboard().pressKey(whichDir);
            }
        }
        bot.getManagers().getKeyboard().releaseKey(whichDir);
    }


    /**
     * Calculates camera angle
     *
     * @return angle of camera
     */
    public final int getAngle() {
        double mapAngle = bot.getAccessors().getClient().getCameraPitch();
        mapAngle /= 2040D;
        mapAngle *= 360D;
        return (int) mapAngle;
    }

}

