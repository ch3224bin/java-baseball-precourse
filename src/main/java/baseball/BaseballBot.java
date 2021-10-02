package baseball;

import baseball.validator.GameProgressValidator;
import baseball.validator.GameWaitingValidator;
import baseball.validator.Validator;
import nextstep.utils.Randoms;

import java.util.*;
import java.util.function.Function;

public class BaseballBot {
    private final static int HIDDEN_NUMBER_COUNT = 3; // 정답 숫자의 자리수

    private GameState gameState;
    private List<Integer> hiddenNumber;
    private Map<GameState, Function<String, Result>> router;

    public BaseballBot() {
        gameState = GameState.PROGRESS;
        initHiddenNumber();
        initRouter();
    }

    private void initHiddenNumber() {
        Set<Integer> resultSet = new LinkedHashSet<>();
        while (resultSet.size() < HIDDEN_NUMBER_COUNT) {
            resultSet.add(Randoms.pickNumberInRange(1, 9));
        }
        hiddenNumber = new ArrayList<>(resultSet);
    }

    private void initRouter() {
        router = new HashMap<>();
        router.put(GameState.PROGRESS, progressHandler(new GameProgressValidator()));
        router.put(GameState.WAITING, waitingHandler(new GameWaitingValidator()));
    }

    public Result send(String answer) {
        return this.router.get(this.gameState).apply(answer);
    }

    private Function<String, Result> progressHandler(Validator validator) {
        return answer -> {
            Result.Builder resultBuilder = Result.builder();
            if (!validator.isValidate(resultBuilder, answer)) {
                return resultBuilder.build();
            }

            Result.Code code = Result.Code.OK;
            int strike = getStrike(answer);
            int ball = getBall(answer);

            if (strike == HIDDEN_NUMBER_COUNT) {
                code = Result.Code.WIN;
                changeWaitingState();
            }

            return Result.builder().code(code).strike(strike).ball(ball).build();
        };
    }

    private Function<String, Result> waitingHandler(Validator validator) {
        return answer -> {
            Result.Builder resultBuilder = Result.builder();
            if (!validator.isValidate(resultBuilder, answer)) {
                return resultBuilder.build();
            }

            return Result.builder().build();
        };
    }

    private void changeWaitingState() {
        this.gameState = GameState.WAITING;
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
        return this.gameState.getMessage();
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<Integer> getHiddenNumber() {
        return hiddenNumber;
    }
}
