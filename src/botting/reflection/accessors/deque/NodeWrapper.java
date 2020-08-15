package botting.reflection.accessors.deque;

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
public class NodeWrapper extends ClassWrapper implements Node {

    public NodeWrapper(BotInstance bot, Object accessor) {
        super(bot, accessor);
    }

    @Override
    public Node getNext() {
        if (getAccessor() == null) {
            return null;
        }

        boolean found = false;
        for (RefField f : new RefClass(getAccessor()).getFields()) {


            if (f.getName().equals(getBot().getHooks().value(ClientHook.FIELD_NODE_NEXT))) {
                found = true;
            }
        }

        if (!found) {
            return null;
        }


        Object node = new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_NODE_NEXT)).asObject();

        return new NodeWrapper(getBot(), node);
    }

    @Override
    public String getBaseMenuItemName() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_MENU_NODE_NAME)).asString();
    }

    @Override
    public String getExtraBaseMenuItemName() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_EXTRA_MENU_NODE_NAME)).asString();
    }





}
