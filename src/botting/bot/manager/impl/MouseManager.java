package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.bot.manager.Manager;
import botting.game.MouseButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * @author Youri Dudock
 */
public class MouseManager extends Manager {

    public MouseManager(BotInstance bot) {
        super(bot);
    }

    public void scrollMouse(int amount) {

        // create a mouse wheel event
        MouseWheelEvent event = new MouseWheelEvent((Component) bot.getAccessors().getGame().getMainInstance(), MouseWheelEvent.MOUSE_WHEEL, System.currentTimeMillis(), 0, 50, 50, 0, false, MouseWheelEvent.WHEEL_UNIT_SCROLL, amount, 100);

        // invoke scroll method
        bot.getAccessors().getMouse().scrollMouse(event);


        Debugger.write(getClass(), "Scrolled mouse wheel: " + amount, DebugPriority.HIGH);
    }

    /**
     * Clicks the mouse with the left button
     *
     * @param point
     */
    public void click(Point point) {
        click(point, MouseButton.LEFT_BUTTON);
    }


    /**
     * Clicks the mouse
     *
     * @param point
     * @param button type of mouse button (MouseButton)
     */
    public void click(Point point, int button) {
        // move the mouse to the location
        moveMouse(point);

        // sleep
        getManagers().getAntiBan().sleep(100, 200);

        // create fake mouse events
        MouseEvent press = new MouseEvent((Component) bot.getAccessors().getGame().getMainInstance(), MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, point.x, point.y, 1, false, button);
        MouseEvent release = new MouseEvent((Component) bot.getAccessors().getGame().getMainInstance(), MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), 0, point.x, point.y, 1, false, button);

        // invoke mouse events
        bot.getAccessors().getMouse().mousePressed(press);
        bot.getAccessors().getMouse().mouseReleased(release);



        Debugger.write(getClass(), "Clicked mouse at: " + point.x + ":" + point.y, DebugPriority.HIGH);
    }


    /**
     * Moves the mouse to a coordinate point on screen
     *
     * @param point
     */
    public void moveMouse(Point point) {
        bot.getAccessors().getMouse().setX(point.x);
        bot.getAccessors().getMouse().setY(point.y);

        //DrawTask.mousePaintable.setMouseLocation(point);

        Debugger.write(getClass(), "Moved mouse to: " + point.x + ":" + point.y, DebugPriority.HIGH);
    }

    /**
     *
     * @return current location of the mouse
     */
    public Point getMouseLocation() {
        return new Point(bot.getAccessors().getMouse().getX(), bot.getAccessors().getMouse().getY());
    }


}
