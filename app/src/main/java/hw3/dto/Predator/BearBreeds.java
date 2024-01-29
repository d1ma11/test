package hw3.dto.Predator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum BearBreeds {
    SLOTH_BEAR,
    ASIAN_BLACK_BEAR,
    BROWN_BEAR,
    POLAR_BEAR,
    PANDA;

    private static final List<BearBreeds> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static BearBreeds randomBreed() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
