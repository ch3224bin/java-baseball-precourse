package baseball.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum WaitingCommandAnswerSet {
    ONE_MORE_PLAY ("1"),
    GAME_FINISH ("2");

    private final String value;

    WaitingCommandAnswerSet(String value) {
        this.value = value;
    }

    public static boolean contains(String str) {
        List<String> strLst = new ArrayList<>();
        for (WaitingCommandAnswerSet value : values()) {
            strLst.add(value.value);
        }
        return strLst.contains(str);
    }

    public boolean isEqualTo(String str) {
        return Objects.equals(this.value, str);
    }
}
