package botting.reflection.accessors.io;

import botting.bot.BotInstance;
import botting.reflection.accessors.ClassWrapper;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * @author Youri Dudock
 */
public class MouseWrapper extends ClassWrapper implements Mouse {


    @Override
    public Object getAccessor() {
        return getBot().getAccessors().getGame().getClientInstance();
    }


    public MouseWrapper(BotInstance bot) {
        super(bot);
    }

    @Override
    public int getX() {
        return new RefClass(getAccessor()).getSuperclass().getField(getBot().getHooks().value(ClientHook.FIELD_MOUSE_X)).asInt();
    }

    @Override
    public int getY() {
        return new RefClass(getAccessor()).getSuperclass().getField(getBot().getHooks().value(ClientHook.FIELD_MOUSE_Y)).asInt();
    }

    @Override
    public void setX(int x) {
        new RefClass(getAccessor()).getSuperclass().getField(getBot().getHooks().value(ClientHook.FIELD_MOUSE_X)).setInt(x);
    }

    @Override
    public void setY(int y) {
        new RefClass(getAccessor()).getSuperclass().getField(getBot().getHooks().value(ClientHook.FIELD_MOUSE_Y)).setInt(y);
    }

    @Override
    public void scrollMouse(MouseEvent event) {
        new RefClass(getBot().getAccessors().getGame().getClientInstance()).getSuperclass().getMethod(getBot().getHooks().value(ClientHook.METHOD_MOUSE_WHEEL_MOVED), MouseWheelEvent.class).invoke(event);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        new RefClass(getBot().getAccessors().getGame().getClientInstance()).getSuperclass().getMethod(getBot().getHooks().value(ClientHook.METHOD_MOUSE_PRESSED), MouseEvent.class).invoke(event);
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        new RefClass(getBot().getAccessors().getGame().getClientInstance()).getSuperclass().getMethod(getBot().getHooks().value(ClientHook.METHOD_MOUSE_RELEASED), MouseEvent.class).invoke(event);
    }

}
