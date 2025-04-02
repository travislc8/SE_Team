package GameLogic;

import java.util.ArrayList;

public final class PawnLogic {

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
