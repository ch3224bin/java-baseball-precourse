package baseball;

public enum GameState {
    PROGRESS ("숫자를 입력해주세요 : "),
    WAITING (""),
    FINISH ("");

    private String message;

    GameState(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
