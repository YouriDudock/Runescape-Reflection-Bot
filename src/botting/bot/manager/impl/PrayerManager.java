package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.manager.Manager;
import botting.game.data.GamePrayer;
import botting.game.data.GameSkill;
import botting.game.data.GameTab;
import botting.rs.client.clients.ikov.Ikov;

import static java.lang.Thread.sleep;

/**
 * @author Youri Dudock
 */
public class PrayerManager extends Manager {
    public PrayerManager(BotInstance bot) {
        super(bot);
    }

    public int getPrayerPoints() {
        return bot.getAccessors().getClient().getStats()[Ikov.Game.Skills.PRAYER.getID()];
    }


    public void enablePrayer(GamePrayer prayer) {
        try {

            getManagers().getHud().openTab(Ikov.Game.Tabs.PRAYER);

            sleep(500);

            getManagers().getMouse().click(prayer.getPoint());

            debug("Clicked prayer:" + prayer.toString(), DebugPriority.HIGH);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    // @TODO actual prayer check? lol
    public boolean hasPrayerOn() {
        try {

            int prayerPoints = getPrayerPoints();

            sleep(2000);

            return getPrayerPoints() != prayerPoints;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
