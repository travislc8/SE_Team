package GameLogic;

public class Move {
    PieceType pieceType;
    PieceLocation startLocation;
    PieceLocation endLocation;
    PlayerType player;

    public Move(PieceType pieceType, PieceLocation start, PieceLocation end) {
        this.player = start.getPlayer();
        this.pieceType = pieceType;
        startLocation = new PieceLocation(start.getxPos(), start.getyPos(), start.getPlayer());
        endLocation = new PieceLocation(end.getxPos(), end.getyPos(), end.getPlayer());
    }

    public Move(Piece piece, PieceLocation end) {
        this.player = piece.getPlayer();
        this.pieceType = piece.getPieceType();
        startLocation = new PieceLocation(piece.getLocation().getxPos(), piece.getLocation().getyPos(),
                piece.getLocation().getPlayer());
        endLocation = new PieceLocation(end.getxPos(), end.getyPos(), end.getPlayer());
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

}
