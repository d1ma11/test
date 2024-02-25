package dto.Pet;

import java.util.List;
import java.util.Random;

public enum ParrotBreeds {
    MACAW_PARROT,
    COCKATOOS_PARROT,
    AMAZON_PARROT,
    CONURES_PARROT,
    EASTERN_ROSELLA,
    FISCHERS_LOVEBIRD,
    FINCH_PARROT,
    NANDAY_CONURE,
    QUACKER_PARAKEET;

    private static final List<ParrotBreeds> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static ParrotBreeds randomBreed() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
