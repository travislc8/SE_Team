package GameLogic;

import java.util.ArrayList;

/**
 * Logic class for the lance moves calculations.
 */
public class LanceLogic {
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
            if (piece.isPromoted()) {
                potentialMoves = PieceLogic.goldGeneralMoveList(x, y, piece.getPlayer());
                for (var move : potentialMoves) {
                    if (PieceLogic.isValidLocation(move, gameData)) {
                        list.add(move);
                    }
                }
            } else {
                list = nonPromotedList(x, y, piece.getPlayer(), gameData);
            }

        }

        return list;
    }

    /**
     * Returns the available moves if there were none of the opponents pieces on the
     * board
     *
     * @param piece    the piece that the moves should be calculated for
     * @param gameData the state the game is in
     * @return an ArrayList containing all of the possible locations that the piece
     *         can move
     */
    public static ArrayList<PieceLocation> noOpponentMoves(Piece piece, GameData gameData) {
        int x = piece.getLocation().getxPos();
        int y = piece.getLocation().getyPos();

        var list = new ArrayList<PieceLocation>();
        PlayerType type = piece.getPlayer();
        int offset = type.direction;

        y += (1 * offset);
        while (y < 9 && y >= 0
                && PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData)) {
            list.add(new PieceLocation(x, y, type));
            y += (1 * offset);
        }

        return list;
    }

    private static ArrayList<PieceLocation> nonPromotedList(int x, int y, PlayerType type, GameData gameData) {
        var list = new ArrayList<PieceLocation>();
        int offset = type.direction;
        boolean opponent = false;
        PieceLocation location = new PieceLocation(x, y, type);

        y += (1 * offset);
        location.setyPos(y);
        while (y < 9 && y >= 0
                && PieceLogic.isValidLocation(location, gameData)
                && !opponent) {

            if (PieceLogic.isOpponentLocation(location, gameData))
                opponent = true;
            list.add(new PieceLocation(x, y, type));
            y += (1 * offset);
            location.setyPos(y);
        }

        return list;
    }

}
