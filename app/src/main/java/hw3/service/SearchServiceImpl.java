package hw3.service;

import hw3.dto.Animal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static hw3.service.helper.SearchUtilityClass.calculateAge;
import static hw3.service.helper.SearchUtilityClass.isLeapYear;

public class SearchServiceImpl implements SearchService {

    protected static List<Animal> duplicates = new ArrayList<>();

    @Override
    public String[] findLeapYearNames(Animal[] animals) {
        List<String> names = new ArrayList<>();
        for (Animal animal : animals) {
            int birthYear = animal.getBirthDate().getYear();
            if (isLeapYear(birthYear)) {
                names.add(animal.getName());
            }
        }
        return names.toArray(new String[0]);
    }

    @Override
    public Animal[] findOlderAnimal(Animal[] animals, int n) {
        List<Animal> oldAnimals = new ArrayList<>();
        for (Animal animal : animals) {
            int age = calculateAge(animal.getBirthDate());
            if (age > n) {
                oldAnimals.add(animal);
            }
        }
        return oldAnimals.toArray(new Animal[0]);
    }

    @Override
    public void findDuplicate(Animal[] animals) {
        Set<Animal> seenAnimals = new HashSet<>();
        for (Animal animal : animals) {
            if (!seenAnimals.add(animal)) {
                System.out.println("Duplicate animal: " + animal);
            }
        }
    }

    public List<Animal> getDuplicates() {
        return duplicates;
    }

    public void printDuplicates(Animal[] duplicateAnimals) {
        findDuplicate(duplicateAnimals);

        for (Animal animal : duplicates) {
            System.out.println(animal);
        }
    }
}
