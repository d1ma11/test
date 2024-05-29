package repository;

import annotation.Logging;
import dto.Animal;
import exception.NegativeAgeParameterException;
import exception.SmallListSizeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import service.helper.JsonHelper;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static service.helper.SearchUtilityClass.calculateAge;
import static service.helper.SearchUtilityClass.isLeapYear;

@Service
public class AnimalsRepositoryImpl {

    private final static Logger LOGGER = LoggerFactory.getLogger(AnimalsRepositoryImpl.class);
    private final AnimalsRepository animalsRepository;
    private final JsonHelper jsonHelper;

    public AnimalsRepositoryImpl(@Lazy AnimalsRepository animalsRepository, JsonHelper jsonHelper) {
        this.animalsRepository = animalsRepository;
        this.jsonHelper = jsonHelper;
    }

    /**
     * ������� ���� ��������, ���������� � ���������� ���
     *
     * @return ������ ��������
     */
    @Logging(value = "method FindLeapYearNames()", entering = true, exiting = true)
    public Map<String, LocalDate> findLeapYearNames() {
        List<Animal> animals = animalsRepository.findAll();

        Map<String, LocalDate> result = animals.stream()
                .filter(animal -> isLeapYear(animal.getBirthDate().getYear()))
                .collect(Collectors.toMap(
                        animal -> animal.getAnimalType() + " " + animal.getName(),
                        Animal::getBirthDate,
                        (oldValue, newValue) -> newValue
                ));

        jsonHelper.writeToJsonFile("findLeapYearNames", result);

        return result;
    }

    /**
     * ������� ���� �������� ������ N ���
     *
     * @param n ����������� �� ��������
     * @return ������ ��������
     */
    @Logging(value = "method FindOlderAnimal()", entering = true, exiting = true)
    public Map<Animal, Integer> findOlderAnimal(int n) {
        Set<Animal> animals = new HashSet<>(animalsRepository.findAll());

        if (n < 0) {
            throw new NegativeAgeParameterException("Age parameter \"n\" should be greater or equals to 0");
        }

        Map<Animal, Integer> result = animals.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        animal -> calculateAge(animal.getBirthDate())
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > n)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        jsonHelper.writeToJsonFile("findOlderAnimal", result);

        return result;
    }

    /**
     * ������� ��������� ��������
     */
    @Logging(value = "method FindDuplicate()", entering = true, exiting = true)
    public Map<String, List<Animal>> findDuplicate() {
        List<Animal> animals = animalsRepository.findAll();

        Map<String, List<Animal>> result = new HashMap<>();

        Map<String, List<Animal>> groupedByType = animals.stream()
                .collect(Collectors.groupingBy(animal -> animal.getAnimalType().toString()));

        for (Map.Entry<String, List<Animal>> entry : groupedByType.entrySet()) {
            List<Animal> duplicateAnimals = entry.getValue().stream()
                    .collect(Collectors.groupingBy(Function.identity()))
                    .entrySet().stream()
                    .filter(e -> e.getValue().size() > 1)
                    .flatMap(e -> e.getValue().stream())
                    .collect(Collectors.toList());

            if (!duplicateAnimals.isEmpty()) {
                result.put(entry.getKey(), duplicateAnimals);
            }
        }

        jsonHelper.writeToJsonFile("findDuplicate", result);

        return result;
    }

    @Logging(value = "method PrintDuplicate()", entering = true, exiting = true)
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

    /**
     * ������� ������� ������� ���� ��������
     *
     * @param animalList ������ ��������
     * @return ������� �������
     */
    @Logging(value = "method FindAverageAge()", entering = true, exiting = true)
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

    /**
     * ������� ��������, �������:
     * - ������ 5 ���
     * - ���� ������ ������� ��������� ���� ��������
     *
     * @param animalList ������ ��������
     * @return ��������������� �� ���� �������� (�� �����������) ������ ��������
     */
    @Logging(value = "method FindOldAndExpensive()", entering = true, exiting = true)
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

    /**
     * ������� �������� 3 �������� � ����� ������ �����
     *
     * @param animalList ������ ��������
     * @return ������ ����, ��������������� � �������� �������
     * @throws SmallListSizeException ���� ������ ������ animalList ������, ��� 3
     */
    @Logging(value = "method FindMicCostAnimals()", entering = true, exiting = true)
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
}
