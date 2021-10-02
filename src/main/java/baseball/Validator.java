package baseball;

public class Validator {
    static boolean isValidate(Result.Builder resultBuilder, String answer) {
        return isNotEmpty(resultBuilder, answer) &&
                isThreeNumbers(resultBuilder, answer) &&
                isNumeric(resultBuilder, answer) &&
                doesNotContainsZero(resultBuilder, answer) &&
                isNotDuplicated(resultBuilder, answer);
    }

    private static boolean isNotEmpty(Result.Builder resultBuilder, String answer) {
        // 입력 값이 없을 때
        if (answer == null || answer.length() == 0) {
            resultBuilder.code(Result.Code.ERROR_WRONG_ANSWER);
            return false;
        }
        return true;
    }

    private static boolean isThreeNumbers(Result.Builder resultBuilder, String answer) {
        // 숫자는 세자리만
        if (answer.length() != 3) {
            resultBuilder.code(Result.Code.ERROR_NUMBER_LENGTH);
            return false;
        }
        return true;
    }

    private static boolean isNumeric(Result.Builder resultBuilder, String answer) {
        // 숫자만
        try {
            Integer.parseInt(answer);
        } catch (NumberFormatException e) {
            resultBuilder.code(Result.Code.ERROR_NOT_NUMERIC);
            return false;
        }
        return true;
    }

    private static boolean doesNotContainsZero(Result.Builder resultBuilder, String answer) {
        // 0이 없어야함
        if (answer.contains("0")) {
            resultBuilder.code(Result.Code.ERROR_WRONG_ANSWER);
            return false;
        }
        return true;
    }

    private static boolean isNotDuplicated(Result.Builder resultBuilder, String answer) {
        // 중복이 없어야 함
        int[] arr = new int[10];
        int max = 0;
        for (int numericAnswer = Integer.parseInt(answer); numericAnswer > 0; numericAnswer /= 10) {
            int number = numericAnswer % 10;
            max = Math.max(max, ++arr[number]);
        }

        if (max > 1) {
            resultBuilder.code(Result.Code.ERROR_DUPLICATE);
            return false;
        }

        return true;
    }
}
