package GameLogic;

import java.util.ArrayList;

public abstract class PieceLogic {

    public abstract ArrayList<PieceLocation> calculateMoves(Piece piece, GameData gameData);

    protected boolean isValidLocation(PieceLocation location, GameData gameData) {
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

}
