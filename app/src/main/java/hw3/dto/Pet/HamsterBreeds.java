package hw3.dto.Pet;

import java.util.List;
import java.util.Random;

public enum HamsterBreeds {
    SYRIAN_HAMSTER,
    CHINESE_HAMSTER,
    DWARF_CAMPBELL_RUSSIAN_HAMSTER,
    DWARF_WINTER_WHITE_RUSSIAN_HAMSTER,
    DWARF_ROBOROVSKI_HAMSTER;

    private static final List<HamsterBreeds> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static HamsterBreeds randomBreed() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
