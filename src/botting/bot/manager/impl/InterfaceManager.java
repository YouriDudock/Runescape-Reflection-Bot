package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.bot.manager.Manager;
import botting.game.GameItem;
import botting.game.data.InventoryType;
import botting.reflection.accessors.rsinterface.RSInterface;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * @author Youri Dudock
 */
public class InterfaceManager extends Manager {

    private BankManager bankManager;

    private DialogManager dialogManager;

    private MenuManager menuManager;

    public InterfaceManager(BotInstance bot) {
        super(bot);

        bankManager = new BankManager(bot);
        dialogManager = new DialogManager(bot);
        menuManager = new MenuManager(bot);
        dialogManager = new DialogManager(bot);

    }

    /**
     *
     * @return if there is an interface open
     */
    public boolean hasInterfaceOpen() {
        return getOpenInterfaceID() >= 0;
    }

    public int getOpenInterfaceID() {
            return bot.getAccessors().getClient().getOpenInterfaceID();
    }

    /**
     * Closes an interface
     */
    public void closeInterface() {
        Debugger.write(getClass(), "Closing interface.", DebugPriority.LOW);
        getManagers().getKeyboard().pressKey(KeyEvent.VK_ESCAPE);
    }

    public RSInterface[] getInterfaces() {
        return bot.getAccessors().getClient().getInterfaces();
    }

    public RSInterface getInterface(int interfaceId) {
        return getInterfaces()[interfaceId];
    }



    public ArrayList<GameItem> getInterfaceItems(int interfaceID) {
        ArrayList<GameItem> items = new ArrayList<>();

        Object[] interfaces = bot.getAccessors().getClient().getInterfaces();

        Object gameInterface = interfaces[interfaceID];

        if (gameInterface == null)
            return items;

        int[] interfaceItems = (int[]) new RefClass(gameInterface).getField(getHooks().value(ClientHook.FIELD_RS_INTERFACE_INVENTORY)).asObject();

        if (interfaceItems == null)
            return items;


        for (int i = 0; i < interfaceItems.length; i++) {
            items.add(new GameItem(bot, interfaceItems[i] - 1, i, InventoryType.NONE));
        }

        return items;
    }


    public BankManager getBank() {
        return bankManager;
    }

    public DialogManager getDialog() {
        return dialogManager;
    }



    public MenuManager getMenu() {
        return menuManager;
    }


}
