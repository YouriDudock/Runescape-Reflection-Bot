package botting.reflection.accessors.client;

import botting.bot.BotInstance;
import botting.reflection.ClassLoader;
import botting.reflection.accessors.ClassWrapper;
import botting.reflection.accessors.deque.Deque;
import botting.reflection.accessors.deque.DequeWrapper;
import botting.reflection.accessors.npc.NPC;
import botting.reflection.accessors.npc.NPCWrapper;
import botting.reflection.accessors.objects.Scene;
import botting.reflection.accessors.objects.SceneWrapper;
import botting.reflection.accessors.player.Player;
import botting.reflection.accessors.player.PlayerWrapper;
import botting.reflection.accessors.rsinterface.RSInterface;
import botting.reflection.accessors.rsinterface.RSInterfaceWrapper;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;

import java.sql.Ref;

/**
 * @author Youri Dudock
 */
public class ClientWrapper extends ClassWrapper implements Client {

    @Override
    public Object getAccessor() {
        return getBot().getAccessors().getGame().getClientInstance();
    }

    public ClientWrapper(BotInstance bot) {
        super(bot);
    }

    @Override
    public Player getLocalPlayer() {
        Object player = new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_PLAYER_INSTANCE)).asObject();

        return new PlayerWrapper(getBot(), player);
    }

    @Override
    public NPC[] getNPCS() {
        Object[] npcArray = (Object[]) new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_NPC_ARRAY)).asObject();

        NPC[] npcs = new NPC[npcArray.length];

        for (int i = 0; i < npcs.length; i++) {

            if (npcArray[i] != null) {
                npcs[i] = new NPCWrapper(getBot(), npcArray[i]);
            }

        }

        return npcs;
    }

    @Override
    public Player[] getPlayers() {
        Object[] playerArray = (Object[]) new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_PLAYER_ARRAY)).asObject();

        Player[] players = new Player[playerArray.length];

        for (int i = 0; i < players.length; i++) {
            if (playerArray[i] != null) {
                players[i] = new PlayerWrapper(getBot(), playerArray[i]);
            }
        }

        return players;
    }

    @Override
    public boolean isLoggedIn() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_IS_LOGGED_IN)).asBoolean();
    }

    @Override
    public int getBaseX() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_BASE_X)).asInt();
    }

    @Override
    public int getBaseY() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_BASE_Y)).asInt();
    }

    @Override
    public int getOpenInterfaceID() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_INTERFACE_ID_OPEN)).asInt();

    }

    @Override
    public int getPlane() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_PLANE)).asInt();
    }

    @Override
    public RSInterface[] getInterfaces() {
        Object[] interfaces = (Object[]) new RefClass(getBot().getAccessors().getGame().getRSInterfacesClass())
                .getField(getBot().getHooks()
                        .value(ClientHook.FIELD_RS_INTERFACES_ARRAY))
                .asObject();

        RSInterface[] rsInterfaces = new RSInterface[interfaces.length];

        for (int i = 0; i < interfaces.length; i++) {
            rsInterfaces[i] = new RSInterfaceWrapper(getBot(), interfaces[i]);
        }

        return rsInterfaces;
    }

    @Override
    public Deque[][][] getGroundItems() {
        Object[][][] rawItems = (Object[][][]) new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_GROUND_ITEMS_ARRAY)).asObject();

        Deque[][][] deque = new Deque[rawItems.length][rawItems[0].length][rawItems[0][0].length];

        for (int i = 0; i < rawItems.length; i++) {

            for (int j = 0; j < rawItems[i].length; j++) {

                for (int k = 0; k < rawItems[i][j].length; k++) {

                    if (rawItems[i][j][k] != null) {
                        deque[i][j][k] = new DequeWrapper(getBot(), rawItems[i][j][k]);
                    }
                }

            }

        }


        return deque;
    }

    @Override
    public void setUsername(String username) {
        new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_PLAYER_USERNAME)).setString(username);
    }

    @Override
    public void setPassword(String password) {
        new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_PLAYER_PASSWORD)).setString(password);
    }

    @Override
    public int getCameraZ() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_Z_CAMERA_POS)).asInt();
    }

    @Override
    public int getCameraPitch() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_CAMERA_PITCH)).asInt();
    }

    @Override
    public int getCameraYaw() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_CAMERA_YAW)).asInt();
    }

    @Override
    public double getCameraX() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_X_CAMERA_POS)).asInt();
    }

    @Override
    public double getCameraY() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_Y_CAMERA_POS)).asInt();
    }

    @Override
    public int[][][] getTileOffsets() {
        return (int[][][]) new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_TILE_OFFSETS)).asObject();
    }

    @Override
    public byte[][][] getSceneFlags() {
        return (byte[][][]) new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_SCENE_FLAGS)).asObject();
    }

    @Override
    public Scene getScene() {
        Object scene =  new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_SCENE)).asObject();
        return new SceneWrapper(getBot(), scene);
    }

    @Override
    public int[] getStats() {
        return (int[])new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_SKILL_STAT_ARRAY)).asObject();
    }

    @Override
    public int getMenuXOffset() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_X_MENU_OFFSET)).asInt();
    }

    @Override
    public int getMenuYOffset() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_Y_MENU_OFFSET)).asInt();
    }

    @Override
    public boolean hasMenuOpen() {
        return new RefClass(getBot().getAccessors().getGame().getRightMenuClass()).getField(getBot().getHooks().value(ClientHook.FIELD_HAS_MENU_OPEN)).asBoolean();
    }

    @Override
    public int getMenuActionRow() {
        return new RefClass(getBot().getAccessors().getGame().getRightMenuClass()).getField(getBot().getHooks().value(ClientHook.FIELD_MENU_ACTION_ROW)).asInt();
    }


    @Override
    public int getMenuWidth() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_MENU_WIDTH)).asInt();
    }

    @Override
    public int getMenuHeight() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_MENU_HEIGHT)).asInt();
    }

    @Override
    public int getLoopCycle() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_LOOP_CYCLE)).asInt();
    }

    @Override
    public int getClientZoom() {
        return new RefClass(getBot().getAccessors().getGame().getClientDetailsClass()).getField(getBot().getHooks().value(ClientHook.FIELD_CLIENT_ZOOM)).asInt();
    }

    @Override
    public int getMinimapViewRotation() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_MINIMAP_ROTATION_VIEW)).asInt();
    }

    @Override
    public int getMinimapRotation() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_MINIMAP_ROTATION)).asInt();
    }

    @Override
    public int getMinimapZoom() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_MINIMAP_ZOOM)).asInt();
    }

    @Override
    public int getOpenDialogID() {
        return new RefClass(getAccessor()).getField(getBot().getHooks().value(ClientHook.FIELD_DIALOG_OPEN_ID)).asInt();
    }


}
