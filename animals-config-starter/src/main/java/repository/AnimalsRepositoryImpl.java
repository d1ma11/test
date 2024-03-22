package repository;

import dto.Animal;
import exception.NegativeAgeParameterException;
import exception.SmallListSizeException;
import org.springframework.beans.factory.annotation.Autowired;
import service.CreateAnimalService;
import service.helper.UtilityClass;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static service.helper.SearchUtilityClass.*;
import static service.helper.UtilityClass.getAnimalType;

public class AnimalsRepositoryImpl implements AnimalsRepository {
    private Map<String, List<Animal>> animalMap = new ConcurrentHashMap<>();

    @Autowired
    private CreateAnimalService createAnimalService;

    @PostConstruct
    public void init() {
        Map<String, List<Animal>> tempAnimalMap = createAnimalService.createAnimals();
        tempAnimalMap.forEach((key, value) -> {
            List<Animal> synchronizedList = Collections.synchronizedList(value);
            animalMap.put(key, synchronizedList);
        });
    }

    public Map<String, List<Animal>> getAnimalMap() {
        return animalMap;
    }

    public void setAnimalMap(Map<String, List<Animal>> animalMap) {
        this.animalMap = animalMap;
    }

    @Override
    public Map<String, LocalDate> findLeapYearNames() {
        if (animalMap == null) {
            throw new NullPointerException("Your animal collection should not be null");
        }
        return animalMap.values().stream()
                .flatMap(List::stream)
                .filter(animal -> isLeapYear(animal.getBirthDate().getYear()))
                .collect(Collectors.toMap(
                        animal -> getAnimalType(animal) + " " + animal.getName(),
                        Animal::getBirthDate,
                        (oldValue, newValue) -> newValue
                ));
    }

    @Override
    public Map<Animal, Integer> findOlderAnimal(int n) {
        Map<Animal, Integer> result = new HashMap<>();

        if (animalMap == null) {
            throw new NullPointerException("Your animal collection should not be null");
        }
        if (n < 0) {
            throw new NegativeAgeParameterException("Age parameter \"n\" should be greater or equals to 0");
        }

        animalMap.values()
                .forEach(
                        animalList -> {
                            Animal oldestAnimal = findAnimalWithMaxAge(animalList);
                            int maxAge = calculateAge(oldestAnimal.getBirthDate());

                            if (maxAge < n) {
                                result.put(oldestAnimal, maxAge);
                            } else {
                                for (Animal animal : animalList) {
                                    int age = calculateAge(animal.getBirthDate());

                                    if (age > n) {
                                        result.put(animal, age);
                                    }
                                }
                            }
                        }
                );

        return result;
    }

    @Override
    public Map<String, List<Animal>> findDuplicate() {
        Map<String, List<Animal>> result = new HashMap<>();

        if (animalMap == null) {
            throw new NullPointerException("Your animal collection should not be null");
        }

        animalMap.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(UtilityClass::getAnimalType))
                .forEach((animalType, animalList) -> {
                    List<Animal> duplicateAnimals = animalList.stream()
                            .collect(Collectors.groupingBy(Function.identity()))
                            .entrySet().stream()
                            .filter(entry -> entry.getValue().size() > 1)
                            .flatMap(entry -> Collections.nCopies(entry.getValue().size(), entry.getKey()).stream())
                            .collect(Collectors.toList());
                    if (!duplicateAnimals.isEmpty()) {
                        result.put(animalType.toString(), duplicateAnimals);
                    }
                });

        return result;
    }

    @Override
    public void printDuplicate() {
        Map<String, List<Animal>> duplicateAnimals = findDuplicate();

        for (Map.Entry<String, List<Animal>> entry : duplicateAnimals.entrySet()) {
            int duplicateCount = duplicateAnimals.get(entry.getKey()).size();
            if (duplicateCount == 0) {
                System.out.println("There are no duplicated animals with type " + entry.getKey());
            } else {
                System.out.println(entry.getKey() + " has the following duplicates animals:\n* * * * * Duplicates * * * * *");
                for (Animal animal : entry.getValue()) {
                    System.out.println(animal);
                }
                System.out.println("* * * * * End of list * * * * *\n");
            }
        }
    }

    @Override
    public OptionalDouble findAverageAge(List<Animal> animalList) {
        if (animalList == null) {
            throw new NullPointerException("Your animal list should not be null");
        }

        return animalList.stream()
                .mapToDouble(animal -> calculateAge(animal.getBirthDate()))
                .average();
    }

    @Override
    public List<Animal> findOldAndExpensive(List<Animal> animalList) {
        if (animalList == null) {
            throw new NullPointerException("Your animal list should not be null");
        }

        double averageCost = animalList.stream()
                .mapToDouble(animal -> animal.getCost().doubleValue())
                .average()
                .orElse(0);

        return animalList.stream()
                .filter(animal -> calculateAge(animal.getBirthDate()) > 5 && animal.getCost().doubleValue() > averageCost)
                .sorted(Comparator.comparing(Animal::getBirthDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findMinCostAnimals(List<Animal> animalList) throws SmallListSizeException {
        if (animalList == null) {
            throw new NullPointerException("Your animal list should not be null");
        }
        if (animalList.size() < 3) {
            throw new SmallListSizeException("Animal list must contain at least 3 animals");
        }

        return animalList.stream()
                .sorted(Comparator.comparing(Animal::getCost))
                .limit(3)
                .map(Animal::getName)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }
}
