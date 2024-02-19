import autoconfigure.AnimalsConfiguration;
import dto.Animal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.AnimalsRepositoryImpl;
import service.CreateAnimalService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = AnimalsConfiguration.class)
public class SpringBootStarterTest {
    @Autowired
    private CreateAnimalService createAnimalService;

    /**
     * Проверка, что контекст нашего приложения поднимается
     */
    @Test
    public void testContextCreation() {}

    /**
     * Проверка, что приложение корректно создает животных.
     */
    @Test
    public void testAnimalCreation() {
        Animal bear = createAnimalService.createBear();
        assertThat(bear).isNotNull();
        assertThat(bear.getBreed()).isNotEmpty();
        assertThat(bear.getName()).isNotEmpty();
        assertThat(bear.getCost()).isPositive();
        assertThat(bear.getCharacter()).isNotNull();
        assertThat(bear.getBirthDate()).isBeforeOrEqualTo(LocalDate.now());
    }

    /**
     * Проверка, что значение null при создании животного выбрасывает исключение NullPointerException
     */
    @Test
    public void testUnknownAnimalCreation() {
        assertThrows(NullPointerException.class, () -> createAnimalService.createAnimal(null));
    }

    /**
     * Проверка, что не создается бин AnimalsRepositoryImpl, т.к. он не указан в конфигурационном файле
     */
    @Test
    public void test() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AnimalsConfiguration.class);
        assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(AnimalsRepositoryImpl.class));
    }
}
