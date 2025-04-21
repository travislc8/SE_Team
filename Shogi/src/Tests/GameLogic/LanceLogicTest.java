package Tests.GameLogic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import GameLogic.GameData;
import GameLogic.LanceLogic;
import GameLogic.Piece;
import GameLogic.PieceLocation;
import GameLogic.PieceType;
import GameLogic.PlayerType;

public class LanceLogicTest {
    @Test
    public void SimpleWhiteMoveTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.LANCE, 5, 5);
        var play2list = new ArrayList<Piece>();
        play2list.add(piece);
        data.setPlayer2Pieces(play2list);

        var list = LanceLogic.calculateMoves(piece, data);

        // no conflict
        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 3);
        // x,y
        // 5,6
        assertTrue("x=5,y=6", list.contains(new PieceLocation(5, 6, PlayerType.WHITE)));
        // 5,7
        assertTrue("x=5,y=7", list.contains(new PieceLocation(5, 7, PlayerType.WHITE)));
        // 5,8
        assertTrue("x=5,y=8", list.contains(new PieceLocation(5, 8, PlayerType.WHITE)));

        // conflict
        data.getPlayerPieces(piece.getPlayer()).add(new Piece(PlayerType.WHITE, PieceType.PAWN, 5, 7));
        list = LanceLogic.calculateMoves(piece, data);
        message = "List size = " + list.size();
        assertTrue(message, list.size() == 1);
        // x,y
        // 5,6
        assertTrue("x=5,y=6", list.contains(new PieceLocation(5, 6, PlayerType.WHITE)));

    }

    @Test
    public void SimpleBlackMoveTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.BLACK, PieceType.LANCE, 5, 5);
        var play1list = new ArrayList<Piece>();
        play1list.add(piece);
        data.setPlayer1Pieces(play1list);

        var list = LanceLogic.calculateMoves(piece, data);

        // no conflict
        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 5);
        // x,y
        // 5,4
        assertTrue("x=5,y=4", list.contains(new PieceLocation(5, 4, PlayerType.BLACK)));
        // 5,3
        assertTrue("x=5,y=3", list.contains(new PieceLocation(5, 3, PlayerType.BLACK)));
        // 5,2
        assertTrue("x=5,y=2", list.contains(new PieceLocation(5, 2, PlayerType.BLACK)));
        // 5,1
        assertTrue("x=5,y=1", list.contains(new PieceLocation(5, 1, PlayerType.BLACK)));
        // 5,0
        assertTrue("x=5,y=0", list.contains(new PieceLocation(5, 0, PlayerType.BLACK)));

        // conflict
        data.getPlayerPieces(piece.getPlayer()).add(new Piece(PlayerType.BLACK, PieceType.PAWN, 5, 2));
        list = LanceLogic.calculateMoves(piece, data);
        message = "List size = " + list.size();
        assertTrue(message, list.size() == 2);
        // x,y
        // 5,4
        assertTrue("x=5,y=4", list.contains(new PieceLocation(5, 4, PlayerType.BLACK)));
        // 5,3
        assertTrue("x=5,y=3", list.contains(new PieceLocation(5, 3, PlayerType.BLACK)));

    }

    @Test
    public void PieceConflictTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.BLACK, PieceType.LANCE, 8, 8);
        var play2list = new ArrayList<Piece>();
        play2list.add(new Piece(PlayerType.WHITE, PieceType.ROOK, 8, 7));
        data.setPlayer2Pieces(play2list);

        var list = LanceLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 1);
    }

    @Test
    public void LocationOutOfBoundsTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.LANCE, 8, 8);
        var play2list = new ArrayList<Piece>();
        play2list.add(piece);
        data.setPlayer2Pieces(play2list);

        var list = LanceLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 0);
    }

    @Test
    public void PromotedMovesTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.LANCE, 5, 5);
        piece.setPromoted(true);
        var play2list = new ArrayList<Piece>();
        play2list.add(piece);
        data.setPlayer2Pieces(play2list);

        var list = LanceLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 6);
        // x,y
        // 5,6
        assertTrue("x=5,y=6", list.contains(new PieceLocation(5, 6, PlayerType.WHITE)));
        // 5,4
        assertTrue("x=5,y=4", list.contains(new PieceLocation(5, 4, PlayerType.WHITE)));
        // 6,6
        assertTrue("x=6,y=6", list.contains(new PieceLocation(6, 6, PlayerType.WHITE)));
        // 4,6
        assertTrue("x=4,y=6", list.contains(new PieceLocation(4, 6, PlayerType.WHITE)));
        // 6,5
        assertTrue("x=6,y=5", list.contains(new PieceLocation(6, 5, PlayerType.WHITE)));
        // 4,5
        assertTrue("x=4,y=5", list.contains(new PieceLocation(4, 5, PlayerType.WHITE)));

    }

    @Test
    public void freePlacementMovesTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.LANCE, 0, 0);
        Piece onBoardPiece = new Piece(PlayerType.WHITE, PieceType.LANCE, 1, 0);
        piece.setOnBoard(false);
        var play2list = new ArrayList<Piece>();
        play2list.add(onBoardPiece);
        data.setPlayer2Pieces(play2list);

        var list = LanceLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 80);
        assertFalse("no contain", list.contains(new PieceLocation(1, 0, PlayerType.WHITE)));
    }

}
