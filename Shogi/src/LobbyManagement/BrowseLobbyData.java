package LobbyManagement;
import java.io.Serializable;
import java.util.ArrayList;

public class BrowseLobbyData implements Serializable {
    private ArrayList<LobbyData> availableLobbies;
    private LobbyData selectedLobby;

    public BrowseLobbyData() {
        availableLobbies = new ArrayList<>();
    }

    public void setAvailableLobbies(ArrayList<LobbyData> lobbies) {
        this.availableLobbies = lobbies;
    }

    public ArrayList<LobbyData> getAvailableLobbies() {
        return availableLobbies;
    }

    public void setSelectedLobby(LobbyData lobby) {
        this.selectedLobby = lobby;
    }

    public LobbyData getSelectedLobby() {
        return selectedLobby;
    }
}
