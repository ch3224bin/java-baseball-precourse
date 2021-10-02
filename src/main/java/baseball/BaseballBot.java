package baseball;

import nextstep.utils.Randoms;

import java.util.*;

public class BaseballBot {
    private final static int HIDDEN_NUMBER_COUNT = 3; // 정답 숫자의 자리수

    private GameState gameState;
    private Integer[] hiddenNumber;

    public BaseballBot() {
        gameState = GameState.PROGRESS;
        initHiddenNumber();
    }

    private void initHiddenNumber() {
        Set<Integer> resultSet = new LinkedHashSet<>();
        while (resultSet.size() < HIDDEN_NUMBER_COUNT) {
            resultSet.add(Randoms.pickNumberInRange(1, 9));
        }
        hiddenNumber = resultSet.toArray(new Integer[HIDDEN_NUMBER_COUNT]);
    }

    public Result send(String answer) {
        Result.Builder resultBuilder = Result.builder();
        if (!Validator.isValidate(resultBuilder, answer)) {
            return resultBuilder.build();
        }

        int strike = 0;
        for (int i = 0; i < HIDDEN_NUMBER_COUNT; i++) {
            boolean isEqual = hiddenNumber[i] == Integer.parseInt(answer.substring(i, i+1));
            strike += isEqual ? 1 : 0;
        }

        return Result.builder().code(Result.Code.OK).strike(strike).build();
    }

    public String getMessage() {
        return "숫자를 입력해주세요 : ";
    }

    public GameState getGameState() {
        return gameState;
    }

    public Integer[] getHiddenNumber() {
        return hiddenNumber;
    }
}
