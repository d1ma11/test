package autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import service.helper.JsonHelper;

@Configuration
@EnableJpaRepositories(basePackages = {"repository", "dto"})
@ComponentScan(basePackages = {"repository", "dto"})
@EntityScan(basePackages = {"repository", "dto"})
public class AnimalsConfiguration {

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