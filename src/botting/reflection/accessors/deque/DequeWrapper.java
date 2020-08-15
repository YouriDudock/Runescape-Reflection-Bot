package botting.reflection.accessors.deque;

import botting.bot.BotInstance;
import botting.reflection.accessors.ClassWrapper;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

/**
 * @author Youri Dudock
 */
public class DequeWrapper extends ClassWrapper implements Deque {
    public DequeWrapper(BotInstance bot, Object accessor) {
        super(bot, accessor);
    }

    @Override
    public Node getHead() {
        if (getAccessor() == null) {
            return null;
        }

        Object node = new RefClass(getAccessor()).getMethod(getBot().getHooks().value(ClientHook.METHOD_DEQUE_GET_FIRST)).invoke();

        return new NodeWrapper(getBot(), node);
    }

    @Override
    public Node getCurrent() {
        if (getAccessor() == null) {
            return null;
        }

        Object node = new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_DEQUE_CURRENT_NODE)).asObject();

        return new NodeWrapper(getBot(), node);
    }

    @Override
    public Node reverseGetFirst() {
        if (getAccessor() == null) {
            return null;
        }

        Object node = new RefClass(getAccessor()).getMethod(getBot().getHooks().value(ClientHook.METHOD_REVERSE_GET_FIRST)).invoke();

        return new NodeWrapper(getBot(), node);
    }


    @Override
    public Node getNext() {
        if (getAccessor() == null) {
            return null;
        }

        Object node = new RefClass(getAccessor()).getMethod(getBot().getHooks().value(ClientHook.METHOD_DEQUE_GET_NEXT)).invoke();

        if (node == null) {
            return null;
        }

        return new NodeWrapper(getBot(), node);
    }
}
