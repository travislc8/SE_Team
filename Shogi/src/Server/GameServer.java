package Server;

import java.awt.*;

import javax.swing.*;

import Database.GameDatabase;
import UserManagement.CreateAccountData;
import UserManagement.LoginData;
import LobbyManagement.BrowseLobbyData;
import LobbyManagement.LobbyData;
import GameLogic.Game;
import GameLogic.GameData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
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
  private Map<Integer, Game> activeGames = new HashMap<>();

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
        arg1.setInfo("lobbyId", newlobbyId);

        try 
        {
          arg1.sendToClient(newLobby);
        } 
        catch (IOException e) 
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        //sendBrowseLobbyUpdate();

      }
      else if(msg.startsWith("JOIN_LOBBY "))
      {
    	int joinLobbyId = Integer.parseInt(msg.split(" ")[1]);
    	User joiningUser = (User) arg1.getInfo("user");
        for (LobbyData lobby : currentLobbies) 
        {
          if (lobby.getLobbyId() == joinLobbyId) 
          {
            if (lobby.getOpponent() == null) 
            {
              lobby.setOpponent(joiningUser);
              arg1.setInfo("lobbyId", joinLobbyId);
              try 
              {
                arg1.sendToClient(lobby);
              } 
              catch (IOException e) 
              {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            } 
            else 
            {
              try 
              {
                arg1.sendToClient(new Error("BrowseLobby Lobby is already full."));
              } 
              catch (IOException e) 
              {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            }
            return;
          }
        }

        try 
        {
          arg1.sendToClient(new Error("BrowseLobby Lobby not found."));
        } 
        catch (IOException e) 
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      else if (msg.equals("START_GAME")) 
      {
    	int clientLobbyId = (int) arg1.getInfo("lobbyId");

    	// Find the lobby from the list
    	LobbyData lobbyToStart = null;
    	for (LobbyData lobby : currentLobbies) 
    	{
    	  if (lobby.getLobbyId() == clientLobbyId) 
    	  {
    	    lobbyToStart = lobby;
    	    break;
    	  }
    	}

        if (lobbyToStart == null) 
   	    {
   	      try 
   	      {
   	        arg1.sendToClient(new Error("Lobby not found."));
   	      } 
   	      catch (IOException e) 
   	      {
   	        e.printStackTrace();
   	      }
   	      return;
   	    }
        // Create the game and add to activeGames
        Game newGame = new Game();
    	newGame.getGameData().setGameId(clientLobbyId); // Link lobbyId to game

    	activeGames.put(clientLobbyId, newGame);

    	// Sets the player timers
    	    
    	// Also set who is player 1 and player 2
    	// Could be through LobbyControl setGameOptions() information
    	// sent to the server or I can add a loop control variable
    	// to my send to both players where the first one gets sent player
    	// i and the second gets sent i++
    	    
    	// Broadcast starting game state to both players
    	for (ConnectionToClient client : getLobbyClients(clientLobbyId)) 
    	{
    	  client.setInfo("gameId", clientLobbyId);
    	  try
    	  {
    	    client.sendToClient(newGame.getGameData());
    	  } 
    	  catch (IOException e) 
    	  {
    	    e.printStackTrace();
    	  }
    	}
      }
      else if (msg.equals("OFFER_DRAW")) 
      {
    	  int lobbyId = (int) arg1.getInfo("lobbyId");

    	    for (ConnectionToClient client : getLobbyClients(lobbyId)) 
    	    {
    	        // Send to opponent only
    	        if (client != arg1) 
    	        {
    	            try 
    	            {
    	                client.sendToClient("OfferDraw");
    	            }
    	            catch (IOException e)
    	            {
    	                e.printStackTrace();
    	            }
    	        }
    	    }
  	    }
  	  
    }
    else if (arg0 instanceof GameData gameDataFromClient) 
    {
        
    	// Get the game id
    	int gameId = gameDataFromClient.getGameId();
    	Game game = activeGames.get(gameId);


        if (game == null) 
        {
          try 
          {
            arg1.sendToClient(new Error("No game found for this lobby."));
          } 
          catch (IOException e) 
          {
            e.printStackTrace();
          }
          return;
        }

        // Run handleMove on the gameData object
        GameData updatedGameData = game.handleMove(gameDataFromClient);
        
        
        
        // Send the updated gameData object to both players in the game id 
        for (ConnectionToClient client : getLobbyClients(gameId)) 
        {
          try 
          {
            client.sendToClient(updatedGameData);
          } 
          catch (IOException e) 
          {
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
  
  private List<ConnectionToClient> getLobbyClients(int lobbyId) 
  {
	  List<ConnectionToClient> result = new ArrayList<>();
	  for (Thread clientThread : getClientConnections()) 
	  {
	    ConnectionToClient client = (ConnectionToClient) clientThread;
	    Object info = client.getInfo("lobbyId");
	    if (info != null && info instanceof Integer && (int) info == lobbyId) 
	    {
	      result.add(client);
	    }
	  }
	  return result;
  }
}

