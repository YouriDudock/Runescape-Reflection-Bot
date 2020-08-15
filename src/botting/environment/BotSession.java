package botting.environment;

import botting.bot.BotInstance;

/**
 * A data container containing information about a bot session
 *
 * @author Youri Dudock
 */
public class BotSession {

    private BotInstance botInstance;

    private String username, password;

    public BotSession(BotInstance botInstance, String username, String password) {
        this.username = username;
        this.password = password;
        this.botInstance = botInstance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public BotInstance getBotInstance() {
        return botInstance;
    }
}
