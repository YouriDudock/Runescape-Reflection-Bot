package botting.bot.drawing;

import botting.bot.BotInstance;
import botting.bot.debug.screen.*;

import java.applet.Applet;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Youri Dudock
 */
public class PaintArtist {

    private BotInstance bot;

    public boolean hasStarted = false;

    public PaintArtist(BotInstance bot) {
        this.bot = bot;
    }

    public AbstractPainter[] jobs = {
            new NPCPainter(),
            new ObjectPainer(),
            new MousePainer(),
            new InventoryPainter(),
            new BankPainter(),
            new InterfacePainter()
    };

    public void toggle(Class painter) {
        for (AbstractPainter p : jobs) {
            if (p.getClass().equals(painter)) {
                p.toggle();

                if (!hasStarted) {
                    start(bot);
                }
            }
        }
    }


    public void start(BotInstance bot) {
        hasStarted = true;

        // get new thread executor
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            Applet applet = bot.getAccessors().getGame().getApplet();
            Graphics g = applet.getGraphics();

            while (true) {
//                try {
                    for (AbstractPainter p : jobs) {
                        if (p.isEnabled()) {
                            p.paint(g, bot);
                        }
                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                bot.getManagers().getAntiBan().sleep(10, false);
            }


        });
    }

}
