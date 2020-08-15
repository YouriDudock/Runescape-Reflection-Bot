package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.bot.manager.Manager;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

import java.awt.*;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.getExtendedKeyCodeForChar;
import static java.lang.Thread.sleep;

/**
 * @author Youri Dudock
 */
public class KeyboardManager extends Manager {

    public KeyboardManager(BotInstance bot) {
        super(bot);
    }

    /**
     * Presses a key from the keyboard
     *
     * @param key as keycode
     */
    public void pressKey(int key) {
            // create a fake key event
            KeyEvent event = new KeyEvent((Component) bot.getAccessors().getGame().getMainInstance(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, key);

            // invoke key press method
            bot.getAccessors().getKeyboard().pressKey(event);

            Debugger.write(getClass(), "Pressed key: " + key, DebugPriority.HIGH);
    }


    /**
     * Presses a key from the keyboard
     *
     * @param key as keycode
     */
    public void releaseKey(int key) {
        // create a fake key event
        KeyEvent event = new KeyEvent((Component) bot.getAccessors().getGame().getMainInstance(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, key);

        // invoke key press method
        bot.getAccessors().getKeyboard().releaseKey(event);

        Debugger.write(getClass(), "Release key: " + key, DebugPriority.HIGH);
    }

    /**
     * Presses a key from the keyboard
     *
     * @param key the key char
     */
    public void pressKey(char key) {
        pressKey(getExtendedKeyCodeForChar(key));
    }

    /**
     * Writes a line of text
     *
     * @param text text to debug
     */
    public void writeText(String text) {
        // convert text to char array
        char[] textArray = text.toCharArray();

        // debug text with delay between
        for (char key : textArray) {
            pressKey(key);
            getManagers().getAntiBan().sleep(70, 100);
        }

        Debugger.write(getClass(), "Wrote text: " + text, DebugPriority.HIGH);
    }

}
