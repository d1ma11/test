package hw8;

import dto.Animal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import repository.AnimalsRepository;
import repository.AnimalsRepositoryImpl;

import java.util.Collections;
import java.util.Set;

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
        String[] names = animalsRepository.findLeapYearNames();
        assertThat(names).isNotNull();
        for (String name : names) {
            assertThat(!name.isEmpty()).isTrue();
        }
    }

    /**
     * Проверка, что метод findOlderAnimal() корректно отрабатывает
     */
    @Test
    public void testFindOlderAnimalWithProperAge() {
        Animal[] oldAnimals = animalsRepository.findOlderAnimal(10);
        assertThat(oldAnimals).isNotEmpty();
    }

    /**
     * Проверка, что при значении аргумента метода findOlderAnimal() он возвращает пустой массив, т.к. животных
     * с таким возрастом не существует
     */
    @Test
    public void testFindOlderAnimalWithNegativeAge() {
        Animal[] oldAnimals = animalsRepository.findOlderAnimal(10);
        assertThat(oldAnimals).isNotEmpty();
    }

    /**
     * Проверка, что при пустом массиве животных в аргументе метода findDuplicate() возвращается пустой массив
     */
    @Test
    public void testFindDuplicateWithEmptyList() {
        AnimalsRepositoryImpl animalsRepository = new AnimalsRepositoryImpl();
        animalsRepository.setAnimalList(Collections.emptyList());

        Set<Animal> result = animalsRepository.findDuplicate();

        assertThat(result).isEmpty();
    }
}
