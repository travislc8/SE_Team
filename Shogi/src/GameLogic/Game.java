package GameLogic;

public class Game {
    GameData gameData;
    MoveCalculator mc;

    public Game() {
        gameData = new GameData();
        mc = new MoveCalculator();
        gameData.newGame();
        mc.calculateAvailableMoves(gameData);
    }

    public void setGameId(int id) {
        gameData.setGameId(id);
    }

    public int getGameId() {
        return gameData.getGameId();
    }

    public GameData handleMove(GameData gameFromServer) {
        if (gameData == gameFromServer) {
            throw new IllegalStateException(
                    "Expected the game from the server to be different than the retained game data, rethink life");
        }
        if (moveAdded(gameFromServer)) {
            if (gameData.makeMove(gameFromServer.getMoveList().getLast())) {
                gameData.changeTurn();
                mc.calculateAvailableMoves(gameData);
                gameData.updateEndGame();
            } else { // invalid move case
                gameData.updateTimer();
            }
        }
        return gameData;
    }

    private boolean moveAdded(GameData other) {
        if (other.getMoveList().size() == 0) {
            return false;
        }
        if (other.getMoveList().size() == gameData.getMoveList().size())
            if (other.getMoveList().getLast() == gameData.getMoveList().getLast())
                return false;

        return true;

    }

    public GameData getGameData() {
        return gameData;
    }

}
