package botting.reflection.accessors.client;

import botting.reflection.accessors.deque.Deque;
import botting.reflection.accessors.entity.Entity;
import botting.reflection.accessors.npc.NPC;
import botting.reflection.accessors.objects.Scene;
import botting.reflection.accessors.player.Player;
import botting.reflection.accessors.rsinterface.RSInterface;

/**
 * @author Youri Dudock
 */
public interface Client {

    public Player getLocalPlayer();

    public NPC[] getNPCS();

    public Player[] getPlayers();

    public boolean isLoggedIn();

    public int getBaseX();

    public int getBaseY();

    public int getOpenInterfaceID();

    public int getPlane();

    public RSInterface[] getInterfaces();

    public Deque[][][] getGroundItems();

    public void setUsername(String username);

    public void setPassword(String password);

    int getCameraZ();

    int getCameraPitch();

    int getCameraYaw();

    double getCameraX();

    double getCameraY();

    public int[][][] getTileOffsets();

    public byte[][][] getSceneFlags();

    // or world controller
    public Scene getScene();

    public int[] getStats();

    public int getMenuYOffset();

    public boolean hasMenuOpen();

    public int getMenuActionRow();

    int getMenuXOffset();

    int getMenuWidth();

    int getMenuHeight();

    int getLoopCycle();

    int getClientZoom();

    int getMinimapViewRotation();

    int getMinimapRotation();

    int getMinimapZoom();

    int getOpenDialogID();
}
