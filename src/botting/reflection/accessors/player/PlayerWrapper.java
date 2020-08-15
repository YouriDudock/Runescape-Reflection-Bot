package botting.reflection.accessors.player;

import botting.bot.BotInstance;
import botting.reflection.accessors.ClassWrapper;
import botting.reflection.accessors.entity.Entity;
import botting.reflection.accessors.entity.EntityWrapper;

/**
 * @author Youri Dudock
 */
public class PlayerWrapper extends ClassWrapper implements Player {

    private Entity entity;


    public PlayerWrapper(BotInstance bot, Object accessor) {
        super(bot, accessor);

        entity = new EntityWrapper(bot, accessor);
    }

    @Override
    public int getX() {
        return entity.getX();
    }

    @Override
    public int getY() {
        return entity.getY();
    }

    @Override
    public int getHeight() {
        return entity.getHeight();
    }

    @Override
    public int getHealth() {
        return entity.getHealth();
    }

    @Override
    public int getInteractingID() {
        return entity.getInteractingID();
    }

    @Override
    public void setInteractingID(int ID) {
        entity.setInteractingID(ID);
    }

    @Override
    public int getAnimation() {
        return entity.getAnimation();
    }

    @Override
    public int getLoopCycleStatus() {
        return entity.getLoopCycleStatus();
    }
}
