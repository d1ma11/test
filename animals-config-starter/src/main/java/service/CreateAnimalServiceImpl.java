package service;

import dto.Animal;
import dto.AnimalsEnum;
import service.factory.AnimalFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, List<Animal>> createAnimals() {
        Map<String, List<Animal>> animals = new HashMap<>();

        for (int i = 0; i < 20; i++) {
            animalType = AnimalsEnum.randomAnimal();
            String animalTypeKey = animalType.name();
            if (!animals.containsKey(animalTypeKey)) {
                animals.put(animalTypeKey, new ArrayList<>());
            }
            Animal newAnimalValue = createAnimal(animalType);
            animals.get(animalTypeKey).add(newAnimalValue);
        }
        return animals;
    }

    @Override
    public void setAnimalType(AnimalsEnum animalsEnum) {
        this.animalType = animalsEnum;
    }
}