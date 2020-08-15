package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.bot.manager.Manager;

import java.util.Random;

/**
 * @author Youri Dudock
 */
public class AntiBanManager extends Manager {

    // random instance
    private final Random random = new Random();

    public AntiBanManager(BotInstance bot) {
        super(bot);
    }

    public void sleep(int duration) {
        sleep(duration, true);
    }

    public void sleep(int duration, boolean debug) {
        try {

            if (debug)
                debug("Sleeping for: " + duration, DebugPriority.LOW);

            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sleep(int durationMin, int durationMax) {
        try {
            int random = getRandomNumber(durationMin, durationMax);

            debug( "Sleeping for: " + random, DebugPriority.LOW);

            Thread.sleep(getRandomNumber(durationMin, durationMax));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getRandomNumber(int min, int max) {
        return random.nextInt(max - min) + min;
    }

}
