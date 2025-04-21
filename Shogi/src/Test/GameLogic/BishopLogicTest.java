package Test.GameLogic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import GameLogic.GameData;
import GameLogic.BishopLogic;
import GameLogic.Piece;
import GameLogic.PieceLocation;
import GameLogic.PieceType;
import GameLogic.PlayerType;

public class BishopLogicTest {
    @Test
    public void SimpleWhiteMoveTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.BISHOP, 5, 5);
        var play2list = new ArrayList<Piece>();
        play2list.add(piece);
        data.setPlayer2Pieces(play2list);

        var list = BishopLogic.calculateMoves(piece, data);

        // no conflict
        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 14);
        // x,y
        // 0,0
        assertTrue("x=0,y=0", list.contains(new PieceLocation(0, 0, PlayerType.WHITE)));
        // 1,1
        assertTrue("x=1,y=1", list.contains(new PieceLocation(1, 1, PlayerType.WHITE)));
        // 2,2
        assertTrue("x=2,y=2", list.contains(new PieceLocation(2, 2, PlayerType.WHITE)));
        // 3,3
        assertTrue("x=3,y=3", list.contains(new PieceLocation(3, 3, PlayerType.WHITE)));
        // 4,4
        assertTrue("x=4,y=4", list.contains(new PieceLocation(4, 4, PlayerType.WHITE)));
        // 6,6
        assertTrue("x=6,y=6", list.contains(new PieceLocation(6, 6, PlayerType.WHITE)));
        // 7,7
        assertTrue("x=7,y=7", list.contains(new PieceLocation(7, 7, PlayerType.WHITE)));
        // 8,8
        assertTrue("x=8,y=8", list.contains(new PieceLocation(8, 8, PlayerType.WHITE)));

        // 8,2
        assertTrue("x=8,y=2", list.contains(new PieceLocation(8, 2, PlayerType.WHITE)));
        // 7,3
        assertTrue("x=7,y=3", list.contains(new PieceLocation(7, 3, PlayerType.WHITE)));
        // 6,4
        assertTrue("x=6,y=4", list.contains(new PieceLocation(6, 4, PlayerType.WHITE)));
        // 4,6
        assertTrue("x=4,y=6", list.contains(new PieceLocation(4, 6, PlayerType.WHITE)));
        // 3,7
        assertTrue("x=3,y=7", list.contains(new PieceLocation(3, 7, PlayerType.WHITE)));
        // 2,8
        assertTrue("x=2,y=8", list.contains(new PieceLocation(2, 8, PlayerType.WHITE)));

        // conflict
        data.getPlayerPieces(piece.getPlayer()).add(new Piece(PlayerType.WHITE, PieceType.PAWN, 2, 2));
        list = BishopLogic.calculateMoves(piece, data);
        message = "List size = " + list.size();
        assertTrue(message, list.size() == 11);
        // x,y
        // 3,3
        assertTrue("x=3,y=3", list.contains(new PieceLocation(3, 3, PlayerType.WHITE)));
        // 4,4
        assertTrue("x=4,y=4", list.contains(new PieceLocation(4, 4, PlayerType.WHITE)));
        // 6,6
        assertTrue("x=6,y=6", list.contains(new PieceLocation(6, 6, PlayerType.WHITE)));
        // 7,7
        assertTrue("x=7,y=7", list.contains(new PieceLocation(7, 7, PlayerType.WHITE)));
        // 8,8
        assertTrue("x=8,y=8", list.contains(new PieceLocation(8, 8, PlayerType.WHITE)));

        // 8,2
        assertTrue("x=8,y=2", list.contains(new PieceLocation(8, 2, PlayerType.WHITE)));
        // 7,3
        assertTrue("x=7,y=3", list.contains(new PieceLocation(7, 3, PlayerType.WHITE)));
        // 6,4
        assertTrue("x=6,y=4", list.contains(new PieceLocation(6, 4, PlayerType.WHITE)));
        // 4,6
        assertTrue("x=4,y=6", list.contains(new PieceLocation(4, 6, PlayerType.WHITE)));
        // 3,7
        assertTrue("x=3,y=7", list.contains(new PieceLocation(3, 7, PlayerType.WHITE)));
        // 2,8
        assertTrue("x=2,y=8", list.contains(new PieceLocation(2, 8, PlayerType.WHITE)));

    }

    @Test
    public void SimpleBlackMoveTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.BLACK, PieceType.BISHOP, 5, 5);
        var play1list = new ArrayList<Piece>();
        play1list.add(piece);
        data.setPlayer1Pieces(play1list);

        var list = BishopLogic.calculateMoves(piece, data);

        // no conflict
        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 14);
        // x,y
        // 0,0
        assertTrue("x=0,y=0", list.contains(new PieceLocation(0, 0, PlayerType.BLACK)));
        // 1,1
        assertTrue("x=1,y=1", list.contains(new PieceLocation(1, 1, PlayerType.BLACK)));
        // 2,2
        assertTrue("x=2,y=2", list.contains(new PieceLocation(2, 2, PlayerType.BLACK)));
        // 3,3
        assertTrue("x=3,y=3", list.contains(new PieceLocation(3, 3, PlayerType.BLACK)));
        // 4,4
        assertTrue("x=4,y=4", list.contains(new PieceLocation(4, 4, PlayerType.BLACK)));
        // 6,6
        assertTrue("x=6,y=6", list.contains(new PieceLocation(6, 6, PlayerType.BLACK)));
        // 7,7
        assertTrue("x=7,y=7", list.contains(new PieceLocation(7, 7, PlayerType.BLACK)));
        // 8,8
        assertTrue("x=8,y=8", list.contains(new PieceLocation(8, 8, PlayerType.BLACK)));

        // 8,2
        assertTrue("x=8,y=2", list.contains(new PieceLocation(8, 2, PlayerType.BLACK)));
        // 7,3
        assertTrue("x=7,y=3", list.contains(new PieceLocation(7, 3, PlayerType.BLACK)));
        // 6,4
        assertTrue("x=6,y=4", list.contains(new PieceLocation(6, 4, PlayerType.BLACK)));
        // 4,6
        assertTrue("x=4,y=6", list.contains(new PieceLocation(4, 6, PlayerType.BLACK)));
        // 3,7
        assertTrue("x=3,y=7", list.contains(new PieceLocation(3, 7, PlayerType.BLACK)));
        // 2,8
        assertTrue("x=2,y=8", list.contains(new PieceLocation(2, 8, PlayerType.BLACK)));

        // conflict
        data.getPlayerPieces(piece.getPlayer()).add(new Piece(PlayerType.BLACK, PieceType.PAWN, 2, 2));
        list = BishopLogic.calculateMoves(piece, data);
        message = "List size = " + list.size();
        assertTrue(message, list.size() == 11);
        // x,y
        // 3,3
        assertTrue("x=3,y=3", list.contains(new PieceLocation(3, 3, PlayerType.BLACK)));
        // 4,4
        assertTrue("x=4,y=4", list.contains(new PieceLocation(4, 4, PlayerType.BLACK)));
        // 6,6
        assertTrue("x=6,y=6", list.contains(new PieceLocation(6, 6, PlayerType.BLACK)));
        // 7,7
        assertTrue("x=7,y=7", list.contains(new PieceLocation(7, 7, PlayerType.BLACK)));
        // 8,8
        assertTrue("x=8,y=8", list.contains(new PieceLocation(8, 8, PlayerType.BLACK)));

        // 8,2
        assertTrue("x=8,y=2", list.contains(new PieceLocation(8, 2, PlayerType.BLACK)));
        // 7,3
        assertTrue("x=7,y=3", list.contains(new PieceLocation(7, 3, PlayerType.BLACK)));
        // 6,4
        assertTrue("x=6,y=4", list.contains(new PieceLocation(6, 4, PlayerType.BLACK)));
        // 4,6
        assertTrue("x=4,y=6", list.contains(new PieceLocation(4, 6, PlayerType.BLACK)));
        // 3,7
        assertTrue("x=3,y=7", list.contains(new PieceLocation(3, 7, PlayerType.BLACK)));
        // 2,8
        assertTrue("x=2,y=8", list.contains(new PieceLocation(2, 8, PlayerType.BLACK)));

    }

    @Test
    public void PieceConflictTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.BLACK, PieceType.BISHOP, 8, 8);
        var play2list = new ArrayList<Piece>();
        play2list.add(new Piece(PlayerType.WHITE, PieceType.ROOK, 6, 6));
        data.setPlayer2Pieces(play2list);

        var list = BishopLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 2);
    }

    @Test
    public void LocationOutOfBoundsTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.BISHOP, 8, 8);
        var play2list = new ArrayList<Piece>();
        play2list.add(piece);
        data.setPlayer2Pieces(play2list);

        var list = BishopLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 8);
    }

    @Test
    public void PromotedMovesTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.BISHOP, 5, 5);
        piece.setPromoted(true);
        var play2list = new ArrayList<Piece>();
        play2list.add(piece);
        data.setPlayer2Pieces(play2list);

        var list = BishopLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 18);
        // x,y
        // 0,0
        assertTrue("x=0,y=0", list.contains(new PieceLocation(0, 0, PlayerType.WHITE)));
        // 1,1
        assertTrue("x=1,y=1", list.contains(new PieceLocation(1, 1, PlayerType.WHITE)));
        // 2,2
        assertTrue("x=2,y=2", list.contains(new PieceLocation(2, 2, PlayerType.WHITE)));
        // 3,3
        assertTrue("x=3,y=3", list.contains(new PieceLocation(3, 3, PlayerType.WHITE)));
        // 4,4
        assertTrue("x=4,y=4", list.contains(new PieceLocation(4, 4, PlayerType.WHITE)));
        // 6,6
        assertTrue("x=6,y=6", list.contains(new PieceLocation(6, 6, PlayerType.WHITE)));
        // 7,7
        assertTrue("x=7,y=7", list.contains(new PieceLocation(7, 7, PlayerType.WHITE)));
        // 8,8
        assertTrue("x=8,y=8", list.contains(new PieceLocation(8, 8, PlayerType.WHITE)));

        // 8,2
        assertTrue("x=8,y=2", list.contains(new PieceLocation(8, 2, PlayerType.WHITE)));
        // 7,3
        assertTrue("x=7,y=3", list.contains(new PieceLocation(7, 3, PlayerType.WHITE)));
        // 6,4
        assertTrue("x=6,y=4", list.contains(new PieceLocation(6, 4, PlayerType.WHITE)));
        // 4,6
        assertTrue("x=4,y=6", list.contains(new PieceLocation(4, 6, PlayerType.WHITE)));
        // 3,7
        assertTrue("x=3,y=7", list.contains(new PieceLocation(3, 7, PlayerType.WHITE)));
        // 2,8
        assertTrue("x=2,y=8", list.contains(new PieceLocation(2, 8, PlayerType.WHITE)));

        // 5,4
        assertTrue("x=5,y=4", list.contains(new PieceLocation(5, 4, PlayerType.WHITE)));
        // 5,6
        assertTrue("x=5,y=6", list.contains(new PieceLocation(5, 6, PlayerType.WHITE)));
        // 4,5
        assertTrue("x=4,y=5", list.contains(new PieceLocation(4, 5, PlayerType.WHITE)));
        // 6,5
        assertTrue("x=6,y=5", list.contains(new PieceLocation(6, 5, PlayerType.WHITE)));
    }

    @Test
    public void freePlacementMovesTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.BISHOP, 0, 0);
        Piece onBoardPiece = new Piece(PlayerType.WHITE, PieceType.BISHOP, 1, 0);
        piece.setOnBoard(false);
        var play2list = new ArrayList<Piece>();
        play2list.add(onBoardPiece);
        data.setPlayer2Pieces(play2list);

        var list = BishopLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 80);
        assertFalse("no contain", list.contains(new PieceLocation(1, 0, PlayerType.WHITE)));
    }

}
