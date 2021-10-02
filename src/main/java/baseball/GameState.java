package baseball;

public enum GameState {
    PROGRESS ("숫자를 입력해주세요 : "),
    WAITING ("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.\n"),
    FINISH ("");

    private final String message;

    GameState(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
