package hw8;

import dto.Animal;
import dto.AnimalsEnum;
import dto.CharacterEnum;
import dto.Predator.Bear;
import dto.Predator.BearBreeds;
import dto.Predator.Tiger;
import dto.Predator.TigerBreeds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import repository.AnimalsRepository;
import repository.AnimalsRepositoryImpl;
import service.helper.SearchUtilityClass;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(classes = SpringBootTestConfiguration.class)
public class SpringBootApplicationTest {
    @Autowired
    private AnimalsRepository animalsRepository;

    /**
     * Проверка, что метод findLeapYearNames() корректно отрабатывает
     */
    @Test
    public void testFindLeapYearNames() {
        Map<String, LocalDate> leapYearNames = animalsRepository.findLeapYearNames();

        assertThat(leapYearNames).isNotNull();

        for (String name : leapYearNames.keySet()) {
            LocalDate birthDate = leapYearNames.get(name);

            assertThat(SearchUtilityClass.isLeapYear(birthDate.getYear())).isTrue();
        }
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
        assertEquals(2, result.get(animals.get(1)));
    }


    /**
     * Проверка, что при отрицательном значении аргумента метода findOlderAnimal() он возвращает пустую коллекцию,
     * т.к. животных с таким возрастом не существует
     */
    @Test
    public void testFindOlderAnimalWithNegativeAge() {
        int ageThreshold = -50;
        Map<Animal, Integer> olderAnimals = animalsRepository.findOlderAnimal(ageThreshold);

        assertThat(olderAnimals).isEmpty();
    }

    /**
     * Проверка, что метод findDuplicate() корректно отрабатывает
     */
    @Test
    public void testFindDuplicate() {
        Map<String, Integer> duplicateAnimals = animalsRepository.findDuplicate();

        assertThat(duplicateAnimals).isNotNull();

        for (String animalType : duplicateAnimals.keySet()) {
            int duplicateCount = duplicateAnimals.get(animalType);

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

        Map<String, Integer> leapYearNames = animalsRepositoryImpl.findDuplicate();

        assertThat(leapYearNames).isEmpty();
    }

}
