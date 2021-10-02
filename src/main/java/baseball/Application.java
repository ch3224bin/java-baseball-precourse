package baseball;

import nextstep.utils.Console;

import java.util.NoSuchElementException;

public class Application {
    public static void main(String[] args) {
        BaseballBot baseballBot = new BaseballBot();

        System.out.print(baseballBot.getMessage());

        try {
            String answer = Console.readLine().trim();
            Result result = baseballBot.send(answer);
            System.out.println(result.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println(Result.Code.ERROR_WRONG_ANSWER.getMessage());
        }
    }
}
