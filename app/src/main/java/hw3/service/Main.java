package hw3.service;

import hw3.dto.Animal;
import hw3.repository.AnimalsRepository;
import hw3.repository.AnimalsRepositoryImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "hw3")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        AnimalsRepository animalsRepository = context.getBean(AnimalsRepositoryImpl.class);

        System.out.println("--> Вывод всех бинов, которые находятся в контексте ");
        for (Object o : context.getBeanDefinitionNames())
            if (!o.toString().contains("org."))
                System.out.println(o);

        System.out.println("\n--> Животные, которые родились в високосный год: ");
        for (String animal : animalsRepository.findLeapYearNames()) {
            System.out.println(animal);
        }

        System.out.println("\n--> Животные, которые старше определенного возраста: ");
        for (Animal animal : animalsRepository.findOlderAnimal(30)) {
            System.out.println(animal);
        }

        System.out.println("\n--> Дубликаты животных: ");
        animalsRepository.printDuplicate();

        context.close();
    }
}