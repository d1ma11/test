package hw3.service;

import dto.Animal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import repository.AnimalsRepository;
import repository.AnimalsRepositoryImpl;

import java.util.List;

//@Component
public class DatabaseScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseScheduler.class);
    private final AnimalsRepositoryImpl animalsRepositoryImpl;
    private final AnimalsRepository animalsRepository;

    @Autowired
    public DatabaseScheduler(AnimalsRepositoryImpl animalsRepositoryImpl, AnimalsRepository animalsRepository) {
        this.animalsRepositoryImpl = animalsRepositoryImpl;
        this.animalsRepository = animalsRepository;
    }

    @Scheduled(fixedRate = 10_000)
    public void scheduledLog() {
        try {
            List<Animal> animals = animalsRepository.findAll();

            LOGGER.info("FindDuplicate animal {}", animalsRepositoryImpl.findDuplicate());
            LOGGER.info("FindLeapYearNames animal {}", animalsRepositoryImpl.findLeapYearNames());
            LOGGER.info("FindAverageAge {}", animalsRepositoryImpl.findAverageAge(animals));
            LOGGER.info("FindOlder animal {}", animalsRepositoryImpl.findOlderAnimal(5));
            LOGGER.info("FindMinConstAnimals {}", animalsRepositoryImpl.findMinCostAnimals(animals));
            LOGGER.info("FindOldAndExpensive {}", animalsRepositoryImpl.findOldAndExpensive(animals));
        } catch (Exception e) {
            LOGGER.error("Exception! : " + e.getMessage(), e);
        }
    }
}
