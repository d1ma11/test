package autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dto.NamesProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.*;
import repository.AnimalsRepository;
import repository.AnimalsRepositoryImpl;
import service.CreateAnimalService;
import service.CreateAnimalServiceImpl;
import service.CreateAnimalServicePostProcessor;
import service.factory.AnimalFactory;
import service.helper.JsonHelper;

@Configuration
@ConditionalOnClass({AnimalsRepository.class, CreateAnimalServiceImpl.class})
public class AnimalsConfiguration {
    @Bean
    public NamesProvider namesProvider() {
        return new NamesProvider();
    }

    @Bean
    public AnimalFactory animalFactory(NamesProvider namesProvider) {
        return new AnimalFactory(namesProvider);
    }

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
    public CreateAnimalService createAnimalService(AnimalFactory animalFactory) {
        return new CreateAnimalServiceImpl(animalFactory);
    }

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
    public AnimalsRepository animalsRepository() {
        return new AnimalsRepositoryImpl();
    }

    @Bean
    public static CreateAnimalServicePostProcessor createAnimalServicePostProcessor() {
        return new CreateAnimalServicePostProcessor();
    }

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AnimalsRepositoryImpl animalsRepositoryImpl() {
        return new AnimalsRepositoryImpl();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public JsonHelper jsonHelper() {
        return new JsonHelper(objectMapper());
    }
}