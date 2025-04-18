package GameLogic;

import java.util.ArrayList;

/**
 * Logic class for the pawn moves calculations.
 */
public final class PawnLogic {

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
            list = freePlacementMoves(piece, gameData);
        } else {
            if (piece.isPromoted()) {
                potentialMoves = PieceLogic.goldGeneralMoveList(x, y, piece.getPlayer());
            } else {
                potentialMoves = nonPromotedList(x, y, piece.getPlayer());
            }

            for (var move : potentialMoves) {
                if (PieceLogic.isValidLocation(move, gameData)) {
                    list.add(move);
                }
            }
        }

        return list;
    }

    private static ArrayList<PieceLocation> nonPromotedList(int x, int y, PlayerType type) {
        var list = new ArrayList<PieceLocation>();
        int offset = type.direction;
        list.add(new PieceLocation(x, y + (offset * 1), type));
        return list;
    }

    private static ArrayList<PieceLocation> freePlacementMoves(Piece piece, GameData gameData) {
        var invalidCollumns = new ArrayList<Integer>();

        var allFreeLocations = PieceLogic.emptyLocationList(gameData, piece.getPlayer());
        for (var playerPiece : gameData.getPlayerPieces(piece.getPlayer())) {
            if (playerPiece.getPieceType() == PieceType.PAWN) {
                invalidCollumns.add(playerPiece.getLocation().getxPos());
            }
        }
        java.util.Iterator<PieceLocation> it = allFreeLocations.iterator();
        while (it.hasNext()) {
            PieceLocation location = it.next();
            if (invalidCollumns.contains(location.getxPos())) {
                it.remove();
            }
        }
        return allFreeLocations;
    }
}
