package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.manager.Manager;
import botting.game.data.GameTab;
import botting.rs.client.clients.ikov.Ikov;

import java.awt.*;
import java.awt.event.KeyEvent;

import static java.lang.Thread.sleep;

/**
 * @author Youri Dudock
 */
public class HUDManager extends Manager {

    public HUDManager(BotInstance bot) {
        super(bot);
    }

    public void openTab(GameTab tab) {
        getManagers().getMouse().click(tab.getPoint());
    }

    public void setupHub() {
        getManagers().getKeyboard().pressKey(KeyEvent.VK_ENTER);
        getManagers().getAntiBan().sleep(500);

        if (getManagers().getInterfaces().hasInterfaceOpen()) {
            getManagers().getInterfaces().closeInterface();
            getManagers().getAntiBan().sleep(1000);
        }

        getManagers().getMouse().scrollMouse(100);

        for (int i = 0; i < 15; i++) {
            getManagers().getKeyboard().pressKey(KeyEvent.VK_UP);
        }

        getManagers().getAntiBan().sleep(1000);

        getManagers().getHud().openTab(Ikov.Game.Tabs.INVENTORY);

        getManagers().getAntiBan().sleep(1000);

        clickCompass();

        getManagers().getAntiBan().sleep(500);
    }

    public void clickCompass() {
        if (getManagers().getInterfaces().hasInterfaceOpen()) {
            getManagers().getInterfaces().closeInterface();
            getManagers().getAntiBan().sleep(2000);
        }

        getManagers().getMouse().click(new Point(540, 22));
    }
}
