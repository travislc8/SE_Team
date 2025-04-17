package Server;

import java.awt.*;

import javax.swing.*;

import Database.GameDatabase;
import UserManagement.CreateAccountData;
import UserManagement.LoginData;
import LobbyManagement.BrowseLobbyData;
import LobbyManagement.LobbyData;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class GameServer extends AbstractServer
{
  // Data fields for this chat server.
  private JTextArea log;
  private JLabel status;
  private boolean running = false;
  private GameDatabase database = new GameDatabase();
  private List<LobbyData> currentLobbies = new ArrayList<>();
  private int lobbyId;

  // Constructor for initializing the server with default settings.
  public GameServer()
  {
    super(12345);
    this.setTimeout(500);
  }

  // Getter that returns whether the server is currently running.
  public boolean isRunning()
  {
    return running;
  }

  public void setDatabase(GameDatabase database)
  {
    this.database = database;
  }

  // When a message is received from a client, handle it.
  public void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
  {
    // If we received LoginData, verify the account information.
    if (arg0 instanceof LoginData)
    {
      // Check the username and password with the database.
      LoginData data = (LoginData)arg0;
      Object result;
      if (database.verifyUser(data.getUsername(), data.getPassword()))
      {
        result = "LoginSuccessful";
        //log.append("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
      }
      else
      {
        result = new Error("The username and password are incorrect.");
        //log.append("Client " + arg1.getId() + " failed to log in\n");
      }

      // Send the result to the client.
      try
      {
        arg1.sendToClient(result);
      }
      catch (IOException e)
      {
        return;
      }
    }

    // If we received CreateAccountData, create a new account.
    else if (arg0 instanceof CreateAccountData)
    {
      // Try to create the account.
      CreateAccountData data = (CreateAccountData)arg0;
      Object result;
      if (!database.usernameExists(data.getUsername()) &&
              database.createUser(data.getUsername(), data.getPassword()))
      {
        result = "CreateAccountSuccessful";
        //log.append("Client " + arg1.getId() + " created a new account called " + data.getUsername() + "\n");
      }
      else
      {
        result = new Error("This username is already in use");
        //log.append("Client " + arg1.getId() + " failed to create a new account\n");
      }

      // Send the result to the client.
      try
      {
        arg1.sendToClient(result);
      }
      catch (IOException e)
      {
        return;
      }
    }
    else if(arg0 instanceof String msg)
    {
      if(msg.equals("CREATE_LOBBY"))
      {
        User owner = (User) arg1.getInfo("user");
        int newlobbyId = lobbyId++;
        LobbyData newLobby = new LobbyData(newlobbyId, owner, 120, true);
        currentLobbies.add(newLobby);

        try {
          arg1.sendToClient(newLobby);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        //sendBrowseLobbyUpdate();

      }
    }
    else if(arg0 instanceof String msg)
    {
      if(msg.equals("JOIN_LOBBY"))
      {
        User joiningUser = (User) arg1.getInfo("user");
        for (LobbyData lobby : currentLobbies) {
          if (lobby.getLobbyId() == lobbyId) {
            if (lobby.getOpponent() == null) {
              lobby.setOpponent(joiningUser);
              try {
                arg1.sendToClient(lobby);
              } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            } else {
              try {
                arg1.sendToClient(new Error("BrowseLobby Lobby is already full."));
              } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            }
            return;
          }
        }

        try {
          arg1.sendToClient(new Error("BrowseLobby Lobby not found."));
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }

  // Method that handles listening exceptions by displaying exception information.
  public void listeningException(Throwable exception)
  {
    running = false;
    //status.setText("Exception occurred while listening");
    //status.setForeground(Color.RED);
    //log.append("Listening exception: " + exception.getMessage() + "\n");
    //log.append("Press Listen to restart server\n");
  }
}

