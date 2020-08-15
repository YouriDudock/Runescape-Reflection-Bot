package botting;

import botting.bot.debug.Debugger;
import botting.environment.Environment;

import java.net.MalformedURLException;

/**
 * A reflection bot for the 317 Runescape client
 *
 * @author Youri Dudock
 */
public class ReflectionBot {

    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException {
        for (String arg : args) {
            switch (arg) {
                case "debug":
                    Debugger.isDebugging = true;
                    break;
            }
        }

        Environment.getInstance().setup();
    }


}
