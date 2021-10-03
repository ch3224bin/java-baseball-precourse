package baseball;

public class Result {
    private final ResultView view;
    private final int strike;
    private final int ball;

    public Result(ResultView view, int strike, int ball) {
        this.view = view;
        this.strike = strike;
        this.ball = ball;
    }

    public String getMessage() {
        return view.getMessage(this);
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

    public static class Builder {
        private ResultView view;
        private int strike;
        private int ball;

        private Builder() {}

        public Builder view(ResultView view) {
            this.view = view;
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
            return new Result(view, strike, ball);
        }
    }
}
