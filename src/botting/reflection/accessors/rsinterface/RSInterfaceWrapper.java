package botting.reflection.accessors.rsinterface;

import botting.bot.BotInstance;
import botting.reflection.accessors.ClassWrapper;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

/**
 * @author Youri Dudock
 */
public class RSInterfaceWrapper extends ClassWrapper implements RSInterface {


    public RSInterfaceWrapper(BotInstance bot, Object accessor) {
        super(bot, accessor);
    }


    @Override
    public int[] getInterfaceItems() {
        return(int[]) new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_RS_INTERFACE_INVENTORY)).asObject();
    }

    public String getInterfaceText() {
        return new RefClass(getAccessor()).getField("aI").asString();
    }


}
