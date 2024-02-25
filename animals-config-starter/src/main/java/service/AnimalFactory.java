package service;

import dto.Animal;
import dto.CharacterEnum;
import dto.NamesProvider;
import dto.Pet.Hamster;
import dto.Pet.HamsterBreeds;
import dto.Pet.Parrot;
import dto.Pet.ParrotBreeds;
import dto.Predator.Bear;
import dto.Predator.BearBreeds;
import dto.Predator.Tiger;
import dto.Predator.TigerBreeds;

import java.time.LocalDate;
import java.util.Random;

public class AnimalFactory {
    private static final Random RANDOM = new Random();

    private final NamesProvider namesProvider;

    public AnimalFactory(NamesProvider namesProvider) {
        this.namesProvider = namesProvider;
    }

    /**
     * Создает медведя со случайными параметрами
     *
     * @return медведь
     */
    public Animal createBear() {
        BearBreeds breed = BearBreeds.randomBreed();
        String name = namesProvider.randomName();
        double cost = RANDOM.nextDouble(100_000, 200_000);
        CharacterEnum character = CharacterEnum.randomCharacter();
        LocalDate birthDate = LocalDate.ofEpochDay(RANDOM.nextInt(19_700));
        return new Bear(breed, name, cost, character, birthDate);
    }

    /**
     * Создает тигра со случайными параметрами
     *
     * @return тигр
     */
    public Animal createTiger() {
        TigerBreeds breed = TigerBreeds.randomBreed();
        String name = namesProvider.randomName();
        double cost = RANDOM.nextDouble(500_000, 1_000_000);
        CharacterEnum character = CharacterEnum.randomCharacter();
        LocalDate birthDate = LocalDate.ofEpochDay(RANDOM.nextInt(19_700));
        return new Tiger(breed, name, cost, character, birthDate);
    }

    /**
     * Создает попугая со случайными параметрами
     *
     * @return попугай
     */
    public Animal createParrot() {
        ParrotBreeds breed = ParrotBreeds.randomBreed();
        String name = namesProvider.randomName();
        double cost = RANDOM.nextDouble(500_000, 1_000_000);
        CharacterEnum character = CharacterEnum.randomCharacter();
        LocalDate birthDate = LocalDate.ofEpochDay(RANDOM.nextInt(19_700));
        return new Parrot(breed, name, cost, character, birthDate);
    }

    /**
     * Создает хомяка со случайными параметрами
     *
     * @return хомяк
     */
    public Animal createHamster() {
        HamsterBreeds breed = HamsterBreeds.randomBreed();
        String name = namesProvider.randomName();
        double cost = RANDOM.nextDouble(500_000, 1_000_000);
        CharacterEnum character = CharacterEnum.randomCharacter();
        LocalDate birthDate = LocalDate.ofEpochDay(RANDOM.nextInt(19_700));
        return new Hamster(breed, name, cost, character, birthDate);
    }
}
