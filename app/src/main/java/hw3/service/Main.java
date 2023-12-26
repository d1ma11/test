package hw3.service;

public class Main {
    public static void main(String[] args) {
        CreateAnimalService createAnimalService = new CreateAnimalServiceImpl();

        createAnimalService.createAnimals(2);

        System.out.println();

        createAnimalService.createAnimals();
    }
}