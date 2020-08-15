package botting.reflection.accessors.entity;

import botting.bot.BotInstance;
import botting.reflection.accessors.SuperClassWrapper;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

/**
 * @author Youri Dudock
 */
public class EntityWrapper extends SuperClassWrapper implements Entity {


    public EntityWrapper(BotInstance bot, Object childAccessor) {
        super(bot, childAccessor);
    }

    @Override
    public int getX() {
        return new RefClass(getAccessor(), getChildAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_ENTITY_X)).asInt();
    }

    @Override
    public int getY() {
        return new RefClass(getAccessor(), getChildAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_ENTITY_Y)).asInt();
    }

    @Override
    public int getHeight() {
        return new RefClass(getAccessor(), getChildAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_ENTITY_HEIGHT)).asInt();
    }

    @Override
    public int getHealth() {
        return new RefClass(getAccessor(), getChildAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_ENTITY_CURRENT_HEALTH)).asInt();
    }

    @Override
    public int getInteractingID() {
        return new RefClass(getAccessor(), getChildAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_INTERACTING_ENTITY)).asInt();
    }

    @Override
    public void setInteractingID(int ID) {
        new RefClass(getAccessor(), getChildAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_INTERACTING_ENTITY)).setInt(ID);
    }

    @Override
    public int getAnimation() {
        return new RefClass(getAccessor(), getChildAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_ENTITY_ANIMATION)).asInt();
    }

    @Override
    public int getLoopCycleStatus() {
        return new RefClass(getAccessor(), getChildAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_LOOP_CYCLE_STATUS)).asInt();
    }
}
