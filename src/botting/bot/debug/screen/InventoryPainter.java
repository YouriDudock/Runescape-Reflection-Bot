package botting.bot.debug.screen;

import botting.bot.BotInstance;
import botting.bot.drawing.AbstractPainter;
import botting.game.GameItem;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public class InventoryPainter extends AbstractPainter {
    @Override
    public void paint(Graphics g, BotInstance bot) {
        GameItem[] items = bot.getManagers().getPlayer().getInventory().items().getItems();

        for (GameItem item : items) {
            item.draw(g);
        }

    }
}
