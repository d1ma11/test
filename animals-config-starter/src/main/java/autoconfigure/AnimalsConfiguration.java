package autoconfigure;

import aop.LogAspect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import service.helper.JsonHelper;

@Configuration
@EnableAspectJAutoProxy
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

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}