package botting.rs.client.clients.ikov;

import botting.rs.client.RSPSClients;

import java.awt.*;
import java.util.ArrayList;

/**
 * The Ikov RSPS client (https://ikov.io/)
 *
 * @author Youri Dudock
 */
public class IkovClient implements RSPSClients {

    @Override
    public String getJAR() {
        return System.getProperty("user.home") + "\\.ikov_cache\\client.jar";
    }

    @Override
    public String getHookFile() {
        return "ikov_hooks.json";
    }

    @Override
    public String[] getArgs() {
        return new String[0];
    }

    @Override
    public ArrayList<Point> getLoginButtonPoints() {
        ArrayList<Point> loginPoints = new ArrayList<>();

        // random X button that needs to be pressed before logging in
        loginPoints.add(new Point(511, 131));

        // actual login button point
        loginPoints.add(new Point(386, 317));

        return loginPoints;
    }

    @Override
    public ArrayList<Point> getLogoutButtonPoints() {
        ArrayList<Point> logoutPoints = new ArrayList<>();

        // random X button that needs to be pressed before logging in
        logoutPoints.add(new Point(754, 7));

        // actual login button point
        logoutPoints.add(new Point(639, 372));

        return logoutPoints;
    }

    @Override
    public Point getHomeTeleportPoint() {
        return new Point(570, 224);
    }

    @Override
    public int getBankID() {
        return 5292;
    }

    @Override
    public int getMinimapCenterX() {
        return 620;
    }

    @Override
    public int getMinimapCenterY() {
        return 82;
    }

    @Override
    public Point getFirstInventoryItem() {
        return new Point(580, 228);
    }

    @Override
    public Point getFirstBankItem() {
        return new Point(49, 120);
    }

    @Override
    public Point bankDepositAllPoint() {
        return new Point(395, 297);
    }
}
