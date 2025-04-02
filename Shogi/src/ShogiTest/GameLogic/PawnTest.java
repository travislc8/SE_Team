package ShogiTest.GameLogic;

import static org.junit.Assert.*;

import org.junit.*;

import GameLogic.Pawn;

public class PawnTest {

    @Test
    public void InstantiateTest() {
        Pawn pawn = new Pawn();
        assertNotNull(pawn);
    }

}
