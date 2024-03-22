package hw3.service;

import dto.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AnimalsRepositoryImpl;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static service.helper.UtilityClass.getAnimalType;

@Component
public class ScheduledTasks {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\033[0;36m";

    private final AnimalsRepositoryImpl animalsRepositoryImpl;

    @Autowired
    public ScheduledTasks(AnimalsRepositoryImpl animalsRepositoryImpl) {
        this.animalsRepositoryImpl = animalsRepositoryImpl;
    }

    @PostConstruct
    public void init() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        executor.scheduleAtFixedRate(() -> {
            Thread.currentThread().setName("DuplicatePrinter");
            logPrintDuplicateMethod();
        }, 1, 10, TimeUnit.SECONDS);

        executor.scheduleAtFixedRate(() -> {
            Thread.currentThread().setName("AverageAgeCalculator");
            logFindAverageAgeMethod();
        }, 2, 20, TimeUnit.SECONDS);
    }

    /**
     * Логирование работы потока с методом printDuplicate() синим цветом
     */
    private void logPrintDuplicateMethod() {
        System.out.println(
                ANSI_BLUE +
                        "\n1. Thread: " + Thread.currentThread().getName() +
                        " - executing method printDuplicate()"
        );

        animalsRepositoryImpl.printDuplicate();

        System.out.print(ANSI_RESET);
    }

    /**
     * Логирование работы потока с методом finaAverageAge() голубым цветом
     */
    private void logFindAverageAgeMethod() {
        System.out.println(
                ANSI_CYAN +
                        "2. Thread: " + Thread.currentThread().getName() +
                        " - executing method findAverageAge()"
        );

        for (List<Animal> animalList : animalsRepositoryImpl.getAnimalMap().values()) {
            OptionalDouble averageAge = animalsRepositoryImpl.findAverageAge(animalList);

            System.out.println("Average age of " + getAnimalType(animalList.get(0)) + " is " +
                    Math.round(averageAge.isPresent() ? averageAge.getAsDouble() : 0));
        }

        System.out.println(ANSI_RESET);
    }
}
