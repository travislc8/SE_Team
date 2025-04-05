package GameLogic;

import java.util.ArrayList;

public class KingLogic {
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
