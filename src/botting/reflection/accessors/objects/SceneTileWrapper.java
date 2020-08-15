package botting.reflection.accessors.objects;

import botting.bot.BotInstance;
import botting.reflection.accessors.ClassWrapper;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

import java.util.ArrayList;

/**
 * @author Youri Dudock
 */
public class SceneTileWrapper extends ClassWrapper implements SceneTile {

    public SceneTileWrapper(BotInstance bot, Object accessor) {
        super(bot, accessor);
    }

    @Override
    public SceneObjectTile[] getInteractiveObjects() {
        Object[] gameObjects = (Object[]) new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_SCENE_OBJECT_TILE)).asObject();

        ArrayList<SceneObjectTile> foundObjects = new ArrayList<>();

        for (int i = 0; i < gameObjects.length; i++) {
            if (gameObjects[i] != null) {
                foundObjects.add(new SceneObjectTileWrapper(getBot(), gameObjects[i]));
            }
        }

        return foundObjects.toArray(new SceneObjectTile[0]);
    }
}
