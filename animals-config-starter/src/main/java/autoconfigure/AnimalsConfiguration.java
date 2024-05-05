package autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dto.NamesProvider;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import repository.AnimalsRepositoryImpl;
import service.CreateAnimalServiceImpl;
import service.CreateAnimalServicePostProcessor;
import service.factory.AnimalFactory;
import service.helper.JsonHelper;
import service.helper.UtilityClass;

@Configuration
public class AnimalsConfiguration {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    @Bean
    public NamesProvider namesProvider() {
        return new NamesProvider();
    }

    @Bean
    public AnimalFactory animalFactory(NamesProvider namesProvider) {
        return new AnimalFactory(namesProvider);
    }

    @Bean
    public CreateAnimalServiceImpl createAnimalService(AnimalFactory animalFactory) {
        return new CreateAnimalServiceImpl(animalFactory);
    }

    @Bean
    public static CreateAnimalServicePostProcessor createAnimalServicePostProcessor() {
        return new CreateAnimalServicePostProcessor();
    }

    @Bean
    public AnimalsRepositoryImpl animalsRepositoryImpl(CreateAnimalServiceImpl createAnimalService,
                                                       SessionFactory sessionFactory,
                                                       UtilityClass utilityClass,
                                                       JsonHelper jsonHelper) {
        return new AnimalsRepositoryImpl(createAnimalService, sessionFactory, utilityClass, jsonHelper);
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

    @Bean
    public SessionFactory sessionFactory() {
        if (sessionFactory == null) {
            try {
                registry = new StandardServiceRegistryBuilder().configure().build();
                MetadataSources sources = new MetadataSources(registry);
                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    @Bean
    public UtilityClass utilityClass(SessionFactory sessionFactory) {
        return new UtilityClass(sessionFactory);
    }
}