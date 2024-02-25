package hw3.service;

import dto.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import repository.AnimalsRepository;

@Component
public class ScheduledTasks {
    private final AnimalsRepository animalsRepository;

    @Autowired
    public ScheduledTasks(AnimalsRepository animalsRepository) {
        this.animalsRepository = animalsRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void reportLeapYearAnimals() {
        System.out.println("\n--> Животные, которые родились в високосный год: ");
        for (String animal : animalsRepository.findLeapYearNames()) {
            System.out.println(animal);
        }
    }

    @Scheduled(cron = "1 * * * * *")
    public void reportOlderAnimals() {
        System.out.println("\n--> Животные, которые старше определенного возраста: ");
        for (Animal animal : animalsRepository.findOlderAnimal(30)) {
            System.out.println(animal);
        }
    }

    @Scheduled(cron = "2 * * * * *")
    public void printDuplicateAnimals() {
        System.out.println("\n--> Дубликаты животных: ");
        animalsRepository.printDuplicate();
    }
}
