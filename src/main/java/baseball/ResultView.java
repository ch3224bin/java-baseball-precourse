package baseball;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 결과 메세지 생성 역할을 담당한다.
 */
public enum ResultView {
    ERROR_WRONG_ANSWER(errorViewHandler("1~9 사이의 중복 없는 세자리 숫자를 입력하세요.")),
    ERROR_NOT_NUMERIC (errorViewHandler("숫자를 입력하세요.")),
    ERROR_NUMBER_LENGTH (errorViewHandler("세자리 숫자를 입력하세요.")),
    ERROR_DUPLICATE (errorViewHandler("중복 없는 숫자를 입력하세요.")),
    ERROR_WAITING (errorViewHandler("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요")),
    OK (okViewHandler()),
    WIN (winViewHandler()),
    CHANGE (changeViewHandler());

    private final Function<Result, String> handler;

    ResultView(Function<Result, String> handler) {
        this.handler = handler;
    }

    private static Function<Result, String> errorViewHandler(String message) {
        return result -> String.format("[ERROR] %s", message);
    }

    private static Function<Result, String> okViewHandler() {
        return result -> {
            List<String> messages = new ArrayList<>();

            if (result.getStrike() > 0) {
                messages.add(String.format("%d스트라이크", result.getStrike()));
            }

            if (result.getBall() > 0) {
                messages.add(String.format("%d볼", result.getBall()));
            }

            return messages.size() == 0 ? "낫싱" : String.join(" ", messages);
        };
    }

    private static Function<Result, String> winViewHandler() {
        return result -> {
            List<String> messages = new ArrayList<>();
            messages.add(String.format("%d스트라이크", result.getStrike()));
            messages.add(String.format("%d개의 숫자를 모두 맞히셨습니다! 게임 끝", result.getStrike()));
            return String.join("\n", messages);
        };
    }

    private static Function<Result, String> changeViewHandler() {
        return result -> "";
    }

    public String getMessage(Result result) {
        return handler.apply(result);
    }
}
