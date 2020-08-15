package botting.reflection.accessors;

import botting.bot.BotInstance;

/**
 * @author Youri Dudock
 */
public abstract class ClassWrapper {

    private BotInstance bot;

    private Object accessor;


    public ClassWrapper(BotInstance bot, Object accessor) {
        this.bot = bot;
        this.accessor = accessor;
    }

    public ClassWrapper(BotInstance bot) {
        this.bot = bot;
    }



    public BotInstance getBot() {
        return bot;
    }

    public Object getAccessor() {
        return accessor;
    }

}
