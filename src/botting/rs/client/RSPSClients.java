package botting.rs.client;

import java.awt.*;
import java.util.ArrayList;

/**
 * Client configuration that differs per RSPS
 * @TODO better config storage for some of the data points in this interface
 *
 * @author Youri Dudock
 */
public interface RSPSClients {

    public String getJAR();

    public String getHookFile();

    public String[] getArgs();

    public ArrayList<Point> getLoginButtonPoints();

    public ArrayList<Point> getLogoutButtonPoints();

    public Point getHomeTeleportPoint();

    public int getBankID();

    public int getMinimapCenterX();

    public int getMinimapCenterY();

    public Point getFirstInventoryItem();

    public Point getFirstBankItem();

    public Point bankDepositAllPoint();
}
