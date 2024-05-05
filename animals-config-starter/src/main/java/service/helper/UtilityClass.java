package service.helper;

import dto.db_objects.Animal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class UtilityClass {
    private static final String SECRET_FILE_PATH = "D:\\Yndx\\МТС\\Проекты\\backup\\mts\\animals-config-starter\\src\\main\\resources\\secretStore\\secretInformation.txt";
    private static final String LOG_FILE_PATH = "mts/animals-config-starter/src/main/resources/animals/logData.txt";
    private static int animalCount = 0;

    private final SessionFactory sessionFactory;

    public UtilityClass(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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

    public Map<String, List<Animal>> getAnimalsFromDatabase() {
        Map<String, List<Animal>> animalMap = new ConcurrentHashMap<>();

        try (Session session = sessionFactory.openSession()) {
            List<Animal> animals;
            animals = session.createQuery("FROM Animal", Animal.class).getResultList();
            for (Animal animal : animals) {
                if (!animalMap.containsKey(animal.getAnimalType().toString())) {
                    animalMap.put(animal.getAnimalType().toString(), new CopyOnWriteArrayList<>());
                }
                animalMap.get(animal.getAnimalType().toString()).add(animal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return animalMap;
    }
}
