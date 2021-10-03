package baseball;

import nextstep.test.NSTest;
import nextstep.utils.Randoms;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mockStatic;

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
                List<Integer> hiddenNumber = baseballBot.getHiddenNumber();

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
            running(" ");
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
            running("");
            verify(ResultView.ERROR_WRONG_ANSWER.getMessage(null));
        }

        @DisplayName("입력 값이 숫자가 아니면 에러 메세지를 보낸다")
        @Test
        public void testNumericValue() {
            running("abc");
            verify(ResultView.ERROR_NOT_NUMERIC.getMessage(null));
        }

        @DisplayName("입력 값이 세자리가 아니면 에러 메세지를 보낸다")
        @Test
        public void testLength() {
            running("12345");
            verify(ResultView.ERROR_NUMBER_LENGTH.getMessage(null));
        }

        @DisplayName("입력 값에 0이 들어가면 에러 메세지를 보낸다")
        @Test
        public void testContainsZero() {
            running("109");
            verify(ResultView.ERROR_WRONG_ANSWER.getMessage(null));
        }

        @DisplayName("중복이 있으면 에러 메세지를 보낸다")
        @Test
        public void testDuplicated() {
            running("113");
            verify(ResultView.ERROR_DUPLICATE.getMessage(null));
        }

        @DisplayName("입력 받은 세자리 숫자와 정답의 같은 자리 숫자 만큼 스트라이크 횟수를 증가시킨다")
        @Test
        public void testStrikeResult() {
            try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
                mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
                        .thenReturn(7, 1, 3);
                BaseballBot baseballBot = new BaseballBot();
                Result result = baseballBot.send("823");
                assertThat(result.getStrike()).isEqualTo(1);
                result = baseballBot.send("413");
                assertThat(result.getStrike()).isEqualTo(2);
                result = baseballBot.send("713");
                assertThat(result.getStrike()).isEqualTo(3);
            }
        }

        @DisplayName("같은 자리의 숫자 만큼 스트라이크로 출력 한다")
        @Test
        public void testPrintStrike() {
            try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
                mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
                        .thenReturn(7, 1, 3);
                running("724", "714", "713");
                verify("1스트라이크", "2스트라이크", "3스트라이크");
            }
        }

        @DisplayName("입력 받은 세자리 숫자와 정답의 다른 자리 숫자 만큼 볼 횟수를 증가시킨다")
        @Test
        public void testBallResult() {
            try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
                mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
                        .thenReturn(7, 1, 3);
                BaseballBot baseballBot = new BaseballBot();
                Result result = baseballBot.send("371");
                assertThat(result.getBall()).isEqualTo(3);
                result = baseballBot.send("136");
                assertThat(result.getBall()).isEqualTo(2);
                result = baseballBot.send("439");
                assertThat(result.getBall()).isEqualTo(1);
                result = baseballBot.send("713");
                assertThat(result.getBall()).isEqualTo(0);
                result = baseballBot.send("824");
                assertThat(result.getBall()).isEqualTo(0);
            }
        }

        @DisplayName("같은 자리의 숫자 만큼 볼로 출력 한다")
        @Test
        public void testPrintBall() {
            try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
                mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
                        .thenReturn(7, 1, 3);
                running("439", "136", "371");
                verify("1볼", "2볼", "3볼");
            }
        }

        @DisplayName("결과는 스트라이크 다음 볼 순서로 출력한다.")
        @Test
        public void testPrintStrikeAndBall() {
            try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
                mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
                        .thenReturn(7, 1, 3);
                running("731", "316", "317");
                verify("1스트라이크 2볼", "1스트라이크 1볼", "1스트라이크 2볼");
            }
        }

        @DisplayName("모든 숫자가 다른 경우 \"낫싱\"으로 출력한다")
        @Test
        public void testPrintNothing() {
            try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
                mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
                        .thenReturn(7, 1, 3);
                running("246");
                verify("낫싱");
            }
        }

        @DisplayName("입력 숫자와 정답이 같으면 \"3개의 숫자를 모두 맞히셨습니다! 게임 끝\" 메세지를 전송한다")
        @Test
        public void testPrintGameEnd() {
            try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
                mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
                        .thenReturn(7, 1, 3);
                running("713");
                verify("3스트라이크", "3개의 숫자를 모두 맞히셨습니다! 게임 끝");
            }
        }

        @DisplayName("입력 숫자와 정답이 같으면 대기상태로 변경한다")
        @Test
        public void testChangeWaitingState() {
            try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
                mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
                        .thenReturn(7, 1, 3);
                BaseballBot baseballBot = new BaseballBot();
                baseballBot.send("713");
                assertThat(baseballBot.getGameState()).isEqualTo(GameState.WAITING);
            }
        }

        @DisplayName("대기 상태로 변경되면 \"게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.\"를 전송한다")
        @Test
        public void testWhenChangedWaitingStateThenSendMessage() {
            try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
                mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
                        .thenReturn(7, 1, 3);
                running("713");
                verify("3스트라이크", "3개의 숫자를 모두 맞히셨습니다! 게임 끝", "게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
            }
        }

        @Override
        public void runMain() {
            Application.main(new String[]{});
        }
    }

    @DisplayName("대기 상태에서")
    @Nested
    class WhenWaiting extends NSTest {

        @BeforeEach
        void beforeEach() {
            super.setUp();
        }

        @AfterEach
        void tearDown() {
            outputStandard();
        }

        @DisplayName("1, 2 외의 값을 받으면 오류 메세지를 출력한다")
        @Test
        public void testPrintErrorMessage() {
            try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
                mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
                        .thenReturn(7, 1, 3);
                running("713", "444");
                verify("3스트라이크", "[ERROR] 게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요");
            }
        }

        @DisplayName("1을 입력 받으면 게임을 시작 상태로 변경한다")
        @Test
        public void testGameRestart() {
            try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
                mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
                        .thenReturn(7, 1, 3)
                        .thenReturn(7, 1, 3);
                BaseballBot baseballBot = new BaseballBot();
                baseballBot.send("713");
                assertThat(baseballBot.getGameState()).isEqualTo(GameState.WAITING);
                baseballBot.send("1");
                assertThat(baseballBot.getGameState()).isEqualTo(GameState.PROGRESS);
                baseballBot.send("713");
                baseballBot.send("2");
            }
        }

        @DisplayName("2를 입력 받으면 프로그램을 종료시킨다")
        @Test
        public void testGameShutdown() {
            try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
                mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
                        .thenReturn(7, 1, 3);
                BaseballBot baseballBot = new BaseballBot();
                baseballBot.send("713");
                assertThat(baseballBot.getGameState()).isEqualTo(GameState.WAITING);
                baseballBot.send("2");
                assertThat(baseballBot.getGameState()).isEqualTo(GameState.FINISH);
            }
        }

        @Override
        public void runMain() {
            Application.main(new String[]{});
        }
    }
}
