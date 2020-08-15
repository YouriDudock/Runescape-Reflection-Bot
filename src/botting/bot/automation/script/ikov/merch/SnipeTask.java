package botting.bot.automation.script.ikov.merch;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.tools.condition.Condition;
import botting.bot.debug.DebugPriority;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public class SnipeTask extends BotTask<MerchScript> {

    public SnipeTask(BotInstance bot, MerchScript script) {
        super(bot, script);
    }

    @Override
    public void execute() {
        debug("Sniping..", DebugPriority.HIGH);

        script.getMouse().click(script.REFRESH_BUTTON);

        int offset = script.FIRST_ROW_INTERFACE_ID;

        // loop through current offers
        for (int i = 0; i < 7; i++) {

            try {


            String name = script.getInterfaces().getInterface(offset + script.NAME_INTERFACE_OFFSET).getInterfaceText();
            String price = script.getInterfaces().getInterface(offset + script.PRICE_INTERFACE_OFFSET).getInterfaceText();

            int actualPrice = Integer.parseInt(price.replace(",", ""));

            if (name.toLowerCase().equals("frost dragon bones")) {
                debug("Bones for: " + actualPrice, DebugPriority.HIGH);
            }

               // debug("Item: " + name + " for: " + actualPrice, DebugPriority.HIGH);


            if (name.toLowerCase().equals("frost dragon bones") && actualPrice <= 40000) {
                Point openShopPoint = new Point(script.FIRST_OPEN_SHOP_LOCATION.x, script.FIRST_OPEN_SHOP_LOCATION.y + (i == 0 ? i : i*script.ROW_OFFSET));

                script.getMouse().click(openShopPoint);

                Condition.waitTill(() -> script.getInterfaces().getOpenInterfaceID() == script.SHOP_INTERFACE_ID, 2000);

                if (script.getInterfaces().getOpenInterfaceID() == script.SHOP_INTERFACE_ID) {
                    script.targetName = name;
                    script.price = actualPrice;
                    break;
                }

            }



            } catch (NumberFormatException e) {
                //e.printStackTrace();
            }

            offset += 6;

        }



    }

    @Override
    protected boolean shouldActivate() {
        if (script.getInterfaces().getOpenInterfaceID() == script.RECENT_INTERFACE) {
            return true;
        }

        return false;
    }



}
