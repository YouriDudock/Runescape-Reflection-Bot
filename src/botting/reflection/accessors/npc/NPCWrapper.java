package botting.reflection.accessors.npc;

import botting.bot.BotInstance;
import botting.reflection.accessors.ClassWrapper;
import botting.reflection.accessors.entity.Entity;
import botting.reflection.accessors.entity.EntityWrapper;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

/**
 * @author Youri Dudock
 */
public class NPCWrapper extends ClassWrapper implements NPC {

    private Entity entity;

    public NPCWrapper(BotInstance bot, Object accessor) {
        super(bot, accessor);

        entity = new EntityWrapper(bot, accessor);
    }

    @Override
    public NPCDefinition getDefinition() {
        Object definition = new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_MOB_DEF)).asObject();

        NPCDefinitionWrapper definitionWrapper = new NPCDefinitionWrapper(getBot(), definition);

        return definitionWrapper;
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
