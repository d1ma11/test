package hw3.service;

import java.util.Random;

import hw3.dto.AnimalsEnum;
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

public interface CreateAnimalService {
    Random rand = new Random();

    /**
     * Создает одно уникальное животное случайного типа
     */
    default void createAnimal(AnimalsEnum animal) {
        switch (animal) {
            case BEAR:
                System.out.println(new Bear(BearBreeds.randomBreed(), NamesEnum.randomName(), rand.nextDouble(100_000, 200_000),
                                CharacterEnum.randomCharacter()));
                break;
            case TIGER:
                System.out.println(new Tiger(TigerBreeds.randomBreed(), NamesEnum.randomName(), rand.nextDouble(500_000, 1_000_000),
                        CharacterEnum.randomCharacter()));
                break;
            case PARROT:
                System.out.println(new Parrot(ParrotBreeds.randomBreed(), NamesEnum.randomName(), rand.nextDouble(1000, 2000),
                                CharacterEnum.randomCharacter()));
                break;
            case HAMSTER:
                System.out.println(new Hamster(HamsterBreeds.randomBreed(), NamesEnum.randomName(), rand.nextDouble(2500, 4000),
                                CharacterEnum.randomCharacter()));
                break;
        }
    }

    /**
     * Создает 10 уникальных животных случайного типа
     */
    default void createAnimals() {
        System.out.println("Using while loop:");
        int count = 0;
        while (count < 10) {
            createAnimal(AnimalsEnum.randomAnimal());
            count++;
        }
    }

    /**
     * Создает указанное количество животных случайного типа
     *
     * @param n количество животных для создания
     */
    void createAnimals(int n);
}
