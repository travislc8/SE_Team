package GameLogic;

import java.util.ArrayList;

public class GameData {
    ArrayList<Piece> player1Pieces;
    ArrayList<Piece> player2Pieces;

    public ArrayList<Piece> getPlayer1Pieces() {
        return player1Pieces;
    }

    public void setPlayer1Pieces(ArrayList<Piece> player1Pieces) {
        this.player1Pieces = player1Pieces;
    }

    public ArrayList<Piece> getPlayer2Pieces() {
        return player2Pieces;
    }

    public void setPlayer2Pieces(ArrayList<Piece> player2Pieces) {
        this.player2Pieces = player2Pieces;
    }

}
