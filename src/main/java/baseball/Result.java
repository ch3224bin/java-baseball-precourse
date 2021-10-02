package baseball;

public class Result {
    private final Code code;

    public Result(Code code) {
        this.code = code;
    }

    public String getMessage() {
        return code.getMessage();
    }

    public static Builder builder() {
        return new Builder();
    }

    enum Code {
        ERROR_WRONG_ANSWER("[ERROR] 1~9 사이의 중복 없는 세자리 숫자를 입력하세요."),
        ERROR_NOT_NUMERIC ("[ERROR] 숫자를 입력하세요."),
        ERROR_NUMBER_LENGTH ("[ERROR] 세자리 숫자를 입력하세요."),
        ERROR_DUPLICATE ("[ERROR] 중복 없는 숫자를 입력하세요.");

        String message;

        Code(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class Builder {
        private Code code;

        private Builder() {}

        public Builder code(Code code) {
            this.code = code;
            return this;
        }

        public Result build() {
            return new Result(code);
        }
    }
}
