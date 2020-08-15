package botting.bot.debug.screen;

import botting.bot.BotInstance;
import botting.bot.drawing.AbstractPainter;
import botting.game.entity.GameNPC;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public class NPCPainter extends AbstractPainter {

    @Override
    public void paint(Graphics g, BotInstance bot) {
        final GameNPC[] npcs = bot.getManagers().getWorld().getNPCManager().getNpcs();

        for (GameNPC npc : npcs) {
            npc.draw(g);
        }
    }


}
