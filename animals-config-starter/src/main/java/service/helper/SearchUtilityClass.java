package service.helper;

import dto.Animal;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public final class SearchUtilityClass {

    private SearchUtilityClass() {
    }

    /**
     * Високосный ли год?
     *
     * @param year год
     * @return true - если високосный, иначе - false
     */
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    /**
     * Возвращает разницу в годах между текущей и переданной даты.
     *
     * @param birthDate дата рождения
     * @return возраст животного
     */
    public static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * Возвращает самого старшего животного из списка
     *
     * @param animalList список животных
     * @return животное
     */
    public static Animal findAnimalWithMaxAge(List<Animal> animalList) {
        if (animalList.isEmpty()) {
            throw new IllegalArgumentException("Your animal list is empty");
        }
        Animal oldestAnimal = animalList.get(0);
        int maxAge = 0;

        for (Animal animal : animalList) {
            int currentAnimalAge = calculateAge(animal.getBirthDate());

            if (currentAnimalAge > maxAge) {
                oldestAnimal = animal;
                maxAge = currentAnimalAge;
            }
        }
        return oldestAnimal;
    }
}
