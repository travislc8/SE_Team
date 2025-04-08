package client;

import java.io.IOException;
import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient {
 
  private LoginControl loginControl;
  private CreateAccountControl createAccountControl;
  private LobbyControl lobbyControl;
  private BrowseLobbyControl browseLobbyControl;
  private GameControl gameControl;

 
  public GameClient() 
  {
    super("localhost", 8300);
  }

 
  public void addLoginControl(LoginControl loginControl) 
  {
    this.loginControl = loginControl;
  }

  public void addCreateAccountControl(CreateAccountControl createAccountControl) 
  {
    this.createAccountControl = createAccountControl;
  }

  public void addLobbyControl(LobbyControl lobbyControl) 
  {
    this.lobbyControl = lobbyControl;
  }

  public void addBrowseLobbyControl(BrowseLobbyControl browseLobbyControl) 
  {
    this.browseLobbyControl = browseLobbyControl;
  }
  
 public void addGameControl(GameControl gameControl)
 {
	 this.gameControl = gameControl;
 }

  public void handleMessageFromServer(Object msg)
  {
      if (msg instanceof String message) {
          switch (message) {
              case "LoginSuccessful" -> {
                  loginControl.loginSuccess();
              }
              case "CreateAccountSuccessful" -> {
                  createAccountControl.createAccountSuccess();
              }
              default -> {
                  System.out.println("Unknown server message: " + message);
              }
          }
      }

      // Handle Error objects from the server
      else if (msg instanceof Error error) {
          switch (error.getType()) {
              case "Login" -> loginControl.displayError(error.getMessage());
              case "CreateAccount" -> createAccountControl.displayError(error.getMessage());
              case "Lobby" -> lobbyControl.displayMessage(error.getMessage());
              case "BrowseLobby" -> browseLobbyControl.displayError(error.getMessage());
              default -> System.out.println("Unhandled error type: " + error.getType());
          }
      }

      // Handle login data sent from server (e.g., on auto-login success)
      else if (msg instanceof LoginData loginData) {
          this.currentUser = new User(loginData.getUsername(), loginData.getPassword());
          System.out.println("Received LoginData from server for user: " + currentUser.getUsername());
      }

      // Handle successful lobby creation
      else if (msg instanceof LobbyData lobbyData) {
          this.lobbyData = lobbyData;
          lobbyControl.lobbyCreated(lobbyData); 
      }

      
      else if (msg instanceof BrowseLobbyData browseLobbyData) 
      {
          browseLobbyControl.updateLobbyList(browseLobbyData.getLobbyList());
      }

      // Handle game-related updates from the server
      else if (msg instanceof GameData gameData) 
      {
          //forward to GameLogic controller
          System.out.println("Received GameData from server: " + gameData.toString());
      }
  }



}
