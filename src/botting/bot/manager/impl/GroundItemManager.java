package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.manager.Manager;
import botting.game.GameGroundItem;
import botting.game.GameTile;
import botting.reflection.accessors.deque.Deque;
import botting.reflection.accessors.deque.Node;
import botting.reflection.accessors.items.GroundItemWrapper;

import java.util.ArrayList;

/**
 * @author Youri Dudock
 */
public class GroundItemManager extends Manager {

    public GroundItemManager(BotInstance bot) {
        super(bot);
    }

    public final GameGroundItem[] getAll() {
        ArrayList<GameGroundItem> items = new ArrayList<>();

        try {

            for (int x = 0; x < 104; x++) {
                for (int y = 0; y < 104; y++) {

                    Deque deque = bot.getAccessors().getClient().getGroundItems()[bot.getAccessors().getClient().getPlane()][x][y];

                    if (deque == null) {
                        continue;
                    }

                    Node holder = deque.getHead();

                    if (holder == null) {
                        continue;
                    }

                    Node curNode = deque.getHead();

                    if (curNode == null) {
                        continue;
                    }


                    while (curNode != null) {
                        items.add(new GameGroundItem(bot, new GroundItemWrapper(bot, curNode.getAccessor()),  x , y));

                        curNode = deque.getNext();

                    }



                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return items.toArray(new GameGroundItem[0]);
    }

    public final GameGroundItem[] getNearby(int range) {
        ArrayList<GameGroundItem> items = new ArrayList<>();

        try {

            int playerX = bot.getManagers().getPlayer().getLocalPlayer().getLocalRegionX();
            int playerY = bot.getManagers().getPlayer().getLocalPlayer().getLocalRegionY();

            for (int x = playerX - range; x < playerX + range; x++) {
                for (int y = playerY - range; y < playerY + range; y++) {

                    Deque deque = bot.getAccessors().getClient().getGroundItems()[bot.getAccessors().getClient().getPlane()][x][y];

                    if (deque == null) {
                        continue;
                    }

                    Node holder = deque.getHead();

                    if (holder == null) {
                        continue;
                    }

                    Node curNode = deque.getHead();

                    if (curNode == null) {
                        continue;
                    }


                    while (curNode != null) {
                        items.add(new GameGroundItem(bot, new GroundItemWrapper(bot, curNode.getAccessor()), x , y));

                        curNode = deque.getNext();

                    }



                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return items.toArray(new GameGroundItem[0]);
    }




    public boolean contains(int item) {
        GameGroundItem[] items = getAll();

        for (GameGroundItem groundItem : items) {

            if (groundItem.getItem().getID() == item) {
                return true;
            }
        }

        return false;
    }

    public GameGroundItem[] findAny(int[] items, int range) {
        // getNearest ground items
        GameGroundItem[] groundItems = getNearby(range);

        // array with found items that is returned
        ArrayList<GameGroundItem> foundItems = new ArrayList<>();

        // loop through ground items
        for (GameGroundItem groundItem : groundItems) {

            // loop through items we are looking for
            for (int item : items) {

                // compare ids
                if (groundItem.getItem().getID() == item) {

                    // if match, add to found item list
                    foundItems.add(groundItem);

                    // stop this loop as this ground item is already added to the found item list
                    break;
                }

            }



        }

        // return found items
        return foundItems.toArray(new GameGroundItem[0]);
    }

    public GameGroundItem find(int item) {
        for (GameGroundItem groundItem : getAll()) {

            if (groundItem.getItem().getID() == item) {
                return groundItem;
            }

        }

        return null;
    }





}
