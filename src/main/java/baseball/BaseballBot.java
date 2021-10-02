package baseball;

public class BaseballBot {
    private GameState gameState;

    public BaseballBot() {
        gameState = GameState.PROGRESS;
    }

    public GameState getGameState() {
        return gameState;
    }
}
