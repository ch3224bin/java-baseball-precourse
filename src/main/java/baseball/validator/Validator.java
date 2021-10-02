package baseball.validator;

import baseball.Result;

public interface Validator {
    boolean isValidate(Result.Builder resultBuilder, String answer);
}
