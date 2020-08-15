package botting.bot.automation.script.ikov.merch;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.script.Script;
import botting.reflection.accessors.rsinterface.RSInterface;
import botting.reflection.modifiers.RefClass;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public class MerchScript extends Script {

    public final Point REFRESH_BUTTON = new Point(450,60);

    public final int RECENT_INTERFACE = 52580;
    public final int FIRST_ROW_INTERFACE_ID = 52025;
    public final int SHOP_HOME = 41409;
    public final int SHOP_INTERFACE_ID = 3824;

    public final int NAME_INTERFACE_OFFSET = 2;
    public final int PRICE_INTERFACE_OFFSET = 3;

    public final Point FIRST_OPEN_SHOP_LOCATION = new Point(421, 88);
    public final int ROW_OFFSET = 34;


    public String targetName = "";
    public int price = 0;

    // 52027 =  aI = bA


    public BotTask[] taskList = {
            new SnipeTask(getBot(), this)
    };


    public MerchScript(BotInstance bot) {
        super(bot);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onTick() {

//        if (getInterfaces().getOpenInterfaceID() == RECENT_INTERFACE) {
//            RSInterface row = getInterfaces().getInterface(52027);
//
//            System.out.println(new RefClass(row).getField("aI").asString());
//            System.out.println(new RefClass(row).getField("bA").asString());
//
//        }


        for (BotTask task : taskList) {
            if (task.shouldRun()) {
                task.execute();
            }
        }

        getAntiBan().sleep(100, false);

    }

    @Override
    public void onStop() {

    }

    @Override
    public boolean shouldStop() {
        return false;
    }

}
