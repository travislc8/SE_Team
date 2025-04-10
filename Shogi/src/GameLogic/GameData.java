package GameLogic;

import java.util.ArrayList;

public class GameData {
    PlayerType player1Type;
    PlayerType player2Type;
    ArrayList<Piece> player1Pieces;
    ArrayList<Piece> player2Pieces;

    public GameData() {
        player1Type = PlayerType.BLACK;
        player2Type = PlayerType.WHITE;
        player1Pieces = new ArrayList<>();
        player2Pieces = new ArrayList<>();
    }

    public ArrayList<Piece> getPlayer1Pieces() {
        return player1Pieces;
    }

    public void setPlayer1Pieces(ArrayList<Piece> player1Pieces) {
        if (player1Pieces.isEmpty()) {
            return;
        }
        if (player1Pieces.getFirst().getPlayer() != player1Type) {
            throw new IllegalArgumentException("Player 1 type must be Black");
        }
        this.player1Pieces = player1Pieces;
    }

    public ArrayList<Piece> getPlayer2Pieces() {
        return player2Pieces;
    }

    public PlayerType getPlayer1Type() {
        return player1Type;
    }

    public PlayerType getPlayer2Type() {
        return player2Type;
    }

    public void setPlayer2Pieces(ArrayList<Piece> player2Pieces) {
        if (player2Pieces.isEmpty()) {
            return;
        }
        if (player2Pieces.getFirst().getPlayer() != player2Type) {
            throw new IllegalArgumentException("Player 2 type must be White");
        }
        this.player2Pieces = player2Pieces;
    }

    public ArrayList<Piece> getPlayerPieces(PlayerType playerType) {
        if (playerType == player1Type) {
            return player1Pieces;
        } else {
            return player2Pieces;
        }
    }

    public ArrayList<Piece> getOtherPlayerPieces(PlayerType playerType) {
        if (playerType == player1Type) {
            return player2Pieces;
        } else {
            return player1Pieces;
        }
    }

}
