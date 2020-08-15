package botting.bot.automation.script.ikov.frost;

import botting.bot.drawing.ScriptPainting;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public class FrostPainting extends ScriptPainting<FrostDragonScript> {

    @Override
    public void draw(Graphics g, FrostDragonScript script) {
        int x = 30;
        int y = (int) (script.getCalculuations().GAME3D_SCREEN.getHeight() - 600);

        g.setColor(Color.GREEN);
        g.drawString("Frost Dragon Bot", x, y);
        g.drawString("Bones looted: " + script.bonesLooted, x, y + 20);
        g.drawString("Time Running: " + script.getTimeRunning(), x, y + 40);
    }


}
