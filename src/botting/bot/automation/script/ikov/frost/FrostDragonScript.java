package botting.bot.automation.script.ikov.frost;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.script.Script;
import botting.game.GameTile;
import botting.game.data.BankWithdraw;
import botting.game.data.GameArea;
import botting.game.data.RSItem;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public class FrostDragonScript extends Script {

    public final GameArea HOME = new GameArea(createTile(3071, 3470), createTile(3117, 3519));
    public final GameArea FROST_DRAGONS = new GameArea(createTile(2815, 3740), createTile(2873, 3789));

    public final GameTile FROST_TELEPORT_TILE = createTile(2867, 9955);
    public final GameTile HOME_TELEPORT_TILE = createTile(3088, 3502);



    public final String TARGET_NAME = "Frost Dragon";

    private final BankWithdraw FOOD_WITHDRAW_AMOUNT = BankWithdraw.WITHDRAW_5;

    private final BankWithdraw RESTORE_WITHDRAW_AMOUNT = BankWithdraw.WITHDRAW_5;

    public final int[] DROPS = {RSItem.FROST_DRAGON_BONES.getID(), RSItem.DRACONIC_VISAGE.getID(), RSItem.LOOP_HALF_OF_A_KEY.getID(), RSItem.TOOTH_HALF_OF_A_KEY.getID()};

    public final int FOOD = 385;

    public final int[] PRAYER_POTS = {3030, 3028, 3026, 3024};

    public final int[] STRENGTH_POTS = {161, 159, 157, 2440};

    public final int[] ATTACK_POTS = {149, 147, 145, 2436};

    public final int WITHDRAW_STRENGTH_POT_ID = 2440;
    public final int WITHDRAW_ATTACK_POT_ID = 2436;


    public final int WITHDRAW_PRAYER_POT_ID = 3024;

    public final int EMPTY_VIAL = 229;

    public final int HITPOINTS_THRESPOINT = 60;
    public final int PRAYER_THRESPOINT = 60;
    public final int COMBAT_POT_THRESPOINT = 115;

    public final Point HOME_TELEPORT_SPELL_POINT = new Point(570, 224);

    public final int REJU_BOX_ID = 16118;

    public final int CAVE_ENTRANCE_ID = 2;


    public int bonesLooted = 0;


    public FrostDragonScript(BotInstance bot) {
        super(bot);

        setAutoLogin(true);
        setDismissCastOn(true);
        setDismissUseItemOnItem(true);
        setDismissRandomInterfaces(true, new int[bot.getRSClient().getBankID()]);

        registerPainting(new FrostPainting());

    }




    public BotTask[] taskList = {
            new EscapeToHomeTask(getBot(), this),
            new ConsumingTask(getBot(), this),
            new LootingTask(getBot(), this),
            new VisitRejuBoxTask(getBot(), this),
            new VisitBankTask(getBot(), this),
            new BankTask(getBot(), this),
            new VisitFrostDragonsTask(getBot(), this),
            new CombatTask(getBot(), this)
    };

    @Override
    public void onStart() {
    }

    @Override
    public void onTick() {
        for (BotTask task : taskList) {
            if (task.shouldRun()) {
                task.execute();
            }
        }

        getAntiBan().sleep(20);
    }

    @Override
    public void onStop() {

    }

    @Override
    public boolean shouldStop() {
        return false;
    }




}
