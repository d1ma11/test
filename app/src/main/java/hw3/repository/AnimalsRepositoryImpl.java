package hw3.repository;

import hw3.dto.Animal;
import hw3.service.CreateAnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


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

    /**
     * Проверка года на весокосность
     *
     * @param year год
     * @return true - если високосный, иначе - false
     */
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
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

    /**
     * Подсчет возраста животного
     *
     * @param birthDate день рождения животного
     * @return возраст животного
     */
    public static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    @Override
    public Animal[] findOlderAnimal(int n) {
        List<Animal> oldAnimals = new ArrayList<>();
        for (Animal animal : animalList) {
            int age = calculateAge(animal.getBirthDate());
            if (age > n) {
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
