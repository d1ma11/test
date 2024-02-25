package autoconfigure;

import dto.NamesProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import repository.AnimalsRepository;
import repository.AnimalsRepositoryImpl;
import service.AnimalFactory;
import service.CreateAnimalService;
import service.CreateAnimalServiceImpl;
import service.CreateAnimalServicePostProcessor;

import java.util.List;

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
    @Scope("prototype")
    public CreateAnimalService createAnimalService(AnimalFactory animalFactory) {
        return new CreateAnimalServiceImpl(animalFactory);
    }

    @Bean
    @Scope("prototype")
    public AnimalsRepository animalsRepository() {
        return new AnimalsRepositoryImpl();
    }

    @Bean
    public static CreateAnimalServicePostProcessor createAnimalServicePostProcessor() {
        return new CreateAnimalServicePostProcessor();
    }

}
