package Client;
import java.awt.*;
import javax.swing.*;

import UserManagement.CreateAccountPanel;
import UserManagement.LoginPanel;
import ocsf.client.AbstractClient;

import java.awt.event.*;

public class InitialControl implements ActionListener
{
  // Private data field for storing the container.
  private JPanel container;
  private GameClient client;
  // Constructor for the initial controller.
  public InitialControl(JPanel container, GameClient client)
  {
    this.container = container;
    this.client = client;
  }
  
  // Handle button clicks.
  public void actionPerformed(ActionEvent ae)
  {
    // Get the name of the button clicked.
    String command = ae.getActionCommand();
    
    // The Login button takes the user to the login panel.
    if (command.equals("Login"))
    {
      LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
      loginPanel.setError("");
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "2");
     
    }
    
    // The Create button takes the user to the create account panel.
    else if (command.equals("Create"))
    {
      CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
      createAccountPanel.setError("");
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "3");
    }
  }
}
