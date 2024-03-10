package repository;

import dto.Animal;

import java.time.LocalDate;
import java.util.Map;

public interface AnimalsRepository {
    /**
     * Находит всех животных, родившихся в високосный год
     *
     * @return массив животных
     */
    Map<String, LocalDate> findLeapYearNames();

    /**
     * Находит всех животных старше N лет
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
