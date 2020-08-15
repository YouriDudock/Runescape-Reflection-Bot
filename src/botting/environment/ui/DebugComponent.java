package botting.environment.ui;

import javax.swing.*;

/**
 * @author Youri Dudock
 */
public class DebugComponent extends JTextArea {

    public void addLog(Class caller, String log) {
        append("["+ caller.getName()+"] " + log);
    }

}
