package baseball.validator;

import baseball.Result;

public class GameWaitingValidator implements Validator {

    @Override
    public boolean isValidate(Result.Builder resultBuilder, String answer) {
        if (!"1".equals(answer) && !"2".equals(answer)) {
            resultBuilder.code(Result.Code.ERROR_WAITING);
            return false;
        }
        return true;
    }
}
