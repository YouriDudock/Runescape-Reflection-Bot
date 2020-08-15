package botting.reflection.accessors.io;

import botting.bot.BotInstance;
import botting.reflection.accessors.ClassWrapper;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

import java.awt.event.KeyEvent;

/**
 * @author Youri Dudock
 */
public class KeyboardWrapper extends ClassWrapper implements Keyboard {


    public KeyboardWrapper(BotInstance bot) {
        super(bot);
    }

    @Override
    public void pressKey(KeyEvent event) {
        new RefClass(getBot().getAccessors().getGame().getClientInstance()).getSuperclass().getMethod(getBot().getHooks().value(ClientHook.METHOD_KEY_PRESSED), KeyEvent.class).invoke(event);
    }

    @Override
    public void releaseKey(KeyEvent event) {
        new RefClass(getBot().getAccessors().getGame().getClientInstance()).getSuperclass().getMethod(getBot().getHooks().value(ClientHook.METHOD_KEY_RELEASED), KeyEvent.class).invoke(event);
    }
}
