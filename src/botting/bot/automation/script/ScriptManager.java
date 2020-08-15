package botting.bot.automation.script;

import botting.bot.BotInstance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Youri Dudock
 */
public class ScriptManager {

    private Script runningScript;

    public void startScript(Script script) {
        this.runningScript = script;

        // get new thread executor
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            script.start();
        });


    }

    public void stopScript() {
        runningScript.stop();
    }
}
