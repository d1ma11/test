package repository;

import dto.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import service.CreateAnimalService;

import javax.annotation.PostConstruct;
import java.util.*;

import static service.helper.SearchUtilityClass.calculateAge;
import static service.helper.SearchUtilityClass.isLeapYear;


public class AnimalsRepositoryImpl implements AnimalsRepository {
    private List<Animal> animalList = new ArrayList<>();

    @Autowired
    private CreateAnimalService createAnimalService;

    public List<Animal> getAnimalList() {
        return animalList;
    }

    public void setAnimalList(List<Animal> animalList) {
        this.animalList = animalList;
    }

    @PostConstruct
    public void init() {
        Animal[] animals = createAnimalService.createAnimals();
        Collections.addAll(animalList, animals);
    }

    @Override
    public String[] findLeapYearNames() {
        List<String> names = new ArrayList<>();
        for (Animal animal : animalList) {
            int birthYear = animal.getBirthDate().getYear();
            if (isLeapYear(birthYear)) {
                names.add(animal.getName());
            }
        }
        if (names.isEmpty()) {
            System.out.println("Животных, родившихся в високосный год, нет");
        }
        return names.toArray(new String[0]);
    }

    @Override
    public Animal[] findOlderAnimal(int n) {
        List<Animal> oldAnimals = new ArrayList<>();
        for (Animal animal : animalList) {
            int age = calculateAge(animal.getBirthDate());
            if (age > n && n > 0) {
                oldAnimals.add(animal);
            }
        }
        if (oldAnimals.isEmpty()) {
            System.out.println("Животных старше " + n + " лет нет");
        }
        return oldAnimals.toArray(new Animal[0]);
    }

    @Override
    public Set<Animal> findDuplicate() {
        Set<Animal> duplicatedAnimals = new HashSet<>();
        for (Animal animal : animalList) {
            if (duplicatedAnimals.add(animal)) {
                duplicatedAnimals.remove(animal);
            }
        }
        return duplicatedAnimals;
    }

    @Override
    public void printDuplicate() {
        if (findDuplicate().isEmpty()) {
            System.out.println("Дубликатов животных нет");
        }
        for (Animal animal : findDuplicate()) {
            System.out.println(animal);
        }
    }
}
