import autoconfigure.AnimalsConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Animal;
import dto.CharacterEnum;
import dto.Pet.Parrot;
import dto.Pet.ParrotBreeds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.AnimalsRepositoryImpl;
import service.CreateAnimalService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = AnimalsConfiguration.class)
public class SpringBootStarterTest {
    @Autowired
    private CreateAnimalService createAnimalService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Проверка, что контекст нашего приложения поднимается
     */
    @Test
    public void testContextCreation() throws JsonProcessingException {
        Parrot parrot = new Parrot(ParrotBreeds.AMAZON_PARROT, "name", 2d, CharacterEnum.KIND, LocalDate.now());
        String json = objectMapper.writeValueAsString(parrot);
        Animal animal = new Parrot(ParrotBreeds.AMAZON_PARROT, "name", 2d, CharacterEnum.KIND, LocalDate.now());
        Animal animal1 = new Parrot(ParrotBreeds.AMAZON_PARROT, "name", 2d, CharacterEnum.KIND, LocalDate.now());
        List<Animal> list = List.of(animal, animal1, animal1, animal1);
        String s = objectMapper.writeValueAsString(list);
        System.out.println(json);
        Parrot[] objects = objectMapper.readValue(s, Parrot[].class);
        System.out.println(json);
    }

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