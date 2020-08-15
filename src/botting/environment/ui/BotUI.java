package botting.environment.ui;

import botting.bot.automation.script.ikov.TestScript;
import botting.bot.automation.script.ikov.dung.DungScript;
import botting.bot.automation.script.ikov.frost.FrostDragonScript;
import botting.bot.automation.script.ikov.merch.MerchScript;
import botting.bot.automation.script.ikov.rockcrabs.RockCrabsScript;
import botting.bot.debug.Debugger;
import botting.bot.debug.screen.*;
import botting.environment.Environment;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * The JFrame UI of the bot
 *
 * @author Youri Dudock
 */
public class BotUI extends JFrame {

    private static BotUI instance;

    private GamePanel gamePanel;

    private JTabbedPane tabs;

    private BotUI() {
        setResizable(false);
        setSize(780, 800);
        createMenuBar();
        createTabbing();

        this.setTitle("Prime Bot - Ikov");
    }

    private void createTabbing() {
        tabs =new JTabbedPane();
        tabs.setBounds(500,500,200,200);
        add(tabs);
    }

    private void createMenuBar() {
        JMenuBar menubar = new JMenuBar();

        JMenu bot = new JMenu("Bot");
        JMenu script = new JMenu("Script");
        JMenu debug = new JMenu("Debug");

        JMenuItem createBot = new JMenuItem("Start New Bot");

        if (Debugger.isDebugging) {
            Environment.getInstance().createNewBotInstance("DEBUG", "");
        }

        createBot.addActionListener(e -> {
                GameCredentialsPanel credPanel = new GameCredentialsPanel();

                int result = JOptionPane.showConfirmDialog(null, credPanel,
                        credPanel.getTitle(), JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    Environment.getInstance().createNewBotInstance(credPanel.getUsername(), credPanel.getPassword());
                }
        });

        JMenuItem startScript = new JMenuItem("Start Script");

        startScript.addActionListener(e -> {
            Environment.getInstance().getScriptManager().startScript(new RockCrabsScript(Environment.getInstance().getSessions().get(0).getBotInstance()));
            //Environment.getInstance().getScriptManager().startScript(new MerchScript(Environment.getInstance().getSessions().get(0).getBotInstance()));

        });

        JMenuItem stopScript = new JMenuItem("Stop Script");

        stopScript.addActionListener(e -> Environment.getInstance().getScriptManager().stopScript());


        JMenuItem dNpcs = new JMenuItem("NPC");
        dNpcs.addActionListener(e -> {
            Environment.getInstance().getPainter().toggle(NPCPainter.class);

        });

        JMenuItem dObjects = new JMenuItem("Object");
        dObjects.addActionListener(e -> {
            Environment.getInstance().getPainter().toggle(ObjectPainer.class);

        });

        JMenuItem dMouse = new JMenuItem("Mouse");
        dMouse.addActionListener(e -> {
            Environment.getInstance().getPainter().toggle(MousePainer.class);

        });

        JMenuItem dInventory = new JMenuItem("Inventory");
        dInventory.addActionListener(e -> {
            Environment.getInstance().getPainter().toggle(InventoryPainter.class);

        });

        JMenuItem dBank = new JMenuItem("Bank");
        dBank.addActionListener(e -> {
            Environment.getInstance().getPainter().toggle(BankPainter.class);

        });

        JMenuItem dInterface = new JMenuItem("Interface");
        dInterface.addActionListener(e -> {
            Environment.getInstance().getPainter().toggle(InterfacePainter.class);

        });



        bot.add(createBot);

        script.add(startScript);
        script.add(stopScript);

        debug.add(dNpcs);
        debug.add(dObjects);
        debug.add(dMouse);
        debug.add(dInventory);
        debug.add(dBank);
        debug.add(dInterface);

        menubar.add(bot);
        menubar.add(script);
        menubar.add(debug);

        setJMenuBar(menubar);
    }

    /**
     * Gets instance of this Bot UI
     *
     * @return instance of this bot ui
     */
    public static BotUI getInstance() {
        return instance == null ? instance = new BotUI() : instance;
    }

    public void showException(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();

        JOptionPane.showMessageDialog(this, exceptionAsString);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public JTabbedPane getTabs() {
        return tabs;
    }

}
