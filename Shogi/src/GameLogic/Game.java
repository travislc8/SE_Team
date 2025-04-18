package GameLogic;

/**
 * Game class for maintaining a game instance on to control the flow of a game
 * of Shogi. handleMove method is the only method that is required to handle a
 * new move of a game.
 */
public class Game {
    GameData gameData;
    MoveCalculator mc;

    /**
     * Creates a new instance of a game and sets up the board. The timer will not
     * start until the first move is made.
     */
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

    /**
     * Handles a move input from a player and updates the board based on the move
     * and returns the newly updated state. If the move is not valid the turn will
     * not switch but the time will be updated.
     * 
     * @param gameFromServer the gameData instance that was received from the client
     * @return the updated gameData object that should be sent to the clients
     */
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
