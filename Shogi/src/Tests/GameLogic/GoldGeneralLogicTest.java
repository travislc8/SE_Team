package Tests.GameLogic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import GameLogic.GameData;
import GameLogic.GoldGeneralLogic;
import GameLogic.Piece;
import GameLogic.PieceLocation;
import GameLogic.PieceType;
import GameLogic.PlayerType;

public class GoldGeneralLogicTest {

    @Test
    public void SimpleWhiteMoveTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.GOLDGENERAL, 5, 5);
        var play2list = new ArrayList<Piece>();
        play2list.add(piece);
        data.setPlayer2Pieces(play2list);

        var list = GoldGeneralLogic.calculateMoves(piece, data);

        // no conflict
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

        // conflict
        data.getPlayerPieces(piece.getPlayer()).add(new Piece(PlayerType.WHITE, PieceType.PAWN, 5, 6));
        list = GoldGeneralLogic.calculateMoves(piece, data);
        message = "List size = " + list.size();
        assertTrue(message, list.size() == 5);
        // x,y
        // 5,6
        assertFalse("x=5,y=6", list.contains(new PieceLocation(5, 6, PlayerType.WHITE)));
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
    public void SimpleBlackMoveTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.BLACK, PieceType.GOLDGENERAL, 5, 5);
        var play1list = new ArrayList<Piece>();
        play1list.add(piece);
        data.setPlayer1Pieces(play1list);

        var list = GoldGeneralLogic.calculateMoves(piece, data);

        // no conflict
        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 6);
        // x,y
        // 5,4
        assertTrue("x=5,y=4", list.contains(new PieceLocation(5, 4, PlayerType.BLACK)));
        // 5,6
        assertTrue("x=5,y=6", list.contains(new PieceLocation(5, 6, PlayerType.BLACK)));
        // 6,4
        assertTrue("x=6,y=4", list.contains(new PieceLocation(6, 4, PlayerType.BLACK)));
        // 4,4
        assertTrue("x=4,y=4", list.contains(new PieceLocation(4, 4, PlayerType.BLACK)));
        // 6,5
        assertTrue("x=6,y=5", list.contains(new PieceLocation(6, 5, PlayerType.BLACK)));
        // 4,5
        assertTrue("x=4,y=5", list.contains(new PieceLocation(4, 5, PlayerType.BLACK)));

        // conflict
        data.getPlayerPieces(piece.getPlayer()).add(new Piece(PlayerType.BLACK, PieceType.PAWN, 5, 4));
        list = GoldGeneralLogic.calculateMoves(piece, data);
        message = "List size = " + list.size();
        assertTrue(message, list.size() == 5);
        // x,y
        // 5,4
        assertFalse("x=5,y=4", list.contains(new PieceLocation(5, 4, PlayerType.BLACK)));
        // 5,6
        assertTrue("x=5,y=6", list.contains(new PieceLocation(5, 6, PlayerType.BLACK)));
        // 6,4
        assertTrue("x=6,y=4", list.contains(new PieceLocation(6, 4, PlayerType.BLACK)));
        // 4,4
        assertTrue("x=4,y=4", list.contains(new PieceLocation(4, 4, PlayerType.BLACK)));
        // 6,5
        assertTrue("x=6,y=5", list.contains(new PieceLocation(6, 5, PlayerType.BLACK)));
        // 4,5
        assertTrue("x=4,y=5", list.contains(new PieceLocation(4, 5, PlayerType.BLACK)));

    }

    @Test
    public void PieceConflictTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.GOLDGENERAL, 0, 0);
        var play2list = new ArrayList<Piece>();
        play2list.add(piece);
        play2list.add(new Piece(PlayerType.WHITE, PieceType.ROOK, 0, 1));
        data.setPlayer2Pieces(play2list);

        var list = GoldGeneralLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 2);
    }

    @Test
    public void LocationOutOfBoundsTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.GOLDGENERAL, 8, 8);
        var play2list = new ArrayList<Piece>();
        play2list.add(piece);
        data.setPlayer2Pieces(play2list);

        var list = GoldGeneralLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 2);
    }

    @Test
    public void PromotedMovesTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.GOLDGENERAL, 5, 5);
        piece.setPromoted(true);
        var play2list = new ArrayList<Piece>();
        play2list.add(piece);
        data.setPlayer2Pieces(play2list);

        var list = GoldGeneralLogic.calculateMoves(piece, data);

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
        Piece piece = new Piece(PlayerType.WHITE, PieceType.GOLDGENERAL, 0, 0);
        Piece onBoardPiece = new Piece(PlayerType.WHITE, PieceType.GOLDGENERAL, 1, 0);
        piece.setOnBoard(false);
        var play2list = new ArrayList<Piece>();
        play2list.add(onBoardPiece);
        data.setPlayer2Pieces(play2list);

        var list = GoldGeneralLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 80);
        assertFalse("no contain", list.contains(new PieceLocation(1, 0, PlayerType.WHITE)));
    }

}
