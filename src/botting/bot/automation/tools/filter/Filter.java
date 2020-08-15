package botting.bot.automation.tools.filter;

public interface Filter<T> {

    public boolean accept(T subject);
}
