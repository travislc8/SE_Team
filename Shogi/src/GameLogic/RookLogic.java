package GameLogic;

import java.util.ArrayList;

public class RookLogic {
    public static ArrayList<PieceLocation> calculateMoves(Piece piece, GameData gameData) {
        var list = new ArrayList<PieceLocation>();
        int x = piece.getLocation().getxPos();
        int y = piece.getLocation().getyPos();
        if (!piece.isOnBoard()) {
            list = PieceLogic.emptyLocationList(gameData, piece.getPlayer());
        } else {
            if (piece.isPromoted()) {
                list = PromotedList(x, y, piece.getPlayer(), gameData);
            } else {
                list = nonPromotedList(x, y, piece.getPlayer(), gameData);
            }
        }

        return list;
    }

    private static ArrayList<PieceLocation> nonPromotedList(int x, int y, PlayerType type, GameData gameData) {
        var list = new ArrayList<PieceLocation>();
        int saveX = x;
        int saveY = y;
        PieceLocation location = new PieceLocation(x, y, type);

        boolean opponent = false;

        y += 1;
        x = saveX;
        location.setxPos(x);
        location.setyPos(y);
        while (PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData)
                && !opponent) {
            list.add(new PieceLocation(x, y, type));
            opponent = PieceLogic.isOpponentLocation(location, gameData);
            y += 1;
            location.setyPos(y);
        }
        opponent = false;

        y = saveY - 1;
        x = saveX;
        location.setxPos(x);
        location.setyPos(y);
        while (PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData)
                && !opponent) {
            list.add(new PieceLocation(x, y, type));
            opponent = PieceLogic.isOpponentLocation(location, gameData);
            y -= 1;
            location.setyPos(y);
        }
        opponent = false;

        y = saveY;
        x = saveX + 1;
        location.setxPos(x);
        location.setyPos(y);
        while (PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData)
                && !opponent) {
            list.add(new PieceLocation(x, y, type));
            opponent = PieceLogic.isOpponentLocation(location, gameData);
            x += 1;
            location.setxPos(x);
        }
        opponent = false;

        y = saveY;
        x = saveX - 1;
        location.setxPos(x);
        location.setyPos(y);
        while (PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData)
                && !opponent) {
            list.add(new PieceLocation(x, y, type));
            opponent = PieceLogic.isOpponentLocation(location, gameData);
            x -= 1;
            location.setxPos(x);
        }

        return list;
    }

    public static ArrayList<PieceLocation> PromotedList(int x, int y, PlayerType type, GameData gameData) {
        var list = nonPromotedList(x, y, type, gameData);
        int saveX = x;
        int saveY = y;

        x = saveX + 1;
        y = saveY + 1;
        if (PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData))
            list.add(new PieceLocation(x, y, type));

        x = saveX - 1;
        y = saveY - 1;
        if (PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData))
            list.add(new PieceLocation(x, y, type));

        x = saveX - 1;
        y = saveY + 1;
        if (PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData))
            list.add(new PieceLocation(x, y, type));

        x = saveX + 1;
        y = saveY - 1;
        if (PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData))
            list.add(new PieceLocation(x, y, type));

        return list;
    }

    public static ArrayList<PieceLocation> noOpponentMoves(Piece piece, GameData gameData) {
        var list = new ArrayList<PieceLocation>();
        int x = piece.getLocation().getxPos();
        int y = piece.getLocation().getyPos();
        PlayerType type = piece.getPlayer();

        int saveX = x;
        int saveY = y;

        y += 1;
        x = saveX;
        while (PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData)) {
            list.add(new PieceLocation(x, y, type));
            y += 1;
        }

        y = saveY - 1;
        x = saveX;
        while (PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData)) {
            list.add(new PieceLocation(x, y, type));
            y -= 1;
        }

        y = saveY;
        x = saveX + 1;
        while (PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData)) {
            list.add(new PieceLocation(x, y, type));
            x += 1;
        }

        y = saveY;
        x = saveX - 1;
        while (PieceLogic.isValidLocation(new PieceLocation(x, y, type), gameData)) {
            list.add(new PieceLocation(x, y, type));
            x -= 1;
        }

        return list;
    }
}
