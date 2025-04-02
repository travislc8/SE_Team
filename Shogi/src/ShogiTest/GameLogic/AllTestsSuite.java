package ShogiTest.GameLogic;

import org.junit.internal.TextListener;
import org.junit.runner.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ShogiTest.GameLogic.PieceLocationTest.class,
        ShogiTest.GameLogic.PawnTest.class,
        PieceLogicTest.class })
public final class AllTestsSuite {
}
