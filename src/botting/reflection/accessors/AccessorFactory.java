package botting.reflection.accessors;

import botting.bot.BotInstance;
import botting.reflection.accessors.client.Client;
import botting.reflection.accessors.client.ClientWrapper;
import botting.reflection.accessors.entity.Entity;
import botting.reflection.accessors.game.Game;
import botting.reflection.accessors.game.GameWrapper;
import botting.reflection.accessors.io.Keyboard;
import botting.reflection.accessors.io.KeyboardWrapper;
import botting.reflection.accessors.io.Mouse;
import botting.reflection.accessors.io.MouseWrapper;

/**
 * @author Youri Dudock
 */
public class AccessorFactory {

    private BotInstance bot;

    public AccessorFactory(BotInstance bot) {
        this.bot = bot;
    }

    public Client getClient() {
        return new ClientWrapper(bot);
    }

    public Game getGame() {return new GameWrapper(bot);}


    public Mouse getMouse() {
        return new MouseWrapper(bot);
    }

    public Keyboard getKeyboard() {
        return new KeyboardWrapper(bot);
    }
}
