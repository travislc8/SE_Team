package GameLogic;

import java.util.ArrayList;

public class MoveCalculator {

    private GameData gameData;

    public MoveCalculator() {
        gameData = null;
    }

    public void calculateAvailableMoves(GameData gameData) {
        this.gameData = gameData;

        PlayerType activePlayer = gameData.getActivePlayer();
        var opPieces = gameData.getOtherPlayerPieces(activePlayer);
        for (var piece : opPieces) {
            piece.setAvailableMoves(getMoves(piece));
        }

        // System.out.println("op.size " + opPieces.size());
        PieceLocation kingLocation = getKingLocation(activePlayer);

        if (isKingDiscoverable(kingLocation, opPieces)) {
            System.out.println("kd");
            for (var piece : gameData.getPlayerPieces(activePlayer)) {
                if (piece.getPieceType() != PieceType.KING) {
                    // ??????
                    piece.setAvailableMoves(getSafeMoves(piece, kingLocation, opPieces));
                } else {
                    piece.setAvailableMoves(getSafeKingMoves(piece, kingLocation, opPieces));
                }
            }
        } else {
            System.out.println("nkd");
            for (var piece : gameData.getPlayerPieces(activePlayer)) {
                if (piece.getPieceType() != PieceType.KING) {
                    piece.setAvailableMoves(getMoves(piece));
                } else {
                    System.out.println("king");
                    System.out.println("op.size " + opPieces.size());
                    piece.setAvailableMoves(getSafeKingMoves(piece, kingLocation, opPieces));
                }
            }
        }

    }

    private ArrayList<PieceLocation> getSafeKingMoves(Piece king, PieceLocation kingLocation,
            ArrayList<Piece> opPieces) {

        int returnX = king.getLocation().getxPos();
        int returnY = king.getLocation().getyPos();
        System.out.println(returnX + ":" + returnY);

        var safeMoves = new ArrayList<PieceLocation>();
        for (var move : getMoves(king)) {
            System.out.println("move -> " + move.getxPos() + ":" + move.getyPos());
            king.setx(move.getxPos());
            king.sety(move.getyPos());
            System.out.println("op.size: " + opPieces.size());
            if (!isKingInDanger(new PieceLocation(move), opPieces)) {
                safeMoves.add(move);
            }
        }

        king.getLocation().setxPos(returnX);
        king.getLocation().setyPos(returnY);

        return safeMoves;
    }

    private ArrayList<PieceLocation> getSafeMoves(Piece movePiece, PieceLocation kingLocation,
            ArrayList<Piece> opPieces) {
        // current idea is that changing the movePiece location should change it in the
        // class game data
        int returnX = movePiece.getLocation().getxPos();
        int returnY = movePiece.getLocation().getyPos();

        var safeMoves = new ArrayList<PieceLocation>();
        for (var move : getMoves(movePiece)) {
            movePiece.setx(move.getxPos());
            movePiece.sety(move.getyPos());
            if (!isKingInDanger(kingLocation, opPieces)) {
                safeMoves.add(move);
            }
        }

        movePiece.getLocation().setxPos(returnX);
        movePiece.getLocation().setyPos(returnY);
        return safeMoves;

    }

    private boolean isKingDiscoverable(PieceLocation kingLocation, ArrayList<Piece> opPieces) {
        for (var piece : opPieces) {
            for (var location : getDiscoverMoves(piece)) {
                if (location.xyEqual(kingLocation))
                    return true;
            }
        }

        return false;
    }

    private boolean isKingInDanger(PieceLocation kingLocation, ArrayList<Piece> opPieces) {
        System.out.println("op.size: " + opPieces.size());
        for (var piece : opPieces) {
            for (var location : piece.getAvailableMoves()) {
                if (location.xyEqual(kingLocation)) {
                    System.out.println("d");
                    return true;
                }
                System.out.println(kingLocation.getxPos() + "!=" + piece.getLocation().getxPos()
                        + " && " + kingLocation.getyPos() + "!=" + piece.getLocation().getyPos());
            }
        }
        // only reachable if no locations matched
        return false;
    }

    private PieceLocation getKingLocation(PlayerType player) {
        for (var piece : gameData.getPlayerPieces(player)) {
            if (piece.getPieceType() == PieceType.KING)
                return piece.getLocation();
        }

        // should never get here because a king must exist on the board
        throw new IllegalStateException("No King exists on the board for the player");
    }

    private ArrayList<PieceLocation> getDiscoverMoves(Piece piece) {
        switch (piece.getPieceType()) {
            case PieceType.SILVERGENERAL:
                return SilverGeneralLogic.calculateMoves(piece, gameData);
            case PieceType.GOLDGENERAL:
                return GoldGeneralLogic.calculateMoves(piece, gameData);
            case PieceType.ROOK:
                return RookLogic.calculateMoves(piece, gameData);
            case PieceType.KNIGHT:
                return KnightLogic.calculateMoves(piece, gameData);
            case PieceType.LANCE:
                return LanceLogic.calculateMoves(piece, gameData);
            case PieceType.BISHOP:
                return BishopLogic.calculateMoves(piece, gameData);
            case PieceType.KING:
                return KingLogic.calculateMoves(piece, gameData);
            case PieceType.PAWN:
                return PawnLogic.calculateMoves(piece, gameData);
            default:
                return new ArrayList<>();
        }
    }

    private ArrayList<PieceLocation> getMoves(Piece piece) {
        switch (piece.getPieceType()) {
            case PieceType.SILVERGENERAL:
                return SilverGeneralLogic.calculateMoves(piece, gameData);
            case PieceType.GOLDGENERAL:
                return GoldGeneralLogic.calculateMoves(piece, gameData);
            case PieceType.ROOK:
                return RookLogic.calculateMoves(piece, gameData);
            case PieceType.KNIGHT:
                return KnightLogic.calculateMoves(piece, gameData);
            case PieceType.LANCE:
                return LanceLogic.calculateMoves(piece, gameData);
            case PieceType.BISHOP:
                return BishopLogic.calculateMoves(piece, gameData);
            case PieceType.KING:
                return KingLogic.calculateMoves(piece, gameData);
            case PieceType.PAWN:
                return PawnLogic.calculateMoves(piece, gameData);
            default:
                return new ArrayList<>();
        }
    }

}
