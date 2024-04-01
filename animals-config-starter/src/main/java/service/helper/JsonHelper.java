package service.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AbstractAnimal;
import dto.Animal;
import dto.Pet.Hamster;
import dto.Pet.HamsterBreeds;
import dto.Pet.Parrot;
import dto.Pet.ParrotBreeds;
import dto.Predator.Bear;
import dto.Predator.BearBreeds;
import dto.Predator.Tiger;
import dto.Predator.TigerBreeds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class JsonHelper {

    private final ObjectMapper objectMapper;

    @Autowired
    public JsonHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public synchronized void writeToJsonFile(String methodName, Object result) {
        Path path = Paths.get("mts/animals-config-starter/src/main/resources/results/" + methodName + ".json");
        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            Files.writeString(path, json);
        } catch (IOException e) {
            System.err.println("Failed to write JSON file: " + methodName + ".json");
            e.printStackTrace(); // This will print the stack trace
        }

    }

    public String[] readFindMinCostAnimals(String filePath) throws IOException {
        return objectMapper.readValue(Paths.get(filePath).toFile(), String[].class);
    }

    public Double readFindAverageAge(String filePath) throws IOException {
        return objectMapper.readValue(Paths.get(filePath).toFile(), Double.class);
    }

    public List<AbstractAnimal> readFindOldAndExpensive(String filePath) throws IOException {
        JsonNode animals = objectMapper.readTree(new File(filePath));
        if (animals.isArray()) {
            List<AbstractAnimal> animalList = new ArrayList<>();
            for (JsonNode animalNode : animals) {
                AbstractAnimal animal = convertToAnimalFromJson(animalNode);
                animalList.add(animal);
            }

            return animalList;
        }
        return Collections.emptyList();
    }

    public Map<String, List<Animal>> readFindDuplicate(String filePath) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(new File(filePath));

        Map<String, List<Animal>> duplicateAnimalsMap = new HashMap<>();

        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String animalType = field.getKey();
            JsonNode animalsNode = field.getValue();

            List<Animal> duplicateAnimals = new ArrayList<>();
            for (JsonNode animalNode : animalsNode) {
                Animal animal = convertToAnimalFromJson(animalNode);
                duplicateAnimals.add(animal);
            }

            duplicateAnimalsMap.put(animalType, duplicateAnimals);
        }

        return duplicateAnimalsMap;
    }

    public Map<String, LocalDate> readFindLeapYearNames(String filePath) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(new File(filePath));

        Map<String, LocalDate> leapYearNames = new HashMap<>();

        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String key = field.getKey();
            JsonNode valueNode = field.getValue();

            LocalDate birthDate = LocalDate.of(valueNode.get(0).asInt(), valueNode.get(1).asInt(), valueNode.get(2).asInt());

            leapYearNames.put(key, birthDate);
        }

        return leapYearNames;
    }

    //TODO: для метода findOlderAnimal() как мне кажется создается неправильный json файл, судя по его структуре.
    // В связи с этим, я не знаю как его нормально распарсить для десериализации
    public Map<Animal, Integer> readFindOlderAnimal(String filePath) throws IOException {
        Map<Animal, Integer> olderAnimalsMap = new HashMap<>();
        JsonNode jsonNode = objectMapper.readTree(new File(filePath));

        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            // some code
        }

        return olderAnimalsMap;
    }

    private AbstractAnimal convertToAnimalFromJson(JsonNode animalNode) {
        AbstractAnimal animal = null;

        String whatIsThisAnimal = animalNode.findValue("breed").asText();

        try {
            if (HamsterBreeds.forName(whatIsThisAnimal) != null) {
                animal = new Hamster();
            } else if (ParrotBreeds.forName(whatIsThisAnimal) != null) {
                animal = new Parrot();
            } else if (BearBreeds.forName(whatIsThisAnimal) != null) {
                animal = new Bear();
            } else if (TigerBreeds.forName(whatIsThisAnimal) != null) {
                animal = new Tiger();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Oops");
            e.printStackTrace();
        }

        if (animal != null) {
            animal.setBreed(animalNode.findValue("breed").asText());
            animal.setName(animalNode.findValue("name").asText());
            animal.setCharacter(animalNode.findValue("character").asText());
            animal.setCost(BigDecimal.valueOf(animalNode.findValue("cost").asDouble()));
            animal.setBirthDate(
                    LocalDate.parse(animalNode.findValue("birthDate").asText(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            animal.setSecretInformation(new String(Base64.getDecoder().decode(
                    animalNode.findValue("secretInformation").asText()), StandardCharsets.UTF_8
            ));

            return animal;
        }

        throw new NullPointerException("There is no such animal breed: " + whatIsThisAnimal);
    }
}

