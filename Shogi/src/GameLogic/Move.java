package GameLogic;

import java.io.Serializable;

/**
 * Represents a move of a piece on the shogi board.
 */
public class Move implements Serializable {
    PieceType pieceType;
    PieceLocation startLocation;
    PieceLocation endLocation;
    PlayerType player;
    boolean piecePromoted;

    public Move(Piece piece, PieceLocation end) {
        this.player = piece.getPlayer();
        this.pieceType = piece.getPieceType();
        startLocation = new PieceLocation(piece.getLocation().getxPos(), piece.getLocation().getyPos(),
                piece.getPlayer());
        endLocation = new PieceLocation(end.getxPos(), end.getyPos(), end.getPlayer());
        piecePromoted = piece.isPromoted();
    }

    private Move(PieceType pieceType, PieceLocation start, PieceLocation end, boolean promoted) {
        this.player = start.getPlayer();
        this.pieceType = pieceType;
        startLocation = new PieceLocation(start.getxPos(), start.getyPos(), start.getPlayer());
        endLocation = new PieceLocation(end.getxPos(), end.getyPos(), end.getPlayer());
        this.piecePromoted = promoted;
    }

    public Move deepCopy() {
        var copy = new Move(this.pieceType, this.startLocation, this.endLocation, this.piecePromoted);
        return copy;

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

    public PieceLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(PieceLocation startLocation) {
        this.startLocation = startLocation;
    }

    public PieceLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(PieceLocation endLocation) {
        this.endLocation = endLocation;
    }

    public boolean isPiecePromoted() {
        return piecePromoted;
    }

    public void setPiecePromoted(boolean piecePromoted) {
        this.piecePromoted = piecePromoted;
    }

}
