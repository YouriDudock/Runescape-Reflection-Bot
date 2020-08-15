package botting.bot.automation.script.ikov.frost;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.tools.condition.Condition;
import botting.bot.debug.DebugPriority;
import botting.game.GameObject;
import botting.game.data.GamePrayer;
import botting.game.data.GameTab;
import botting.rs.client.clients.ikov.Ikov;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Optional;

/**
 * @author Youri Dudock
 */
public class VisitFrostDragonsTask extends BotTask<FrostDragonScript> {

    public VisitFrostDragonsTask(BotInstance bot, FrostDragonScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Visiting cave..", DebugPriority.HIGH);

        if (script.getLocalPlayer().isInArea(script.HOME)) {
            debug("Teleporting to frosts...", DebugPriority.HIGH);
            // frost tele!

            script.getHUD().openTab(Ikov.Game.Tabs.MAGIC_BOOK);
            script.getAntiBan().sleep(500);

            // click teleport spell from normal spellbook
            script.getMouse().click(new Point(569, 274));
            script.getAntiBan().sleep(2000);

            script.getKeyboard().pressKey(KeyEvent.VK_BACK_SPACE);
            script.getAntiBan().sleep(100);

            script.getKeyboard().pressKey(KeyEvent.VK_BACK_SPACE);
            script.getAntiBan().sleep(100);

            script.getKeyboard().pressKey('c');
            script.getAntiBan().sleep(200);

            script.getKeyboard().pressKey('g');
            script.getAntiBan().sleep(200);

            script.getKeyboard().pressKey('i');

            Condition.waitTill(() -> script.getLocalPlayer().getLocation().equals(script.FROST_TELEPORT_TILE) && script.getLocalPlayer().getAnimation() <= 0, 6000);
        }

        if (script.getLocalPlayer().getLocation().equals(script.FROST_TELEPORT_TILE)) {
            debug("Looking for cave...", DebugPriority.HIGH);

            Optional<GameObject> entrance = script.getObjects().getNearest(script.CAVE_ENTRANCE_ID, 5);

            if (entrance.isPresent()) {

                if (!entrance.get().isOnScreen()) {
                    script.getCamera().turnTo(entrance.get());
                }

                if (entrance.get().isOnScreen()) {
                    boolean hasInteracted = entrance.get().interact("Enter cave");

                    if (hasInteracted) {
                        Condition.waitTill(() -> !script.getLocalPlayer().isWalking(), 5000);

                        // prayer check
                        if (!script.getPlayer().getPrayer().hasPrayerOn()) {
                            script.getPlayer().getPrayer().enablePrayer(Ikov.Game.Prayer.Curses.TURMOIL);
                            script.getAntiBan().sleep(1000);
                            script.getPlayer().getPrayer().enablePrayer(Ikov.Game.Prayer.Curses.SOUL_SPLIT);
                        }
                    }
                }

            }

        }


    }

    @Override
    public boolean shouldActivate() {
        if (script.getLocalPlayer().isInArea(script.HOME) || script.getLocalPlayer().getLocation().equals(script.FROST_TELEPORT_TILE)) {

            if (script.getBank().isOpen()) {
                return false;
            }

            if (script.getPlayer().getInventory().items().contains(script.FOOD) && script.getPlayer().getInventory().items().contains(script.WITHDRAW_PRAYER_POT_ID)) {
                return true;
            }
        }



        return false;
    }
}
