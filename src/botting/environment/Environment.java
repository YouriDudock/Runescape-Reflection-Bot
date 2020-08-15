package botting.environment;

import botting.bot.BotInstance;
import botting.bot.automation.script.ScriptManager;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.bot.drawing.PaintArtist;
import botting.environment.ui.BotUI;
import botting.environment.ui.DebugComponent;
import botting.environment.ui.GamePanel;

import javax.swing.*;
import java.applet.Applet;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Youri Dudock
 */
public class Environment {

    private static Environment environment;

    private PaintArtist paintArtist;

    public static Environment getInstance() {
        return environment == null ? environment = new Environment() : environment;
    }

    private ArrayList<BotSession> sessions;

    private ScriptManager scriptManager;

    private Environment() {
        sessions = new ArrayList<>();
        scriptManager = new ScriptManager();
    }

    /**
     * Setups the environment
     */
    public void setup() {
        Debugger.write(getClass(), "Setting up the environment..", DebugPriority.HIGH);

        BotUI.getInstance().setVisible(true);
    }

    public void createPainter(BotInstance bot) {
        this.paintArtist = new PaintArtist(bot);
    }


    /**
     * Creates a new bot panel containing the runescape client
     *
     * @param instance instance of a running bot
     */
    public void createBotPanel(BotInstance instance) {
        GamePanel panel = new GamePanel();

        panel.setContext((Applet) instance.getAccessors().getGame().getClientInstance());

        BotUI.getInstance().getTabs().add(instance.getSession().getUsername(), panel);

        // hide original game client
        JFrame gameClientFrame = (JFrame) instance.getAccessors().getGame().getMainInstance();
        gameClientFrame.setVisible(false);

        Debugger.write(getClass(), "Created new bot panel", DebugPriority.MEDIUM);
    }

    /**
     * Creates a new bot instance
     *
     * @param username ingame username of the player
     * @param password ingame password of the player
     */
    public void createNewBotInstance(String username, String password) {
        Debugger.write(getClass(), "Creating a new bot instance..", DebugPriority.MEDIUM);

        BotInstance instance = new BotInstance();

        // add new session to the environment list
        BotSession session = new BotSession(instance, username, password);
        sessions.add(session);

        // get new thread executor
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // submit bot to thread
        executor.submit(() -> {
            // start bot
            instance.start(session);
        });

    }

    public ScriptManager getScriptManager() {
        return scriptManager;
    }

    public PaintArtist getPainter() {
        return paintArtist;
    }

    public ArrayList<BotSession> getSessions() {
        return sessions;
    }
}
