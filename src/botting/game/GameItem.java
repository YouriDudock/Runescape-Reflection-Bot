package botting.game;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.game.data.InventoryType;
import botting.game.data.RSItem;
import botting.game.types.Drawable;
import botting.game.types.Interactable;

import java.awt.*;

/**
 * An item in the botting.game
 *
 * @author Youri Dudock
 */
public class GameItem implements Interactable, Drawable {

    // item ID
    private int ID;

    // name of the item
    private String name;

    private Object instance;

    private int slot;

    private BotInstance bot;

    private InventoryType type;


    public GameItem(BotInstance bot, int ID, int slot, InventoryType type) {
        this.bot = bot;
        this.ID = ID;
        this.slot = slot;
        this.type = type;
        setName();
    }

    public GameItem(BotInstance bot, int ID) {
        this.bot = bot;
        this.ID = ID;
        this.type = InventoryType.NONE;
        setName();
    }


    private void setName() {
        // obtain the name by checking our data list
        for (RSItem item : RSItem.values()) {
            if (item.getID() == ID) {
                // set name and replace enum underscores with spaces
                name = item.name().replace('_', ' ');
            }
        }

        if (name == null) {
            name = "unknown";
            //Debugger.write(getClass(), "Could not find GameItem name for ID: " + ID, DebugPriority.MEDIUM);
        }
    }


    public Point getScreenLocation() {
        switch (type) {

            case INVENTORY:
                int col = slot % 4;
                int row = slot / 4;
                int x = bot.getRSClient().getFirstInventoryItem().x + col * 42;
                int y = bot.getRSClient().getFirstInventoryItem().y + row * 36;
                return new Point(x, y);


            case BANK:
                final Point first_slot_point = bot.getRSClient().getFirstBankItem();
                final Point slot_distance = new Point(44, 40);
                final int length = 10;
                final int column = (slot % length);
                final int therow = (slot / length);
                final int pointX = first_slot_point.x + (column * slot_distance.x);
                final int pointY = first_slot_point.y + (therow * slot_distance.y);
                return new Point(pointX, pointY);

        }

        return null;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == GameItem.class) {

            if (this.ID == ((GameItem) obj).getID()) {
                return true;
            }
        }

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "GameItem{" +
                "ID=" + ID +
                '}';
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public Object getInstance() {
        return instance;
    }

    @Override
    public boolean interact(String option) {
        return bot.getManagers().getInterfaces().getMenu().interact(option, getScreenLocation());
    }

    @Override
    public boolean findAndInteract(String option) {
        return interact(option);
    }

    @Override
    public void draw(Graphics g) {
        Point p = getScreenLocation();
        g.setColor(Color.RED);
        g.fillRect(p.x - 2, p.y - 2, 4, 4);
        g.setColor(Color.yellow);
        g.drawString(getID() + "", p.x, p.y - 2);
    }

    public boolean isOnScreen() {
        Debugger.write(getClass(), "Screen loc: " + getScreenLocation(), DebugPriority.MEDIUM);
        return bot.getManagers().getCalculations().isOnScreen(getScreenLocation());
    }
}
