package baseball.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WaitingCommandAnswerSetTest {

    @DisplayName("contains는 값이 포함되어 있는지 여부를 반환한다")
    @Test
    public void testContains() {
        assertThat(WaitingCommandAnswerSet.contains("1")).isTrue();
        assertThat(WaitingCommandAnswerSet.contains("2")).isTrue();
        assertThat(WaitingCommandAnswerSet.contains("3")).isFalse();
    }

    @DisplayName("String value가 같은지 여부를 반환한다")
    @Test
    public void testIsEqualTo() {
        assertThat(WaitingCommandAnswerSet.ONE_MORE_PLAY.isEqualTo("1")).isTrue();
        assertThat(WaitingCommandAnswerSet.GAME_FINISH.isEqualTo("2")).isTrue();
        assertThat(WaitingCommandAnswerSet.GAME_FINISH.isEqualTo("1")).isFalse();
    }
}
