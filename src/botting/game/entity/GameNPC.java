package botting.game.entity;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.bot.manager.impl.NPCManager;
import botting.reflection.accessors.npc.NPC;

import java.awt.*;

/**
 * A NPC in game
 *
 * @author Youri Dudock
 */
public class GameNPC extends GameEntity {

    // id of the NPC
    private int ID;

    // name of the NPC
    private String name;

    // the reflection accessor
    private NPC accessor;

    public GameNPC(BotInstance bot, NPC accessor) {
        super(bot);

        this.accessor = accessor;
    }

    public String getName() {
        if (name == null) {
            name = accessor.getDefinition().getName();
        }

        return name;
    }




    @Override
    public NPC getAccessor() {
        return accessor;
    }

    public int getID() {
        if (ID == 0) {
            ID = accessor.getDefinition().getID();
        }

        return ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == GameNPC.class) {

            GameNPC other = (GameNPC) obj;

            if (other.getAccessor() == this.getAccessor()) {
                return true;
            }


        }

        return false;
    }


    @Override
    public void draw(Graphics g) {
        if (!super.isOnScreen()) {
            return;
        }

        Point p = getCenterPointOnScreen();
        g.setColor(Color.RED);
        g.fillRect(p.x - 2, p.y - 2, 4, 4);
        g.setColor(Color.yellow);
        g.drawString(getName() + " [" + getID() + "]["+bot.getManagers().getPlayer().getLocalPlayer().distanceFrom(this)+"]", p.x + 5, p.y - 2);
    }
}
