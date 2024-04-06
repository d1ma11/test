package repository;

import dto.Animal;
import exception.SmallListSizeException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

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
    Map<String, List<Animal>> findDuplicate();

    void printDuplicate();

    /**
     * Находит средний возраст всех животных
     *
     * @param animalList список животных
     * @return средний возраст
     */
    OptionalDouble findAverageAge(List<Animal> animalList);

    /**
     * Находит животных, которые:
     * - старше 5 лет
     * - цена больше средней стоимости всех животных
     *
     * @param animalList список животных
     * @return отсортированный по дате рождения (по возрастанию) список животных
     */
    List<Animal> findOldAndExpensive(List<Animal> animalList);

    /**
     * Находит максимум 3 животных с самой низкой ценой
     *
     * @param animalList список животных
     * @return список имен, отсортированный в обратном порядке
     * @throws SmallListSizeException если размер списка animalList меньше, чем 3
     */
    List<String> findMinCostAnimals(List<Animal> animalList) throws SmallListSizeException;
}
