package botting.bot.debug.screen;

import botting.bot.BotInstance;
import botting.bot.drawing.AbstractPainter;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public class InterfacePainter extends AbstractPainter {

    @Override
    public void paint(Graphics g, BotInstance bot) {
        int x = 30;
        int y = 0;

        g.setColor(Color.WHITE);
        g.drawString("Interface Open: " + bot.getManagers().getInterfaces().getOpenInterfaceID(), x , y + 20);

    }
}
