package GameLogic;

import java.util.ArrayList;

public class Piece {
    boolean promoted;
    boolean onBoard;
    PlayerType player;
    PieceType pieceType;
    PieceLocation location;
    ArrayList<PieceLocation> availableMoves;

    public Piece(PlayerType player, PieceType piece, PieceLocation location) {
        this.player = player;
        this.pieceType = piece;
        this.location = location;
        promoted = false;
        onBoard = true;
        availableMoves = new ArrayList<>();
    }

    public Piece(PlayerType player, PieceType piece, int x, int y) {
        this.player = player;
        this.pieceType = piece;
        this.location = new PieceLocation(x, y, player);
        onBoard = true;
        promoted = false;
    }

    public boolean isOnBoard() {
        return onBoard;
    }

    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    public PlayerType getPlayer() {
        return player;
    }

    public void setPlayer(PlayerType player) {
        this.player = player;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public PieceLocation getLocation() {
        return location;
    }

    public void setLocation(PieceLocation location) {
        this.location = location;
    }

    public void setx(int x) {
        location.setxPos(x);
    }

    public void sety(int y) {
        location.setxPos(y);
    }

    public ArrayList<PieceLocation> getAvailableMoves() {
        return availableMoves;
    }

    public void setAvailableMoves(ArrayList<PieceLocation> availableMoves) {
        this.availableMoves = availableMoves;
    }

}
