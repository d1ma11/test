package hw3.service;

import hw3.dto.Animal;
import hw3.dto.AnimalsEnum;

public class CreateAnimalServiceImpl implements CreateAnimalService {

    /**
     * Создает 10 уникальных животных случайного типа
     */
    @Override
    public Animal[] createAnimals() {
        Animal[] animals = new Animal[10];
        for (int i = 0; i < 10; i++) {
            animals[i] = createAnimal(AnimalsEnum.randomAnimal());
        }
        return animals;
    }
}