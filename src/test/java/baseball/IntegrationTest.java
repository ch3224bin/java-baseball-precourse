package baseball;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("기능목록 테스트")
public class IntegrationTest {

    @DisplayName("프로그램 시작시 게임은 진행 상태로 변경된다.")
    @Test
    public void testWhenProgramStartedThenGameStateIsProgress() {
        BaseballBot baseballBot = new BaseballBot();
        assertThat(baseballBot.getGameState()).isEqualTo(GameState.PROGRESS);
    }
}
