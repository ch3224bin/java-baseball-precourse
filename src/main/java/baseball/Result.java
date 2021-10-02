package baseball;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Result {
    private final Code code;
    private final int strike;
    private final int ball;

    public Result(Code code, int strike, int ball) {
        this.code = code;
        this.strike = strike;
        this.ball = ball;
    }

    public String getMessage() {
        return code.getMessage(this);
    }

    public int getStrike() {
        return strike;
    }

    public int getBall() {
        return ball;
    }

    public static Builder builder() {
        return new Builder();
    }

    enum Code {
        ERROR_WRONG_ANSWER(errorCodeHandler("[ERROR] 1~9 사이의 중복 없는 세자리 숫자를 입력하세요.")),
        ERROR_NOT_NUMERIC (errorCodeHandler("[ERROR] 숫자를 입력하세요.")),
        ERROR_NUMBER_LENGTH (errorCodeHandler("[ERROR] 세자리 숫자를 입력하세요.")),
        ERROR_DUPLICATE (errorCodeHandler("[ERROR] 중복 없는 숫자를 입력하세요.")),
        OK (okCodeHandler()),
        WIN (winCodeHandler());

        Function<Result, String> handler;

        Code(Function<Result, String> handler) {
            this.handler = handler;
        }

        private static Function<Result, String> errorCodeHandler(String message) {
            return result -> message;
        }

        private static Function<Result, String> okCodeHandler() {
            return result -> {
                List<String> messages = new ArrayList<>();

                if (result.strike > 0) {
                   messages.add(String.format("%d스트라이크", result.strike));
                }

                if (result.ball > 0) {
                    messages.add(String.format("%d볼", result.ball));
                }

                return messages.size() == 0 ? "낫싱" : String.join(" ", messages);
            };
        }

        private static Function<Result, String> winCodeHandler() {
            return result -> {
                List<String> messages = new ArrayList<>();
                messages.add(String.format("%d스트라이크", result.strike));
                messages.add(String.format("%d개의 숫자를 모두 맞히셨습니다! 게임 끝\n", result.strike));
                return String.join("\n", messages);
            };
        }

        public String getMessage(Result result) {
            return handler.apply(result);
        }
    }

    public static class Builder {
        private Code code;
        private int strike;
        private int ball;

        private Builder() {}

        public Builder code(Code code) {
            this.code = code;
            return this;
        }

        public Builder strike(int strike) {
            this.strike = strike;
            return this;
        }

        public Builder ball(int ball) {
            this.ball = ball;
            return this;
        }

        public Result build() {
            return new Result(code, strike, ball);
        }
    }
}
