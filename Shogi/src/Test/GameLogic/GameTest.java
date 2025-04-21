package Test.GameLogic;

import static org.junit.Assert.*;
import org.junit.*;

import GameLogic.Game;
import GameLogic.GameData;
import GameLogic.Move;
import GameLogic.PlayerType;

public class GameTest {

    @Test
    public void setUpTest() {
        Game game = new Game();
        GameData data = game.getGameData();

        String msg = "player 1 start time = " + data.getPlayer1Time();
        assertTrue(msg, data.getPlayer1Time() == 300);
        msg = "player 2 start time = " + data.getPlayer1Time();
        assertTrue(msg, data.getPlayer2Time() == 300);

        msg = "move list size";
        assertTrue(msg, data.getMoveList().size() == 0);
        msg = "player 1 pieces list size";
        assertTrue(msg, data.getPlayer1Pieces().size() == 20);
        msg = "player 2 pieces list size";
        assertTrue(msg, data.getPlayer2Pieces().size() == 20);
        msg = "player 1 hand list size";
        assertTrue(msg, data.getPlayer1Hand().size() == 0);
        msg = "player 2 hand list size";
        assertTrue(msg, data.getPlayer2Hand().size() == 0);
        msg = "game should not be over";
        assertTrue(msg, !data.isGameOver());
        msg = "game should not be over";
        assertTrue(msg, !data.isGameOver());
    }

    @Test
    public void oneMoveTest() {
        Game game = new Game();
        GameData data = game.getGameData();

        var dataCopy = data.deepCopy();
        var piece = dataCopy.getPlayer1Pieces().getFirst();
        var loc = piece.getAvailableMoves().getFirst();
        int x = loc.getxPos();
        int y = loc.getyPos();
        dataCopy.makeMove(new Move(piece, loc));

        game.handleMove(dataCopy);
        data = game.getGameData();

        String msg = "active player was switched";
        assertTrue(msg, data.getActivePlayer() == PlayerType.WHITE);
        msg = "piece was not moved to " + x + ":" + y;
        assertTrue(msg, data.getPlayer1Pieces().getFirst().getLocation().xyEqual(x, y));
    }

    @Test
    public void setGameIdTest() {
        Game game = new Game();
        game.setGameId(1);

        String msg = "game id";
        assertTrue(msg, game.getGameId() == 1);
    }
}
