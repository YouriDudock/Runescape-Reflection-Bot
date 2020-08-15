package botting.bot.automation.script.ikov.merch;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.script.ikov.frost.FrostDragonScript;
import botting.bot.debug.DebugPriority;

/**
 * @author Youri Dudock
 */
public class BuyTask extends BotTask<MerchScript> {

    public BuyTask(BotInstance bot, MerchScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Buying..", DebugPriority.HIGH);

    }

    @Override
    protected boolean shouldActivate() {
        return script.getInterfaces().getOpenInterfaceID() == script.SHOP_INTERFACE_ID;
    }
}
