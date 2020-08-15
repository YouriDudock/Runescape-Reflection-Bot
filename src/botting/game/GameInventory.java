package botting.game;

import botting.bot.BotInstance;
import botting.game.data.GameTab;
import botting.game.data.InventoryType;
import botting.reflection.accessors.rsinterface.RSInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * An inventory ingame
 *
 * @author Youri Dudock
 */
public class GameInventory {

    // size related
    private final int EMPTY = 0, FULL = 28;

    private RSInterface rsInterface;

    private BotInstance bot;

    private InventoryType type;

    public GameInventory(BotInstance bot, RSInterface rsInterface, InventoryType type) {
        this.bot = bot;
        this.rsInterface = rsInterface;
        this.type = type;

    }

    public GameItem[] getItems() {
        int[] interfaceItems = rsInterface.getInterfaceItems();
        ArrayList<GameItem> foundItems = new ArrayList<>();

        for (int i = 0; i < interfaceItems.length; i++) {
            // reduce with -1 as the ID appears to be offset by 1
            if (interfaceItems[i] > 0) {
                foundItems.add(new GameItem(bot, interfaceItems[i] - 1, i, type));
            }

        }

        return foundItems.toArray(new GameItem[0]);
    }

    private Integer[] getItemsAsIDS() {
        int[] interfaceItems = rsInterface.getInterfaceItems();
        ArrayList<Integer> foundItems = new ArrayList<>();

        for (int i = 0; i < interfaceItems.length; i++) {
            // reduce with -1 as the ID appears to be offset by 1

            if (interfaceItems[i] > 0) {
                foundItems.add(interfaceItems[i] - 1);
            }

        }

        return foundItems.toArray(new Integer[0]);
    }

    public boolean isEmpty() {
        return getItemsAsIDS().length == EMPTY;
    }

    public boolean isFull() {
        return getItemsAsIDS().length == FULL;
    }

    /**
     * Checks if the inventory contains one or more items in the array
     * @param items items to check
     * @return if one or more items is present
     */
    public boolean containsAny(int[] items) {
        for (int item : items) {
            if (contains(item)) {
                return true;
            }
        }

        return false;
    }

    public boolean contains(int id) {
        for (int item : getItemsAsIDS()) {
            if (item == id) {
                return true;
            }
        }

        return false;
    }

    public boolean contains(int id, int amount) {
        int found = (int) Arrays.stream(getItemsAsIDS()).filter(item -> item == id).count();
        return found >= amount;
    }

    public boolean contains(GameItem item, int amount) {
        return contains(item.getID(), amount);
    }


    public boolean contains(GameItem item) {
        return contains(item.getID());
    }


    public Optional<GameItem> first(int itemID) {
        GameItem[] items = getItems();

        for (GameItem item : items) {
            if (item.getID() == itemID) {
                return Optional.of(item);
            }
        }

        return Optional.empty();
    }

    public Optional<GameItem> firstOfAny(int[] itemIDs) {
        GameItem[] items = getItems();

        for (GameItem item : items) {

            for (int itemID : itemIDs) {
                if (item.getID() == itemID) {
                    return Optional.of(item);
                }
            }
        }

        return Optional.empty();
    }
}
