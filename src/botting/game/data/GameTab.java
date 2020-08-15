package botting.game.data;

import java.awt.*;

/**
 * A tab in the HUD
 *
 * @author Youri Dudock
 */
public class GameTab {

    // location of this tab on screen
    private Point point;

    public GameTab(Point point) {
        this.point = point;
    }


    public Point getPoint() {
        return point;
    }
}
