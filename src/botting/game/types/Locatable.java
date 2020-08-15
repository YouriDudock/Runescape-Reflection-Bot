package botting.game.types;

import botting.game.GameTile;

/**
 * In game objects that have a location (players, npcs, game objects, etc)
 *
 * @author Youri Dudock
 */
public interface Locatable {

    public GameTile getLocation();

}
