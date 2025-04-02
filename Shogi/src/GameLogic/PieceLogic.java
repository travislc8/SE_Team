package GameLogic;

import java.util.ArrayList;

public final class PieceLogic {

    public static boolean isValidLocation(PieceLocation location, GameData gameData) {
        boolean valid = true;
        if (location.getxPos() < 0 || location.getxPos() > 8) {
            valid = false;
        } else if (location.getyPos() < 0 || location.getyPos() > 8) {
            valid = false;
        } else {
            for (Piece piece : gameData.getPlayer1Pieces()) {
                if (location.equals(piece.getLocation())) {
                    valid = false;
                    break;
                }
            }
        }

        return valid;
    }

    public static ArrayList<PieceLocation> goldGeneralMoveList(int x, int y, PlayerType type) {
        var list = new ArrayList<PieceLocation>();
        int offset = type.direction;

        list.add(new PieceLocation(x, y + (offset * 1), type));
        list.add(new PieceLocation(x + 1, y + (offset * 1), type));
        list.add(new PieceLocation(x - 1, y + (offset * 1), type));
        list.add(new PieceLocation(x + 1, y, type));
        list.add(new PieceLocation(x - 1, y, type));
        list.add(new PieceLocation(x, y - (offset * 1), type));
        return list;
    }

    public static ArrayList<PieceLocation> emptyLocationList(GameData gameData, PlayerType type) {
        var list = new ArrayList<PieceLocation>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                list.add(new PieceLocation(x, y, type));
            }
        }
        PieceLocation location = new PieceLocation(0, 0, type);
        for (var piece : gameData.getPlayer1Pieces()) {
            location.setxPos(piece.getLocation().getxPos());
            location.setyPos(piece.getLocation().getyPos());
            list.remove(location);
        }
        for (var piece : gameData.getPlayer2Pieces()) {
            location.setxPos(piece.getLocation().getxPos());
            location.setyPos(piece.getLocation().getyPos());
            list.remove(location);
        }

        return list;
    }

}
