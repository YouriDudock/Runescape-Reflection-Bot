package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.manager.Manager;
import botting.game.GameInventory;
import botting.game.GameItem;
import botting.game.data.BankWithdraw;
import botting.game.data.InventoryType;

import java.util.Optional;

import static java.lang.Thread.sleep;

/**
 * @author Youri Dudock
 */
public class BankManager extends Manager {

    public static final int[] BANKERS = new int[] { 44, 45, 494, 495, 498, 499,
            909, 958, 1036, 2271, 2354, 2355, 3824, 5488, 5901, 4456, 4457,
            4458, 4459, 5912, 5913, 6362, 6532, 6533, 6534, 6535, 7605, 8948,
            9710, 14367 };
    public static final int[] BANKS = new int[] { 782, 2213, 2995, 5276, 6084,
            10517, 11402, 11758, 12759, 14367, 19230, 20325, 24914, 25808,
            26972, 29085, 52589, 34752, 35647, 36786, 2012, 2015, 2019, 693,
            4483, 12308, 20607, 21301, 27663, 42192 };


    public BankManager(BotInstance bot) {
        super(bot);
    }


    public GameInventory items() {
        return new GameInventory(bot, bot.getAccessors().getClient().getInterfaces()[18687], InventoryType.BANK);
    }

    /**
     *
     * @return if the bank interface is open
     */
    public boolean isOpen() {
        return getManagers().getInterfaces().getOpenInterfaceID() == bot.getRSClient().getBankID();
    }

    public void bankDepositAll() {
        getManagers().getMouse().click(bot.getRSClient().bankDepositAllPoint());
    }

    public void withdrawItem(int id, BankWithdraw option) {
        if (!isOpen()) {
            return;
        }

        Optional<GameItem> bankItem =  items().first(id);

        bankItem.ifPresent(gameItem -> gameItem.interact(option.getIdentifier()));
    }
}
