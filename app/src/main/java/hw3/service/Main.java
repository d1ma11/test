package hw3.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            if (beanDefinitionName.toLowerCase().contains("animal"))
                System.out.println(beanDefinitionName);
        }
    }
}