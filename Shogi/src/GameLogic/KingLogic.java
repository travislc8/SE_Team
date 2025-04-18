package GameLogic;

import java.util.ArrayList;

/**
 * Logic class for the king moves calculations.
 */
public class KingLogic {
    /**
     * Calculates the locations for all of the available moves for a given piece
     * based on the game data
     *
     * @param piece    the piece that the moves should be calculated for
     * @param gameData the state the game is in
     * @return an ArrayList containing all of the possible locations that the piece
     *         can move
     */
    public static ArrayList<PieceLocation> calculateMoves(Piece piece, GameData gameData) {
        var list = new ArrayList<PieceLocation>();
        int x = piece.getLocation().getxPos();
        int y = piece.getLocation().getyPos();
        ArrayList<PieceLocation> potentialMoves;
        potentialMoves = moveList(x, y, piece.getPlayer());

        for (var move : potentialMoves) {
            if (PieceLogic.isValidLocation(move, gameData)) {
                list.add(move);
            }
        }

        return list;
    }

    private static ArrayList<PieceLocation> moveList(int x, int y, PlayerType type) {
        var list = new ArrayList<PieceLocation>();
        int offset = type.direction;

        list.add(new PieceLocation(x, y + (offset * 1), type));
        list.add(new PieceLocation(x + 1, y + (offset * 1), type));
        list.add(new PieceLocation(x - 1, y + (offset * 1), type));

        list.add(new PieceLocation(x, y - (offset * 1), type));
        list.add(new PieceLocation(x + 1, y - (offset * 1), type));
        list.add(new PieceLocation(x - 1, y - (offset * 1), type));

        list.add(new PieceLocation(x + 1, y, type));
        list.add(new PieceLocation(x - 1, y, type));
        return list;
    }
}
