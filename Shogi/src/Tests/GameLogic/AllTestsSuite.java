package Tests.GameLogic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ Tests.GameLogic.PieceLocationTest.class,
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
        MoveCalculatorTest.class,
        GameTest.class,
        GameDataTest.class
})
public final class AllTestsSuite {
}
