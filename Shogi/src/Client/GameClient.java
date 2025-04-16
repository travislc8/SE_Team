package Client;

import java.io.IOException;

import GameLogic.GameControl;
import GameLogic.GameData;
import LobbyManagement.BrowseLobbyControl;
import LobbyManagement.BrowseLobbyData;
import LobbyManagement.LobbyControl;
import LobbyManagement.LobbyData;
import Server.User;
import UserManagement.CreateAccountControl;
import UserManagement.LoginControl;
import UserManagement.LoginData;
import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient {

    private LoginControl loginControl;
    private CreateAccountControl createAccountControl;
    private LobbyControl lobbyControl;
    private BrowseLobbyControl browseLobbyControl;
    private GameControl gameControl;
    private LobbyData lobbyData;
    private User currentUser;




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

    public void handleMessageFromServer(Object arg0)
    {
        if (arg0 instanceof String message) {
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
        else if (arg0 instanceof Error error) {
            switch (error.getType()) {
                case "Login" -> loginControl.displayError(error.getMessage());
                case "CreateAccount" -> createAccountControl.displayError(error.getMessage());
                case "Lobby" -> lobbyControl.displayMessage(error.getMessage());
                case "BrowseLobby" -> browseLobbyControl.displayError(error.getMessage());
                default -> System.out.println("Unhandled error type: " + error.getType());
            }
        }

        // Handles login data sent from server
        else if (arg0 instanceof LoginData loginData) {
            this.currentUser = new User(loginData.getUsername(), loginData.getPassword());
            System.out.println("Received LoginData from server for user: " + currentUser.getUsername());
        }

        // Handles successful lobby creation
        else if (arg0 instanceof LobbyData lobbyData) {
            this.lobbyData = lobbyData;
            lobbyControl.lobbyCreated(lobbyData);
        }


        else if (arg0 instanceof BrowseLobbyData browseLobbyData)
        {
            browseLobbyControl.updateLobbyList(browseLobbyData.getAvailableLobbies());
        }

        // Handle game-related updates from the server
        else if (arg0 instanceof GameData gameData)
        {
            //forward to GameLogic controller
            System.out.println("Received GameData from server: " + gameData.toString());
        }
    }



}
