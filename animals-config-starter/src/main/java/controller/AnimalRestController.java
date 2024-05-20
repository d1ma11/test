package controller;

import annotation.Logging;
import dto.Animal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.AnimalService;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalRestController {

    private final AnimalService animalService;

    public AnimalRestController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/getAnimals")
    @Logging(value = "REST GET request - /api/animals/getAnimals", entering = true, exiting = true)
    public List<Animal> getAnimals() {
        return animalService.getAnimals();
    }

    @PostMapping("/addAnimal")
    @ResponseStatus(HttpStatus.CREATED)
    @Logging(value = "REST POST request - /api/animals/addAnimal", entering = true, exiting = true)
    public void saveAnimal(@RequestBody Animal animal) {
        animalService.addAnimal(animal);
    }

    @DeleteMapping("/deleteAnimal/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Logging(value = "REST DELETE request - /api/animal/deleteAnimal/{id}", entering = true, exiting = true)
    public void deleteAnimal(@PathVariable(name = "id") Integer id) {
        animalService.deleteAnimalById(id);
    }
}
