package ShogiTest.GameLogic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ShogiTest.GameLogic.PieceLocationTest.class,
        PieceLocationTest.class,
        PieceLogicTest.class,
        KingLogicTest.class,
        GoldGeneralLogicTest.class,
        SilverGeneralLogicTest.class,
        KnightLogicTest.class,
        LanceLogicTest.class,
        BishopLogicTest.class,
        RookLogicTest.class,
        PawnLogicTest.class,
        MoveCalculatorTest.class
})
public final class AllTestsSuite {
}
