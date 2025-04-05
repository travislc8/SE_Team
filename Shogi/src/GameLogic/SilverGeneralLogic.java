package GameLogic;

import java.util.ArrayList;

public class SilverGeneralLogic {
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
        list.add(new PieceLocation(x + 1, y + (offset * 1), type));
        list.add(new PieceLocation(x - 1, y + (offset * 1), type));

        list.add(new PieceLocation(x + 1, y - (offset * 1), type));
        list.add(new PieceLocation(x - 1, y - (offset * 1), type));
        return list;
    }
}
