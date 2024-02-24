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


    public Map<String, List<Animal>> getAnimalMap() {
        return animalMap;
    }

    public void setAnimalMap(Map<String, List<Animal>> animalMap) {
        this.animalMap = animalMap;
    }

    @PostConstruct
    public void init() {
        animalMap = createAnimalService.createAnimals();
    }

    @Override
    public Map<String, LocalDate> findLeapYearNames() {
        Map<String, LocalDate> animalsBornInLeapYear = new HashMap<>();

        for (AnimalsEnum animalType : AnimalsEnum.values()) {
            putAnimalsBornInLeapYear(animalType, animalsBornInLeapYear);
        }

        return animalsBornInLeapYear;
    }

    /**
     * Данный метод служит вспомогательным методом для метода findLeapYearNames()
     *
     * @param animalType            тип животного
     * @param animalsBornInLeapYear ассоциативный массив, хранящий в себе в виде ключа "тип животного + имя животного",
     *                              а в виде значения - его год рождения
     */
    private void putAnimalsBornInLeapYear(AnimalsEnum animalType, Map<String, LocalDate> animalsBornInLeapYear) {
        List<Animal> animalList = animalMap.getOrDefault(animalType.toString(), Collections.emptyList());
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

    @Override
    public Map<Animal, Integer> findOlderAnimal(int n) {
        Map<Animal, Integer> olderAnimals = new HashMap<>();

        for (AnimalsEnum animalType : AnimalsEnum.values()) {
            putOldAnimal(animalType, olderAnimals, n);
        }

        return olderAnimals;
    }

    /**
     * Данный метод служит вспомогательным методом для метода findOlderAnimal()
     *
     * @param animalType тип животного
     * @param olderAnimals ассоциативный массив, хранящий в себе в виде ключа "животное",
     *                     а в виде значения - его возраст
     * @param n возраст, больше которого должен быть возраст животных
     */
    private void putOldAnimal(AnimalsEnum animalType, Map<Animal, Integer> olderAnimals, int n) {
        List<Animal> animalList = animalMap.getOrDefault(animalType.toString(), Collections.emptyList());

        if (animalList.isEmpty()) {
            System.out.println("No animals with type " + animalType + " were generated");
            return;
        }

        Animal oldestAnimal = findAnimalWithMaxAge(animalList);
        int maxAge = calculateAge(oldestAnimal.getBirthDate());

        if (maxAge < n && n > 0) {
            olderAnimals.put(oldestAnimal, maxAge);
        } else {
            for (Animal animal : animalList) {
                int age = calculateAge(animal.getBirthDate());

                if (age > n && n > 0) {
                    olderAnimals.put(animal, age);
                }
            }
        }

    }

    @Override
    public Map<String, Integer> findDuplicate() {
        Map<String, Integer> duplicateAnimals = new HashMap<>();

        for (AnimalsEnum animalType : AnimalsEnum.values()) {
            countDuplicateAnimals(animalType, duplicateAnimals);
        }

        return duplicateAnimals;
    }

    /**
     * Данный метод служит вспомогательным методом для метода findDuplicate()
     *
     * @param animalType тип животного
     * @param duplicateAnimals ассоциативный массив, хранящий в себе в виде ключа "тип животного",
     *                         а в виде значения - количество дублирующих животных данного типа
     */
    private void countDuplicateAnimals(AnimalsEnum animalType, Map<String, Integer> duplicateAnimals) {
        List<Animal> animalList = animalMap.getOrDefault(animalType.toString(), Collections.emptyList());

        if (animalList.isEmpty()) {
            System.out.println("No animals with type " + animalType + " were generated");
            return;
        }

        Set<Animal> animalSet = new HashSet<>(animalList);
        duplicateAnimals.put(animalType.toString(), animalList.size() - animalSet.size());

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
}
