package repository;

import dto.Animal;
import dto.AnimalsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import service.CreateAnimalService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

import static service.helper.SearchUtilityClass.*;


public class AnimalsRepositoryImpl implements AnimalsRepository {
    private Map<String, List<Animal>> animalMap = new HashMap<>();

    @Autowired
    private CreateAnimalService createAnimalService;

    @PostConstruct
    public void init() {
        animalMap = createAnimalService.createAnimals();
    }

    public Map<String, List<Animal>> getAnimalMap() {
        return animalMap;
    }

    public void setAnimalMap(Map<String, List<Animal>> animalMap) {
        this.animalMap = animalMap;
    }

    @Override
    public Map<String, LocalDate> findLeapYearNames() {
        Map<String, LocalDate> animalsBornInLeapYear = new HashMap<>();

        for (AnimalsEnum animalType : AnimalsEnum.values()) {
            putAnimalsBornInLeapYear(animalType, animalsBornInLeapYear);
        }

        return animalsBornInLeapYear;
    }

    @Override
    public Map<Animal, Integer> findOlderAnimal(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Your argument n should be greater than 0");
        }
        Map<Animal, Integer> olderAnimals = new HashMap<>();

        for (AnimalsEnum animalType : AnimalsEnum.values()) {
            putOldAnimal(animalType, olderAnimals, n);
        }

        return olderAnimals;
    }

    @Override
    public Map<String, Integer> findDuplicate() {
        Map<String, Integer> duplicateAnimals = new HashMap<>();

        for (AnimalsEnum animalType : AnimalsEnum.values()) {
            countDuplicateAnimals(animalType, duplicateAnimals);
        }

        return duplicateAnimals;
    }

    @Override
    public void printDuplicate() {
        Map<String, Integer> duplicateAnimals = findDuplicate();

        for (String animalType : duplicateAnimals.keySet()) {
            int duplicateCount = duplicateAnimals.get(animalType);
            if (duplicateCount == 0) {
                System.out.println("There are no duplicated animals with type " + animalType);
            } else {
                System.out.println("Type " + animalType + " has " + duplicateCount + " duplications");
            }
        }
    }

    private void putAnimalsBornInLeapYear(AnimalsEnum animalType, Map<String, LocalDate> animalsBornInLeapYear) {
        List<Animal> animalList = getAnimalListByAnimalTypeOrEmptyList(animalType);
        boolean putAtLeastOneAnimal = false;

        if (animalList.isEmpty()) {
            System.out.println("No animals with type " + animalType + " were generated");
            return;
        }

        for (Animal animal : animalList) {
            LocalDate birthYear = animal.getBirthDate();
            if (isLeapYear(birthYear.getYear())) {
                animalsBornInLeapYear.put(animalType + " " + animal.getName(), birthYear);
                putAtLeastOneAnimal = true;
            }
        }

        if (!putAtLeastOneAnimal) {
            System.out.println("No animals with type " + animalType + " were born in leap year");
        }
    }

    private void putOldAnimal(AnimalsEnum animalType, Map<Animal, Integer> olderAnimals, int n) {
        List<Animal> animalList = getAnimalListByAnimalTypeOrEmptyList(animalType);

        if (animalList.isEmpty()) {
            System.out.println("No animals with type " + animalType + " were generated");
            return;
        }

        Animal oldestAnimal = findAnimalWithMaxAge(animalList);
        int maxAge = calculateAge(oldestAnimal.getBirthDate());

        if (maxAge < n) {
            olderAnimals.put(oldestAnimal, maxAge);
        } else {
            for (Animal animal : animalList) {
                int age = calculateAge(animal.getBirthDate());

                if (age > n) {
                    olderAnimals.put(animal, age);
                }
            }
        }

    }

    private void countDuplicateAnimals(AnimalsEnum animalType, Map<String, Integer> duplicateAnimals) {
        List<Animal> animalList = getAnimalListByAnimalTypeOrEmptyList(animalType);

        if (animalList.isEmpty()) {
            System.out.println("No animals with type " + animalType + " were generated");
            return;
        }

        Set<Animal> animalSet = new HashSet<>(animalList);
        duplicateAnimals.put(animalType.toString(), animalList.size() - animalSet.size());
    }

    private List<Animal> getAnimalListByAnimalTypeOrEmptyList(AnimalsEnum animalType) {
        return animalMap.getOrDefault(animalType.name(), Collections.emptyList());
    }
}
