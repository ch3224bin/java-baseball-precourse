# 숫자 야구 게임
## 진행 방법
* 숫자 야구 게임 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 과제를 제출한다.

## 과제 제출 과정
* [과제 제출 방법](https://github.com/next-step/nextstep-docs/tree/master/precourse)

## 기능목록

* 프로그램 시작시 게임은 진행 상태로 변경된다.
* 게임 시작시
  * 임의의 숫자 3개를 생성한다.
  * "숫자를 입력해주세요 : " 묻는다.
* 진행 상태에서 숫자를 입력받으면 결과를 전송한다.
  * 세자리 숫자만 입력 받고 그 외에는 오류 메세지를 보낸다.
  * 중복이 없는 세자리 숫자를 입력 받고 그 외에는 오류 메세지를 보낸다.
  * 입력 받은 세자리 숫자와 정답의 같은 자리 숫자 만큼 스트라이크 횟수를 증가시킨다.
    * 결과에는 "1 스트라이크"로 출력한다.
  * 입력 받은 세자리 숫자와 정답의 다른 자리 숫자 만큼 볼 횟수를 증가시킨다.
    * 결과에는 "1 볼"로 출력한다.
  * 결과는 스트라이크 다음 볼 순서로 출력한다.
  * 모든 숫자가 다른 경우 "낫싱"으로 출력한다.
  * 입력 숫자와 정답이 같으면 "3개의 숫자를 모두 맞히셨습니다! 게임 끝" 메세지를 전송하고 대기 상태로 변경한다.
* 대기 상태로 변경되면 "게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요."를 전송한다.
* 대기 상태에서
  * 대기 상태에서는 1, 2 값만 받는다.
  * 1을 입력 받으면 게임을 시작 상태로 변경한다.
  * 2를 입력 받으면 프로그램을 종료시킨다.
