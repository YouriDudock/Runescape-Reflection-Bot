package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.manager.Manager;
import botting.game.GameTile;
import botting.reflection.accessors.client.Client;

import java.awt.*;

/**
 * @author Youri Dudock
 */
public class CalculationManager extends Manager {

    public int[] SINUS = new int[2048];
    public int[] COSINUS = new int[2048];
    public final Rectangle GAME3D_SCREEN = new Rectangle(0, 0, 516, 337);

    public CalculationManager(BotInstance bot) {
        super(bot);

        for (int i = 0; i < 2048; i++) {
            SINUS[i] = (int) (65536.0D * Math.sin(i * 0.0030679615D));
            COSINUS[i] = (int) (65536.0D * Math.cos(i * 0.0030679615D));
        }
    }


    /**
     *
     * @param x
     * @param y
     * @return
     */
    public final int tileHeight(int x, int y) {
        final Client client = bot.getAccessors().getClient();

        int[][][] ground = client.getTileOffsets();
        int zidx = client.getPlane();
        int x1 = x >> 7;
        int y1 = y >> 7;
        int x2 = x & 0x7f;
        int y2 = 0x7f & y;

        if (x1 < 0 || y1 < 0 || x1 > 103 || y1 > 103)
            return 0;

        if (zidx < 3 && (2 & client.getSceneFlags()[1][x1][y1]) == 2)
            zidx++;

        int i = ground[zidx][1 + x1][y1] * x2 + (128 + -x2) * ground[zidx][x1][y1] >> 7;
        int j = ground[zidx][1 + x1][1 + y1] * x2 + ground[zidx][x1][y1 + 1] * (128 - x2) >> 7;

        return j * y2 + (128 - y2) * i >> 7;
    }

    /**
     *
     * @param x
     * @param y
     * @param offsetX
     * @param offsetY
     * @param height
     * @return
     */
    public final Point tileToScreen(int x, int y, final double offsetX, final double offsetY, int height) {
        double worldUnitX = (x + offsetX) * 128.0D;
        double worldUnitY = (y + offsetY) * 128.0D;
        height = tileHeight((int) worldUnitX, (int) worldUnitY) - height;
        return worldToScreen(worldUnitX, worldUnitY, height);
    }

    /**
     * Convert a tile to the point on screen
     * @param tile
     * @return screen location
     */
    public final Point tileToScreen(final GameTile tile) {
        return tileToScreen(tile.getLocalRegionX(), tile.getLocalRegionY(), 0.5D, 0.5D, 0);
    }

    /**
     *
     * @param X
     * @param Y
     * @param height
     * @return
     */
    public final Point worldToScreen(double X, double Y, final double height) {
        if (X < 128 || Y < 128 || X > 13056 || Y > 13056) {
            return new Point(-1, -1);
        }
        try {
            final Client client = bot.getAccessors().getClient();
            int clientZoom = bot.getAccessors().getClient().getClientZoom();

            final int tileCalculation = ((int) height) - client.getCameraZ();
            X -= client.getCameraX();
            final int pitchSinus = SINUS[client.getCameraPitch()];
            final int pitchCosinus = COSINUS[client.getCameraPitch()];
            Y -= client.getCameraY();
            final int yawSinus = SINUS[client.getCameraYaw()];
            final int yawCosinus = COSINUS[client.getCameraYaw()];
            int calculation = pitchSinus * (int) Y + ((int) X * pitchCosinus) >> 16;
            Y = -(pitchSinus * (int) X) + (int) Y * pitchCosinus >> 16;
            X = calculation;
            calculation = yawCosinus * tileCalculation - yawSinus * (int) Y >> 16;
            Y = yawSinus * tileCalculation + ((int) Y * yawCosinus) >> 16;

            final int screenX = 260 + (int)X * clientZoom / (int)Y;
            final int screenY = 167 + calculation * clientZoom / (int)Y;

            // old from parabot
//            final int screenX = ((int) X << 9) / (int) Y + 256;
//            final int screenY = (calculation << 9) / (int) Y + 167;
            return new Point(screenX, screenY);
        } catch (ArithmeticException e){
            // / by zero
        }
        return new Point(-1, -1);
    }

    /**
     * Calculates camera angle to tile
     *
     * @param tile
     * @return camera angle to tile
     */
    public final int angleToTile(GameTile t) {
        GameTile me = bot.getManagers().getPlayer().getLocalPlayer().getLocation();
        int angle = (int) Math.toDegrees(Math.atan2(t.getY() - me.getY(), t.getX() - me.getX()));
        return angle >= 0 ? angle : 360 + angle;
    }

    private final Point worldToMinimap(int x, int y, boolean in) {
        final Client client = bot.getAccessors().getClient();
        int i;

        try{
            i = client.getMinimapViewRotation() + client.getMinimapRotation() & 0x7FF;
        }catch (AbstractMethodError e){
            i = client.getMinimapViewRotation();
        }

        if (in) {
            int j = x * x + y * y;
            if (j > 6400) {
                return new Point(-1, -1);
            }
        }
        int k = SINUS[i];
        int m = COSINUS[i];
        k = k * 256 / (client.getMinimapZoom() + 256);
        m = m * 256 / (client.getMinimapZoom() + 256);
        int n = y * k + x * m >> 16;
        int i1 = y * m - x * k >> 16;
        return new Point( bot.getRSClient().getMinimapCenterX() + n,bot.getRSClient().getMinimapCenterY() -  i1);
    }

    /**
     * Calculates point on minimap
     *
     * @param tile
     * @param in
     *            if true it will return a point only within minimap, false for points out of minimap too
     * @return point in or out of minimap
     */
    public final Point tileToMinimap(GameTile tile, boolean in) {
        final Client client = bot.getAccessors().getClient();
        int x = tile.getX() - client.getBaseX();
        int y = tile.getY() - client.getBaseY();
        int mmX = x * 4 + 2 - client.getLocalPlayer().getX() / 32;
        int mmY = y * 4 + 2 - client.getLocalPlayer().getY() / 32;
        return worldToMinimap(mmX, mmY, in);
    }

    public boolean isOnScreen(Point point) {
        return GAME3D_SCREEN.contains(point);
    }









//    public Point getTileDrawLocation(GameTile gameTile) {
//        // height of item
//        int height = 0;
//
//        // invoke calculate sprite location method
//        new RefClass(getClientInstance())
//                .getMethod(
//                        getHooks().value(ClientHook.METHOD_CLIENT_CALCULATE_ENTITY_POS),
//                        getManagers().getPlayer().getLocalPlayer().getEntityClass(), int.class, int.class, int.class)
//                .invoke(null, (gameTile.getX() << 7) + 64, height, (gameTile.getY() << 7) + 64);
//
//        // see sprite draw location and return point
//        return getLastSpriteDrawnLocation();
//    }
}
