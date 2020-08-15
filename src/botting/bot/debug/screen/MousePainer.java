package botting.bot.debug.screen;

import botting.bot.BotInstance;
import botting.bot.drawing.AbstractPainter;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public class MousePainer extends AbstractPainter {

    @Override
    public void paint(Graphics g, BotInstance bot) {
        Point p = bot.getManagers().getMouse().getMouseLocation();

        g.setColor(Color.RED);
        g.fillOval(p.x - 2, p.y - 2, 4, 4);
    }


}
