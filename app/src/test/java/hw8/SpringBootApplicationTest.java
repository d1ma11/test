package hw8;

import dto.Animal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import repository.AnimalsRepository;
import repository.AnimalsRepositoryImpl;
import service.helper.SearchUtilityClass;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
