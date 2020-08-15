package botting.reflection.accessors.objects;

import botting.bot.BotInstance;
import botting.reflection.accessors.ClassWrapper;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

/**
 * @author Youri Dudock
 */
public class SceneWrapper extends ClassWrapper implements Scene {

    public SceneWrapper(BotInstance bot, Object accessor) {
        super(bot, accessor);
    }

    @Override
    public SceneTile[][][] getSceneTiles() {
        Object[][][] tiles = (Object[][][]) new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_SCENE_TILES)).asObject();


        SceneTile[][][] foundTiles = new SceneTileWrapper[tiles.length][tiles[0].length][tiles[0][0].length];

        for (int i = 0; i < tiles.length; i++) {

            for (int j = 0; j < tiles[i].length; j++) {

                for (int k = 0; k < tiles[i][j].length; k++) {

                    if (tiles[i][j][k] != null) {
                        foundTiles[i][j][k] = new SceneTileWrapper(getBot(), tiles[i][j][k]);
                    }
                }

            }

        }

        return foundTiles;
    }
}
