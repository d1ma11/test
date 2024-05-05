package service;

import dto.AbstractAnimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CreateAnimalService {

    /**
     * ������� ��������� ��������
     *
     * @return ��������� ��������
     */
    AbstractAnimal createRandomAnimal();

    /**
     * ������� ������� �� ��������� ��������
     *
     * @param n ���������� ��������
     * @return ������� �� n ��������� ��������
     */
    default Map<String, List<AbstractAnimal>> createRandomAnimals(int n) {
        Map<String, List<AbstractAnimal>> animalMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            AbstractAnimal animal = createRandomAnimal();
            animalMap.computeIfAbsent(animal.getAnimalType(), k -> new ArrayList<>()).add(animal);
        }
        return animalMap;
    }
}
