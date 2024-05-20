package controller;

import annotation.Logging;
import dto.Animal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import service.AnimalService;

@Controller
public class AnimalViewController {

    private final AnimalService animalService;

    public AnimalViewController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/index")
    @Logging(value = "MVC GET request - /index", entering = true, exiting = true)
    public String index(Model model) {
        model.addAttribute("animalList", animalService.getAnimals());
        return "home";
    }

    @PostMapping("/add")
    @Logging(value = "MVC POST request - /add", entering = true, exiting = true)
    public String add(Model model) {
        model.addAttribute("animal", new Animal());
        model.addAttribute("breeds", animalService.getBreeds());
        model.addAttribute("animalTypes", animalService.getAnimalTypes());
        return "add";
    }

    @PostMapping(value = "/add", params = "action=add")
    @Logging(value = "MVC POST request - /add", entering = true, exiting = true)
    public String addAnimal(Animal animal) {
        animalService.addAnimal(animal);
        return "redirect:/home";
    }

    @DeleteMapping("/delete/{id}")
    @Logging(value = "MVC DELETE request - /add", entering = true, exiting = true)
    public String deleteAnimalById(@PathVariable(name = "id") Integer id) {
        animalService.deleteAnimalById(id);
        return "redirect:/home";
    }

    @GetMapping(value = "/add", params = "action=cancel")
    @Logging(value = "MVC GET request - /add", entering = true, exiting = true)
    public String cancel() {
        return "redirect:/home";
    }
}
