package LobbyManagement;

import Client.GameClient;
import Server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LobbyControl implements ActionListener {
    private boolean inLobby = false;
    private JPanel container;
    private GameClient gameClient;
    private LobbyData currentLobby;
    private User currentUser;
    private LobbyPanel lobbyPanel;
    public boolean hasOpponent;

    public boolean hasOpponent() {
    	if (currentLobby != null &&  currentLobby.getOpponent() != null) {
    		return true;
    	} else  {
    		return false;
    	}
    }
    
    public LobbyControl(GameClient gameClient, JPanel container) {
        this.gameClient = gameClient;
        this.container = container;
    }

    public void setLobby(LobbyData lobby, User user) {
        this.currentLobby = lobby;
        this.currentUser = user;
        this.inLobby = true;

        for (Component comp : container.getComponents()) {
            if (comp instanceof LobbyPanel panel) {
                this.lobbyPanel = panel;
                panel.updateLobby(lobby);
            }
        }
        display();
    }

    public void setReady(boolean isReady) {
        if (currentLobby != null && currentUser != null) {
            try {
                if (currentUser.equals(currentLobby.getOwner())) {
                    currentLobby.setOwnerReady(isReady);  // Update owner's readiness status
                } else if (currentUser.equals(currentLobby.getOpponent())) {
                    currentLobby.setOpponentReady(isReady);  // Update opponent's readiness status
                }

                // Send the updated ready status to the server
                gameClient.sendToServer("SET_READY " + currentLobby.getLobbyId() + " " + currentUser.getUsername() + " " + isReady);

                // Update the UI (lobbyPanel) with the new ready status
                if (lobbyPanel != null) {
                    lobbyPanel.updateReadyStatus(isReady);
                }

                // Enable or disable the start button based on both players' readiness
                if (lobbyPanel != null) {
                    lobbyPanel.updateSubmitState();  // Ensure the button reflects the current state

                }
            } catch (IOException e) {
                e.printStackTrace();
                displayMessage("Failed to update ready status.");
            }
        }
    }



    public void startGame() {
        if (currentLobby != null && currentUser != null && lobbyPanel != null) {
            if (bothUsersReady()) {

                try {
                    gameClient.sendToServer("START_GAME " + currentLobby.getLobbyId() + " " + currentUser.getUsername()
                            + " " + currentLobby.getGameTimerLength());
                } catch (IOException e) {
                    e.printStackTrace();
                    displayMessage("Failed to start game.");
                }
            } else {
                displayMessage("Both users must be ready before starting the game.");
            }
        }
    }


    public void leaveLobby() {
        if (currentLobby != null && currentUser != null) {
            try {
                gameClient.sendToServer("LEAVE_LOBBY " + currentLobby.getLobbyId() + " " + currentUser.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
                displayMessage("Failed to leave lobby.");
            }
        }
        inLobby = false;
        currentLobby = null;
        ((CardLayout) container.getLayout()).show(container, "BrowseLobby");
    }

    public void updateLobby(LobbyData updatedLobby) {
        this.currentLobby = updatedLobby;
        if (lobbyPanel != null) {
            lobbyPanel.updateLobby(updatedLobby);
        }
    }

    public void display() {
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "Lobby");
    }

    public void lobbyCreated(LobbyData data) {
        setLobby(data, data.getCurrentUser());
        display();
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(container, message, "Lobby Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean bothUsersReady() {
        if (currentLobby == null || currentLobby.getOwner() == null || currentLobby.getOpponent() == null)
            return false;

        return currentLobby.isOwnerReady() && currentLobby.isOpponentReady();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Start":
                if (bothUsersReady()) {
                    startGame();
                } else {
                    displayMessage("Both users must be ready before starting the game.");
                }
                break;

            case "Leave":
                leaveLobby();
                break;

            case "Ready":
                setReady(true);
                break;

            case "NotReady":
                setReady(false);
                break;
        }
    }

}
