package GameLogic;

import java.util.ArrayList;

/**
 * Logic class for the gold general moves calculations.
 */
public class GoldGeneralLogic {

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
        if (!piece.isOnBoard()) {
            list = PieceLogic.emptyLocationList(gameData, piece.getPlayer());
        } else {
            potentialMoves = PieceLogic.goldGeneralMoveList(x, y, piece.getPlayer());

            for (var move : potentialMoves) {
                if (PieceLogic.isValidLocation(move, gameData)) {
                    list.add(move);
                }
            }
        }

        return list;
    }
}
