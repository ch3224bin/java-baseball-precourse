package baseball;

import nextstep.test.NSTest;
import org.junit.jupiter.api.*;

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

        @BeforeEach
        void beforeEach() {
            super.setUp();
        }

        @AfterEach
        void tearDown() {
            outputStandard();
        }

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

        @DisplayName("\"숫자를 입력해주세요 : \" 묻는다")
        @Test
        public void testPrintEnterTheNumberMessage() {
            run(" ");
            verify("숫자를 입력해주세요 : ");
        }

        @Override
        public void runMain() {
            Application.main(new String[]{});
        }
    }

    @DisplayName("진행 상태에서")
    @Nested
    class WhenProgress extends NSTest {

        @BeforeEach
        void beforeEach() {
            super.setUp();
        }

        @AfterEach
        void tearDown() {
            outputStandard();
        }

        @DisplayName("입력 값이 비어 있으면 에러 메세지를 보낸다")
        @Test
        public void testEmptyValue() {
            run("");
            verify(Result.Code.ERROR_WRONG_ANSWER.getMessage());
        }

        @DisplayName("입력 값이 숫자가 아니면 에러 메세지를 보낸다")
        @Test
        public void testNumericValue() {
            run("abc");
            verify(Result.Code.ERROR_NOT_NUMERIC.getMessage());
        }

        @DisplayName("입력 값이 세자리가 아니면 에러 메세지를 보낸다")
        @Test
        public void testLength() {
            run("12345");
            verify(Result.Code.ERROR_NUMBER_LENGTH.getMessage());
        }

        @DisplayName("입력 값에 0이 들어가면 에러 메세지를 보낸다")
        @Test
        public void testContainsZero() {
            run("109");
            verify(Result.Code.ERROR_WRONG_ANSWER.getMessage());
        }

        @DisplayName("중복이 있으면 에러 메세지를 보낸다")
        @Test
        public void testDuplicated() {
            run("113");
            verify(Result.Code.ERROR_DUPLICATE.getMessage());
        }

        @Override
        public void runMain() {
            Application.main(new String[]{});
        }
    }

}
