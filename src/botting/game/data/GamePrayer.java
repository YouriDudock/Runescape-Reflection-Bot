package botting.game.data;

import java.awt.*;

/**
 * A prayer in the game
 *
 * @author Youri Dudock
 */
public class GamePrayer {

    private Point point;

    public GamePrayer(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }
}
