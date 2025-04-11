package GameLogic;

import java.util.ArrayList;

public class GameData {
    PlayerType player1Type;
    PlayerType player2Type;
    PlayerType activePlayer;
    ArrayList<Move> moveList;
    ArrayList<Piece> player1Pieces;
    ArrayList<Piece> player2Pieces;
    ArrayList<Piece> player1Hand;
    ArrayList<Piece> player2Hand;
    boolean gameOver;
    int player1Time;
    int player2Time;
    int gameId;

    public GameData() {
        player1Type = PlayerType.BLACK;
        player2Type = PlayerType.WHITE;
        player1Pieces = new ArrayList<>();
        player2Pieces = new ArrayList<>();
    }

    public boolean makeMove(Move move) {
        if (move.getPlayer() != activePlayer) {
            throw new IllegalStateException("Move player must be the same as the active player");
        }

        Piece movePiece = getPieceAt(move.getStartLocation());

        if (movePiece == null) {
            return false;
        }

        if (!movePiece.getAvailableMoves().contains(move.getEndLocation())) {
            System.out.println("invalid move");
            return false;
        }

        moveList.add(move);

        Piece opPiece = getOpPieceAt(move.getEndLocation());

        // piece was captured
        if (opPiece != null) {
            getOtherPlayerPieces(activePlayer).remove(opPiece);
            System.out.println("matching piece check");

            opPiece.setOnBoard(false);
            opPiece.setx(-1);
            opPiece.sety(-1);
            opPiece.setPlayer(activePlayer);
            opPiece.setPromoted(false);
            getPlayerHand(activePlayer).add(opPiece);
        }

        // move piece
        movePiece.setLocation(move.getEndLocation());

        return true;
    }

    public Piece removeOpPieceAt(PlayerType playerType, int x, int y) {
        for (var piece : getOtherPlayerPieces(playerType)) {
            if (piece.getLocation().xyEqual(x, y)) {
                getOtherPlayerPieces(activePlayer).remove(piece);
                return piece;
            }
        }

        return null;
    }

    private Piece getOpPieceAt(PieceLocation location) {
        for (var piece : getOtherPlayerPieces(activePlayer)) {
            if (piece.getLocation().xyEqual(location)) {
                return piece;
            }
        }

        return null;
    }

    private Piece getPieceAt(PieceLocation location) {
        for (var piece : getPlayerPieces(activePlayer)) {
            if (piece.getLocation().equals(location)) {
                return piece;
            }
        }

        return null;
    }

    public PlayerType getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(PlayerType activePlayer) {
        this.activePlayer = activePlayer;
    }

    public ArrayList<Piece> getPlayer1Pieces() {
        return player1Pieces;
    }

    public void setPlayer1Pieces(ArrayList<Piece> player1Pieces) {
        if (player1Pieces.isEmpty()) {
            return;
        }
        if (player1Pieces.getFirst().getPlayer() != player1Type) {
            throw new IllegalArgumentException("Player 1 type must be Black");
        }
        this.player1Pieces = player1Pieces;
    }

    public ArrayList<Piece> getPlayer2Pieces() {
        return player2Pieces;
    }

    public PlayerType getPlayer1Type() {
        return player1Type;
    }

    public PlayerType getPlayer2Type() {
        return player2Type;
    }

    public void setPlayer2Pieces(ArrayList<Piece> player2Pieces) {
        if (player2Pieces.isEmpty()) {
            return;
        }
        if (player2Pieces.getFirst().getPlayer() != player2Type) {
            throw new IllegalArgumentException("Player 2 type must be White");
        }
        this.player2Pieces = player2Pieces;
    }

    public ArrayList<Piece> getPlayerPieces(PlayerType playerType) {
        if (playerType == player1Type) {
            return player1Pieces;
        } else {
            return player2Pieces;
        }
    }

    public ArrayList<Piece> getOtherPlayerPieces(PlayerType playerType) {
        if (playerType == player1Type) {
            return player2Pieces;
        } else {
            return player1Pieces;
        }
    }

    public ArrayList<Piece> getPlayerHand(PlayerType playerType) {
        if (playerType == player1Type) {
            return player1Hand;
        } else {
            return player2Hand;
        }
    }

    public ArrayList<Piece> getOtherPlayerHand(PlayerType playerType) {
        if (playerType == player1Type) {
            return player2Hand;
        } else {
            return player1Hand;
        }
    }

    public void setPlayer1Type(PlayerType player1Type) {
        this.player1Type = player1Type;
    }

    public void setPlayer2Type(PlayerType player2Type) {
        this.player2Type = player2Type;
    }

    public ArrayList<Move> getMoveList() {
        return moveList;
    }

    public void setMoveList(ArrayList<Move> moveList) {
        this.moveList = moveList;
    }

    public ArrayList<Piece> getPlayer1Hand() {
        return player1Hand;
    }

    public void setPlayer1Hand(ArrayList<Piece> player1Hand) {
        if (player1Hand.isEmpty()) {
            return;
        }
        if (player1Hand.getFirst().getPlayer() != player1Type) {
            throw new IllegalArgumentException("Player 1 type must be Black");
        }
        this.player1Hand = player1Hand;
    }

    public ArrayList<Piece> getPlayer2Hand() {
        return player2Hand;
    }

    public void setPlayer2Hand(ArrayList<Piece> player2Hand) {
        if (player2Hand.isEmpty()) {
            return;
        }
        if (player2Hand.getFirst().getPlayer() != player2Type) {
            throw new IllegalArgumentException("Player 2 type must be Black");
        }
        this.player2Hand = player2Hand;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getPlayer1Time() {
        return player1Time;
    }

    public void setPlayer1Time(int player1Time) {
        this.player1Time = player1Time;
    }

    public int getPlayer2Time() {
        return player2Time;
    }

    public void setPlayer2Time(int player2Time) {
        this.player2Time = player2Time;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

}
