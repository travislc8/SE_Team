package LobbyManagement;

import Server.User;

public class LobbyData {
    private int lobbyId;
    private Server.User owner;
    private Server.User opponent;
    private String password;
    private boolean isPublic;
    private int gameTimerLength;
    private boolean start;

    public LobbyData(int lobbyId, Server.User owner, int gameTimerLength, boolean isPublic) {
        this.lobbyId = lobbyId;
        this.owner = owner;
        this.gameTimerLength = gameTimerLength;
        this.isPublic = isPublic;
        this.start = false;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(int lobbyId) {
        this.lobbyId = lobbyId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOpponent() {
        return opponent;
    }

    public void setOpponent(User opponent) {
        this.opponent = opponent;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public int getGameTimerLength() {
        return gameTimerLength;
    }

    public void setGameTimerLength(int gameTimerLength) {
        this.gameTimerLength = gameTimerLength;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
