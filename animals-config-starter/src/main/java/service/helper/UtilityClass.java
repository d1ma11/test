package service.helper;

import dto.Animal;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
public class UtilityClass {
    private static final String SECRET_FILE_PATH = "D:\\Yndx\\МТС\\Проекты\\backup\\mts\\animals-config-starter\\src\\main\\resources\\secretStore\\secretInformation.txt";
    private static final String LOG_FILE_PATH = "mts/animals-config-starter/src/main/resources/animals/logData.txt";
    private static int animalCount = 0;

    public synchronized static void logAnimalDetails(Animal animal) {
        Path logFilePathObj = Paths.get(LOG_FILE_PATH);

        String animalDetails = String.format(
                "%d %s %s %s\n",
                ++animalCount,
                animal.getBreed(),
                animal.getName(),
                animal.getBirthDate()
        );

        try {
            Files.createDirectories(logFilePathObj.getParent());
            Files.writeString(logFilePathObj, animalDetails, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static String getSecretLine(String animalType) {
        String animalInfo = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(SECRET_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(animalType)) {
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
