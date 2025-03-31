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
     * @throws IndexOutOfBoundsException when a position argument is < 0 or > 8
     */
    PieceLocation(int x, int y, PlayerType player) {
        setxPos(x);
        setyPos(y);
        setPlayer(player);
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
     * @throws IndexOutOfBoundsException when the position argument is < 0 or > 8
     */
    public void setxPos(int xPos) {
        if (xPos > 8) {
            throw new IndexOutOfBoundsException("PieceLocation x position cannot be greater than 8");
        }

        if (xPos < 0) {
            throw new IndexOutOfBoundsException("PieceLocation x position cannot be less than 0");
        }
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    /**
     * @param yPos the y position of the piece
     * @throws IndexOutOfBoundsException when the position argument is < 0 or > 8
     */
    public void setyPos(int yPos) {
        if (yPos > 8) {
            throw new IndexOutOfBoundsException("PieceLocation y position cannot be greater than 8");
        }

        if (yPos < 0) {
            throw new IndexOutOfBoundsException("PieceLocation y position cannot be less than 0");
        }
        this.yPos = yPos;
    }

}
