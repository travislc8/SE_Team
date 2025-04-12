package LobbyManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BrowseLobbyPanel extends JPanel {
    private JButton createButton, joinButton;
    private JList<LobbyData> lobbyList;
    private DefaultListModel<LobbyData> listModel;

    public BrowseLobbyPanel(ActionListener listener) {
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        lobbyList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(lobbyList);

        createButton = new JButton("Create Lobby");
        createButton.setActionCommand("CreateLobby");
        createButton.addActionListener(listener);

        joinButton = new JButton("Join Lobby");
        joinButton.setActionCommand("JoinLobby");
        joinButton.addActionListener(listener);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton);
        buttonPanel.add(joinButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setDisplay() {
        this.setVisible(true);
    }

    public void updateLobbyList(java.util.List<LobbyData> lobbies) {
        listModel.clear();
        for (LobbyData lobby : lobbies) {
            listModel.addElement(lobby);
        }
    }

    public LobbyData getSelectedLobby() {
        return lobbyList.getSelectedValue();
    }
}
