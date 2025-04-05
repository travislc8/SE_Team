package ShogiTest.GameLogic;

import org.junit.internal.TextListener;
import org.junit.runner.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

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
        PawnLogicTest.class
})
public final class AllTestsSuite {
}
