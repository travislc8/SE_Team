package Test.GameLogic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import GameLogic.GameData;
import GameLogic.PawnLogic;
import GameLogic.Piece;
import GameLogic.PieceLocation;
import GameLogic.PieceType;
import GameLogic.PlayerType;

public class PawnLogicTest {

    @Test
    public void SimpleWhiteMoveTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.PAWN, 0, 0);
        var play2list = new ArrayList<Piece>();
        play2list.add(piece);
        data.setPlayer2Pieces(play2list);

        var list = PawnLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 1);
        assertTrue("x check", list.get(0).getxPos() == 0);
        message = "y = " + list.get(0).getyPos();
        assertTrue(message, list.get(0).getyPos() == 1);
    }

    @Test
    public void SimpleBlackMoveTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.BLACK, PieceType.PAWN, 0, 8);
        var play1list = new ArrayList<Piece>();
        play1list.add(piece);
        data.setPlayer1Pieces(play1list);

        var list = PawnLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 1);
        assertTrue("x check", list.get(0).getxPos() == 0);
        message = "y = " + list.get(0).getyPos();
        assertTrue(message, list.get(0).getyPos() == 7);
    }

    @Test
    public void PieceConflictTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.BLACK, PieceType.PAWN, 0, 0);
        var play1list = new ArrayList<Piece>();
        play1list.add(piece);
        play1list.add(new Piece(PlayerType.BLACK, PieceType.ROOK, 0, 1));
        data.setPlayer1Pieces(play1list);

        var list = PawnLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 0);
    }

    @Test
    public void LocationOutOfBoundsTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.PAWN, 8, 8);
        var play2list = new ArrayList<Piece>();
        play2list.add(piece);
        data.setPlayer2Pieces(play2list);

        var list = PawnLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 0);
    }

    @Test
    public void PromotedMovesTest() {
        GameData data = new GameData();
        Piece piece = new Piece(PlayerType.WHITE, PieceType.PAWN, 5, 5);
        piece.setPromoted(true);
        var play2list = new ArrayList<Piece>();
        play2list.add(piece);
        data.setPlayer2Pieces(play2list);

        var list = PawnLogic.calculateMoves(piece, data);

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
        Piece piece = new Piece(PlayerType.BLACK, PieceType.PAWN, 0, 0);
        Piece onBoardPiece = new Piece(PlayerType.BLACK, PieceType.PAWN, 1, 0);
        piece.setOnBoard(false);
        var play1list = new ArrayList<Piece>();
        play1list.add(onBoardPiece);
        data.setPlayer1Pieces(play1list);

        var list = PawnLogic.calculateMoves(piece, data);

        String message = "List size = " + list.size();
        assertTrue(message, list.size() == 72);
        for (var location : list) {
            assertFalse(location.getxPos() == 1);
        }
    }

}
