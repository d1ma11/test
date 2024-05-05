package repository;

import dto.db_objects.Animal;
import dto.db_objects.AnimalType;
import exception.NegativeAgeParameterException;
import exception.SmallListSizeException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.CreateAnimalServiceImpl;
import service.helper.JsonHelper;
import service.helper.UtilityClass;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static service.helper.SearchUtilityClass.*;


@Service
public class AnimalsRepositoryImpl implements AnimalsRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(AnimalsRepositoryImpl.class);
    private final CreateAnimalServiceImpl createAnimalService;
    private final SessionFactory sessionFactory;
    private final UtilityClass utilityClass;
    private final JsonHelper jsonHelper;

    public AnimalsRepositoryImpl(CreateAnimalServiceImpl createAnimalService, SessionFactory getSessionFactory, UtilityClass utilityClass, JsonHelper jsonHelper) {
        this.createAnimalService = createAnimalService;
        this.sessionFactory = getSessionFactory;
        this.utilityClass = utilityClass;
        this.jsonHelper = jsonHelper;
    }

    @PostConstruct
    public void init() {
        List<Animal> animals = createAnimalService
                .getAnimalList()
                .stream()
                .map(Animal::new)
                .toList();
        saveAnimal(animals);
    }

    @Override
    public Map<String, LocalDate> findLeapYearNames() {
        Map<String, List<Animal>> animalMap = utilityClass.getAnimalsFromDatabase();

        Map<String, LocalDate> result = animalMap.values().stream()
                .flatMap(List::stream)
                .filter(animal -> isLeapYear(animal.getBirthDate().getYear()))
                .collect(Collectors.toMap(
                        animal -> animal.getAnimalType() + " " + animal.getName(),
                        Animal::getBirthDate,
                        (oldValue, newValue) -> newValue
                ));

        jsonHelper.writeToJsonFile("findLeapYearNames", result);

        return result;
    }

    @Override
    public Map<Animal, Integer> findOlderAnimal(int n) {
        Map<String, List<Animal>> animalMap = utilityClass.getAnimalsFromDatabase();
        Map<Animal, Integer> result = new HashMap<>();

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

        jsonHelper.writeToJsonFile("findOlderAnimal", result);

        return result;
    }

    @Override
    public Map<String, List<Animal>> findDuplicate() {
        Map<String, List<Animal>> animalMap = utilityClass.getAnimalsFromDatabase();
        Map<String, List<Animal>> result = new HashMap<>();

        if (animalMap == null) {
            throw new NullPointerException("Your animal collection should not be null");
        }

        animalMap.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Animal::getAnimalType))
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

        jsonHelper.writeToJsonFile("findDuplicate", result);

        return result;
    }

    @Override
    public void printDuplicate() {
        Map<String, List<Animal>> duplicateAnimals = findDuplicate();

        for (Map.Entry<String, List<Animal>> entry : duplicateAnimals.entrySet()) {
            int duplicateCount = duplicateAnimals.get(entry.getKey()).size();
            if (duplicateCount == 0) {
                LOGGER.info("There are no duplicated animals with type {}", entry.getKey());
            } else {
                LOGGER.info("{} has the following duplicates animals:\\n* * * * * Duplicates * * * * *", entry.getKey());
                for (Animal animal : entry.getValue()) {
                    LOGGER.info(String.valueOf(animal));
                }
                LOGGER.info("* * * * * End of list * * * * *\n");
            }
        }
    }

    @Override
    public OptionalDouble findAverageAge(List<Animal> animalList) {
        if (animalList == null) {
            throw new NullPointerException("Your animal list should not be null");
        }

        OptionalDouble optionalDouble = animalList.stream()
                .mapToDouble(animal -> calculateAge(animal.getBirthDate()))
                .average();

        jsonHelper.writeToJsonFile("findAverageAge", optionalDouble.isPresent() ? optionalDouble.getAsDouble() : -1);

        return optionalDouble;
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

        List<Animal> result = animalList.stream()
                .filter(animal -> calculateAge(animal.getBirthDate()) > 5 && animal.getCost().doubleValue() > averageCost)
                .sorted(Comparator.comparing(Animal::getBirthDate))
                .collect(Collectors.toList());

        jsonHelper.writeToJsonFile("findOldAndExpensive", result);

        return result;
    }

    @Override
    public List<String> findMinCostAnimals(List<Animal> animalList) throws SmallListSizeException {
        if (animalList == null) {
            throw new NullPointerException("Your animal list should not be null");
        }
        if (animalList.size() < 3) {
            throw new SmallListSizeException("Animal list must contain at least 3 animals");
        }

        List<String> result = animalList.stream()
                .sorted(Comparator.comparing(Animal::getCost))
                .limit(3)
                .map(Animal::getName)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        jsonHelper.writeToJsonFile("findMinCostAnimals", result);

        return result;
    }

    private void saveAnimal(List<Animal> animalList) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Set<AnimalType> animalTypes = animalList.stream().map(Animal::getAnimalType).collect(Collectors.toSet());

            for (AnimalType animalType : animalTypes) {
                for (Animal animal : animalList) {
                    if (animal.getAnimalType().equals(animalType)) {
                        animalType.addToAnimalList(animal);
                        animal.setAnimalType(animalType);
                    }
                }
            }

            for (AnimalType animalType : animalTypes) session.persist(animalType);

            for (Animal animal : animalList) {
                session.persist(animal.getBreed());
                session.persist(animal);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
