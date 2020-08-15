package botting.bot;

import botting.ReflectionBot;
import botting.bot.automation.script.ScriptManager;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.bot.manager.ManagerRepository;
import botting.environment.BotSession;
import botting.environment.Environment;
import botting.environment.ui.BotUI;
import botting.reflection.ClassLoader;
import botting.reflection.accessors.AccessorFactory;
import botting.reflection.hooking.HookProvider;
import botting.reflection.modifiers.RefClass;
import botting.rs.client.RSPSClients;
import botting.rs.client.clients.ikov.IkovClient;


/**
 * A single instance of a running bot
 *
 * @author Youri Dudock
 */
public class BotInstance {

    // the selected runescape client
    private final RSPSClients rsClient = new IkovClient();

    private ManagerRepository managers;

    private HookProvider hooks;

    private BotSession session;

    private ScriptManager scriptManager;

    private AccessorFactory accessors;

    private ClassLoader classLoader;

    public void start(BotSession session) {
        try {
            Debugger.write(ReflectionBot.class, "Starting bot instance for " + session.getUsername() + " with selected client: " + rsClient.getClass().getName(), DebugPriority.HIGH);

            // set game credentials
            this.session = session;

            // set accessors
            accessors = new AccessorFactory(this);

            // loading client hooks
            hooks = new HookProvider();
            hooks.loadHooks(rsClient);

            // create the class loader
            classLoader = new ClassLoader();
            classLoader.createClassLoader(rsClient.getJAR());


            // create and setup managers
            managers = new ManagerRepository(this);

            // launch the original client
            launchClient();

            // create new script manager
            scriptManager = new ScriptManager();

            while (getAccessors().getGame().getMainInstance() == null) {
                managers.getAntiBan().sleep(100, false);
            }

            // create our own bot environment with the launched client
            Environment.getInstance().createBotPanel(this);

            Environment.getInstance().createPainter(this);


        } catch (Exception e) {
            e.printStackTrace();
            BotUI.getInstance().showException(e);
        }
    }


    /**
     * Launches the botting.game client
     */
    private void launchClient() {
        Debugger.write(ReflectionBot.class, "Launching client..", DebugPriority.HIGH);
        getAccessors().getGame().main(rsClient.getArgs());
        Debugger.write(ReflectionBot.class, "Client launched.", DebugPriority.HIGH);
    }

    public ManagerRepository getManagers() {
        return managers;
    }

    public HookProvider getHooks() {
        return hooks;
    }

    public BotSession getSession() {
        return session;
    }

    public RSPSClients getRSClient() {
        return rsClient;
    }

    public AccessorFactory getAccessors() {
        return accessors;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}
