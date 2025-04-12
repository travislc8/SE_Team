package LobbyManagement;

import Client.GameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LobbyControl implements ActionListener {
    private boolean inLobby = false;
    private JPanel container;
    private GameClient gameClient;

    public LobbyControl(GameClient gameClient, JPanel container) {
        this.gameClient = gameClient;
        this.container = container;
    }

    public void startGame() {
        // Placeholder: logic to start the game
        System.out.println("Starting game...");
    }

    public void leaveLobby() {
        inLobby = false;
        System.out.println("Leaving lobby...");
    }

    public void setGameOptions() {
        // Placeholder: update options
        System.out.println("Setting game options...");
    }

    public void updateLobby() {
        // Placeholder: update UI or lobby data
        System.out.println("Updating lobby info...");
    }

    public void display() {
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "4"); // Show lobby panel
    }

    public void lobbyCreated(LobbyData data) {
        // Potentially store lobby data, and show lobby screen
        System.out.println("Lobby created: " + data.getLobbyId());
        display();
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(container, message, "Lobby Message", JOptionPane.INFORMATION_MESSAGE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button events from LobbyPanel
        String command = e.getActionCommand();
        switch (command) {
            case "Start":
                startGame();
                break;
            case "Leave":
                leaveLobby();
                break;
            case "Options":
                setGameOptions();
                break;
        }
    }
}
