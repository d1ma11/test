package hw3.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum AnimalsEnum {
    PARROT,
    HAMSTER,
    BEAR,
    TIGER;

    private static final List<AnimalsEnum> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static AnimalsEnum randomAnimal() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
