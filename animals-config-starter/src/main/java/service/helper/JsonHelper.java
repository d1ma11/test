package service.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class JsonHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonHelper.class);
    private final ObjectMapper objectMapper;

    @Autowired
    public JsonHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public synchronized void writeToJsonFile(String methodName, Object result) {
        Path path = Paths.get("D:\\Yndx\\МТС\\Проекты\\backup\\mts\\animals-config-starter\\src\\main\\resources\\results\\" + methodName + ".json");
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), result);
        } catch (IOException e) {
            LOGGER.error("Failed to write JSON file: {}.json {}", methodName, e.getMessage());
        }
    }

    public synchronized <T> T readJson(String methodName, TypeReference<T> typeReference) throws IOException {
        Path path = Paths.get("D:\\Yndx\\МТС\\Проекты\\backup\\mts\\animals-config-starter\\src\\main\\resources\\results\\" + methodName + ".json");
        try {
            return objectMapper.readValue(path.toFile(), typeReference);
        } catch (IOException e) {
            LOGGER.error("Failed to read data from JSON file: {}.json {}", methodName, e.getMessage());
            throw e;
        }
    }
}
