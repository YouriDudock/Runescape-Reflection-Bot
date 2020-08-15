package botting.bot.debug.screen;

import botting.bot.BotInstance;
import botting.bot.automation.tools.filter.Filter;
import botting.bot.drawing.AbstractPainter;
import botting.game.GameObject;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public class ObjectPainer extends AbstractPainter {

    @Override
    public void paint(Graphics g, BotInstance bot) {
        final GameObject[] objects = bot.getManagers().getWorld().getObjectManager().getObjectsNearby(subject -> true, 5);

        for (GameObject object : objects) {
            object.draw(g);
        }
    }


}
