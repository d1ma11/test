package controller;

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
    public List<Animal> getAnimals() {
        return animalService.getAnimals();
    }

    @PostMapping("/addAnimal")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAnimal(@RequestBody Animal animal) {
        animalService.addAnimal(animal);
    }

    @DeleteMapping("/deleteAnimal/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimal(@PathVariable(name = "id") Integer id) {
        animalService.deleteAnimalById(id);
    }
}
