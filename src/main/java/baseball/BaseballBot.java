package baseball;

import baseball.validator.GameProgressValidator;
import baseball.validator.GameWaitingValidator;
import baseball.validator.Validator;
import baseball.validator.WaitingCommandAnswerSet;
import nextstep.utils.Randoms;

import java.util.*;

/**
 * 플레이어의 행위에 따라 게임진행 상태를 변경하며 게임을 진행시킨다.
 *
 * 이 프로그램의 실행 절차는 아래와 같다.
 *
 * 1. 인스턴스 생성시
 * 1-1. 진행상태를 "진행중"으로 초기화 한다.
 * 1-2. 정답(hiddenNUmber)를 초기화 한다.
 * 2. 진행중
 * 2-1. 플레이어의 제시값에 맞게 코드, 스트라이크, 볼 갯수를 카운트를 담은 Result를 반환한다.
 * 2-2. 정답을 맞추면 대기 상태로 변경한다.
 * 3. 대기중
 * 3-1. 게임을 새로 시작하면 1-1, 1-2의 행위를 한다.
 * 3-2. 게임을 종료하면 종료 상태로 변경한다.
 */
public class BaseballBot {
    private final static int HIDDEN_NUMBER_COUNT = 3; // 정답 숫자의 자리수

    private GameState gameState;
    private List<Integer> hiddenNumber;
    private Map<GameState, GameHandler> router;

    public BaseballBot() {
        changeStateToProgress();
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
        return this.router.get(this.gameState).send(answer);
    }

    private GameHandler progressHandler(Validator validator) {
        return new GameHandler(validator) {
            @Override
            Result run(String answer) {
                ResultView view = ResultView.OK;
                int strike = getStrike(answer);
                int ball = getBall(answer);

                if (strike == HIDDEN_NUMBER_COUNT) {
                    view = ResultView.WIN;
                    changeStateToWaiting();
                }

                return Result.builder().view(view).strike(strike).ball(ball).build();
            }
        };
    }

    private GameHandler waitingHandler(Validator validator) {
        return new GameHandler(validator) {
            @Override
            Result run(String answer) {
                if (WaitingCommandAnswerSet.ONE_MORE_PLAY.isEqualTo(answer)) {
                    changeStateToProgress();
                }

                if (WaitingCommandAnswerSet.GAME_FINISH.isEqualTo(answer)) {
                    changeStateToFinish();
                }

                return Result.builder().view(ResultView.CHANGE).build();
            }
        };
    }

    private void changeStateToFinish() {
        this.gameState = GameState.FINISH;
    }

    private void changeStateToProgress() {
        this.gameState = GameState.PROGRESS;
        initHiddenNumber();
    }

    private void changeStateToWaiting() {
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

    public boolean isFinish() {
        return this.gameState == GameState.FINISH;
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
