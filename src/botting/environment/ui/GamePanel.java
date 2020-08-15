package botting.environment.ui;

import javax.naming.Context;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;

/**
 * Main panel where applets are added.
 *
 * @author Everel
 */
public class GamePanel extends JPanel {

    public GamePanel() {
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setOpaque(true);
        setBackground(Color.white);
        GroupLayout panelLayout = new GroupLayout(this);
        setLayout(panelLayout);
        panelLayout.setHorizontalGroup(panelLayout.createParallelGroup(
                GroupLayout.Alignment.LEADING).addGap(0, 770, Short.MAX_VALUE));
        panelLayout.setVerticalGroup(panelLayout.createParallelGroup(
                GroupLayout.Alignment.LEADING).addGap(0, 418, Short.MAX_VALUE));

    }


    /**
     * Updates context of this panel and adds a different Applet to the panel
     *
     * @param c
     */
    public void setContext(final Applet c) {
        add(c);
    }

    /**
     * Removes getNearest components
     */
    public void removeComponents() {
        removeAll();
    }
}
