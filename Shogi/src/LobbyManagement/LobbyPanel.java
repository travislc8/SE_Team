package LobbyManagement;

import Server.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class LobbyPanel extends JPanel {
    private Image backgroundImage;
    private final JLabel ownerLabel;
    private final JLabel opponentLabel;
    private final JCheckBox readyCheckBox;
    private final JButton submitButton;
    private final LobbyControl control;
    private final AtomicInteger dotCount = new AtomicInteger(0);// For counting dots animation


    public LobbyPanel(LobbyControl control) {
        this.control = control;

        // Load background image
        try {
            backgroundImage = ImageIO.read(getClass().getResource("waitingLobby.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Background image failed to load: " + e.getMessage());
            backgroundImage = null;
        }

        setLayout(new GridBagLayout());

        //Rounded Content Panel
        RoundedPanel contentPanel = new RoundedPanel(30, new Color(219, 231, 211, 230));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(30, 50, 30, 50));


        // Title Label
        JLabel titleLabel = new JLabel("Shogi Lobby", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(45, 45, 45));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        contentPanel.add(titleLabel);  // Add it without the glue

        // Ensure the content panel uses GridBagLayout for better management
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(40, 0, 0, 0); // Closer to top
        add(titleLabel, gbc);  // Position titleLabel fixed at the top of the panel


        // Player Panel
        JPanel playersPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        playersPanel.setOpaque(false);
        playersPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        ownerLabel = new JLabel("Owner: ");
        ownerLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        ownerLabel.setForeground(new Color(45, 45, 45));

        opponentLabel = new JLabel("Opponent: Waiting for player...");
        opponentLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        opponentLabel.setForeground(new Color(45, 45, 45));

        // Set a fixed width for opponentLabel to prevent resizing
        opponentLabel.setPreferredSize(new Dimension(300, opponentLabel.getPreferredSize().height));

        playersPanel.add(ownerLabel);
        playersPanel.add(opponentLabel);
        playersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(playersPanel);

        // Ready Panel
        JPanel readyPanel = new JPanel();
        readyPanel.setOpaque(false);
        readyCheckBox = new JCheckBox("I'm ready");
        readyCheckBox.setFont(new Font("Serif", Font.PLAIN, 16));
        readyCheckBox.setBackground(new Color(220, 230, 200));
        readyCheckBox.setFocusPainted(false);
        readyCheckBox.setForeground(new Color(45, 45, 45));
        readyCheckBox.addActionListener(e -> {
            control.setReady(readyCheckBox.isSelected());
            updateSubmitState();
        });
        readyPanel.add(readyCheckBox);
        readyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(readyPanel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        submitButton = createStyledButton("Start", "Submit", control);
        submitButton.setEnabled(false);
        JButton leaveButton = createStyledButton("Leave", "Leave", control);

        buttonPanel.add(submitButton);
        buttonPanel.add(leaveButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());

        // Positioning the content panel
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.anchor = GridBagConstraints.NORTH;
        gbc1.insets = new Insets(40, 0, 0, 0); // Closer to top
        add(contentPanel, gbc1);


        // Initialize typing animation
        startTypingAnimation();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private JButton createStyledButton(String text, String command, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setActionCommand(command);
        btn.addActionListener(listener);
        btn.setFont(new Font("Helvetica", Font.PLAIN, 16));
        btn.setBackground(new Color(102, 153, 102));
        btn.setForeground(Color.WHITE);
        applyHoverEffect(btn);
        return btn;
    }

    private void applyHoverEffect(final JButton button) {
        button.setBorder(BorderFactory.createLineBorder(new Color(80, 130, 80), 1, true));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(120, 170, 120));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(102, 153, 102));
            }
        });
    }

    public void updateLobby(LobbyData lobby) {
        ownerLabel.setText("Owner: " + lobby.getOwner().toString());
        User opponent = lobby.getOpponent();
        if (opponent != null) {
            opponentLabel.setText("Opponent: " + opponent.toString());
        } else {
            opponentLabel.setText("Opponent: Waiting for player...");
        }
        updateSubmitState();
    }

    void updateSubmitState() {
        submitButton.setEnabled(control.bothUsersReady());
    }

    private void startTypingAnimation() {
        Timer typingAnimationTimer = new Timer(500, e -> updateOpponentLabelWithDots());
        typingAnimationTimer.start();
    }

    private void updateOpponentLabelWithDots() {
        int dotCount = this.dotCount.getAndIncrement();
        String text = "Opponent: Waiting for player" + ".".repeat(dotCount % 4); // Add 0-3 dots
        opponentLabel.setText(text);
    }

    public void updateReadyStatus(boolean isReady) {
        readyCheckBox.setSelected(isReady);  // Sync the checkbox with server-side status
        updateSubmitState();                 // Update Start button state based on readiness
    }

    //Inner Class: RoundedPanel
    private class RoundedPanel extends JPanel {
        private final int cornerRadius;
        private final Color backgroundColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.cornerRadius = radius;
            this.backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }
    }
}
