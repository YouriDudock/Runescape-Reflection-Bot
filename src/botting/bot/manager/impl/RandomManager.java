package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.manager.Manager;
import botting.game.data.GameTab;
import botting.rs.client.clients.ikov.Ikov;

import java.util.Arrays;

/**
 * @author Youri Dudock
 */
public class RandomManager extends Manager {

    public RandomManager(BotInstance bot) {
        super(bot);
    }

    public boolean isUsingItemOnItem() {
        return (getManagers().getInterfaces().getMenu().contains("use") && getManagers().getInterfaces().getMenu().contains("with")) ||
                (getManagers().getInterfaces().getMenu().contains("use") && getManagers().getInterfaces().getMenu().contains("->"));
    }

    public boolean isCastingOn() {
        return getManagers().getInterfaces().getMenu().contains("cast") && getManagers().getInterfaces().getMenu().contains("on");
    }

    public void solveCastingOn() {
        getManagers().getHud().openTab(Ikov.Game.Tabs.FRIENDS);
    }

    public void solveUseItemOnItem() {
        getManagers().getHud().openTab(Ikov.Game.Tabs.INVENTORY);
    }

    public boolean hasRandomInterfaceOpen(int... excluded) {
        if (!getManagers().getInterfaces().hasInterfaceOpen()) {
            return false;
        }

        int interfaceID = getManagers().getInterfaces().getOpenInterfaceID();

        return !Arrays.stream(excluded).anyMatch(id -> id == interfaceID);
    }
}
