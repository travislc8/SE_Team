package GameLogic;

public enum PlayerType {
    WHITE(1), BLACK(-1);

    public final int direction;

    PlayerType(int direction) {
        this.direction = direction;
    }
}
