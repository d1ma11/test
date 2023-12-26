package hw3.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hw3.dto.Animal;

public class SearchServiceImpl implements SearchService {

    /**
     * Проверка года на весокосность
     *
     * @param year
     * @return true - если весокосный, иначе - false
     */
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

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

    /**
     * Подсчет возраста животного
     *
     * @param birthDate
     * @return возраст животноого
     */
    public static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
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

}
