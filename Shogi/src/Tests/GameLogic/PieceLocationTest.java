package Tests.GameLogic;

import GameLogic.PieceLocation;
import GameLogic.PlayerType;

import static org.junit.Assert.*;

import org.junit.*;

public class PieceLocationTest {

    @Test
    public void equalsTest() {
        var p1 = new PieceLocation(5, 6, PlayerType.BLACK);
        var p2 = new PieceLocation(5, 6, PlayerType.BLACK);
        var p3 = new PieceLocation(0, 1, PlayerType.WHITE);
        var p4 = new PieceLocation(0, 1, PlayerType.BLACK);
        var p5 = new PieceLocation(1, 1, PlayerType.BLACK);
        var p6 = new PieceLocation(1, 2, PlayerType.BLACK);

        assertTrue("p1 == p2", p1.equals(p2));
        assertFalse("p3 != p1", p3.equals(p1));
        assertFalse("p3 != p4", p3.equals(p4));
        assertFalse("p5 != p4", p5.equals(p4));
        assertFalse("p5 != p6", p5.equals(p6));
    }

    @Test
    public void test() {
        assertTrue(true);
    }
}
