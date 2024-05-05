package hw3.service;

import dto.db_objects.Animal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import repository.AnimalsRepositoryImpl;
import service.helper.UtilityClass;

import java.util.Comparator;
import java.util.List;

@Component
public class DatabaseScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseScheduler.class);
    private final AnimalsRepositoryImpl animalsRepository;
    private final UtilityClass utilityClass;

    @Autowired
    public DatabaseScheduler(AnimalsRepositoryImpl animalsRepository, UtilityClass utilityClass) {
        this.animalsRepository = animalsRepository;
        this.utilityClass = utilityClass;
    }

    @Scheduled(fixedRate = 10_000)
    public void scheduledLog() {
        try {
            List<Animal> animalList = utilityClass
                    .getAnimalsFromDatabase()
                    .values()
                    .stream()
                    .max(Comparator.comparingInt(List::size))
                    .orElse(List.of());

            LOGGER.info("FindDuplicate animal {}", animalsRepository.findDuplicate());
            LOGGER.info("FindLeapYearNames animal {}", animalsRepository.findLeapYearNames());
            LOGGER.info("FindAverageAge {}", animalsRepository.findAverageAge(animalList));
            LOGGER.info("FindOlder animal {}", animalsRepository.findOlderAnimal(5));
            LOGGER.info("FindMinConstAnimals {}", animalsRepository.findMinCostAnimals(animalList));
            LOGGER.info("FindOldAndExpensive {}", animalsRepository.findOldAndExpensive(animalList));
        } catch (Exception e) {
            LOGGER.error("Exception! : " + e.getMessage(), e);
        }
    }
}
