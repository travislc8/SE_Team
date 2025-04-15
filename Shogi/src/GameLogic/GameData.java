package GameLogic;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;

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

    boolean gameStarted;
    long lastMoveTime;

    public GameData() {
        player1Type = PlayerType.BLACK;
        player2Type = PlayerType.WHITE;
        player1Pieces = new ArrayList<>();
        player2Pieces = new ArrayList<>();
        player1Hand = new ArrayList<>();
        player2Hand = new ArrayList<>();
        moveList = new ArrayList<>();

    }

    public void newGame() {
        activePlayer = PlayerType.BLACK;
        moveList = new ArrayList<>();
        player1Hand = new ArrayList<>();
        player2Hand = new ArrayList<>();
        player1Time = 300;
        player2Time = 300;
        gameOver = false;

        setPieces();
    }

    private void setPieces() {
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.PAWN, 0, 2));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.PAWN, 1, 2));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.PAWN, 2, 2));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.PAWN, 3, 2));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.PAWN, 4, 2));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.PAWN, 5, 2));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.PAWN, 6, 2));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.PAWN, 7, 2));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.PAWN, 8, 2));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.ROOK, 1, 1));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.BISHOP, 7, 1));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.LANCE, 0, 0));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.LANCE, 8, 0));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.KNIGHT, 1, 0));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.KNIGHT, 7, 0));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.SILVERGENERAL, 6, 0));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.SILVERGENERAL, 2, 0));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.GOLDGENERAL, 5, 0));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.GOLDGENERAL, 3, 0));
        player2Pieces.add(new Piece(PlayerType.WHITE, PieceType.KING, 4, 0));

        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.PAWN, 0, 6));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.PAWN, 1, 6));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.PAWN, 2, 6));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.PAWN, 3, 6));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.PAWN, 4, 6));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.PAWN, 5, 6));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.PAWN, 6, 6));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.PAWN, 7, 6));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.PAWN, 8, 6));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.ROOK, 7, 7));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.BISHOP, 1, 7));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.LANCE, 0, 8));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.LANCE, 8, 8));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.KNIGHT, 1, 8));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.KNIGHT, 7, 8));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.SILVERGENERAL, 6, 8));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.SILVERGENERAL, 2, 8));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.GOLDGENERAL, 5, 8));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.GOLDGENERAL, 3, 8));
        player1Pieces.add(new Piece(PlayerType.BLACK, PieceType.KING, 4, 8));
    }

    public void changeTurn() {
        long currentTime = System.currentTimeMillis();
        if (!gameStarted) {
            gameStarted = true;
            lastMoveTime = currentTime;
        }

        int elapsedTime = (int) (currentTime - lastMoveTime);
        if (activePlayer == PlayerType.BLACK) {
            player1Time -= elapsedTime;
            activePlayer = PlayerType.WHITE;
        } else {
            player2Time -= elapsedTime;
            activePlayer = PlayerType.BLACK;
        }

        lastMoveTime = currentTime;
    }

    public void updateEndGame() {
        for (var piece : getPlayerPieces(activePlayer)) {
            if (piece.getAvailableMoves().size() > 0) {
                gameOver = false;
                return;
            }
        }
        for (var piece : getPlayerHand(activePlayer)) {
            if (piece.getAvailableMoves().size() > 0) {
                gameOver = false;
                return;
            }
        }
        // if all pieces have no moves
        gameOver = true;
    }

    public void updateTimer() {
        long currentTime = System.currentTimeMillis();
        if (!gameStarted) {
            gameStarted = true;
            lastMoveTime = currentTime;
        }

        int elapsedTime = (int) (currentTime - lastMoveTime);
        if (activePlayer == PlayerType.BLACK) {
            player1Time -= elapsedTime;
        } else {
            player2Time -= elapsedTime;
        }
    }

    public boolean makeMove(Move move) {
        if (move.getPlayer() != activePlayer) {
            throw new IllegalStateException("Move player must be the same as the active player");
        }

        Piece movePiece = null;
        ArrayList<Piece> playerHand = getPlayerHand(activePlayer);
        //if the piece is NOT on the board
        if (move.getStartLocation().getxPos() == -1 && move.getStartLocation().getyPos() == -1) {
        	//select the first piece in hand of the same type
        	for (Piece piece : playerHand) {
        		if (piece.getPieceType() == move.getPieceType() && !piece.isOnBoard()) {
        			movePiece = piece;
        			break;
        		}
        	}
        	
        } else {
        	//if the piece IS on the board
        	movePiece = getPieceAt(move.getStartLocation());
        }
        
        

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
        if (!movePiece.isOnBoard()) {
        	movePiece.setOnBoard(true);
			getPlayerHand(activePlayer).remove(movePiece);
			getPlayerPieces(activePlayer).add(movePiece);
        }

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

    public GameData deepCopy() {
        GameData copy = new GameData();

        copy.activePlayer = this.activePlayer;
        copy.gameOver = this.gameOver;
        copy.player1Time = this.player1Time;
        copy.player2Time = this.player2Time;
        copy.gameId = this.gameId;
        copy.gameStarted = this.gameStarted;
        copy.lastMoveTime = this.lastMoveTime;

        var p1p = new ArrayList<Piece>();
        for (var piece : this.getPlayer1Pieces()) {
            p1p.add(piece.deepCopy());

        }
        copy.setPlayer1Pieces(p1p);

        var p2p = new ArrayList<Piece>();
        for (var piece : this.getPlayer2Pieces()) {
            p2p.add(piece.deepCopy());

        }
        copy.setPlayer2Pieces(p2p);

        var p1h = new ArrayList<Piece>();
        for (var piece : this.getPlayer1Hand()) {
            p1h.add(piece.deepCopy());

        }
        copy.setPlayer1Hand(p1h);

        var p2h = new ArrayList<Piece>();
        for (var piece : this.getPlayer2Hand()) {
            p2h.add(piece.deepCopy());

        }
        copy.setPlayer2Hand(p2h);

        var ml = new ArrayList<Move>();
        for (var move : this.getMoveList()) {
            ml.add(move.deepCopy());
        }
        copy.setMoveList(ml);

        return copy;
    }

}
