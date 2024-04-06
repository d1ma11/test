package service.factory;

import dto.Animal;
import dto.CharacterEnum;
import dto.Pet.Hamster;
import dto.Pet.HamsterBreeds;
import dto.Pet.Parrot;
import dto.Pet.ParrotBreeds;
import dto.Predator.Bear;
import dto.Predator.BearBreeds;
import dto.Predator.Tiger;
import dto.Predator.TigerBreeds;

import java.time.LocalDate;

/**
 * Данный класс несет в себе методы, которые создают дубликаты конкретных животных
 */
public class AnimalDuplicateFactory {

    private AnimalDuplicateFactory() {}

    /**
     * Создает дубликат медведя с предопределенными значениями
     *
     * @return медведь с одними и теми же значениями
     */
    public static Animal createDuplicateBear() {
        BearBreeds breed = BearBreeds.BROWN_BEAR;
        String name = "Misha";
        double cost = 228.48;
        CharacterEnum character = CharacterEnum.TRICKY;
        LocalDate birthDate = LocalDate.of(2000, 4, 6);

        return new Bear(breed, name, cost, character, birthDate);
    }

    /**
     * Создает дубликат тигра с предопределенными значениями
     *
     * @return тигр с одними и теми же значениями
     */
    public static Animal createDuplicateTiger() {
        TigerBreeds breed = TigerBreeds.BENGAL_TIGER;
        String name = "Vasya";
        double cost = 1337.48;
        CharacterEnum character = CharacterEnum.SMART;
        LocalDate birthDate = LocalDate.of(2004, 7, 28);

        return new Tiger(breed, name, cost, character, birthDate);
    }

    /**
     * Создает дубликат попугая с предопределенными значениями
     *
     * @return попугай с одними и теми же значениями
     */
    public static Animal createDuplicateParrot() {
        ParrotBreeds breed = ParrotBreeds.AMAZON_PARROT;
        String name = "Kesha";
        double cost = 666.48;
        CharacterEnum character = CharacterEnum.TALKATIVE;
        LocalDate birthDate = LocalDate.of(1996, 1, 2);

        return new Parrot(breed, name, cost, character, birthDate);
    }

    /**
     * Создает дубликат хомяка с предопределенными значениями
     *
     * @return хомяк с одними и теми же значениями
     */
    public static Animal createDuplicateHamster() {
        HamsterBreeds breed = HamsterBreeds.CHINESE_HAMSTER;
        String name = "Vasya";
        double cost = 48.48;
        CharacterEnum character = CharacterEnum.LAZY;
        LocalDate birthDate = LocalDate.of(2012, 2, 3);

        return new Hamster(breed, name, cost, character, birthDate);
    }
}
