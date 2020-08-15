package botting.bot.drawing;

import botting.bot.automation.script.Script;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public abstract class ScriptPainting<T extends Script> {

    public abstract void draw(Graphics g, T script);

}
