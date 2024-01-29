package hw3.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum CharacterEnum {
    SMART,
    TALKATIVE,
    TRICKY,
    WICKED,
    KIND,
    SHY,
    LAZY,
    SAD;

    private static final List<CharacterEnum> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static CharacterEnum randomCharacter() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
