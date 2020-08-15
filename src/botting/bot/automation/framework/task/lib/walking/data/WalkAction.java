package botting.bot.automation.framework.task.lib.walking.data;

import botting.bot.BotInstance;
import botting.bot.manager.ManagerRepository;

/**
 * @author Youri Dudock
 */
public interface WalkAction {

    public void perform(ManagerRepository managers);

    public boolean hasArrived(ManagerRepository managers);
}
