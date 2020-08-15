package botting.bot.automation.script.ikov.frost;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.debug.DebugPriority;
import botting.game.data.BankWithdraw;

/**
 * @author Youri Dudock
 */
public class BankTask extends BotTask<FrostDragonScript> {

    public BankTask(BotInstance bot, FrostDragonScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Banking..", DebugPriority.HIGH);

        // check if we have any loot in inventory
        if (script.getPlayer().getInventory().items().containsAny(script.DROPS)) {
            script.getBank().bankDepositAll();
            script.getAntiBan().sleep(1000);
        }

        // check for atleast 5 food
        if (!script.getPlayer().getInventory().items().contains(script.FOOD, 5)) {
            script.getBank().withdrawItem(script.FOOD, BankWithdraw.WITHDRAW_5);
            script.getAntiBan().sleep(1000);
        }

        // check for atleast 5 prayer pots
        if (!script.getPlayer().getInventory().items().contains(script.WITHDRAW_PRAYER_POT_ID, 5)) {
            script.getBank().withdrawItem(script.WITHDRAW_PRAYER_POT_ID, BankWithdraw.WITHDRAW_5);
            script.getAntiBan().sleep(1000);
        }

        // check for atleast 1 att pot
        if (!script.getPlayer().getInventory().items().contains(script.WITHDRAW_ATTACK_POT_ID)) {
            script.getBank().withdrawItem(script.WITHDRAW_ATTACK_POT_ID, BankWithdraw.WITHDRAW_1);
            script.getAntiBan().sleep(1000);
        }

        // check for atleast 1 str pot
        if (!script.getPlayer().getInventory().items().contains(script.WITHDRAW_STRENGTH_POT_ID)) {
            script.getBank().withdrawItem(script.WITHDRAW_STRENGTH_POT_ID, BankWithdraw.WITHDRAW_1);
            script.getAntiBan().sleep(1000);
        }


        // check if we have all the needed items
        if (script.getPlayer().getInventory().items().contains(script.FOOD) && script.getPlayer().getInventory().items().contains(script.WITHDRAW_PRAYER_POT_ID)) {

            if (!script.getPlayer().getInventory().items().contains(script.WITHDRAW_STRENGTH_POT_ID) || !script.getPlayer().getInventory().items().contains(script.WITHDRAW_ATTACK_POT_ID)) {
                debug("Could not withdraw a strength or attack pot from the bank. Continue without..", DebugPriority.HIGH);
            }

            // close bank
            script.getInterfaces().closeInterface();
            script.getAntiBan().sleep(1000);
        }

    }

    @Override
    public boolean shouldActivate() {
        // check if bank is open
        return script.getBank().isOpen();
    }
}
