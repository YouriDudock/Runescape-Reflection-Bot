package botting.bot.automation.script.ikov;

import botting.bot.BotInstance;
import botting.bot.automation.framework.task.BotTask;
import botting.bot.automation.script.Script;
import botting.bot.automation.script.ikov.smithing.BankTask;
import botting.bot.automation.script.ikov.smithing.TeleportTask;
import botting.bot.automation.script.ikov.smithing.VisitAnvilTask;
import botting.bot.automation.script.ikov.smithing.VisitBankTask;
import botting.game.GameTile;
import botting.game.data.GameArea;
import botting.game.data.RSItem;
import java.awt.Point;

public class PracticeScript extends Script {
    public final GameArea SMITHING_AREA = new GameArea(createTile(3180,3420), createTile(3191,3439));
    public final GameTile SMITHING_TELEPORT_TILE = createTile(3187, 3425);
    public final Point SMITHING_TELEPORT_SPELL_POINT = new Point(570, 345);
    public final int[] SMIHTINGPRODUCTS;
    public final int BARS;
    public final int HAMMER;
    public final int REJU_BOX_ID;
    public final int ANVIL_ID;
    public BotTask[] taskList;

    public PracticeScript(BotInstance bot) {
        super(bot);
        this.SMIHTINGPRODUCTS = new int[]{1127, 1123, 1121, 1119, 1115, 1117, 1205};
        this.BARS = 2363;
        this.HAMMER = 2347;
        this.REJU_BOX_ID = 16118;
        this.ANVIL_ID = 2783;
        this.taskList = new BotTask[]{new TeleportTask(this.getBot(), this), new VisitBankTask(this.getBot(), this), new BankTask(this.getBot(), this), new VisitAnvilTask(this.getBot(), this)};
    }

    public void onStart() {
    }

    public void onTick() {
        BotTask[] var1 = this.taskList;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            BotTask task = var1[var3];
            if (task.shouldRun()) {
                task.execute();
            }
        }

        this.getAntiBan().sleep(20);
    }

    public void onStop() {
    }

    public boolean shouldStop() {
        return false;
    }
}