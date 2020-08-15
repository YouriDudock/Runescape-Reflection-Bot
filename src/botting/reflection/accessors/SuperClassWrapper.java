package botting.reflection.accessors;

import botting.bot.BotInstance;

/**
 * @author Youri Dudock
 */
public class SuperClassWrapper {

    private BotInstance bot;

    private Class<?> accessor;

    private Object childAccessor;


    public SuperClassWrapper(BotInstance bot, Object childAccessor) {
        this.bot = bot;
        this.accessor = childAccessor.getClass().getSuperclass();
        this.childAccessor = childAccessor;
    }

    public BotInstance getBot() {
        return bot;
    }

    public Class<?> getAccessor() {
        return accessor;
    }

    public Object getChildAccessor() {
        return childAccessor;
    }
}
