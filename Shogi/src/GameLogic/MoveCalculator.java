package GameLogic;

import java.util.ArrayList;

/**
 * MoveCalculator does the necessary calculations to update a GameData object so
 * that the active players pieces contain the correct available moves.
 */
public class MoveCalculator {

    private GameData gameData;

    public MoveCalculator() {
        gameData = null;
    }

    /**
     * Calculates the available moves for all of the active players pieces. The
     * GameData instance is not returned because it is modified at the location that
     * is sent into the method.
     *
     * @param gameData the gameData instance that the moves will be calculated for
     */
    public void calculateAvailableMoves(GameData gameData) {
        this.gameData = gameData;

        PlayerType activePlayer = gameData.getActivePlayer();
        var opPieces = gameData.getOtherPlayerPieces(activePlayer);
        for (var piece : opPieces) {
            piece.setAvailableMoves(getMoves(piece));
        }

        PieceLocation kingLocation = getKingLocation(activePlayer);

        if (isKingDiscoverable(kingLocation, opPieces)) {
            for (var piece : gameData.getPlayerPieces(activePlayer)) {
                if (piece.getPieceType() != PieceType.KING) {
                    piece.setAvailableMoves(getSafeMoves(piece, kingLocation, opPieces));
                } else {
                    piece.setAvailableMoves(getSafeKingMoves(piece, kingLocation, opPieces));
                }
            }
            if (gameData.getPlayerHand(activePlayer) != null) {
                for (var piece : gameData.getPlayerHand(activePlayer)) {
                    piece.setAvailableMoves(getSafeFreeMove(piece, kingLocation, opPieces));
                }
            }
        } else {
            for (var piece : gameData.getPlayerPieces(activePlayer)) {
                if (piece.getPieceType() != PieceType.KING) {
                    piece.setAvailableMoves(getMoves(piece));
                } else {
                    piece.setAvailableMoves(getSafeKingMoves(piece, kingLocation, opPieces));
                }
            }
            if (gameData.getPlayerHand(activePlayer) != null) {
                for (var piece : gameData.getPlayerHand(activePlayer)) {
                    piece.setAvailableMoves(getMoves(piece));
                }
            }
        }

    }

    private ArrayList<PieceLocation> getSafeKingMoves(Piece king, PieceLocation kingLocation,
            ArrayList<Piece> opPieces) {

        int returnX = king.getLocation().getxPos();
        int returnY = king.getLocation().getyPos();

        var safeMoves = new ArrayList<PieceLocation>();
        for (var move : getMoves(king)) {
            king.setx(move.getxPos());
            king.sety(move.getyPos());
            if (!isKingInDanger(new PieceLocation(move), opPieces)) {
                safeMoves.add(move);
            }
        }

        king.getLocation().setxPos(returnX);
        king.getLocation().setyPos(returnY);

        return safeMoves;
    }

    private ArrayList<PieceLocation> getSafeFreeMove(Piece movePiece, PieceLocation kingLocation,
            ArrayList<Piece> opPieces) {
        // current idea is that changing the movePiece location should change it in the
        // class game data
        int returnX = movePiece.getLocation().getxPos();
        int returnY = movePiece.getLocation().getyPos();
        var moves = getMoves(movePiece);
        gameData.getPlayerPieces(movePiece.getPlayer()).add(movePiece);

        // THIS LINE IS SETTING ITEMS IN HAND TO BE ON BOARD
        movePiece.setOnBoard(true); // ??????????????????

        var safeMoves = new ArrayList<PieceLocation>();
        Piece removedPiece = null;
        for (var move : moves) {
            movePiece.setx(move.getxPos());
            movePiece.sety(move.getyPos());
            removedPiece = gameData.removeOpPieceAt(movePiece.getPlayer(), move.getxPos(), move.getyPos());
            if (movePiece.getLocation().getxPos() == 5 && movePiece.getLocation().getyPos() == 4) {
            }
            if (!isKingInDanger(kingLocation, opPieces)) {
                safeMoves.add(move);
            }
            if (removedPiece != null) {
                gameData.getOtherPlayerPieces(movePiece.getPlayer()).add(removedPiece);
                removedPiece = null;
            }
        }

        movePiece.getLocation().setxPos(returnX);
        movePiece.getLocation().setyPos(returnY);
        gameData.getPlayerPieces(movePiece.getPlayer()).remove(movePiece);

        // SET THE ITEM BACK TO NOT BEING ON BOARD????
        movePiece.setOnBoard(false); /// MAYBE THE SOLUTION?????

        return safeMoves;

    }

    private ArrayList<PieceLocation> getSafeMoves(Piece movePiece, PieceLocation kingLocation,
            ArrayList<Piece> opPieces) {
        // current idea is that changing the movePiece location should change it in the
        // class game data
        int returnX = movePiece.getLocation().getxPos();
        int returnY = movePiece.getLocation().getyPos();

        var safeMoves = new ArrayList<PieceLocation>();
        Piece removedPiece = null;
        for (var move : getMoves(movePiece)) {
            movePiece.setx(move.getxPos());
            movePiece.sety(move.getyPos());
            removedPiece = gameData.removeOpPieceAt(movePiece.getPlayer(), move.getxPos(), move.getyPos());
            if (!isKingInDanger(kingLocation, opPieces)) {
                safeMoves.add(move);
            }
            if (removedPiece != null) {
                gameData.getOtherPlayerPieces(movePiece.getPlayer()).add(removedPiece);
                removedPiece = null;
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
        for (var piece : opPieces) {
            for (var location : getMoves(piece)) {
                if (location.xyEqual(kingLocation)) {
                    return true;
                }
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
