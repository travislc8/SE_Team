package LobbyManagement;

import Client.GameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BrowseLobbyControl implements ActionListener {
    private final JPanel container;
    private final GameClient gameClient;
    private final BrowseLobbyData browseLobbyData;

    public BrowseLobbyControl(GameClient gameClient, JPanel container) {
        this.container = container;
        this.gameClient = gameClient;
        this.browseLobbyData = new BrowseLobbyData();
    }

    public void updateLobbyList(List<LobbyData> lobbies) {
        browseLobbyData.setAvailableLobbies(new ArrayList<>(lobbies));
        for (Component comp : container.getComponents()) {
            if (comp instanceof BrowseLobbyPanel panel) {
                panel.updateLobbyList(lobbies);
            }
        }
    }

    public void createLobby(String password, boolean isPublic, int timer) {
        try {
            gameClient.sendToServer("CREATE_LOBBY " + password + " " + isPublic + " " + timer);
        } catch (IOException e) {
            displayError("Failed to create lobby.");
        }
    }

    public void joinLobby(LobbyData lobby, String password) {
        try {
            gameClient.sendToServer("JOIN_LOBBY " + lobby.getLobbyId() + " " + password);
        } catch (IOException e) {
            displayError("Failed to join lobby.");
        }
    }

    public void sendTimerUpdate(int lobbyId, int timer) {
        try {
            gameClient.sendToServer("UPDATE_TIMER " + lobbyId + " " + timer);
        } catch (IOException e) {
            displayError("Failed to send timer update.");
        }
    }


    public void displayError(String message) {
        JOptionPane.showMessageDialog(container, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            case "CreateLobby" -> switchToCard("CreateLobby");

            case "JoinLobby" -> handleJoinFromList();

            case "ConfirmJoin" -> handleConfirmJoin();

            case "ConfirmSearchJoin" -> handleConfirmSearchJoin();

            case "CreateConfirm" -> handleCreateConfirm();

            case "BackToMain", "BackToBrowse" -> {
                requestLobbyListUpdate();
                switchToCard("Browse");
            }

            case "Refresh" -> requestLobbyListUpdate();

            case "GoToSearch" -> switchToCard("Search");

            case "SearchOwner" -> handleSearchOwner();

            case "JoinSearchLobby" -> handleJoinFromSearch();
        }
    }

    private void switchToCard(String name) {
        CardLayout cl = (CardLayout) container.getLayout();
        cl.show(container, name);
    }

    private void requestLobbyListUpdate() {
        try {
            gameClient.sendToServer("REQUEST_LOBBY_LIST");
        } catch (IOException e) {
            displayError("Failed to refresh lobby list.");
        }
    }

    private void handleJoinFromList() {
        for (Component comp : container.getComponents()) {
            if (comp instanceof BrowseLobbyPanel panel) {
                LobbyData selected = panel.getSelectedLobby();
                if (selected != null) {
                    if (selected.getPassword() != null && !selected.getPassword().isEmpty()) {
                        panel.showPasswordField(true);
                    } else {
                        joinLobby(selected, "");
                        switchToCard("Lobby");
                    }
                } else {
                    displayError("No lobby selected.");
                }
            }
        }
    }

    private void handleConfirmJoin() {
        for (Component comp : container.getComponents()) {
            if (comp instanceof BrowseLobbyPanel panel) {
                LobbyData selected = panel.getSelectedLobby();
                String inputPassword = panel.getEnteredPassword();

                if (selected != null && selected.getPassword().trim().equals(inputPassword.trim())) {
                    joinLobby(selected, inputPassword);
                    panel.showPasswordField(false);
                    panel.clearPasswordField();
                    switchToCard("Lobby");
                } else {
                    displayError("Incorrect password.");
                }
            }
        }
    }

    private void handleConfirmSearchJoin() {
        for (Component comp : container.getComponents()) {
            if (comp instanceof BrowseLobbyPanel panel) {
                LobbyData selected = panel.getSelectedSearchLobby();
                String inputPassword = panel.getSearchPassword();

                if (selected != null && selected.getPassword().trim().equals(inputPassword.trim())) {
                    joinLobby(selected, inputPassword);
                    panel.showSearchPasswordField(false);
                    panel.clearSearchPasswordField();
                    switchToCard("Lobby");
                } else {
                    displayError("Incorrect password.");
                }
            }
        }
    }

    private void handleCreateConfirm() {
        for (Component comp : container.getComponents()) {
            if (comp instanceof BrowseLobbyPanel panel) {
                String password = panel.getCreatePassword();
                boolean isPublic = panel.isCreatePublic();
                int timer = panel.getCreateTimer();
                createLobby(password, isPublic, timer);
            }
        }
    }

    private void handleSearchOwner() {
        for (Component comp : container.getComponents()) {
            if (comp instanceof BrowseLobbyPanel panel) {
                String search = panel.getSearchText();
                try {
                    gameClient.sendToServer("SEARCH_LOBBIES " + search);
                } catch (IOException e) {
                    displayError("Failed to search lobbies.");
                }
            }
        }
    }

    private void handleJoinFromSearch() {
        for (Component comp : container.getComponents()) {
            if (comp instanceof BrowseLobbyPanel panel) {
                LobbyData selected = panel.getSelectedSearchLobby();
                String inputPassword = panel.getSearchPassword();

                if (selected != null) {
                    if (selected.getPassword() != null && !selected.getPassword().isEmpty()) {
                        if (inputPassword == null || inputPassword.isEmpty()) {
                            panel.showSearchPasswordField(true);
                        } else if (selected.getPassword().trim().equals(inputPassword.trim())) {
                            joinLobby(selected, inputPassword);
                            switchToCard("Lobby");
                        } else {
                            displayError("Incorrect password.");
                        }
                    } else {
                        joinLobby(selected, "");
                        switchToCard("Lobby");
                    }
                }
            }
        }
    }
}
