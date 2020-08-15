package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.automation.tools.filter.Filter;
import botting.bot.manager.Manager;
import botting.game.entity.GameNPC;
import botting.reflection.accessors.npc.NPC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

/**
 * @author Youri Dudock
 */
public class NPCManager extends Manager {

    private final Comparator<GameNPC> NEAREST_SORTER = (n1, n2) -> bot.getManagers().getPlayer().getLocalPlayer().distanceFrom(n1.getLocation()) - bot.getManagers().getPlayer().getLocalPlayer().distanceFrom(n2.getLocation());

    public NPCManager(BotInstance bot) {
        super(bot);
    }

    private final Filter<GameNPC> ALL_FILTER = new Filter<GameNPC>() {
        @Override
        public boolean accept(GameNPC subject) {
            return true;
        }
    };


    public GameNPC[] getNpcs(Filter<GameNPC> filter) {
        ArrayList<GameNPC> nonNullNPCS = new ArrayList<>();

        for (NPC npc : bot.getAccessors().getClient().getNPCS()) {
            if (npc != null) {

                GameNPC gameNPC = new GameNPC(bot, npc);

                if (filter.accept(gameNPC)) {
                    nonNullNPCS.add(gameNPC);
                }
            }

        }
        Collections.sort(nonNullNPCS, NEAREST_SORTER);

        return nonNullNPCS.toArray(new GameNPC[0]);
    }

    public GameNPC[] getNpcs() {
        return getNpcs(ALL_FILTER);
    }



    public Optional<GameNPC> getNearest(String... names) {
        // filter npcs on name
        GameNPC[] npcs = getNpcs(subject -> {
            for (String name : names) {
                if (subject.getName().toLowerCase().contains(name.toLowerCase())) {
                    return true;
                }
            }

            return false;
        });

        // check if we have found npcs
        if (npcs.length > 0) {
            // get first index
            return Optional.of(npcs[0]);
        }

        // return empty
        return Optional.empty();
    }

    public Optional<GameNPC> getNearest(Filter<GameNPC> filter) {
        GameNPC[] npcs = getNpcs(filter);

        // check if we have found npcs
        if (npcs.length > 0) {
            // get first index
            return Optional.of(npcs[0]);
        }

        // return empty because nothing was found
        return Optional.empty();
    }
}
