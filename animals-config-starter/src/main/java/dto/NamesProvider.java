package dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Random;

public class NamesProvider {
    @Value("#{'${animalNames}'.split(',')}")
    private List<String> names;
    private final Random random = new Random();

    public String randomName() {
        return names.get(random.nextInt(names.size()));
    }
}
