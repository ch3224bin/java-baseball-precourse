package baseball;

import baseball.validator.Validator;

/**
 * PROGRESS, WAITING 등의 진행 상태에 따른
 * 요청, 응답에 대한 공통 처리부분을 담당한다.
 *
 * 요청 값에 대한 검증 코드를 공통화 함.
 */
public abstract class GameHandler {
    private final Validator validator;

    public GameHandler(Validator validator) {
        this.validator = validator;
    }

    public Result send(String answer) {
        Result.Builder resultBuilder = Result.builder();
        if (!validator.isValidate(resultBuilder, answer)) {
            return resultBuilder.build();
        }

        return run(answer);
    }

    abstract Result run(String answer);
}
