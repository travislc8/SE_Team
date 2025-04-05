package GameLogic;

import java.util.ArrayList;

public class GoldGeneralLogic {

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
