package hw3.service;

import dto.Animal;
import exception.NegativeAgeParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import repository.AnimalsRepository;

import java.time.LocalDate;
import java.util.Map;

@Component
public class ScheduledTasks {
    private final AnimalsRepository animalsRepository;

    @Autowired
    public ScheduledTasks(AnimalsRepository animalsRepository) {
        this.animalsRepository = animalsRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void reportLeapYearAnimals() {
        try {
            System.out.println("\n--> Animals that were born in a leap year: ");
            Map<String, LocalDate> leapYearAnimals = animalsRepository.findLeapYearNames();

            for (Map.Entry<String, LocalDate> entry : leapYearAnimals.entrySet()) {
                System.out.println(entry.getKey() + " was born in " + entry.getValue());
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Scheduled(cron = "1 * * * * *")
    public void reportOlderAnimals() {
        try {
            int thresholdAge = 30;
            System.out.println("\n--> Animals that are older than " + thresholdAge + " years old: ");
            Map<Animal, Integer> olderAnimals = animalsRepository.findOlderAnimal(thresholdAge);
            for (Map.Entry<Animal, Integer> entry : olderAnimals.entrySet()) {
                if (entry.getValue() < thresholdAge) {
                    System.out.println(entry.getKey().getBreed() + " has the maximum age among all the animals on its list: " + entry.getValue() + " years old");
                } else {
                    System.out.println(entry.getKey().getBreed() + " is " + entry.getValue() + " years old");
                }
            }
        } catch (NegativeAgeParameterException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Scheduled(cron = "2 * * * * *")
    public void printDuplicateAnimals() {
        try {
            System.out.println("\n--> Duplicated animals: ");
            animalsRepository.printDuplicate();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
