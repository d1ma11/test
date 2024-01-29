package hw3.service;

import hw3.dto.Animal;
import hw3.dto.AnimalsEnum;

public interface CreateAnimalService {
    /**
     * Создает одно уникальное животное случайного типа
     */
    default Animal createAnimal(AnimalsEnum animal) {
        switch (animal) {
            case BEAR:
                return AnimalFactory.createBear();
            case TIGER:
                return AnimalFactory.createTiger();
            case PARROT:
                return AnimalFactory.createParrot();
            case HAMSTER:
                return AnimalFactory.createHamster();
            default:
                System.out.println("Error");
                return null;
        }
    }
    
    Animal[] createAnimals();
}
