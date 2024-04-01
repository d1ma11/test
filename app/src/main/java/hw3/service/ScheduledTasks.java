package hw3.service;

import dto.AbstractAnimal;
import dto.Animal;
import exception.SmallListSizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AnimalsRepositoryImpl;
import service.helper.JsonHelper;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static service.helper.UtilityClass.getAnimalType;

@Component
public class ScheduledTasks {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\033[0;36m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private final AnimalsRepositoryImpl animalsRepositoryImpl;
    private final JsonHelper jsonHelper;

    @Autowired
    public ScheduledTasks(AnimalsRepositoryImpl animalsRepositoryImpl, JsonHelper jsonHelper) {
        this.animalsRepositoryImpl = animalsRepositoryImpl;
        this.jsonHelper = jsonHelper;
    }

    @PostConstruct
    public void init() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(6);

        executor.scheduleAtFixedRate(() -> {
            Thread.currentThread().setName("DuplicatePrinter");
            try {
                logPrintDuplicateMethod();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 1, 10, TimeUnit.SECONDS);

        executor.scheduleAtFixedRate(() -> {
            Thread.currentThread().setName("AverageAgeCalculator");
            try {
                logFindAverageAgeMethod();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 2, 10, TimeUnit.SECONDS);

        executor.scheduleAtFixedRate(() -> {
            Thread.currentThread().setName("FindLeapYearNamesThread");
            try {
                logFindLeapYearNames();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 3, 10, TimeUnit.SECONDS);

        executor.scheduleAtFixedRate(() -> {
            Thread.currentThread().setName("FindOlderAnimalThread");
            try {
                logFindOlderAnimal();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 4, 10, TimeUnit.SECONDS);

        executor.scheduleAtFixedRate(() -> {
            Thread.currentThread().setName("FindOldAndExpensiveAnimalThread");
            try {
                logFindOldAndExpensive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 5, 10, TimeUnit.SECONDS);

        executor.scheduleAtFixedRate(() -> {
            Thread.currentThread().setName("FindMinCostAnimalsThread");
            try {
                logFindMinCostAnimals();
            } catch (SmallListSizeException | IOException e) {
                e.printStackTrace();
            }
        }, 6, 10, TimeUnit.SECONDS);

    }

    /**
     * Логирование работы потока с методом printDuplicate() синим цветом
     */
    private void logPrintDuplicateMethod() throws IOException {
        System.out.println(
                ANSI_BLUE +
                        "\n1. Thread: " + Thread.currentThread().getName() +
                        " - executing method printDuplicate()"
        );

        animalsRepositoryImpl.findDuplicate();

        Map<String, List<Animal>> listMap = jsonHelper.readFindDuplicate(
                "mts/animals-config-starter/src/main/resources/results/findDuplicate.json"
        );

        for (List<Animal> value : listMap.values()) {
            System.out.println(value);
        }

        System.out.print(ANSI_RESET);
    }

    /**
     * Логирование работы потока с методом finaAverageAge() голубым цветом
     */
    private void logFindAverageAgeMethod() throws IOException {
        System.out.println(
                ANSI_CYAN +
                        "\n2. Thread: " + Thread.currentThread().getName() +
                        " - executing method findAverageAge()"
        );

        for (List<Animal> animalList : animalsRepositoryImpl.getAnimalMap().values()) {
            animalsRepositoryImpl.findAverageAge(animalList);

            Double averageAge = jsonHelper.readFindAverageAge(
                    "mts/animals-config-starter/src/main/resources/results/findAverageAge.json"
            );
            System.out.println("Average age of " + getAnimalType(animalList.get(0)) + " is " +
                    Math.round(averageAge));
        }

        System.out.print(ANSI_RESET);
    }

    private void logFindLeapYearNames() throws IOException {
        System.out.println(
                ANSI_YELLOW +
                        "\n3. Thread: " + Thread.currentThread().getName() +
                        " - executing method findLeapYearNames()"
        );

        animalsRepositoryImpl.findLeapYearNames();

        Map<String, LocalDate> leapYearNames = jsonHelper.readFindLeapYearNames(
                "mts/animals-config-starter/src/main/resources/results/findLeapYearNames.json   "
        );

        for (Map.Entry<String, LocalDate> entry : leapYearNames.entrySet()) {
            System.out.println("Animal: " + entry.getKey() + ", Birth Date: " + entry.getValue());
        }

        System.out.print(ANSI_RESET);
    }

    private void logFindOlderAnimal() throws IOException {
        System.out.println(
                ANSI_WHITE +
                        "\n4. Thread: " + Thread.currentThread().getName() +
                        " - executing method findOlderAnimal()"
        );

        animalsRepositoryImpl.findOlderAnimal(30);

        Map<Animal, Integer> olderAnimal = jsonHelper.readFindOlderAnimal(
                "mts/animals-config-starter/src/main/resources/results/findOlderAnimal.json"
        );

        for (Map.Entry<Animal, Integer> entry : olderAnimal.entrySet()) {
            System.out.println("Animal: " + entry.getKey() + ", Age: " + entry.getValue());
        }

        System.out.print(ANSI_RESET);
    }

    private void logFindOldAndExpensive() throws IOException {
        System.out.println(
                ANSI_GREEN +
                        "\n5. Thread: " + Thread.currentThread().getName() +
                        " - executing method findOldAndExpensive()"
        );

        for (List<Animal> animalList : animalsRepositoryImpl.getAnimalMap().values()) {
            animalsRepositoryImpl.findOldAndExpensive(animalList);
            List<AbstractAnimal> animals = jsonHelper.readFindOldAndExpensive(
                    "mts/animals-config-starter/src/main/resources/results/findOldAndExpensive.json"
            );

            for (AbstractAnimal animal : animals) {
                System.out.println(animal);
            }
        }

        System.out.print(ANSI_RESET);
    }

    private void logFindMinCostAnimals() throws SmallListSizeException, IOException {
        System.out.println(
                ANSI_PURPLE +
                        "\n6. Thread: " + Thread.currentThread().getName() +
                        " - executing method findMinCostAnimals()"
        );

        for (List<Animal> animalList : animalsRepositoryImpl.getAnimalMap().values()) {
            animalsRepositoryImpl.findMinCostAnimals(animalList);
            String[] animalNames = jsonHelper.readFindMinCostAnimals(
                    "mts/animals-config-starter/src/main/resources/results/findMinCostAnimals.json"
            );
            System.out.println(Arrays.toString(animalNames));
        }

        System.out.print(ANSI_RESET);
    }
}
