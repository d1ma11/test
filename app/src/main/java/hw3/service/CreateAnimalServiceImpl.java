package hw3.service;

import hw3.dto.AnimalsEnum;

public class CreateAnimalServiceImpl implements CreateAnimalService {

    @Override
    public void createAnimals(int n) {
        System.out.println("Using for-loop:");
        for (int i = 0; i < n; i++) {
            createAnimal(AnimalsEnum.randomAnimal());
        }
    }

    public void createAnimals() {
        System.out.println("Using do-while loop:");
        int count = 0;
        do {
            createAnimal(AnimalsEnum.randomAnimal());
            count++;
        } while (count < 10);
    }
}