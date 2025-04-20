package Server;

import java.awt.*;

import javax.swing.*;

import Database.GameDatabase;
import GameLogic.PlayerType;
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
        arg1.setInfo("user",new User(data.getUsername(),data.getPassword()));
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
      if(msg.startsWith("CREATE_LOBBY "))
      {
        User owner = (User) arg1.getInfo("user");
        int newlobbyId = lobbyId++;
        int time = Integer.parseInt(msg.split(" ")[3]) * 60;
        LobbyData newLobby = new LobbyData(newlobbyId, owner, time, true);
        currentLobbies.add(newLobby);
        arg1.setInfo("lobbyId", newlobbyId);
        newLobby.setCurrentUser(owner);
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
              lobby.setCurrentUser(joiningUser);
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
      else if (msg.startsWith("START_GAME "))
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

          // Create the game and add it to activeGames
          Game newGame = new Game();
          GameData gameData = newGame.getGameData();
          gameData.setGameId(clientLobbyId); // Link lobbyId to game

          // Set players
          //User owner = lobbyToStart.getOwner();
          //User opponent = lobbyToStart.getOpponent();

         
          
          // Assuming PlayerType information is provided somehow (either through a separate message or lobby setup)
          PlayerType player1Type = PlayerType.BLACK;  // Retrieve PlayerType for Player 1 (Owner)
          PlayerType player2Type = PlayerType.WHITE;  // Retrieve PlayerType for Player 2 (Opponent)
          
          
          	
          
          
          gameData.setPlayer1Type(player1Type);  // Set Player 1 Type
          gameData.setPlayer2Type(player2Type);  // Set Player 2 Type

          // Set initial times for both players
          int gameTime = lobbyToStart.getGameTimerLength();
          gameData.setPlayer1Time(gameTime);
          gameData.setPlayer2Time(gameTime);

          activeGames.put(clientLobbyId, newGame);


          // Broadcast starting game state to both players
          for (ConnectionToClient client : getLobbyClients(clientLobbyId))
          {
              client.setInfo("gameId", clientLobbyId);

              // Set player 1 to Black
  	          if (client == arg1) 
  	          {
  	        	  gameData.setReceivingPlayer(player1Type);
  	          }
  	          // Sent player 2 to White
  	          if (client != arg1) 
  	          {
  	        	  gameData.setReceivingPlayer(player2Type);
  	          }
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
      else if (msg.startsWith("LEAVE_LOBBY "))
      {
          int lobbyIdToLeave = Integer.parseInt(msg.split(" ")[1]);
          User leavingUser = (User) arg1.getInfo("user");

          for (LobbyData lobby : currentLobbies)
          {
              if (lobby.getLobbyId() == lobbyIdToLeave)
              {
                  // If the leaving user is the owner, remove the lobby or make the opponent the owner
                  if (lobby.getOwner().equals(leavingUser))
                  {
                      // Transfer ownership to opponent or remove the lobby if no opponent
                      if (lobby.getOpponent() != null)
                      {
                          lobby.setOwner(lobby.getOpponent());
                          lobby.setOpponent(null);
                      }
                      else
                      {
                          currentLobbies.remove(lobby);
                      }
                  }
                  else if (lobby.getOpponent() != null && lobby.getOpponent().equals(leavingUser))
                  {
                      lobby.setOpponent(null);
                  }

                  // Inform the client they have left the lobby
                  try
                  {
                      arg1.sendToClient("You have left the lobby.");
                  }
                  catch (IOException e)
                  {
                      e.printStackTrace();
                  }
                  return;
              }
          }

          // If lobby was not found
          try
          {
              arg1.sendToClient(new Error("Lobby not found."));
          }
          catch (IOException e)
          {
              e.printStackTrace();
          }
      }
      else if (msg.startsWith("SET_READY "))
      {
          int lobbyIdToSetReady = Integer.parseInt(msg.split(" ")[1]);
          User readyUser = (User) arg1.getInfo("user");

          for (LobbyData lobby : currentLobbies)
          {
              if (lobby.getLobbyId() == lobbyIdToSetReady)
              {
                  if (lobby.getOwner().equals(readyUser))
                  {
                      lobby.setOwnerReady(true);

              	    for (ConnectionToClient client : getLobbyClients(lobbyIdToSetReady)) 
              	    {
              	        // Send to opponent only
              	        if (client != arg1) 
              	        {
              	            try 
              	            {
              	                client.sendToClient(lobby);
              	            }
              	            catch (IOException e)
              	            {
              	                e.printStackTrace();
              	            }
              	        }
              	    }
                      
                  }
                  else if (lobby.getOpponent() != null && lobby.getOpponent().equals(readyUser))
                  {
                      lobby.setOpponentReady(true);
                      
                      for (ConnectionToClient client : getLobbyClients(lobbyIdToSetReady)) 
                	    {
                	        // Send to opponent only
                	        if (client != arg1) 
                	        {
                	            try 
                	            {
                	                client.sendToClient(lobby);
                	            }
                	            catch (IOException e)
                	            {
                	                e.printStackTrace();
                	            }
                	        }
                	    }
                  }

                  
                  // Check if both players are ready
                  if (lobby.isOwnerReady() && lobby.isOpponentReady())
                  {
                      // Start the game
                      try
                      {
                          arg1.sendToClient("Both players are ready. Game starting...");
                      }
                      catch (IOException e)
                      {
                          e.printStackTrace();
                      }
                  }
                  return;
              }
          }

          // If lobby was not found
          try
          {
              arg1.sendToClient(new Error("Lobby not found."));
          }
          catch (IOException e)
          {
              e.printStackTrace();
          }
      }
      else if (msg.equals("REQUEST_LOBBY_LIST"))
      {
          ArrayList<LobbyData> lobbyList = new ArrayList<>(currentLobbies);
          BrowseLobbyData bld = new BrowseLobbyData();
          bld.setAvailableLobbies(lobbyList);
          
          try
          {
              arg1.sendToClient(bld); // Send the list of current lobbies
          }
          catch (IOException e)
          {
              e.printStackTrace();
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

