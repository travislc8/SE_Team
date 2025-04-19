package LobbyManagement;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class BrowseLobbyPanel extends JPanel {
    private final JList<LobbyData> lobbyList;
    private final DefaultListModel<LobbyData> listModel;
    private final JPasswordField passwordField;
    private final JPanel passwordPanel;

    private final JTextField searchField;
    private final DefaultListModel<LobbyData> searchListModel;
    private final JList<LobbyData> searchResultList;
    private final JPanel searchPasswordPanel;
    private final JPasswordField searchPasswordField;
    private BufferedImage backgroundImage;
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 18);

    Color backgroundBase = new Color(0, 0, 0, 0);
    Color panelBase = new Color(0xC7D8B6);
    Color borderColor = new Color(0x8B7E74);
    Color titleTextColor = new Color(0x2F4F4F);
    Color buttonBase = new Color(0xA8BCA1);
    Color buttonHover = new Color(0xC1D3B3);
    Color labelTextColor = new Color(0x333333);

    JTextField createPasswordField;
    JSpinner timerSpinner;
    JCheckBox publicBox;
    JButton createConfirmButton;

    public BrowseLobbyPanel(ActionListener listener) {
        setLayout(new BorderLayout());
        setOpaque(false);

        try {
            URL imageUrl = getClass().getResource("/src/LobbyManagement/creatingLobby.jpg");
            if (imageUrl != null) backgroundImage = ImageIO.read(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel leftPanel = createStyledPanel("Available Lobbies");
        listModel = new DefaultListModel<>();
        lobbyList = new JList<>(listModel);
        lobbyList.setFont(TITLE_FONT);
        JScrollPane lobbyScrollPane = new JScrollPane(lobbyList);


        JPanel lobbyButtonPanel = new JPanel();
        JButton joinButton = createStyledButton("Join Lobby", "JoinLobby", listener);
        JButton refreshButton = createStyledButton("Refresh", "Refresh", listener);
        JButton backButton = createStyledButton("Back", "BackToMain", listener);
        lobbyButtonPanel.add(joinButton);
        lobbyButtonPanel.add(refreshButton);
        lobbyButtonPanel.add(backButton);

        passwordPanel = new JPanel();
        passwordField = new JPasswordField(10);
        passwordField.setFont(TITLE_FONT);
        JButton confirmPasswordButton = createStyledButton("Confirm", "ConfirmJoin", listener);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(TITLE_FONT);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        passwordPanel.add(confirmPasswordButton);
        passwordPanel.setVisible(false);

        leftPanel.add(passwordPanel, BorderLayout.NORTH);
        leftPanel.add(lobbyScrollPane, BorderLayout.CENTER);
        leftPanel.add(lobbyButtonPanel, BorderLayout.SOUTH);

        JPanel searchPanel = createStyledPanel("Search by Username");
        JPanel searchTop = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel searchLabel = new JLabel("Username:");
        searchLabel.setFont(TITLE_FONT);
        searchTop.add(searchLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        searchField = new JTextField(15);
        searchField.setFont(TITLE_FONT);
        searchTop.add(searchField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton searchButton = createStyledButton("Search", "SearchOwner", listener);
        searchTop.add(searchButton, gbc);

        searchPasswordPanel = new JPanel();
        searchPasswordField = new JPasswordField(10);
        searchPasswordField.setFont(TITLE_FONT);
        JButton confirmSearchPasswordButton = createStyledButton("Confirm", "ConfirmSearchJoin", listener);
        JLabel searchPasswordLabel = new JLabel("Password:");
        searchPasswordLabel.setFont(TITLE_FONT);
        searchPasswordPanel.add(searchPasswordLabel);
        searchPasswordPanel.add(searchPasswordField);
        searchPasswordPanel.add(confirmSearchPasswordButton);
        searchPasswordPanel.setVisible(false);

        JPanel searchTopContainer = new JPanel();
        searchTopContainer.setLayout(new BoxLayout(searchTopContainer, BoxLayout.Y_AXIS));
        searchTopContainer.add(searchTop);
        searchTopContainer.add(searchPasswordPanel);

        searchPanel.add(searchTopContainer, BorderLayout.NORTH);

        searchListModel = new DefaultListModel<>();
        searchResultList = new JList<>(searchListModel);
        searchResultList.setFont(TITLE_FONT);
        JScrollPane searchScroll = new JScrollPane(searchResultList);

        searchPanel.add(searchScroll, BorderLayout.CENTER);

        JButton joinSearchButton = createStyledButton("Join Lobby", "JoinSearchLobby", listener);
        JPanel searchBottom = new JPanel();
        searchBottom.add(joinSearchButton);
        searchPanel.add(searchBottom, BorderLayout.SOUTH);

        JPanel createPanel = createStyledPanel("Create New Lobby");
        createPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 10, 5, 10);
        gbc2.anchor = GridBagConstraints.WEST;

        gbc2.gridx = 0; gbc2.gridy = 0;
        JLabel createPasswordLabel = new JLabel("Password:");
        createPasswordLabel.setFont(TITLE_FONT);
        createPanel.add(createPasswordLabel, gbc2);
        gbc2.gridx = 1;
        createPasswordField = new JTextField(10);
        createPasswordField.setFont(TITLE_FONT);
        createPanel.add(createPasswordField, gbc2);

        gbc2.gridx = 0; gbc2.gridy = 1;
        JLabel timerLabel = new JLabel("Timer (min):");
        timerLabel.setFont(TITLE_FONT);
        createPanel.add(timerLabel, gbc2);
        gbc2.gridx = 1;
        timerSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 60, 1));
        timerSpinner.setFont(TITLE_FONT);
        createPanel.add(timerSpinner, gbc2);

        gbc2.gridx = 0; gbc2.gridy = 2;
        JLabel publicLabel = new JLabel("Public Lobby:");
        publicLabel.setFont(TITLE_FONT);
        createPanel.add(publicLabel, gbc2);
        gbc2.gridx = 1;
        publicBox = new JCheckBox();
        publicBox.setFont(TITLE_FONT);
        createPanel.add(publicBox, gbc2);

        gbc2.gridx = 0; gbc2.gridy = 3; gbc2.gridwidth = 2;
        createConfirmButton = createStyledButton("Create Lobby", "CreateConfirm", listener);
        createPanel.add(createConfirmButton, gbc2);

        JPanel leftStackedPanel = new JPanel();
        leftStackedPanel.setLayout(new BoxLayout(leftStackedPanel, BoxLayout.Y_AXIS));
        leftStackedPanel.setOpaque(false);
        leftStackedPanel.add(leftPanel);
        leftStackedPanel.add(Box.createVerticalStrut(10));
        leftStackedPanel.add(searchPanel);
        leftStackedPanel.add(Box.createVerticalStrut(10));
        leftStackedPanel.add(createPanel);

        add(leftStackedPanel, BorderLayout.WEST);
    }

    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(panelBase);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(borderColor);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(), title,
                TitledBorder.LEFT, TitledBorder.TOP,
                TITLE_FONT, titleTextColor));
        return panel;
    }

    private JButton createStyledButton(String text, String command, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setActionCommand(command);
        btn.addActionListener(listener);
        btn.setFont(TITLE_FONT);
        applyHoverEffect(btn);
        return btn;
    }

    private void applyHoverEffect(final JButton button) {
        button.setBackground(buttonBase);
        button.setForeground(labelTextColor);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 1, true));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonHover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonBase);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void updateLobbyList(List<LobbyData> lobbies) {
        listModel.clear();
        for (LobbyData lobby : lobbies) listModel.addElement(lobby);
    }

    public LobbyData getSelectedLobby() {
        return lobbyList.getSelectedValue();
    }

    public LobbyData getSelectedSearchLobby() {
        return searchResultList.getSelectedValue();
    }

    public String getEnteredPassword() {
        return new String(passwordField.getPassword());
    }

    public void showPasswordField(boolean visible) {
        passwordPanel.setVisible(visible);
        revalidate();
        repaint();
    }

    public void clearPasswordField() {
        passwordField.setText("");
    }

    public String getSearchText() {
        return searchField.getText().trim();
    }

    public void updateSearchResults(List<LobbyData> results) {
        searchListModel.clear();
        for (LobbyData lobby : results) searchListModel.addElement(lobby);
    }

    public String getCreatePassword() {
        return createPasswordField.getText();
    }

    public boolean isCreatePublic() {
        return publicBox.isSelected();
    }

    public int getCreateTimer() {
        return (int) timerSpinner.getValue();
    }

    public String getSearchPassword() {
        return new String(searchPasswordField.getPassword());
    }

    public void showSearchPasswordField(boolean visible) {
        searchPasswordPanel.setVisible(visible);
        revalidate();
        repaint();
    }

    public void clearSearchPasswordField() {
        searchPasswordField.setText("");
    }
}
