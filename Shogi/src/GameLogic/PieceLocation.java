package GameLogic;

/**
 * Holds a location on a 2D map for a piece.
 */
public class PieceLocation {
    // TODO: does this need to hold a PlayerType
    PlayerType player;
    int xPos;
    int yPos;

    /**
     * @param x      the x position the piece is at
     * @param y      the y position the piece is at
     * @param player the player type that owns the piece
     */
    public PieceLocation(int x, int y, PlayerType player) {
        setxPos(x);
        setyPos(y);
        setPlayer(player);
    }

    public PieceLocation(PieceLocation location) {
        setxPos(location.getxPos());
        setyPos(location.getyPos());
        setPlayer(location.getPlayer());
    }

    public PieceLocation() {
        setxPos(0);
        setyPos(0);
        setPlayer(PlayerType.WHITE);
    }

    public PlayerType getPlayer() {
        return player;
    }

    public void setPlayer(PlayerType player) {
        this.player = player;
    }

    public int getxPos() {
        return xPos;
    }

    /**
     * @param xPos the y position of the piece
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    /**
     * @param yPos the y position of the piece
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        boolean check;
        if (object instanceof PieceLocation location) {
            if (location.getxPos() != this.getxPos()) {
                check = false;
            } else if (location.getyPos() != this.getyPos()) {
                check = false;
            } else if (location.getPlayer() != this.getPlayer()) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

    public boolean xyEqual(PieceLocation location) {
        if (location.getxPos() == xPos && location.getyPos() == yPos)
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
