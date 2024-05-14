package controller;

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
    public String index(Model model) {
        model.addAttribute("animalList", animalService.getAnimals());
        return "home";
    }

    @PostMapping("/add")
    public String add(Model model) {
        model.addAttribute("animal", new Animal());
        model.addAttribute("breeds", animalService.getBreeds());
        model.addAttribute("animalTypes", animalService.getAnimalTypes());
        return "add";
    }

    @PostMapping(value = "/add", params = "action=add")
    public String addAnimal(Animal animal) {
        animalService.addAnimal(animal);
        return "redirect:/home";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAnimalById(@PathVariable(name = "id") Integer id) {
        animalService.deleteAnimalById(id);
        return "redirect:/home";
    }

    @GetMapping(value = "/add", params = "action=cancel")
    public String cancel() {
        return "redirect:/home";
    }
}
