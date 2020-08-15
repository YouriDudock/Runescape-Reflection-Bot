package botting.game.entity;

import botting.bot.BotInstance;
import botting.bot.manager.impl.PlayerManager;
import botting.reflection.accessors.player.Player;

import java.awt.*;

/**
 * An in game player
 *
 * @author Youri Dudock
 */
public class GamePlayer extends GameEntity {

    // the reflection accessor
    private Player accessor;

    // username of this player
    private String username;

    public GamePlayer(BotInstance bot, Player accessor) {
        super(bot);

        // set instance
        this.accessor = accessor;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == GamePlayer.class) {

            GamePlayer other = (GamePlayer) obj;

            return this.getAccessor() == other.getAccessor();

        } else {
            return false;
        }
    }

    public Player getAccessor() {
        return accessor;
    }


    @Override
    public void draw(Graphics g) {
        if (!super.isOnScreen()) {
            return;
        }

        Point p = getCenterPointOnScreen();
        g.setColor(Color.RED);
        g.fillRect(p.x - 2, p.y - 2, 4, 4);
        g.setColor(Color.yellow);
        g.drawString(" [" + username + "]", p.x + 5, p.y - 2);
    }
}
