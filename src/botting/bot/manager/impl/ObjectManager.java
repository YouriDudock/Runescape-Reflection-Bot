package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.automation.tools.filter.Filter;
import botting.bot.manager.Manager;
import botting.game.GameObject;
import botting.game.types.Locatable;
import botting.reflection.accessors.objects.SceneObjectTile;
import botting.reflection.accessors.objects.SceneTile;

import java.util.*;

/**
 * @author Youri Dudock
 */
public class ObjectManager extends Manager {

    private final Comparator<Locatable> NEAREST_SORTER = new Comparator<Locatable>() {

        @Override
        public int compare(Locatable n1,Locatable n2) {
            return bot.getManagers().getPlayer().getLocalPlayer().distanceFrom(n1.getLocation()) - bot.getManagers().getPlayer().getLocalPlayer().distanceFrom(n2.getLocation());
        }

    };


    private final Filter<GameObject> ALL_FILTER = new Filter<GameObject>() {
        @Override
        public boolean accept(GameObject subject) {
            return true;
        }
    };

    public ObjectManager(BotInstance bot) {
        super(bot);
    }

    public GameObject[] getObjects(Filter<GameObject> filter) {
        ArrayList<GameObject> foundObjects = new ArrayList<>();

        SceneTile[][] tiles = bot.getAccessors().getClient().getScene().getSceneTiles()[bot.getAccessors().getClient().getPlane()];

        for (int i = 0; i < 104; i++) {

            for (int j = 0; j < 104; j++) {

                SceneTile tile = tiles[i][j];

                if (tile != null) {
                    SceneObjectTile[] objects = tile.getInteractiveObjects();

                    if (objects != null) {
                        for (SceneObjectTile gameObject : objects) {

                            if (gameObject != null) {
                                GameObject foundObject = new GameObject(bot, gameObject);

                                if (filter.accept(foundObject)) {
                                    foundObjects.add(foundObject);
                                }

                            }
                        }
                    }
                }

            }

        }
        return foundObjects.toArray(new GameObject[0]);
    }


    public GameObject[] getObjectsNearby(Filter<GameObject> filter, int range) {
        ArrayList<GameObject> foundObjects = new ArrayList<>();

        SceneTile[][] tiles = bot.getAccessors().getClient().getScene().getSceneTiles()[bot.getAccessors().getClient().getPlane()];

        int playerX = bot.getManagers().getPlayer().getLocalPlayer().getLocalRegionX();
        int playerY = bot.getManagers().getPlayer().getLocalPlayer().getLocalRegionY();

        for (int i = playerX - range; i < playerX + range; i++) {

            for (int j = playerY - range; j < playerY + range; j++) {

                SceneTile tile = tiles[i][j];

                if (tile != null) {
                    SceneObjectTile[] objects = tile.getInteractiveObjects();

                    if (objects != null) {
                        for (SceneObjectTile gameObject : objects) {

                            if (gameObject != null) {
                                GameObject foundObject = new GameObject(bot, gameObject);

                                if (filter.accept(foundObject)) {
                                    foundObjects.add(foundObject);
                                }
                            }
                        }
                    }
                }

            }

        }
        return foundObjects.toArray(new GameObject[0]);
    }

    public Optional<GameObject> getNearest(int... ids) {
        GameObject[] objects = getObjects(subject -> {
            for (int id : ids) {
                if (id == subject.getID()) {
                    return true;
                }
            }

            return false;
        });

        Arrays.sort(objects, NEAREST_SORTER);

        if (objects.length > 0) {
            return Optional.of(objects[0]);
        }

        return Optional.empty();
    }

    public Optional<GameObject> getNearest(int ID, int range) {
        GameObject[] objects = getObjectsNearby(subject -> subject.getID() == ID, range);

        Arrays.sort(objects, NEAREST_SORTER);

        if (objects.length > 0) {
            return Optional.of(objects[0]);
        }

        return Optional.empty();
    }



}
