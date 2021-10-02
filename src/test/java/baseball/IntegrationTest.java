package baseball;

import nextstep.test.NSTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

@DisplayName("기능목록 테스트")
public class IntegrationTest {

    @DisplayName("프로그램 시작시 게임은 진행 상태로 변경된다.")
    @Test
    public void testWhenProgramStartedThenGameStateIsProgress() {
        BaseballBot baseballBot = new BaseballBot();
        assertThat(baseballBot.getGameState()).isEqualTo(GameState.PROGRESS);
    }

    @DisplayName("게임 시작시")
    @Nested
    class WhenStart extends NSTest {

        @DisplayName("임의의 숫자 3개를 생성한다")
        @Test
        public void testRandomHiddenNumber() {
            for (int i = 0; i < 100; i++) { // 100번 정도 수행
                BaseballBot baseballBot = new BaseballBot();
                Integer[] hiddenNumber = baseballBot.getHiddenNumber();

                // 중복 없는 세자리 숫자 검사
                assertThat(hiddenNumber).isNotNull();
                assertThat(hiddenNumber).hasSize(3).allSatisfy(number ->
                        assertThat(hiddenNumber).filteredOn(n -> Objects.equals(n, number)).hasSize(1)
                );
            }
        }

        @Override
        public void runMain() {

        }
    }

}
