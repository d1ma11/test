package hw3.service;

import java.time.LocalDate;
import java.util.Random;

import hw3.dto.Animal;
import hw3.dto.CharacterEnum;
import hw3.dto.NamesEnum;
import hw3.dto.Pet.Hamster;
import hw3.dto.Pet.HamsterBreeds;
import hw3.dto.Pet.Parrot;
import hw3.dto.Pet.ParrotBreeds;
import hw3.dto.Predator.Bear;
import hw3.dto.Predator.BearBreeds;
import hw3.dto.Predator.Tiger;
import hw3.dto.Predator.TigerBreeds;

public class AnimalFactory {
    private static final Random RANDOM = new Random();

    /**
     * Создает медведя со случайными параметрами
     * @return медведь
     */
    public static Animal createBear() {
        BearBreeds breed = BearBreeds.randomBreed();
        NamesEnum name = NamesEnum.randomName();
        double cost = RANDOM.nextDouble(100_000, 200_000);
        CharacterEnum character = CharacterEnum.randomCharacter();
        LocalDate birthDate = LocalDate.ofEpochDay(RANDOM.nextInt(19_700));
        return new Bear(breed, name, cost, character, birthDate);
    }

    /**
     * Создает тигра со случайными параметрами
     * @return тигр
     */
    public static Animal createTiger() {
        TigerBreeds breed = TigerBreeds.randomBreed();
        NamesEnum name = NamesEnum.randomName();
        double cost = RANDOM.nextDouble(500_000, 1_000_000);
        CharacterEnum character = CharacterEnum.randomCharacter();
        LocalDate birthDate = LocalDate.ofEpochDay(RANDOM.nextInt(19_700));
        return new Tiger(breed, name, cost, character, birthDate);
    }

    /**
     * Создает попугая со случайными параметрами
     * @return попугай
     */
    public static Animal createParrot() {
        ParrotBreeds breed = ParrotBreeds.randomBreed();
        NamesEnum name = NamesEnum.randomName();
        double cost = RANDOM.nextDouble(500_000, 1_000_000);
        CharacterEnum character = CharacterEnum.randomCharacter();
        LocalDate birthDate = LocalDate.ofEpochDay(RANDOM.nextInt(19_700));
        return new Parrot(breed, name, cost, character, birthDate);
    }

    /**
     * Создает хомяка со случайными параметрами
     * @return хомяк
     */
    public static Animal createHamster() {
        HamsterBreeds breed = HamsterBreeds.randomBreed();
        NamesEnum name = NamesEnum.randomName();
        double cost = RANDOM.nextDouble(500_000, 1_000_000);
        CharacterEnum character = CharacterEnum.randomCharacter();
        LocalDate birthDate = LocalDate.ofEpochDay(RANDOM.nextInt(19_700));
        return new Hamster(breed, name, cost, character, birthDate);
    }
}
