package service;

import dto.AbstractAnimal;
import service.factory.AnimalFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class CreateAnimalServiceImpl implements CreateAnimalService {

    private final AnimalFactory animalFactory;
    private AnimalFactory.AnimalType animalType;

    public CreateAnimalServiceImpl(AnimalFactory animalFactory) {
        this.animalFactory = animalFactory;
    }

    public AnimalFactory.AnimalType getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalFactory.AnimalType animalType) {
        this.animalType = animalType;
    }

    @Override
    public AbstractAnimal createRandomAnimal() {
        return animalFactory.getAnimal();
    }

    @Override
    public Map<String, List<AbstractAnimal>> createRandomAnimals(int n) {
        return CreateAnimalService.super.createRandomAnimals(n);
    }

    /**
     * Метод для получения списка 10 рандомных животных
     *
     * @return Список животных длинной 10
     */
    public List<AbstractAnimal> getAnimalList() {
        List<AbstractAnimal> animalList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            AbstractAnimal animal = createRandomAnimal();
            animalList.add(animal);
        }
        return animalList;
    }
}