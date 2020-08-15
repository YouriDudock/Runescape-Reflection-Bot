package botting.reflection.accessors.objects;

import botting.bot.BotInstance;
import botting.reflection.accessors.ClassWrapper;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

/**
 * @author Youri Dudock
 */
public class SceneObjectTileWrapper extends ClassWrapper implements SceneObjectTile {

    public SceneObjectTileWrapper(BotInstance bot, Object accessor) {
        super(bot, accessor);
    }

    @Override
    public int getX() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_OBJECT_X)).asInt();
    }

    @Override
    public int getY() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_OBJECT_Y)).asInt();
    }

    @Override
    public long getHash() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_OBJECT_HASH)).asLong();
    }

    @Override
    public String getName() {
//        Class<?> objectDefClass = getBot().getClassLoader().loadClass("aX");
//        new RefClass(objectDefClass).getMethod("b").
//
//        return new RefClass(def).getField("ab");
        return null;
    }

}
