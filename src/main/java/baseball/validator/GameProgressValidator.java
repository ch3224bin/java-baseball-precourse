package baseball.validator;

import baseball.Result;
import baseball.ResultView;

public class GameProgressValidator implements Validator {

    @Override
    public boolean isValidate(Result.Builder resultBuilder, String answer) {
        return isNotEmpty(resultBuilder, answer) &&
                isThreeNumbers(resultBuilder, answer) &&
                isNumeric(resultBuilder, answer) &&
                doesNotContainsZero(resultBuilder, answer) &&
                isNotDuplicated(resultBuilder, answer);
    }

    private boolean isNotEmpty(Result.Builder resultBuilder, String answer) {
        // 입력 값이 없을 때
        if (answer == null || answer.length() == 0) {
            resultBuilder.view(ResultView.ERROR_WRONG_ANSWER);
            return false;
        }
        return true;
    }

    private boolean isThreeNumbers(Result.Builder resultBuilder, String answer) {
        // 숫자는 세자리만
        if (answer.length() != 3) {
            resultBuilder.view(ResultView.ERROR_NUMBER_LENGTH);
            return false;
        }
        return true;
    }

    private boolean isNumeric(Result.Builder resultBuilder, String answer) {
        // 숫자만
        try {
            Integer.parseInt(answer);
        } catch (NumberFormatException e) {
            resultBuilder.view(ResultView.ERROR_NOT_NUMERIC);
            return false;
        }
        return true;
    }

    private boolean doesNotContainsZero(Result.Builder resultBuilder, String answer) {
        // 0이 없어야함
        if (answer.contains("0")) {
            resultBuilder.view(ResultView.ERROR_WRONG_ANSWER);
            return false;
        }
        return true;
    }

    private boolean isNotDuplicated(Result.Builder resultBuilder, String answer) {
        // 중복이 없어야 함
        int[] arr = new int[10];
        int max = 0;
        for (int numericAnswer = Integer.parseInt(answer); numericAnswer > 0; numericAnswer /= 10) {
            int number = numericAnswer % 10;
            max = Math.max(max, ++arr[number]);
        }

        if (max > 1) {
            resultBuilder.view(ResultView.ERROR_DUPLICATE);
            return false;
        }

        return true;
    }
}
