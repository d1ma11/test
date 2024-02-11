package hw3.configuration;

import hw3.repository.AnimalsRepository;
import hw3.repository.AnimalsRepositoryImpl;
import hw3.service.CreateAnimalService;
import hw3.service.CreateAnimalServiceImpl;
import hw3.service.CreateAnimalServicePostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfiguration {
    @Bean
    @Scope("prototype")
    public CreateAnimalService createAnimalService() {
        return new CreateAnimalServiceImpl();
    }

    @Bean
    public AnimalsRepository animalsRepository() {
        return new AnimalsRepositoryImpl();
    }

    @Bean
    public static CreateAnimalServicePostProcessor createAnimalServicePostProcessor() {
        return new CreateAnimalServicePostProcessor();
    }
}
