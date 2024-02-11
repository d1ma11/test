package hw3.service;

import hw3.dto.Animal;

public interface SearchService {
    /**
     * Finds all animals born in a leap year.
     *
     * @param животные массив животных
     * @return массив животных
     */
    String[] findLeapYearNames(Animal[] animals);

    /**
     * Finds all animals older than N years.
     *
     * @param животные массив животных
     * @param N        ограничение по возрасту
     * @return массив животных
     */
    Animal[] findOlderAnimal(Animal[] animals, int N);

    /**
     * Находит дупликаты животных
     *
     * @param животные массив животных
     */
    void findDuplicate(Animal[] animals);
}
