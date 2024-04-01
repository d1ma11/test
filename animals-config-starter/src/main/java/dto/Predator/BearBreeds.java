package dto.Predator;

import java.util.List;
import java.util.Random;

public enum BearBreeds {
    SLOTH_BEAR,
    ASIAN_BLACK_BEAR,
    BROWN_BEAR,
    POLAR_BEAR,
    PANDA;

    private static final List<BearBreeds> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static BearBreeds forName(String name) {
        for (BearBreeds value : VALUES) {
            if (value.name().replace("_", " ").equals(name)) {
                return value;
            }
        }
        return null;
    }

    public static BearBreeds randomBreed() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
