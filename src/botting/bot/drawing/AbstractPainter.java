package botting.bot.drawing;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public abstract class AbstractPainter {

    public abstract void paint(Graphics g, BotInstance bot);

    private boolean isEnabled;

    public void toggle() {
        this.isEnabled = !this.isEnabled;

        Debugger.write(getClass(), "Painting status: " + this.isEnabled, DebugPriority.MEDIUM);
    }

    public boolean isEnabled() {
        return isEnabled;
    }


}
