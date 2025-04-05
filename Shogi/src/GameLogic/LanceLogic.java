package GameLogic;

import java.util.ArrayList;

public class LanceLogic {
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

    private static ArrayList<PieceLocation> nonPromotedList(int x, int y, PlayerType type, GameData gameData) {
        var list = new ArrayList<PieceLocation>();
        int offset = type.direction;

        y += (1 * offset);
        while (y < 9 && y >= 0
                && PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData)) {
            list.add(new PieceLocation(x, y, type));
            y += (1 * offset);
        }

        return list;
    }
}
