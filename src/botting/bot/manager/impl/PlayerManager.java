package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.manager.Manager;
import botting.game.entity.GameEntity;
import botting.game.entity.GamePlayer;
import botting.reflection.accessors.player.Player;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

import java.util.ArrayList;

/**
 * Deals with player related game functionality
 *
 * @author Youri Dudock
 */
public class PlayerManager extends Manager {

    private InventoryManager inventoryManager;

    private PrayerManager prayerManager;

    private SkillManager skillManager;

    public PlayerManager(BotInstance bot) {
        super(bot);

        inventoryManager = new InventoryManager(bot);
        prayerManager = new PrayerManager(bot);
        skillManager = new SkillManager(bot);
    }



    public GamePlayer getLocalPlayer() {
        Player player = bot.getAccessors().getClient().getLocalPlayer();

        return new GamePlayer(bot, player);
    }







    /**
     *
     * @return the inventory manager
     */
    public InventoryManager getInventory() {
        return inventoryManager;
    }


    public SkillManager getSkills() {
        return skillManager;
    }

    public PrayerManager getPrayer() {
        return prayerManager;
    }

}
