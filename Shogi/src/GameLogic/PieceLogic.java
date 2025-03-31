package GameLogic;

import java.util.ArrayList;

public abstract class PieceLogic {

    public abstract ArrayList<PieceLocation> calculateMoves(PieceLocation pieceLocation, GameData gameData);

}
