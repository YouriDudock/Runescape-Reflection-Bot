package botting.reflection.accessors.items;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.reflection.accessors.ClassWrapper;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;
import botting.reflection.modifiers.RefField;

/**
 * @author Youri Dudock
 */
public class GroundItemWrapper extends ClassWrapper implements GroundItem {
    public GroundItemWrapper(BotInstance bot, Object accessor) {
        super(bot, accessor);
    }

    @Override
    public int getItemID() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_GROUND_ITEM_ID)).asInt();
    }

    @Override
    public int getAmount() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_GROUND_ITEM_AMOUNT)).asInt();
    }
}
