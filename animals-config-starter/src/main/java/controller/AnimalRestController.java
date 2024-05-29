package controller;

import annotation.Logging;
import dto.Animal;
import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import security.jwt.JwtRequest;
import security.jwt.JwtResponse;
import service.AnimalService;
import security.AuthService;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalRestController {

    private final AnimalService animalService;
    private final AuthService authService;

    public AnimalRestController(AnimalService animalService, AuthService authService) {
        this.animalService = animalService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAnimals")
    @Logging(value = "REST GET request - /api/animals/getAnimals", entering = true, exiting = true)
    public List<Animal> getAnimals() {
        return animalService.getAnimals();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addAnimal")
    @ResponseStatus(HttpStatus.CREATED)
    @Logging(value = "REST POST request - /api/animals/addAnimal", entering = true, exiting = true)
    public void saveAnimal(@RequestBody Animal animal) {
        animalService.addAnimal(animal);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteAnimal/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Logging(value = "REST DELETE request - /api/animal/deleteAnimal/{id}", entering = true, exiting = true)
    public void deleteAnimal(@PathVariable(name = "id") Integer id) {
        animalService.deleteAnimalById(id);
    }
}
