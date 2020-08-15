package botting.reflection.accessors.npc;

import botting.bot.BotInstance;
import botting.reflection.accessors.ClassWrapper;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

/**
 * @author Youri Dudock
 */
public class NPCDefinitionWrapper extends ClassWrapper implements NPCDefinition {


    public NPCDefinitionWrapper(BotInstance bot, Object accessor) {
        super(bot, accessor);
    }

    @Override
    public int getID() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_NPC_ID)).asInt();
    }

    @Override
    public String getName() {
        if (getAccessor() == null) {
            return null;
        }

        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_NPC_NAME)).asString();
    }
}
