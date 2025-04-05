package ShogiTest.GameLogic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import GameLogic.GameData;
import GameLogic.RookLogic;
import GameLogic.Piece;
import GameLogic.PieceLocation;
import GameLogic.PieceType;
import GameLogic.PlayerType;

public class RookLogicTest {
    @Test
    public void SimpleWhiteMoveTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.ROOK, 5, 5);
        var play1list = new ArrayList<Piece>();
        play1list.add(piece);
        data.setPlayer1Pieces(play1list);

        var list = RookLogic.calculateMoves(piece, data);

        // no conflict
        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 16);
        // x,y
        // 5,0
        assertTrue("x=5,y=0", list.contains(new PieceLocation(5, 0, PlayerType.WHITE)));
        // 5,1
        assertTrue("x=5,y=1", list.contains(new PieceLocation(5, 1, PlayerType.WHITE)));
        // 5,2
        assertTrue("x=5,y=2", list.contains(new PieceLocation(5, 2, PlayerType.WHITE)));
        // 5,3
        assertTrue("x=5,y=3", list.contains(new PieceLocation(5, 3, PlayerType.WHITE)));
        // 5,4
        assertTrue("x=5,y=4", list.contains(new PieceLocation(5, 4, PlayerType.WHITE)));
        // 5,6
        assertTrue("x=5,y=6", list.contains(new PieceLocation(5, 6, PlayerType.WHITE)));
        // 5,7
        assertTrue("x=5,y=7", list.contains(new PieceLocation(5, 7, PlayerType.WHITE)));
        // 5,8
        assertTrue("x=5,y=8", list.contains(new PieceLocation(5, 8, PlayerType.WHITE)));

        // 0,5
        assertTrue("x=0,y=5", list.contains(new PieceLocation(0, 5, PlayerType.WHITE)));
        // 1,5
        assertTrue("x=1,y=5", list.contains(new PieceLocation(1, 5, PlayerType.WHITE)));
        // 2,5
        assertTrue("x=2,y=5", list.contains(new PieceLocation(2, 5, PlayerType.WHITE)));
        // 3,5
        assertTrue("x=3,y=5", list.contains(new PieceLocation(3, 5, PlayerType.WHITE)));
        // 4,5
        assertTrue("x=4,y=5", list.contains(new PieceLocation(4, 5, PlayerType.WHITE)));
        // 6,5
        assertTrue("x=6,y=5", list.contains(new PieceLocation(6, 5, PlayerType.WHITE)));
        // 7,5
        assertTrue("x=7,y=5", list.contains(new PieceLocation(7, 5, PlayerType.WHITE)));
        // 8,5
        assertTrue("x=8,y=5", list.contains(new PieceLocation(8, 5, PlayerType.WHITE)));

        // conflict
        data.getPlayer1Pieces().add(new Piece(PlayerType.WHITE, PieceType.PAWN, 5, 2));
        list = RookLogic.calculateMoves(piece, data);
        message = "List size = " + list.size();
        assertTrue(message, list.size() == 13);
        // x,y
        // 5,3
        assertTrue("x=5,y=3", list.contains(new PieceLocation(5, 3, PlayerType.WHITE)));
        // 5,4
        assertTrue("x=5,y=4", list.contains(new PieceLocation(5, 4, PlayerType.WHITE)));
        // 5,6
        assertTrue("x=5,y=6", list.contains(new PieceLocation(5, 6, PlayerType.WHITE)));
        // 5,7
        assertTrue("x=5,y=7", list.contains(new PieceLocation(5, 7, PlayerType.WHITE)));
        // 5,8
        assertTrue("x=5,y=8", list.contains(new PieceLocation(5, 8, PlayerType.WHITE)));

        // 0,5
        assertTrue("x=0,y=5", list.contains(new PieceLocation(0, 5, PlayerType.WHITE)));
        // 1,5
        assertTrue("x=1,y=5", list.contains(new PieceLocation(1, 5, PlayerType.WHITE)));
        // 2,5
        assertTrue("x=2,y=5", list.contains(new PieceLocation(2, 5, PlayerType.WHITE)));
        // 3,5
        assertTrue("x=3,y=5", list.contains(new PieceLocation(3, 5, PlayerType.WHITE)));
        // 4,5
        assertTrue("x=4,y=5", list.contains(new PieceLocation(4, 5, PlayerType.WHITE)));
        // 6,5
        assertTrue("x=6,y=5", list.contains(new PieceLocation(6, 5, PlayerType.WHITE)));
        // 7,5
        assertTrue("x=7,y=5", list.contains(new PieceLocation(7, 5, PlayerType.WHITE)));
        // 8,5
        assertTrue("x=8,y=5", list.contains(new PieceLocation(8, 5, PlayerType.WHITE)));

    }

    @Test
    public void SimpleBlackMoveTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.BLACK, PieceType.ROOK, 5, 5);
        var play1list = new ArrayList<Piece>();
        play1list.add(piece);
        data.setPlayer1Pieces(play1list);

        var list = RookLogic.calculateMoves(piece, data);

        // no conflict
        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 16);
        // x,y
        // 5,0
        assertTrue("x=5,y=0", list.contains(new PieceLocation(5, 0, PlayerType.BLACK)));
        // 5,1
        assertTrue("x=5,y=1", list.contains(new PieceLocation(5, 1, PlayerType.BLACK)));
        // 5,2
        assertTrue("x=5,y=2", list.contains(new PieceLocation(5, 2, PlayerType.BLACK)));
        // 5,3
        assertTrue("x=5,y=3", list.contains(new PieceLocation(5, 3, PlayerType.BLACK)));
        // 5,4
        assertTrue("x=5,y=4", list.contains(new PieceLocation(5, 4, PlayerType.BLACK)));
        // 5,6
        assertTrue("x=5,y=6", list.contains(new PieceLocation(5, 6, PlayerType.BLACK)));
        // 5,7
        assertTrue("x=5,y=7", list.contains(new PieceLocation(5, 7, PlayerType.BLACK)));
        // 5,8
        assertTrue("x=5,y=8", list.contains(new PieceLocation(5, 8, PlayerType.BLACK)));

        // 0,5
        assertTrue("x=0,y=5", list.contains(new PieceLocation(0, 5, PlayerType.BLACK)));
        // 1,5
        assertTrue("x=1,y=5", list.contains(new PieceLocation(1, 5, PlayerType.BLACK)));
        // 2,5
        assertTrue("x=2,y=5", list.contains(new PieceLocation(2, 5, PlayerType.BLACK)));
        // 3,5
        assertTrue("x=3,y=5", list.contains(new PieceLocation(3, 5, PlayerType.BLACK)));
        // 4,5
        assertTrue("x=4,y=5", list.contains(new PieceLocation(4, 5, PlayerType.BLACK)));
        // 6,5
        assertTrue("x=6,y=5", list.contains(new PieceLocation(6, 5, PlayerType.BLACK)));
        // 7,5
        assertTrue("x=7,y=5", list.contains(new PieceLocation(7, 5, PlayerType.BLACK)));
        // 8,5
        assertTrue("x=8,y=5", list.contains(new PieceLocation(8, 5, PlayerType.BLACK)));

        // conflict
        data.getPlayer1Pieces().add(new Piece(PlayerType.BLACK, PieceType.PAWN, 5, 2));
        list = RookLogic.calculateMoves(piece, data);
        message = "List size = " + list.size();
        assertTrue(message, list.size() == 13);
        // x,y
        // 5,3
        assertTrue("x=5,y=3", list.contains(new PieceLocation(5, 3, PlayerType.BLACK)));
        // 5,4
        assertTrue("x=5,y=4", list.contains(new PieceLocation(5, 4, PlayerType.BLACK)));
        // 5,6
        assertTrue("x=5,y=6", list.contains(new PieceLocation(5, 6, PlayerType.BLACK)));
        // 5,7
        assertTrue("x=5,y=7", list.contains(new PieceLocation(5, 7, PlayerType.BLACK)));
        // 5,8
        assertTrue("x=5,y=8", list.contains(new PieceLocation(5, 8, PlayerType.BLACK)));

        // 0,5
        assertTrue("x=0,y=5", list.contains(new PieceLocation(0, 5, PlayerType.BLACK)));
        // 1,5
        assertTrue("x=1,y=5", list.contains(new PieceLocation(1, 5, PlayerType.BLACK)));
        // 2,5
        assertTrue("x=2,y=5", list.contains(new PieceLocation(2, 5, PlayerType.BLACK)));
        // 3,5
        assertTrue("x=3,y=5", list.contains(new PieceLocation(3, 5, PlayerType.BLACK)));
        // 4,5
        assertTrue("x=4,y=5", list.contains(new PieceLocation(4, 5, PlayerType.BLACK)));
        // 6,5
        assertTrue("x=6,y=5", list.contains(new PieceLocation(6, 5, PlayerType.BLACK)));
        // 7,5
        assertTrue("x=7,y=5", list.contains(new PieceLocation(7, 5, PlayerType.BLACK)));
        // 8,5
        assertTrue("x=8,y=5", list.contains(new PieceLocation(8, 5, PlayerType.BLACK)));

    }

    @Test
    public void PieceConflictTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.ROOK, 0, 0);
        var play1list = new ArrayList<Piece>();
        play1list.add(piece);
        play1list.add(new Piece(PlayerType.WHITE, PieceType.ROOK, 0, 1));
        data.setPlayer1Pieces(play1list);

        var list = RookLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 8);
    }

    @Test
    public void LocationOutOfBoundsTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.ROOK, 8, 8);
        var play1list = new ArrayList<Piece>();
        play1list.add(piece);
        data.setPlayer1Pieces(play1list);

        var list = RookLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 16);
    }

    @Test
    public void PromotedMovesTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.ROOK, 5, 5);
        piece.setPromoted(true);
        var play1list = new ArrayList<Piece>();
        play1list.add(piece);
        data.setPlayer1Pieces(play1list);

        var list = RookLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 20);
        // x,y
        // 5,0
        assertTrue("x=5,y=0", list.contains(new PieceLocation(5, 0, PlayerType.WHITE)));
        // 5,1
        assertTrue("x=5,y=1", list.contains(new PieceLocation(5, 1, PlayerType.WHITE)));
        // 5,2
        assertTrue("x=5,y=2", list.contains(new PieceLocation(5, 2, PlayerType.WHITE)));
        // 5,3
        assertTrue("x=5,y=3", list.contains(new PieceLocation(5, 3, PlayerType.WHITE)));
        // 5,4
        assertTrue("x=5,y=4", list.contains(new PieceLocation(5, 4, PlayerType.WHITE)));
        // 5,6
        assertTrue("x=5,y=6", list.contains(new PieceLocation(5, 6, PlayerType.WHITE)));
        // 5,7
        assertTrue("x=5,y=7", list.contains(new PieceLocation(5, 7, PlayerType.WHITE)));
        // 5,8
        assertTrue("x=5,y=8", list.contains(new PieceLocation(5, 8, PlayerType.WHITE)));

        // 0,5
        assertTrue("x=0,y=5", list.contains(new PieceLocation(0, 5, PlayerType.WHITE)));
        // 1,5
        assertTrue("x=1,y=5", list.contains(new PieceLocation(1, 5, PlayerType.WHITE)));
        // 2,5
        assertTrue("x=2,y=5", list.contains(new PieceLocation(2, 5, PlayerType.WHITE)));
        // 3,5
        assertTrue("x=3,y=5", list.contains(new PieceLocation(3, 5, PlayerType.WHITE)));
        // 4,5
        assertTrue("x=4,y=5", list.contains(new PieceLocation(4, 5, PlayerType.WHITE)));
        // 6,5
        assertTrue("x=6,y=5", list.contains(new PieceLocation(6, 5, PlayerType.WHITE)));
        // 7,5
        assertTrue("x=7,y=5", list.contains(new PieceLocation(7, 5, PlayerType.WHITE)));
        // 8,5
        assertTrue("x=8,y=5", list.contains(new PieceLocation(8, 5, PlayerType.WHITE)));

        // 4,4
        assertTrue("x=5,y=4", list.contains(new PieceLocation(5, 4, PlayerType.WHITE)));
        // 6,6
        assertTrue("x=5,y=6", list.contains(new PieceLocation(5, 6, PlayerType.WHITE)));
        // 4,6
        assertTrue("x=4,y=5", list.contains(new PieceLocation(4, 5, PlayerType.WHITE)));
        // 6,4
        assertTrue("x=6,y=5", list.contains(new PieceLocation(6, 5, PlayerType.WHITE)));
    }

    @Test
    public void freePlacementMovesTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.ROOK, 0, 0);
        Piece onBoardPiece = new Piece(PlayerType.WHITE, PieceType.ROOK, 1, 0);
        piece.setOnBoard(false);
        var play1list = new ArrayList<Piece>();
        play1list.add(onBoardPiece);
        data.setPlayer1Pieces(play1list);

        var list = RookLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 80);
        assertFalse("no contain", list.contains(new PieceLocation(1, 0, PlayerType.WHITE)));
    }

}
