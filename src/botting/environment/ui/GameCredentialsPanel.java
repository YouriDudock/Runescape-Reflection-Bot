package botting.environment.ui;

import javax.swing.*;

/**
 * @author Youri Dudock
 */
public class GameCredentialsPanel extends JPanel {

    private final String title = "Please enter your ingame credentials";

    private JTextField usernameField, passwordField;

    public GameCredentialsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        usernameField = new JTextField();
        passwordField = new JTextField();

        usernameField.setSize(200, 50);
        passwordField.setSize(200, 50);

        add (new JLabel("Username"));
        add(usernameField);

        add (new JLabel("Password"));
        add(passwordField);
    }


    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }
}
