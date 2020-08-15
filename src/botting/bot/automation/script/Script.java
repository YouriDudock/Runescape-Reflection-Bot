package botting.bot.automation.script;

import botting.bot.BotInstance;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.bot.debug.DebuggerCaller;
import botting.bot.drawing.ScriptPainting;
import botting.bot.manager.impl.*;
import botting.game.GameTile;
import botting.game.entity.GamePlayer;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Youri Dudock
 */
public abstract class Script implements DebuggerCaller {

    public abstract void onStart();

    public abstract void onTick();

    public abstract void onStop();

    public abstract boolean shouldStop();

    private boolean isRunning = false;

    private boolean shouldAutoLogin = false;
    private boolean shouldDismissCastOn = false;
    private boolean shouldDismissUseItemOnItem = false;
    private boolean shouldDismissRandomInterfaces = false;
    private int[] excludedIntefaces;

    private long startTime;

    private ScriptPainting painting;



    public void start() {
        if (isRunning) {
            Debugger.write(getClass(), "This script is already running.", DebugPriority.HIGH);
            return;
        }

        onStart();

        isRunning = true;

        if (bot.getManagers().getSession().isLoggedIn()) {
            bot.getManagers().getHud().setupHub();
        }

        startTime = 0;
        Debugger.write(getClass(), "Script started.", DebugPriority.HIGH);

        startPainting();

        process();
    }

    public void process() {
        while (isRunning) {
            try {

            if (shouldStop()) {
                isRunning = false;
                Debugger.write(getClass(), "Script stopped because shouldStop() condition was met.", DebugPriority.HIGH);
                break;
            }

            if (!bot.getManagers().getSession().isLoggedIn()) {

                if (shouldAutoLogin) {
                    bot.getManagers().getSession().login();
                    bot.getManagers().getHud().setupHub();
                } else {
                    Debugger.write(getClass(), "Script stopped because player is logged out and AUTO LOGIN is not enabled.", DebugPriority.HIGH);
                    isRunning = false;
                    break;
                }


            } else {

                if (shouldDismissCastOn) {
                    if (getRandoms().isCastingOn()) {
                        getRandoms().solveCastingOn();
                    }
                }

                if (shouldDismissUseItemOnItem) {
                    if (getRandoms().isUsingItemOnItem()) {
                        getRandoms().solveUseItemOnItem();
                    }
                }

                if (shouldDismissRandomInterfaces) {
                    if (getRandoms().hasRandomInterfaceOpen(excludedIntefaces)) {
                        getInterfaces().closeInterface();
                    }
                }

                // check for open talking dialogs
                if (getDialog().hasTalkingOpen()) {
                    // continue dialog
                    getDialog().clickContinue();

                    // reloop as there may be more
                    continue;
                }

                onTick();

            }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public final String getTimeRunning(){
        long s = startTime / 1000, m = s / 60, h = m / 60;
        s %= 60; m %= 60; h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }


    public void registerPainting(final ScriptPainting painting) {
        debug("Painting registerd.", DebugPriority.LOW);
        this.painting = painting;
    }

    private void startPainting() {
        if (painting != null) {
            // get new thread executor
            ExecutorService executor = Executors.newSingleThreadExecutor();

            executor.submit(() -> {
                Graphics g = bot.getAccessors().getGame().getApplet().getGraphics();

                while (isRunning) {
                    painting.draw(g, this);
                    getAntiBan().sleep(20, false);
                }


            });
        }


    }

    public void setAutoLogin(boolean enable) {
        this.shouldAutoLogin = enable;
    }

    public void setDismissCastOn(boolean enable) {
        this.shouldDismissCastOn = enable;
    }

    public void setDismissUseItemOnItem(boolean enable) {
        this.shouldDismissUseItemOnItem = enable;
    }

    public void setDismissRandomInterfaces(boolean enable, int... excludedInterface) {
        this.excludedIntefaces = excludedInterface;
        this.shouldDismissRandomInterfaces = enable;
    }


    public GameTile createTile(int x, int y) {
        return new GameTile(bot, x, y);
    }

    public void stop() {
        Debugger.write(getClass(), "Script has been stopped.", DebugPriority.HIGH);
        isRunning = false;
    }

    private BotInstance bot;

    public Script(BotInstance bot) {
        this.bot = bot;
    }


    public boolean isRunning() {
        return isRunning;
    }



    public AntiBanManager getAntiBan() {
        return bot.getManagers().getAntiBan();
    }

    public PlayerManager getPlayer() {
        return bot.getManagers().getPlayer();
    }

    public InterfaceManager getInterfaces() {
        return bot.getManagers().getInterfaces();
    }

    public GamePlayer getLocalPlayer() {
        return getPlayer().getLocalPlayer();
    }

    public HUDManager getHUD() {
        return bot.getManagers().getHud();
    }

    public WorldManager getWorld() {return bot.getManagers().getWorld(); }

    public MouseManager getMouse() {return bot.getManagers().getMouse(); }

    public MenuManager getMenu() { return bot.getManagers().getInterfaces().getMenu();}

    public KeyboardManager getKeyboard() {return bot.getManagers().getKeyboard(); }

    public SessionManager getSession() {return bot.getManagers().getSession(); }

    public CameraManager getCamera() {return bot.getManagers().getCameraManager(); }

    public BankManager getBank() { return bot.getManagers().getInterfaces().getBank(); }

    public CalculationManager getCalculuations() { return bot.getManagers().getCalculations(); }

    public RandomManager getRandoms() {
        return bot.getManagers().getRandomManager();
    }

    public BotInstance getBot() {
        return bot;
    }

    @Override
    public void debug(String log) {
        debug(log, DebugPriority.HIGH);
    }

    @Override
    public void debug(String log, DebugPriority priority) {
        Debugger.write(getClass(), log, priority);
    }

    public NPCManager getNPCS() {
        return bot.getManagers().getWorld().getNPCManager();
    }

    public ObjectManager getObjects() {
        return bot.getManagers().getWorld().getObjectManager();
    }

    public SkillManager getSkills() {
        return bot.getManagers().getPlayer().getSkills();
    }

    public DialogManager getDialog() { return bot.getManagers().getInterfaces().getDialog(); }
}

