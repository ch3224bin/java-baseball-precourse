package baseball;

import nextstep.utils.Randoms;

import java.util.*;

public class BaseballBot {
    private final static int HIDDEN_NUMBER_COUNT = 3; // 정답 숫자의 자리수

    private GameState gameState;
    private List<Integer> hiddenNumber;

    public BaseballBot() {
        gameState = GameState.PROGRESS;
        initHiddenNumber();
    }

    private void initHiddenNumber() {
        Set<Integer> resultSet = new LinkedHashSet<>();
        while (resultSet.size() < HIDDEN_NUMBER_COUNT) {
            resultSet.add(Randoms.pickNumberInRange(1, 9));
        }
        hiddenNumber = new ArrayList<>(resultSet);
    }

    public Result send(String answer) {
        Result.Builder resultBuilder = Result.builder();
        if (!Validator.isValidate(resultBuilder, answer)) {
            return resultBuilder.build();
        }

        int strike = getStrike(answer);
        int ball = getBall(answer);

        return Result.builder().code(Result.Code.OK).strike(strike).ball(ball).build();
    }

    private int getBall(String answer) {
        int ball = 0;
        for (int i = 0, n = hiddenNumber.size(); i < n; i++) {
            List<Integer> otherNumbers = getOtherNumbers(i, n);
            boolean isContains = otherNumbers.contains(Integer.parseInt(answer.substring(i, i + 1)));
            ball += isContains ? 1 : 0;
        }
        return ball;
    }

    private List<Integer> getOtherNumbers(int i, int n) {
        List<Integer> otherNumbers = new ArrayList<>();
        if (i != 0) {
            otherNumbers.addAll(hiddenNumber.subList(0, i));
        }
        if (i < n - 1) {
            otherNumbers.addAll(hiddenNumber.subList(i + 1, n));
        }
        return otherNumbers;
    }

    private int getStrike(String answer) {
        int strike = 0;
        for (int i = 0, n = hiddenNumber.size(); i < n; i++) {
            boolean isEqual = hiddenNumber.get(i) == Integer.parseInt(answer.substring(i, i + 1));
            strike += isEqual ? 1 : 0;
        }
        return strike;
    }

    public String getMessage() {
        return "숫자를 입력해주세요 : ";
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<Integer> getHiddenNumber() {
        return hiddenNumber;
    }
}
