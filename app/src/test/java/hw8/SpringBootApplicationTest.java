package hw8;

import dto.Animal;
import dto.AnimalsEnum;
import dto.CharacterEnum;
import dto.Pet.Hamster;
import dto.Pet.HamsterBreeds;
import dto.Pet.Parrot;
import dto.Pet.ParrotBreeds;
import dto.Predator.Bear;
import dto.Predator.BearBreeds;
import dto.Predator.Tiger;
import dto.Predator.TigerBreeds;
import exception.NegativeAgeParameterException;
import exception.SmallListSizeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import repository.AnimalsRepository;
import repository.AnimalsRepositoryImpl;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static service.helper.SearchUtilityClass.findAnimalWithMaxAge;

@ActiveProfiles("test")
@SpringBootTest(classes = SpringBootTestConfiguration.class)
public class SpringBootApplicationTest {
    @Autowired
    private AnimalsRepository animalsRepository;

    private static final String NULL_ANIMAL_LIST_MESSAGE = "Your animal list should not be null";
    private static final int minListSize = 3;

    /**
     * Проверка, что метод findLeapYearNames() корректно отрабатывает
     */
    @Test
    public void testFindLeapYearNames() {
        List<Animal> animals = List.of(
                new Tiger(TigerBreeds.randomBreed(), "Tiger", 228.68, CharacterEnum.LAZY, LocalDate.of(2020, 1, 1)),
                new Bear(BearBreeds.randomBreed(), "Bear", 48.68, CharacterEnum.TRICKY, LocalDate.of(2022, 1, 1)),
                new Hamster(HamsterBreeds.randomBreed(), "Hamster", 1337.68, CharacterEnum.SMART, LocalDate.of(2021, 1, 1)),
                new Parrot(ParrotBreeds.randomBreed(), "Parrot", 394.54, CharacterEnum.WICKED, LocalDate.of(2004, 1, 3))
        );

        AnimalsRepositoryImpl animalsRepositoryImpl = new AnimalsRepositoryImpl();
        animalsRepositoryImpl.setAnimalMap(Map.of(
                AnimalsEnum.TIGER.name(), List.of(animals.get(0)),
                AnimalsEnum.BEAR.name(), List.of(animals.get(1)),
                AnimalsEnum.HAMSTER.name(), List.of(animals.get(2)),
                AnimalsEnum.PARROT.name(), List.of(animals.get(3))
        ));

        Map<String, LocalDate> leapYearNames = animalsRepositoryImpl.findLeapYearNames();

        assertThat(leapYearNames.size()).isEqualTo(2);

        Map<String, LocalDate> expected = new HashMap<>(Map.of(
                AnimalsEnum.TIGER.name() + " Tiger", animals.get(0).getBirthDate(),
                AnimalsEnum.PARROT.name() + " Parrot", animals.get(3).getBirthDate()
        ));

        assertThat(leapYearNames).isEqualTo(expected);
    }

    /**
     * Проверка, что метод findLeapYearNames() вернет пустой ассоциативный массив,
     * если он будет работать с пустой коллекцией
     */
    @Test
    public void testFindLeapYearNamesWithEmptyList() {
        AnimalsRepositoryImpl animalsRepositoryImpl = new AnimalsRepositoryImpl();
        animalsRepositoryImpl.setAnimalMap(Collections.emptyMap());

        Map<String, LocalDate> leapYearNames = animalsRepositoryImpl.findLeapYearNames();

        assertThat(leapYearNames).isEmpty();
    }

    /**
     * Проверка, что метод findOlderAnimal() корректно отрабатывает
     */
    @Test
    public void testFindOlderAnimal() {
        int ageThreshold = 10;
        Map<Animal, Integer> olderAnimals = animalsRepository.findOlderAnimal(ageThreshold);

        assertThat(olderAnimals).isNotNull();
    }

    /**
     * Проверка, что метод findOlderAnimal() возвращает животного с максимальным возрастом,
     * когда нет животных, у которых возраст больше переданного параметра
     */
    @Test
    public void testFindOlderAnimal_ReturnsOldestAnimal() {
        List<Animal> animals = List.of(
                new Tiger(TigerBreeds.randomBreed(), "Tiger1", 228.68, CharacterEnum.LAZY, LocalDate.of(2020, 1, 1)),
                new Bear(BearBreeds.randomBreed(), "Bear1", 48.68, CharacterEnum.TRICKY, LocalDate.of(2022, 1, 1)),
                new Tiger(TigerBreeds.randomBreed(), "Tiger2", 1337.68, CharacterEnum.SMART, LocalDate.of(2021, 1, 1))
        );

        AnimalsRepositoryImpl animalsRepositoryImpl = new AnimalsRepositoryImpl();
        // У ключа TIGER значениями будут животные с именами Tiger1, Tiger2
        // У ключа BEAR значением будет животное с именем Bear1
        animalsRepositoryImpl.setAnimalMap(Map.of(
                AnimalsEnum.TIGER.toString(), List.of(animals.get(0), animals.get(2)),
                AnimalsEnum.BEAR.toString(), List.of(animals.get(1))
        ));

        Map<Animal, Integer> result = animalsRepositoryImpl.findOlderAnimal(10);

        assertThat(2).isEqualTo(result.size());
        assertThat(2).isEqualTo(result.get(animals.get(1)));
    }


    /**
     * Проверка, что при отрицательном значении аргумента метода findOlderAnimal() он выбрасывает
     * исключение NegativeAgeParameterException
     */
    @Test
    public void testFindOlderAnimalWithNegativeAge() {
        int ageThreshold = -50;

        assertThrows(
                NegativeAgeParameterException.class,
                () -> animalsRepository.findOlderAnimal(ageThreshold),
                "Age parameter \"n\" should be greater or equals to 0"
        );
    }

    /**
     * Проверка, что метод findDuplicate() корректно отрабатывает
     */
    @Test
    public void testFindDuplicate() {
        Map<String, List<Animal>> duplicateAnimals = animalsRepository.findDuplicate();

        assertThat(duplicateAnimals).isNotNull();

        for (String animalType : duplicateAnimals.keySet()) {
            int duplicateCount = duplicateAnimals.get(animalType).size();

            assertThat(duplicateCount).isGreaterThanOrEqualTo(0);
        }
    }

    /**
     * Проверка, что при пустом массиве животных в аргументе метода findDuplicate() возвращается пустая коллекция
     */
    @Test
    public void testFindDuplicateWithEmptyList() {
        AnimalsRepositoryImpl animalsRepositoryImpl = new AnimalsRepositoryImpl();
        animalsRepositoryImpl.setAnimalMap(Collections.emptyMap());

        Map<String, List<Animal>> leapYearNames = animalsRepositoryImpl.findDuplicate();

        assertThat(leapYearNames).isEmpty();
    }

    /**
     * Проверка, что метод findAverageAge() корректно отрабатывает
     */
    @Test
    public void testFindAverageAge() {
        List<Animal> animalList = List.of(
                new Tiger(TigerBreeds.CASPIAN_TIGER, "Hong", 50, CharacterEnum.TALKATIVE, LocalDate.of(2000, 12, 17)),
                new Bear(BearBreeds.POLAR_BEAR, "Nil", 100, CharacterEnum.KIND, LocalDate.of(2000, 12, 3)),
                new Parrot(ParrotBreeds.COCKATOOS_PARROT, "Leo", 150, CharacterEnum.SMART, LocalDate.of(2000, 12, 8)),
                new Hamster(HamsterBreeds.SYRIAN_HAMSTER, "Bob", 200, CharacterEnum.WICKED, LocalDate.of(2000, 12, 11))
        );

        OptionalDouble avgAge = animalsRepository.findAverageAge(animalList);

        assertThat(avgAge).isNotEqualTo(0);
        avgAge.ifPresent(age -> assertThat(age).isEqualTo(23));
    }

    /**
     * Проверка, что метод findAverageAge() при использовании null в качестве аргумента
     * выбрасывает исключение NullPointerException и выводит следующее сообщение:
     * "Your animal list should not be null
     */
    @Test
    public void testFindAverageAgeWhenGivenNull() {
        var message = assertThrows(NullPointerException.class, () -> animalsRepository.findAverageAge(null));
        assertThat(NULL_ANIMAL_LIST_MESSAGE).isEqualTo(message.getMessage());
    }

    /**
     * Проверка, что метод findAverageAge() при использовании пустого списка в качестве аргумента
     * возвращает пустой список
     */
    @Test
    public void testFindAverageAgeWhenGiverEmptyCollection() {
        assertThat(animalsRepository.findAverageAge(Collections.emptyList())).isEmpty();
    }

    /**
     * Проверка, что метод findFindOldAndExpensive() корректно отрабатывает
     */
    @Test
    public void testFindOldAndExpensive() {
        List<Animal> animalList = List.of(
                new Tiger(TigerBreeds.CASPIAN_TIGER, "Hong", 500, CharacterEnum.TALKATIVE, LocalDate.of(1997, 12, 17)),
                new Bear(BearBreeds.POLAR_BEAR, "Nil", 500, CharacterEnum.KIND, LocalDate.of(2003, 12, 3)),
                new Parrot(ParrotBreeds.COCKATOOS_PARROT, "Leo", 100, CharacterEnum.SMART, LocalDate.of(2021, 12, 8)),
                new Hamster(HamsterBreeds.SYRIAN_HAMSTER, "Bob", 400, CharacterEnum.WICKED, LocalDate.of(2013, 12, 11))
        );

        List<Animal> result = animalsRepository.findOldAndExpensive(animalList);

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(3);
    }

    /**
     * Проверка, что метод findFindOldAndExpensive() при использовании null в качестве аргумента
     * выбрасывает исключение NullPointerException и выводит следующее сообщение:
     * "Your animal list should not be null
     */
    @Test
    public void testFindOldAndExpensiveWhenGivenNull() {
        var message = assertThrows(NullPointerException.class, () -> animalsRepository.findOldAndExpensive(null));
        assertThat(NULL_ANIMAL_LIST_MESSAGE).isEqualTo(message.getMessage());
    }

    /**
     * Проверка, что метод findFindOldAndExpensive() при использовании пустого списка в качестве аргумента
     * возвращает пустой список
     */
    @Test
    public void testFindOldAndExpensiveWhenGivenEmptyCollection() {
        assertThat(animalsRepository.findOldAndExpensive(Collections.emptyList())).isEmpty();
    }

    /**
     * Проверка, что метод findMinCostAnimals() корректно отрабатывает
     */
    @Test
    public void testFindMinCostAnimals() throws SmallListSizeException {
        List<Animal> animalList = List.of(
                new Tiger(TigerBreeds.CASPIAN_TIGER, "Hong", 500, CharacterEnum.TALKATIVE, LocalDate.of(1997, 12, 17)),
                new Bear(BearBreeds.POLAR_BEAR, "Nil", 500, CharacterEnum.KIND, LocalDate.of(2003, 12, 3)),
                new Parrot(ParrotBreeds.COCKATOOS_PARROT, "Leo", 100, CharacterEnum.SMART, LocalDate.of(2021, 12, 8)),
                new Hamster(HamsterBreeds.SYRIAN_HAMSTER, "Bob", 400, CharacterEnum.WICKED, LocalDate.of(2013, 12, 11))
        );

        List<String> result = animalsRepository.findMinCostAnimals(animalList);

        assertThat(result.size()).isEqualTo(3);
    }

    /**
     * Проверка, что метод findMinCostAnimals() при использовании списка из одного животного в качестве аргумента
     * выбрасывает исключение SmallListSizeException, т.к. размер списка меньше минимального необходимого для проведения
     * операции
     */
    @Test
    public void testFindMinCostAnimalsWhenGivenOneAnimal() {
        List<Animal> animalList = List.of(
                new Tiger(TigerBreeds.CASPIAN_TIGER, "Hong", 500, CharacterEnum.TALKATIVE, LocalDate.of(1997, 12, 17))
        );

        assertThrows(
                SmallListSizeException.class,
                () -> animalsRepository.findMinCostAnimals(animalList),
                "Animal list must contain at least " + minListSize + " animals"
        );
    }

    /**
     * Проверка, что метод findFindOldAndExpensive() при использовании null в качестве аргумента
     * выбрасывает исключение NullPointerException и выводит следующее сообщение:
     * "Your animal list should not be null
     */
    @Test
    public void testFindMinCostAnimalsWhenGivenNull() {
        var message = assertThrows(NullPointerException.class, () -> animalsRepository.findMinCostAnimals(null));
        assertThat(NULL_ANIMAL_LIST_MESSAGE).isEqualTo(message.getMessage());
    }

    /**
     * Проверка, что метод findMinCostAnimals() при использовании пустого списка в качестве аргумента
     * выбрасывает исключение SmallListSizeException, т.к. размер списка меньше 3
     */
    @Test
    public void testFindMinCostAnimalsWhenGivenEmptyCollection() {
        List<Animal> animalList = Collections.emptyList();

        assertThrows(
                SmallListSizeException.class,
                () -> animalsRepository.findMinCostAnimals(animalList),
                "Animal list must contain at least " + minListSize + " animals"
        );
    }

    @Test
    public void testFindAnimalWithMaxAge() {
        List<Animal> animalList = List.of(
                new Tiger(TigerBreeds.randomBreed(), "Tiger", 228.68, CharacterEnum.LAZY, LocalDate.of(2020, 1, 1)),
                new Bear(BearBreeds.randomBreed(), "Bear", 48.68, CharacterEnum.TRICKY, LocalDate.of(2022, 1, 1)),
                new Hamster(HamsterBreeds.randomBreed(), "Hamster", 1337.68, CharacterEnum.SMART, LocalDate.of(2021, 1, 1)),
                new Parrot(ParrotBreeds.randomBreed(), "Parrot", 394.54, CharacterEnum.WICKED, LocalDate.of(1990, 1, 3))
        );

        Animal oldestAnimal = findAnimalWithMaxAge(animalList);

        assertThat(oldestAnimal).isEqualTo(animalList.get(3));
    }

    @Test
    public void testFindAnimalWithMaxAgeWithEmptyList() {
        List<Animal> animalList = Collections.emptyList();

        assertThrows(
                IllegalArgumentException.class,
                () -> findAnimalWithMaxAge(animalList),
                "Your animal list is empty"
        );
    }
}
