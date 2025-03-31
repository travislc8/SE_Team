package GameLogic;

public enum PieceType {

    KING("king"),
    ROOK("rook"),
    BISHOP("bishop"),
    GOLDGENERAL("goldgeneral"),
    SILVERGENERAL("silvergeneral"),
    KNIGHT("knight"),
    LANCE("lance"),
    PAWN("pawn");

    public final String path;

    PieceType(String path) {
        this.path = path + ".png";
    }

}
