package botting.reflection.accessors.game;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.environment.ui.BotUI;
import botting.reflection.accessors.ClassWrapper;
import botting.reflection.accessors.deque.Deque;
import botting.reflection.accessors.deque.DequeWrapper;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

import javax.swing.*;
import java.applet.Applet;

/**
 * @author Youri Dudock
 */
public class GameWrapper extends ClassWrapper implements Game {

    public GameWrapper(BotInstance bot) {
        super(bot);
    }


    @Override
    public Class<?> getClientClass() {
        return getBot().getClassLoader().loadClass(getBot().getHooks().value(ClientHook.CLASS_CLIENT));
    }

    @Override
    public Object getClientInstance() {
        return new RefClass(getClientClass()).getField(getBot().getHooks().value(ClientHook.FIELD_CLIENT_INSTANCE)).asObject();
    }

    @Override
    public Class<?> getMainClass() {
        return getBot().getClassLoader().loadClass(getBot().getHooks().value(ClientHook.CLASS_MAIN));
    }

    @Override
    public Object getMainInstance() {
        return new RefClass(getMainClass()).getField(getBot().getHooks().value(ClientHook.FIELD_MAIN_INSTANCE)).asObject();
    }

    @Override
    public Class<?> getGameRendererClass() {
        return getClientClass().getSuperclass();
    }

    @Override
    public Class<?> getRSInterfacesClass() {
        return getBot().getClassLoader().loadClass(getBot().getHooks().value(ClientHook.CLASS_RS_INTERFACE));
    }

    @Override
    public Class<?> getRightMenuClass() {
        return getBot().getClassLoader().loadClass(getBot().getHooks().value(ClientHook.CLASS_RIGHT_MENU));
    }



    @Override
    public Deque getRightMenuDequeInstance() {
        Object deque = new RefClass(getRightMenuClass()).getField(getBot().getHooks().value(ClientHook.FIELD_RIGHT_CLICK_MENU_DEQUE)).asObject();
        return new DequeWrapper(getBot(), deque);
    }

    @Override
    public Class<?> getMenuNode() {
        return getBot().getClassLoader().loadClass(getBot().getHooks().value(ClientHook.CLASS_MENU_NODE));
    }

    @Override
    public Class<?> getClientDetailsClass() {
        return getBot().getClassLoader().loadClass(getBot().getHooks().value(ClientHook.CLASS_CLIENT_DETAILS));
    }

    @Override
    public Applet getApplet() {
        return (Applet) getClientInstance();
    }

    @Override
    public void main(String[] args) {
        new RefClass(getMainClass()).getMethod(getBot().getHooks().value(ClientHook.METHOD_MAIN), String[].class).invoke((Object) args);
    }


}
