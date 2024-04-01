package service.helper;

import dto.Animal;
import dto.AnimalsEnum;
import dto.Pet.Parrot;
import dto.Predator.Bear;
import dto.Predator.Tiger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class UtilityClass {
    private static final String SECRET_FILE_PATH = "mts/animals-config-starter/src/main/resources/secretStore/secretInformation.txt";
    private static final String LOG_FILE_PATH = "mts/animals-config-starter/src/main/resources/animals/logData.txt";
    private static int animalCount = 0;

    /**
     * Данный метод служит для определения типа животного, которое передается в виде параметра
     * @param animal животное, у которого нужно определить тип
     * @return тип животного
     */
    public static AnimalsEnum getAnimalType(Animal animal) {
        if (animal instanceof Bear) {
            return AnimalsEnum.BEAR;
        } else if (animal instanceof Tiger) {
            return AnimalsEnum.TIGER;
        } else if (animal instanceof Parrot) {
            return AnimalsEnum.PARROT;
        }
        return AnimalsEnum.HAMSTER;
    }

    public synchronized static void logAnimalDetails(Animal animal) {
        Path logFilePathObj = Paths.get(LOG_FILE_PATH);

        String animalDetails = String.format(
                "%d %s %s %.2f %s\n",
                ++animalCount,
                animal.getBreed(),
                animal.getName(),
                animal.getCost(),
                animal.getBirthDate()
        );

        try {
            Files.createDirectories(logFilePathObj.getParent());
            Files.writeString(logFilePathObj, animalDetails, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static String getSecretLine(AnimalsEnum animal) {
        String animalInfo = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(SECRET_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(animal.name())) {
                    String[] parts = line.split("=");
                    if (parts.length > 1) {
                        animalInfo = parts[1].trim();
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return animalInfo;
    }
}
