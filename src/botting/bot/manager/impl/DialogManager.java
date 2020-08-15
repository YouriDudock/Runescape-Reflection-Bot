package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.manager.Manager;

import javax.sql.rowset.Predicate;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.function.IntPredicate;

/**
 * @author Youri Dudock
 */
public class DialogManager extends Manager {

    private final int SELECT_OPTION_3 = 2469;
    private final int SELECT_OPTION_2 = 2459;
    private final int SELECT_OPTION_4 = 2480;
    private final int SELECT_OPTION_5 = 2492;

    private final int[] CONVERSATION = {
            4900,
            4893,
            4887,
            4882
    };

    public DialogManager(BotInstance bot) {
        super(bot);
    }




    public boolean hasDialogOpen() {
        return getOpenDialogID() >= 0;
    }

    public int getOpenDialogID() {
        return bot.getAccessors().getClient().getOpenDialogID();
    }

    public int getOptionCount() {
        switch (getOpenDialogID()) {


            case SELECT_OPTION_3:
                return 3;

            case SELECT_OPTION_2:
                return 2;

            case SELECT_OPTION_4:
                return 4;

            case SELECT_OPTION_5:
                return 5;

        }

        return 0;
    }

    public boolean hasSelectOptionOpen() {
        return getOptionCount() > 0;
    }

    public boolean hasTalkingOpen() {
        return Arrays.stream(CONVERSATION).anyMatch(i -> i == getOpenDialogID());
    }

    public void clickContinue() {
        getManagers().getKeyboard().pressKey(KeyEvent.VK_SPACE);
    }
}
