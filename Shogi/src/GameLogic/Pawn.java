package GameLogic;

import java.util.ArrayList;

public class Pawn extends PieceLogic {

    @Override
    public ArrayList<PieceLocation> calculateMoves(Piece piece, GameData gameData) {
        // TODO
        var list = new ArrayList<PieceLocation>();
        int x = piece.getLocation().getxPos();
        int y = piece.getLocation().getyPos();
        PieceLocation potentialMove = new PieceLocation(x, y, piece.getPlayer());
        if (isValidLocation(potentialMove, gameData)) {
            list.add(potentialMove);
        }

        return list;
    }

}
