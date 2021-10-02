package baseball;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Result {
    private final Code code;
    private final int strike;

    public Result(Code code, int strike) {
        this.code = code;
        this.strike = strike;
    }

    public String getMessage() {
        return code.getMessage(this);
    }

    public int getStrike() {
        return strike;
    }

    public static Builder builder() {
        return new Builder();
    }

    enum Code {
        ERROR_WRONG_ANSWER(errorCodeHandler("[ERROR] 1~9 사이의 중복 없는 세자리 숫자를 입력하세요.")),
        ERROR_NOT_NUMERIC (errorCodeHandler("[ERROR] 숫자를 입력하세요.")),
        ERROR_NUMBER_LENGTH (errorCodeHandler("[ERROR] 세자리 숫자를 입력하세요.")),
        ERROR_DUPLICATE (errorCodeHandler("[ERROR] 중복 없는 숫자를 입력하세요.")),
        OK (okCodeHandler());

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
                    messages.add(String.format("%d 스트라이크", result.strike));
                }

                return messages.size() == 0 ? "낫싱" : String.join(" ", messages);
            };
        }

        public String getMessage(Result result) {
            return handler.apply(result);
        }
    }

    public static class Builder {
        private Code code;
        private int strike;

        private Builder() {}

        public Builder code(Code code) {
            this.code = code;
            return this;
        }

        public Builder strike(int strike) {
            this.strike = strike;
            return this;
        }

        public Result build() {
            return new Result(code, strike);
        }
    }
}
