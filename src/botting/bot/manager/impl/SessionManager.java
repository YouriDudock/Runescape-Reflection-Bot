package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.bot.manager.Manager;

import java.awt.*;

import static java.lang.Thread.sleep;

/**
 * Deals with the game session functionality, such as logging in and out
 *
 * @author Youri Dudock
 */
public class SessionManager extends Manager {

    // the max amount of times the bot tries to login
    private final int MAX_LOGIN_TRIES = 10;

    // the max amount of times the bot tries to login
    private final int MAX_LOGOUT_TRIES = 10;

    // the amount of time to wait after the MAX_LOGIN_TRIES has been passed
    private final int TRY_LOGIN_AGAIN_TIMER = 600000;

    public SessionManager(BotInstance bot) {
        super(bot);
    }

    public boolean isLoggedIn() {
        return bot.getAccessors().getClient().isLoggedIn();
    }

    /**
     * Logs a user into the game
     */
    public void login() {
        Debugger.write(getClass(), "Trying to login..", DebugPriority.HIGH);

        // check if player is logged in already
        if (isLoggedIn()) {
            Debugger.write(getClass(), "Player is already logged in.", DebugPriority.HIGH);
            return;
        }

        // get login credentials
        final String username = getBotSession().getUsername();
        final String password = getBotSession().getPassword();

        // the amount of tries
        int tries = 0;

        while (!isLoggedIn()) {

            // see if we have passed the login attempts limit
            if (tries >= MAX_LOGIN_TRIES) {
                Debugger.write(getClass(), "Crossed login attempt barrier. Could not login", DebugPriority.HIGH);
                Debugger.write(getClass(), "Sleeping for: " + TRY_LOGIN_AGAIN_TIMER, DebugPriority.HIGH);
                getManagers().getAntiBan().sleep(TRY_LOGIN_AGAIN_TIMER);
            }

            // set credentials
            enterGameCredentials(username, password);

            // click login buttons
            for (Point clickPoint : bot.getRSClient().getLoginButtonPoints()) {
                getManagers().getMouse().click(clickPoint);
                getManagers().getAntiBan().sleep(500);
            }

            // add one try
            tries += 1;

            Debugger.write(getClass(), "Login attempt: " + tries, DebugPriority.MEDIUM);

            // wait a bit
            getManagers().getAntiBan().sleep(5000);
        }

        // check to see if we are actually logged in
        Debugger.write(getClass(), "Player " + username + " has successfully logged in!", DebugPriority.HIGH);
    }

    /**
     * Logs a player out
     *
     * @return if the player is now logged out
     */
    public boolean logout() {
        Debugger.write(getClass(), "Trying to logout..", DebugPriority.HIGH);

        // check if player is logged out
        if (isLoggedIn()) {
            Debugger.write(getClass(), "Player is already logged out.", DebugPriority.HIGH);
            return true;
        }

        // check for any open types that may be in the way
        if (getManagers().getInterfaces().hasInterfaceOpen()) {
            getManagers().getInterfaces().closeInterface();
            getManagers().getAntiBan().sleep(2000);
        }

        // click login buttons
        for (Point clickPoint : bot.getRSClient().getLogoutButtonPoints()) {
            getManagers().getMouse().click(clickPoint);
            getManagers().getAntiBan().sleep(2000);
        }

        // wait a bit
        getManagers().getAntiBan().sleep(5000);

        // check login status
        if (!isLoggedIn()) {
            // success!
            Debugger.write(getClass(), "Player logout succesfully!", DebugPriority.HIGH);
            return true;

        } else {
            // failed to logout
            Debugger.write(getClass(), "Could not logout player.", DebugPriority.HIGH);
            return false;

        }
    }


    /**
     * Sets credentials in the login screen
     *
     * @param username
     * @param password
     */
    private void enterGameCredentials(String username, String password) {
        bot.getAccessors().getClient().setUsername(username);
        bot.getAccessors().getClient().setPassword(password);
    }

}
