package repository;

import dto.Animal;

import java.time.LocalDate;
import java.util.Map;

public interface AnimalsRepository {
    /**
     * Finds all animals born in a leap year.
     *
     * @return массив животных
     */
    Map<String, LocalDate> findLeapYearNames();

    /**
     * Finds all animals older than N years.
     *
     * @param N ограничение по возрасту
     * @return массив животных
     */
    Map<Animal, Integer> findOlderAnimal(int N);

    /**
     * Находит дубликаты животных
     */
    Map<String, Integer> findDuplicate();

    void printDuplicate();
}
