package botting.reflection.hooking;

import botting.ReflectionBot;
import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;
import botting.environment.ui.BotUI;
import botting.rs.client.RSPSClients;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hook provider for a client
 *
 * @author Youri Dudock
 */
public class HookProvider {

    // map which contains getNearest the client hooks
    private HashMap<ClientHook, String> hooks = new HashMap<>();

    /**
     * Loads hooks from a config file to a map
     *
     * @param client the selected client to load hooks from
     */
    public void loadHooks(RSPSClients client) {
        try {
            Debugger.write(ReflectionBot.class, "Setting up hooks..", DebugPriority.HIGH);

            // read the hook json
            FileReader reader = new FileReader(client.getHookFile());

            // get hooks from JSON as HookConfig list
            ArrayList<HookConfig> hooks = new Gson().fromJson(reader, new TypeToken<List<HookConfig>>(){}.getType());

            // set hooks in map
            for (HookConfig hook : hooks) {
                this.hooks.put(hook.hook, hook.value);
            }


            // check if getNearest hooks in the config file are present
            for (ClientHook clientHook : ClientHook.values()) {
                boolean hasHook = false;

                for (HookConfig hook : hooks) {

                    if (hook.hook == null) {
                        Debugger.write(getClass(), "Invalid Hook with value: " + hook.value, DebugPriority.HIGH);

                    }
                    // check for match
                    if (hook.hook.equals(clientHook)) {
                        // found correct hook
                        hasHook = true;
                    }
                }
            }


            Debugger.write(ReflectionBot.class, "Hooks loaded.", DebugPriority.HIGH);

        } catch (Exception e) {
            BotUI.getInstance().showException(e);
            e.printStackTrace();
        }
    }

    public String value(ClientHook hook) {
        return hooks.get(hook);
    }

}
