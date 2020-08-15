package botting.game.data;

import botting.game.GameTile;

/**
 * An area in the game
 *
 * @author Youri Dudock
 */
public class GameArea {

    // the furthest most north east and south west tile
    private GameTile northEastTile, southWestTile;

    public GameArea(GameTile southWestTile, GameTile northEastTile) {
        this.southWestTile = southWestTile;
        this.northEastTile = northEastTile;
    }


    public GameTile getSouthWestTile() {
        return southWestTile;
    }

    public GameTile getNorthEastTile() {
        return northEastTile;
    }
}
