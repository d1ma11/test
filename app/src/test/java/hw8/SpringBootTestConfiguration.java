package hw8;

import dto.NamesProvider;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import repository.AnimalsRepository;
import repository.AnimalsRepositoryImpl;
import service.AnimalFactory;
import service.CreateAnimalService;
import service.CreateAnimalServiceImpl;

@SpringBootConfiguration
public class SpringBootTestConfiguration {
    @Bean
    public AnimalsRepository animalsRepository() {
        return new AnimalsRepositoryImpl();
    }

    @Bean
    public NamesProvider namesProvider() {
        return new NamesProvider();
    }

    @Bean
    public AnimalFactory animalFactory(NamesProvider namesProvider) {
        return new AnimalFactory(namesProvider);
    }

    @Bean
    @Scope("prototype")
    public CreateAnimalService createAnimalService(AnimalFactory animalFactory) {
        return new CreateAnimalServiceImpl(animalFactory);
    }
}
