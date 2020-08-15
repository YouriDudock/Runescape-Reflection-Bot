package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.manager.Manager;
import botting.game.entity.GameNPC;
import botting.game.entity.GamePlayer;
import botting.reflection.accessors.npc.NPC;
import botting.reflection.accessors.player.Player;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Youri Dudock
 */
public class WorldManager extends Manager {

    private NPCManager npcManager;

    private ObjectManager objectManager;

    private GroundItemManager groundItemManager;

    public WorldManager(BotInstance bot) {
        super(bot);

        npcManager = new NPCManager(bot);
        groundItemManager = new GroundItemManager(bot);
        objectManager = new ObjectManager(bot);
    }

    public ArrayList<GamePlayer> getPlayers() {
        ArrayList<GamePlayer> nonNullPlayers = new ArrayList<>();

        for (Player player : bot.getAccessors().getClient().getPlayers()) {
            if (player != null) {
                nonNullPlayers.add(new GamePlayer(bot, player));
            }

        }

        return nonNullPlayers;
    }



    public NPCManager getNPCManager() {
        return npcManager;
    }


    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public GroundItemManager getGroundItemManager() {return groundItemManager; }
}
