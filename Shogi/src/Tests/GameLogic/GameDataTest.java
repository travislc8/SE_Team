package Tests.GameLogic;

import static org.junit.Assert.*;
import org.junit.*;

import GameLogic.GameData;

public class GameDataTest {
    @Test
    public void changeTurnTest() {
        GameData data = new GameData();
        data.newGame();

        var activePlayer = data.getActivePlayer();

        data.changeTurn();
        assertFalse("active player not changed", activePlayer == data.getActivePlayer());
        activePlayer = data.getActivePlayer();
        data.changeTurn();
        assertFalse("active player not changed", activePlayer == data.getActivePlayer());
        activePlayer = data.getActivePlayer();
        data.changeTurn();
        assertFalse("active player not changed", activePlayer == data.getActivePlayer());

        activePlayer = data.getActivePlayer();
        int playerTime = data.getPlayer2Time();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        data.changeTurn();

        String msg = "player time ==" + data.getPlayer2Time();
        assertTrue(msg, data.getPlayer2Time() == (playerTime - 2));

    }

    @Test
    public void updateTimerTest() {
        GameData data = new GameData();
        data.newGame();
        data.changeTurn();
        data.changeTurn();
        data.changeTurn();

        data.updateTimer();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        data.updateTimer();
        int playerTime = data.getPlayer1Time();
        String msg = "player time ==" + data.getPlayer2Time();
        assertTrue(msg, data.getPlayer2Time() == (playerTime - 2));

    }
}
