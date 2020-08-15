package botting.bot.automation.script.ikov.smithing;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.script.ikov.PracticeScript;
import botting.bot.debug.DebugPriority;
import botting.game.data.BankWithdraw;

/**
 * @author Yoeri Leijdekker
 */
public class BankTask extends BotTask<PracticeScript> {

    public BankTask(BotInstance bot, PracticeScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Banking..", DebugPriority.HIGH);

        // check if we have any loot in inventory
        if (script.getPlayer().getInventory().items().containsAny(script.SMIHTINGPRODUCTS)) {
            script.getBank().bankDepositAll();
            script.getAntiBan().sleep(1000);
        }

        // check for hammmer
        if (!script.getPlayer().getInventory().items().contains(script.HAMMER, 1)) {
            script.getBank().withdrawItem(script.HAMMER, BankWithdraw.WITHDRAW_1);
            script.getAntiBan().sleep(1000);
        }

        // check for bars
        if (!script.getPlayer().getInventory().items().contains(script.BARS, 27)) {
            script.getBank().withdrawItem(script.BARS, BankWithdraw.WITHDRAW_ALL);
            script.getAntiBan().sleep(1000);
        }




        // check if we have all the needed items
        if (script.getPlayer().getInventory().items().contains(script.HAMMER) && script.getPlayer().getInventory().items().contains(script.HAMMER)) {
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
