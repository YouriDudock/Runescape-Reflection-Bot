package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.manager.Manager;
import botting.reflection.accessors.deque.Deque;
import botting.reflection.accessors.deque.Node;
import botting.game.MouseButton;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Youri Dudock
 */
public class MenuManager extends Manager {

    public MenuManager(BotInstance bot) {
        super(bot);
    }


    public Rectangle getMenuBounds() {
        int menuWidth = bot.getAccessors().getClient().getMenuWidth();
        int menuHeight = bot.getAccessors().getClient().getMenuHeight();
        int offsetY = bot.getAccessors().getClient().getMenuYOffset();
        int offsetX = bot.getAccessors().getClient().getMenuXOffset();

        return new Rectangle(offsetX, offsetY, menuWidth, menuHeight);
    }

    public int countOptions(String action) {
        String[] options = getOptions();
        int found = 0;

        for (String option : options) {
            String cleaned = clean(option);

            if (cleaned.contains(action.toLowerCase())) {
                found += 1;
            }
        }

        return found;
    }

    public boolean contains(String action) {
        action = action.toLowerCase();

        String[] options = getOptions();

        for (String option : options) {
            if (clean(option).contains(action)) {
                return true;
            }
        }

        return false;
    }





    public String[] getOptions() {
        ArrayList<String> foundMenuItems = new ArrayList<>();

        try {
            Deque menuDeque = bot.getAccessors().getGame().getRightMenuDequeInstance();

            Node node =  menuDeque.reverseGetFirst();


            while (node != null) {
                String menuAction = node.getBaseMenuItemName();

                if (node.getExtraBaseMenuItemName() != null) {
                    menuAction += " " + node.getExtraBaseMenuItemName();
                }

                foundMenuItems.add(menuAction);

                node = menuDeque.getNext();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return foundMenuItems.toArray(new String[0]);
    }



//    public void clickOption(String option) {
//        option = clean(option);
//
//        ArrayList<String> openMenuItems = getOptions();
//
//        int yOffset = bot.getAccessors().getClient().getMenuYOffset();
//        // to get to the first item
//        yOffset += 25;
//
//        for (String item : openMenuItems) {
//            if (item.contains(option)) {
//
//                Point clickPoint = new Point(getManagers().getMouse().getMouseLocation().x, yOffset);
//                getManagers().getMouse().click(clickPoint);
//
//                debug("Clicked right menu option: " + item, DebugPriority.HIGH);
//                break;
//            }
//
//            yOffset += 15;
//        }
//
//        debug("Could not click option: " + option, DebugPriority.HIGH);
//    }
//
//    public boolean containsOption(String option) {
//        option = clean(option);
//
//        for (String menuItem : getOptions()) {
//            if (menuItem.contains(option)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public int getOptionCount(String option) {
//        option = clean(option);
//
//        int found = 0;
//
//        for (String menuItem : getOptions()) {
//            if (menuItem.contains(option)) {
//                found += 1;
//            }
//        }
//
//        return found;
//    }

    private String clean(String option) {
        // set lower case
        option = option.toLowerCase();
        // remove tags
        option = option.replaceAll("<[^>]*>", "");

        return option;

    }
    /**
     * Gets getNearest menu actions
     * @return current menu action
     */
    public final String[] getActions() {
        final String[] actions = getOptions();
        final int menuActionRow = bot.getAccessors().getClient().getMenuActionRow();
        final String[] menuActions = new String[menuActionRow];
        for (int i = menuActionRow - 1; i >= 0; i--) {
            menuActions[(menuActionRow - i - 1)] = clean(actions[i]);
        }
        return menuActions;
    }


    /**
     * Determines if menu is open
     * @return <b>true</b> if menu is open, otherwise <b>false</b>.
     */
    public final boolean isOpen() {
        return bot.getAccessors().getClient().hasMenuOpen();
    }

    /**
     * Gets the menu screen point from given action index
     * @return point
     */
    public final Point getPointFromIndex(int i) {
        final Rectangle bounds = getMenuBounds();
        return new Point(bounds.x + (bounds.width / 2), bounds.y + 25 + (15 * i));
    }

    /**
     * Gets the action index which contains given string action
     * @param action
     * @return index if found, otherwise -1
     */
    public final int getActionIndex(String action) {
        final String[] actions = getActions();
        action = action.toLowerCase();
        for(int i = 0; i < actions.length; i++) {
            if(actions[i].toLowerCase().contains(action)) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Interacts with the menu
     * @param action
     * @param point
     * @return <b>true</b> if successfully interacted, otherwise <b>false</b>.
     */
    public final boolean interact(String action, final Point point) {
        bot.getManagers().getMouse().moveMouse(point);
        bot.getManagers().getAntiBan().sleep(100);
        final int i = getActionIndex(action);
        if(i < 0) {
            debug("Could not find any menu options: " + action, DebugPriority.HIGH);
            return false;
        }
        if(i == 0) {
            // no need to interact with the menu
            bot.getManagers().getMouse().click(point);
            debug("Has interacted without menu: " + action, DebugPriority.HIGH);
            return true;
        }
        if(!isOpen()) {
            bot.getManagers().getMouse().click(point, MouseButton.RIGHT_BUTTON);
            bot.getManagers().getAntiBan().sleep(100);
            if(!isOpen()) {
                debug("Could not interact with menu because there is no menu open: " + action, DebugPriority.HIGH);
                return false;
            }
            debug("Has interacted with menu: " + action, DebugPriority.HIGH);
            return interact(action, point);
        }
        final Point menuItemPoint = getPointFromIndex(i);
        bot.getManagers().getMouse().click(menuItemPoint);
        debug("Has interacted with menu: " + action, DebugPriority.HIGH);
        return true;
    }



}
