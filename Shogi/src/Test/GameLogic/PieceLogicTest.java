package Test.GameLogic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import GameLogic.GameData;
import GameLogic.Piece;
import GameLogic.PieceLocation;
import GameLogic.PieceLogic;
import GameLogic.PieceType;
import GameLogic.PlayerType;

public class PieceLogicTest {

    @Test
    public void goldGeneralMoveTest() {
        var list = PieceLogic.goldGeneralMoveList(5, 5, PlayerType.WHITE);

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
    public void emptyAllLocationListTest() {
        var list = PieceLogic.emptyLocationList(new GameData(), PlayerType.WHITE);
        String message = "list size = " + list.size();
        assertTrue(message, list.size() == 81);

    }

    @Test
    public void allLocationListTest() {
        var gameData = new GameData();
        var play1list = new ArrayList<Piece>();
        var play2list = new ArrayList<Piece>();
        play1list.add(new Piece(PlayerType.BLACK, PieceType.PAWN, 0, 0));
        play2list.add(new Piece(PlayerType.WHITE, PieceType.PAWN, 8, 8));
        gameData.setPlayer1Pieces(play1list);
        gameData.setPlayer2Pieces(play2list);

        var list = PieceLogic.emptyLocationList(gameData, PlayerType.WHITE);

        String message = "list size = " + list.size();
        assertTrue(message, list.size() == 79);
        assertFalse(list.contains(new PieceLocation(0, 0, PlayerType.BLACK)));
        assertFalse(list.contains(new PieceLocation(8, 8, PlayerType.WHITE)));
    }
}
