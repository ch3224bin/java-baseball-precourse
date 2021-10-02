package baseball;

import nextstep.utils.Randoms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseballBot {
    private final static int HIDDEN_NUMBER_COUNT = 3; // 정답 숫자의 자리수

    private GameState gameState;
    private Integer[] hiddenNumber;

    public BaseballBot() {
        gameState = GameState.PROGRESS;
        initHiddenNumber();
    }

    private void initHiddenNumber() {
        List<Integer> uniqueNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        hiddenNumber = new Integer[HIDDEN_NUMBER_COUNT];
        for (int i = 0; i < HIDDEN_NUMBER_COUNT; i++) {
            int randomIndex = Randoms.pickNumberInRange(0, uniqueNumbers.size() - 1);
            hiddenNumber[i] = uniqueNumbers.remove(randomIndex);
        }
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
