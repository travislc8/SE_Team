package Test.GameLogic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import GameLogic.GameData;
import GameLogic.MoveCalculator;
import GameLogic.Piece;
import GameLogic.PieceLocation;
import GameLogic.PieceType;
import GameLogic.PlayerType;

public class MoveCalculatorTest {

    @Test
    public void GetSafeKingMovesTest() {
        GameData gameData = new GameData();
        gameData.setActivePlayer(PlayerType.BLACK);
        var p1list = new ArrayList<Piece>();
        var p2list = new ArrayList<Piece>();

        // p1
        p1list.add(new Piece(PlayerType.BLACK,
                PieceType.KING,
                new PieceLocation(5, 5, PlayerType.BLACK)));
        p1list.add(new Piece(PlayerType.BLACK,
                PieceType.PAWN,
                new PieceLocation(0, 1, PlayerType.BLACK)));
        gameData.setPlayer1Pieces(p1list);

        // p2
        p2list.add(new Piece(PlayerType.WHITE,
                PieceType.PAWN,
                new PieceLocation(5, 3, PlayerType.BLACK)));
        gameData.setPlayer2Pieces(p2list);

        var mc = new MoveCalculator();
        mc.calculateAvailableMoves(gameData);

        p1list = gameData.getPlayer1Pieces();
        String msg = "";
        for (var piece : p1list) {
            if (piece.getPieceType() == PieceType.KING) {

                msg = "count = " + piece.getAvailableMoves().size();
                assertTrue(msg, piece.getAvailableMoves().size() == 7);
                msg = "should not contain 5,4";
                assertFalse(msg, piece.getAvailableMoves().contains(new PieceLocation(5, 4, piece.getPlayer())));
            } else if (piece.getPieceType() == PieceType.PAWN) {
                msg = "Pawn count = " + piece.getAvailableMoves().size();
                assertTrue(msg, piece.getAvailableMoves().size() == 1);
                msg = "should contian 0,1";
                assertTrue(msg, piece.getAvailableMoves().contains(new PieceLocation(0, 0, piece.getPlayer())));
            }
        }

    }

    @Test
    public void DefendKingTest() {
        GameData gameData = new GameData();
        gameData.setActivePlayer(PlayerType.BLACK);
        var p1list = new ArrayList<Piece>();
        var p2list = new ArrayList<Piece>();

        // p1
        p1list.add(new Piece(PlayerType.BLACK,
                PieceType.KING,
                new PieceLocation(5, 5, PlayerType.BLACK)));
        p1list.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                new PieceLocation(6, 5, PlayerType.BLACK)));
        gameData.setPlayer1Pieces(p1list);

        // p2
        p2list.add(new Piece(PlayerType.WHITE,
                PieceType.ROOK,
                new PieceLocation(5, 3, PlayerType.BLACK)));
        gameData.setPlayer2Pieces(p2list);

        var mc = new MoveCalculator();
        mc.calculateAvailableMoves(gameData);

        p1list = gameData.getPlayer1Pieces();
        String msg = "";
        for (var piece : p1list) {
            if (piece.getPieceType() == PieceType.KING) {

                msg = "count = " + piece.getAvailableMoves().size();
                assertTrue(msg, piece.getAvailableMoves().size() == 5);
                msg = "should not contain 5,4";
                assertFalse(msg, piece.getAvailableMoves().contains(new PieceLocation(5, 4, piece.getPlayer())));
                msg = "should not contain 6,5";
                assertFalse(msg, piece.getAvailableMoves().contains(new PieceLocation(6, 5, piece.getPlayer())));
                msg = "should not contain 5,6";
                assertFalse(msg, piece.getAvailableMoves().contains(new PieceLocation(5, 6, piece.getPlayer())));
            } else if (piece.getPieceType() == PieceType.SILVERGENERAL) {
                msg = "Move count = " + piece.getAvailableMoves().size();
                assertTrue(msg, piece.getAvailableMoves().size() == 1);
                msg = "should contian 5,4";
                assertTrue(msg, piece.getAvailableMoves().contains(new PieceLocation(5, 4, piece.getPlayer())));
            }
        }

    }

    @Test
    public void SaveByTakeTest() {
        GameData gameData = new GameData();
        gameData.setActivePlayer(PlayerType.BLACK);
        var p1list = new ArrayList<Piece>();
        var p2list = new ArrayList<Piece>();

        // p1
        p1list.add(new Piece(PlayerType.BLACK,
                PieceType.KING,
                new PieceLocation(5, 5, PlayerType.BLACK)));
        p1list.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                new PieceLocation(6, 4, PlayerType.BLACK)));
        gameData.setPlayer1Pieces(p1list);

        // p2
        p2list.add(new Piece(PlayerType.WHITE,
                PieceType.ROOK,
                new PieceLocation(5, 3, PlayerType.BLACK)));
        gameData.setPlayer2Pieces(p2list);

        var mc = new MoveCalculator();
        mc.calculateAvailableMoves(gameData);

        p1list = gameData.getPlayer1Pieces();
        String msg = "";
        for (var piece : p1list) {
            if (piece.getPieceType() == PieceType.KING) {

                msg = "count = " + piece.getAvailableMoves().size();
                assertTrue(msg, piece.getAvailableMoves().size() == 5);
                msg = "should not contain 5,4";
                assertFalse(msg, piece.getAvailableMoves().contains(new PieceLocation(5, 4, piece.getPlayer())));
                msg = "should not contain 6,4";
                assertFalse(msg, piece.getAvailableMoves().contains(new PieceLocation(6, 4, piece.getPlayer())));
                msg = "should not contain 5,6";
                assertFalse(msg, piece.getAvailableMoves().contains(new PieceLocation(5, 6, piece.getPlayer())));
            } else if (piece.getPieceType() == PieceType.SILVERGENERAL) {
                msg = "Move count = " + piece.getAvailableMoves().size();
                assertTrue(msg, piece.getAvailableMoves().size() == 1);
                msg = "should contian 5,3";
                assertTrue(msg, piece.getAvailableMoves().contains(new PieceLocation(5, 3, piece.getPlayer())));
            }
        }

    }

    @Test
    public void saveByPlacement() {
        GameData gameData = new GameData();
        gameData.setActivePlayer(PlayerType.BLACK);
        var p1list = new ArrayList<Piece>();
        var p1hand = new ArrayList<Piece>();
        var p2list = new ArrayList<Piece>();

        // p1
        p1list.add(new Piece(PlayerType.BLACK,
                PieceType.KING,
                new PieceLocation(5, 5, PlayerType.BLACK)));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        gameData.setPlayer1Pieces(p1list);
        gameData.setPlayer1Hand(p1hand);

        // p2
        p2list.add(new Piece(PlayerType.WHITE,
                PieceType.ROOK,
                new PieceLocation(5, 3, PlayerType.BLACK)));
        gameData.setPlayer2Pieces(p2list);

        var mc = new MoveCalculator();
        mc.calculateAvailableMoves(gameData);

        p1list = gameData.getPlayer1Pieces();
        String msg = "";
        for (var piece : p1list) {
            if (piece.getPieceType() == PieceType.KING) {

                msg = "count = " + piece.getAvailableMoves().size();
                assertTrue(msg, piece.getAvailableMoves().size() == 6);
                msg = "should not contain 5,4";
                assertFalse(msg, piece.getAvailableMoves().contains(new PieceLocation(5, 4, piece.getPlayer())));
                msg = "should not contain 5,6";
                assertFalse(msg, piece.getAvailableMoves().contains(new PieceLocation(5, 6, piece.getPlayer())));
            }
        }

        var piece = gameData.getPlayer1Hand().getFirst();
        msg = "piece type = " + piece.getPieceType();
        assertTrue(msg, piece.getPieceType() == PieceType.SILVERGENERAL);
        msg = "move count = " + piece.getAvailableMoves().size();
        assertTrue(msg, piece.getAvailableMoves().size() == 1);
        msg = "Should not contain 5,4";
        assertTrue(msg, piece.getAvailableMoves().contains(new PieceLocation(5, 4, piece.getPlayer())));
    }

    @Test
    public void gameDataUnchangedTest() {
        GameData gameData = new GameData();
        gameData.setActivePlayer(PlayerType.BLACK);
        var p1list = new ArrayList<Piece>();
        var p1hand = new ArrayList<Piece>();
        var p2list = new ArrayList<Piece>();
        var p2hand = new ArrayList<Piece>();

        // p1
        p1list.add(new Piece(PlayerType.BLACK,
                PieceType.KING,
                new PieceLocation(5, 5, PlayerType.BLACK)));
        p1list.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                new PieceLocation(5, 6, PlayerType.BLACK)));
        p1list.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                new PieceLocation(1, 6, PlayerType.BLACK)));
        p1list.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                new PieceLocation(3, 7, PlayerType.BLACK)));

        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        p1hand.add(new Piece(PlayerType.BLACK,
                PieceType.SILVERGENERAL,
                false));
        gameData.setPlayer1Pieces(p1list);
        gameData.setPlayer1Hand(p1hand);

        // p2
        p2list.add(new Piece(PlayerType.WHITE,
                PieceType.KING,
                new PieceLocation(4, 5, PlayerType.WHITE)));
        p2list.add(new Piece(PlayerType.WHITE,
                PieceType.SILVERGENERAL,
                new PieceLocation(3, 6, PlayerType.WHITE)));
        p2list.add(new Piece(PlayerType.WHITE,
                PieceType.SILVERGENERAL,
                new PieceLocation(1, 3, PlayerType.WHITE)));
        p2list.add(new Piece(PlayerType.WHITE,
                PieceType.SILVERGENERAL,
                new PieceLocation(3, 2, PlayerType.WHITE)));

        p2hand.add(new Piece(PlayerType.WHITE,
                PieceType.SILVERGENERAL,
                false));
        p2hand.add(new Piece(PlayerType.WHITE,
                PieceType.SILVERGENERAL,
                false));
        p2hand.add(new Piece(PlayerType.WHITE,
                PieceType.SILVERGENERAL,
                false));
        p2hand.add(new Piece(PlayerType.WHITE,
                PieceType.SILVERGENERAL,
                false));
        p2hand.add(new Piece(PlayerType.WHITE,
                PieceType.SILVERGENERAL,
                false));
        p2hand.add(new Piece(PlayerType.WHITE,
                PieceType.SILVERGENERAL,
                false));
        p2hand.add(new Piece(PlayerType.WHITE,
                PieceType.SILVERGENERAL,
                false));
        p2hand.add(new Piece(PlayerType.WHITE,
                PieceType.SILVERGENERAL,
                false));
        gameData.setPlayer2Pieces(p2list);
        gameData.setPlayer2Hand(p2hand);

        var p1lc = new ArrayList<Piece>();
        var p2lc = new ArrayList<Piece>();
        var p1hc = new ArrayList<Piece>();
        var p2hc = new ArrayList<Piece>();

        // deep copy lists
        for (var piece : p1list) {
            p1lc.add(new Piece(piece.getPlayer(), piece.getPieceType(), piece.getLocation()));
        }
        for (var piece : p2list) {
            p2lc.add(new Piece(piece.getPlayer(), piece.getPieceType(), piece.getLocation()));
        }
        for (var piece : p1hand) {
            p1hc.add(new Piece(piece.getPlayer(), piece.getPieceType(), false));
        }
        for (var piece : p2hand) {
            p2hc.add(new Piece(piece.getPlayer(), piece.getPieceType(), false));
        }

        var mc = new MoveCalculator();
        mc.calculateAvailableMoves(gameData);
        gameData.setActivePlayer(PlayerType.WHITE);
        mc.calculateAvailableMoves(gameData);
        gameData.setActivePlayer(PlayerType.BLACK);
        mc.calculateAvailableMoves(gameData);

        p1list = gameData.getPlayer1Pieces();
        p2list = gameData.getPlayer2Pieces();
        p1hand = gameData.getPlayer1Hand();
        p2hand = gameData.getPlayer2Hand();

        String msg;
        for (int i = 0; i < p1list.size(); i++) {
            msg = "match";
            boolean check = false;
            for (int j = 0; j < p1lc.size(); j++) {
                check = p1list.get(i).getLocation().equals(p1lc.get(j).getLocation());
                if (check)
                    break;
            }
            assertTrue(msg, check);
        }
        for (int i = 0; i < p2list.size(); i++) {
            msg = "size";
            assertTrue(msg, p2list.size() == p2lc.size());
            msg = "match";
            boolean check = false;
            for (int j = 0; j < p2lc.size(); j++) {
                check = p2list.get(i).getLocation().equals(p2lc.get(j).getLocation());
                if (check)
                    break;
            }
            assertTrue(msg, check);
        }
        for (int i = 0; i < p1hand.size(); i++) {
            msg = "match";
            boolean check = false;
            for (int j = 0; j < p1hc.size(); j++) {
                check = p1hand.get(i).getLocation().equals(p1hc.get(j).getLocation());
                if (check)
                    break;
            }
            assertTrue(msg, check);
        }
        for (int i = 0; i < p2hand.size(); i++) {
            msg = "match";
            boolean check = false;
            for (int j = 0; j < p2hc.size(); j++) {
                check = p2hand.get(i).getLocation().equals(p2hc.get(j).getLocation());
                if (check)
                    break;
            }
            assertTrue(msg, check);
        }

    }
}
