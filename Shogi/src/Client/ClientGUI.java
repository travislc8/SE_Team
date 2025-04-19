package Client;

import javax.swing.*;

import GameLogic.GameControl;
import GameLogic.GamePanel;
import LobbyManagement.BrowseLobbyControl;
import LobbyManagement.BrowseLobbyPanel;
import LobbyManagement.LobbyControl;
import LobbyManagement.LobbyPanel;
import UserManagement.CreateAccountControl;
import UserManagement.CreateAccountPanel;
import UserManagement.LoginControl;
import UserManagement.LoginPanel;

import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame
{
  public ClientGUI()
  {
    GameClient client = new GameClient();
    try{
      client.openConnection();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setTitle("Game Client");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Card layout container
    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);

    // Create control classes
    InitialControl ic = new InitialControl(container, client);
    LoginControl lc = new LoginControl(container, client);
    CreateAccountControl cac = new CreateAccountControl(container, client);
    LobbyControl loc = new LobbyControl(client, container);
    BrowseLobbyControl blc = new BrowseLobbyControl(client, container);
    GameControl gc = new GameControl(container, client);
    
    // Create UI panels
    JPanel view1 = new InitialPanel(ic);
    JPanel view2 = new LoginPanel(lc);
    JPanel view3 = new CreateAccountPanel(cac);
    JPanel view4 = new LobbyPanel(loc);
    JPanel view5 = new BrowseLobbyPanel(blc);
    JPanel view6 = new GamePanel(gc);

    // Register controllers with the client
    client.addLoginControl(lc);
    client.addCreateAccountControl(cac);
    client.addLobbyControl(loc);
    client.addBrowseLobbyControl(blc);
    client.addGameControl(gc);

    // Add views to container
    container.add(view1, "1");
    container.add(view2, "2");
    container.add(view3, "3");
    container.add(view4, "4");
    container.add(view5, "5");
    container.add(view6, "6");

    // Show initial view
    cardLayout.show(container, "1");

    // JFrame setup
    this.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 1;
	c.weighty = 1;
    this.add(container, c);
    this.setSize(1000, 750);
    this.setVisible(true);
    
  }

  public static void main(String[] args)
  {
    new ClientGUI();
  }
}
