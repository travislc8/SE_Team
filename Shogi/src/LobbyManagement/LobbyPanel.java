package LobbyManagement;

import javax.swing.*;
import java.awt.event.ActionListener;

public class LobbyPanel extends JPanel {
    private JButton startButton, leaveButton, optionsButton;

    public LobbyPanel(ActionListener listener) {
        startButton = new JButton("Start");
        startButton.setActionCommand("Start");
        startButton.addActionListener(listener);

        leaveButton = new JButton("Leave");
        leaveButton.setActionCommand("Leave");
        leaveButton.addActionListener(listener);

        optionsButton = new JButton("Options");
        optionsButton.setActionCommand("Options");
        optionsButton.addActionListener(listener);

        add(startButton);
        add(leaveButton);
        add(optionsButton);
    }

    public void setDisplay() {
        this.setVisible(true);
    }
}
