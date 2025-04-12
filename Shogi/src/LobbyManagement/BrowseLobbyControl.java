package LobbyManagement;

import Client.GameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BrowseLobbyControl implements ActionListener {
    private BrowseLobbyData browseLobbyData;
    private LobbyPanel lobbyPanel;
    private JPanel container;
    private GameClient gameClient;

    public BrowseLobbyControl(GameClient gameClient, JPanel container) {
        this.gameClient = gameClient;
        this.container = container;
        this.browseLobbyData = new BrowseLobbyData();
    }

    public void createLobbyPressed() {
        // Send request to server to create a lobby
        gameClient.handleMessageFromServer("CREATE_LOBBY");

    }

    public void joinLobby(LobbyData selectedLobby) {
        // Send request to join the selected lobby
        gameClient.handleMessageFromServer("JOIN_LOBBY " + selectedLobby.getLobbyId());

    }

    public void addBrowseLobbyPanel(BrowseLobbyPanel panel) {
        container.add(panel, "3");
    }

    public void displayError(String message) {
        JOptionPane.showMessageDialog(container, message, "Browse Lobby Error", JOptionPane.ERROR_MESSAGE);
    }

    public void updateLobbyList(java.util.List<LobbyData> lobbies) {
        browseLobbyData.setAvailableLobbies(new ArrayList<>(lobbies));
        for (Component comp : container.getComponents()) {
            if (comp instanceof BrowseLobbyPanel browseLobbyPanel) {
                browseLobbyPanel.updateLobbyList(lobbies);
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("CreateLobby")) {
            createLobbyPressed();
        } else if (command.equals("JoinLobby")) {
            // Access the panel to get selected lobby
            for (Component comp : container.getComponents()) {
                if (comp instanceof BrowseLobbyPanel browseLobbyPanel) {
                    LobbyData selected = browseLobbyPanel.getSelectedLobby();
                    if (selected != null) {
                        joinLobby(selected);
                    } else {
                        JOptionPane.showMessageDialog(container, "No lobby selected.");
                    }
                    break;
                }
            }
        }
    }
}
