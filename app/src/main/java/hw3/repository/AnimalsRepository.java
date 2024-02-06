package hw3.repository;

import hw3.dto.Animal;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface AnimalsRepository {
    /**
     * Finds all animals born in a leap year.
     *
     * @return массив животных
     */
    String[] findLeapYearNames();

    /**
     * Finds all animals older than N years.
     *
     * @param N ограничение по возрасту
     * @return массив животных
     */
    Animal[] findOlderAnimal(int N);

    /**
     * Находит дубликаты животных
     */
    Set<Animal> findDuplicate();

    void printDuplicate();
}
