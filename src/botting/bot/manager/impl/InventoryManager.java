package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.manager.Manager;
import botting.game.GameInventory;
import botting.game.data.InventoryType;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public class InventoryManager extends Manager {

    public InventoryManager(BotInstance bot) {
        super(bot);
    }

    public GameInventory items() {
        return new GameInventory(bot, bot.getAccessors().getClient().getInterfaces()[3214], InventoryType.INVENTORY);
    }

}
