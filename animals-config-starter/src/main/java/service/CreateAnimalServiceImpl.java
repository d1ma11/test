package service;

import dto.Animal;
import dto.AnimalsEnum;

public class CreateAnimalServiceImpl implements CreateAnimalService {
    private AnimalsEnum animalType;

    private final AnimalFactory animalFactory;

    public CreateAnimalServiceImpl(AnimalFactory animalFactory) {
        this.animalFactory = animalFactory;
    }

    @Override
    public Animal createBear() {
        return animalFactory.createBear();
    }

    @Override
    public Animal createTiger() {
        return animalFactory.createTiger();
    }

    @Override
    public Animal createParrot() {
        return animalFactory.createParrot();
    }

    @Override
    public Animal createHamster() {
        return animalFactory.createHamster();
    }

    /**
     * Создает 10 уникальных животных случайного типа
     */
    @Override
    public Animal[] createAnimals() {
        Animal[] animals = new Animal[10];
        for (int i = 0; i < 10; i++) {
            animalType = AnimalsEnum.randomAnimal();
            animals[i] = createAnimal(animalType);
        }
        return animals;
    }

    @Override
    public void setAnimalType(AnimalsEnum animalsEnum) {
        this.animalType = animalsEnum;
    }
}