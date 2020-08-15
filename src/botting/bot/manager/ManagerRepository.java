package botting.bot.manager;

import botting.ReflectionBot;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.bot.manager.impl.*;
import botting.bot.BotInstance;

/**
 * @author Youri Dudock
 */
public class ManagerRepository {

    private AntiBanManager antiBanManager;

    private MouseManager mouseManager;

    private KeyboardManager keyboardManager;

    private InterfaceManager interfaceManager;

    private PlayerManager playerManager;

    private WorldManager worldManager;

    private CalculationManager calculationManager;

    private SessionManager sessionManager;

    private HUDManager hudManager;

    private CameraManager cameraManager;

    private RandomManager randomManager;


    public ManagerRepository(BotInstance bot) {
        setupRepository(bot);
    }

    private void setupRepository(BotInstance bot) {
        Debugger.write(ReflectionBot.class, "Setting up managers..", DebugPriority.HIGH);

        antiBanManager = new AntiBanManager(bot);
        mouseManager = new MouseManager(bot);
        keyboardManager = new KeyboardManager(bot);
        playerManager = new PlayerManager(bot);
        worldManager = new WorldManager(bot);
        calculationManager = new CalculationManager(bot);
        sessionManager = new SessionManager(bot);
        hudManager = new HUDManager(bot);
        interfaceManager = new InterfaceManager(bot);
        cameraManager = new CameraManager(bot);
        randomManager = new RandomManager(bot);


        Debugger.write(ReflectionBot.class, "Managers loaded", DebugPriority.HIGH);
    }



    public AntiBanManager getAntiBan() {
        return antiBanManager;
    }


    public MouseManager getMouse() {
        return mouseManager;
    }

    public KeyboardManager getKeyboard() {
        return keyboardManager;
    }

    public InterfaceManager getInterfaces() {
        return interfaceManager;
    }

    public PlayerManager getPlayer() {
        return playerManager;
    }

    public WorldManager getWorld() {
        return worldManager;
    }

    public CalculationManager getCalculations() {
        return calculationManager;
    }

    public SessionManager getSession() {
        return sessionManager;
    }

    public HUDManager getHud() {
        return hudManager;
    }


    public CameraManager getCameraManager() {
        return cameraManager;
    }


    public RandomManager getRandomManager() {
        return randomManager;
    }
}
