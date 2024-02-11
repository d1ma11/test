package hw3.service;

import hw3.dto.Animal;
import hw3.dto.AnimalsEnum;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class CreateAnimalServiceImpl implements CreateAnimalService {
    private AnimalsEnum animalType;

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