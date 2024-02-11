package hw3.service;

import java.time.LocalDate;

import hw3.dto.Animal;
import hw3.dto.CharacterEnum;
import hw3.dto.NamesEnum;
import hw3.dto.Predator.Bear;
import hw3.dto.Predator.BearBreeds;
import hw3.dto.Predator.Tiger;
import hw3.dto.Predator.TigerBreeds;

public class Main {
    public static void main(String[] args) {
        CreateAnimalService createAnimalService = new CreateAnimalServiceImpl();
        SearchServiceImpl searchService = new SearchServiceImpl();

        Animal[] arr = createAnimalService.createAnimals();

        System.out.println("Обычный вывод всех нагенерированных животных");
        for (Animal elem : arr) {
            System.out.println(elem);
        }

        System.out.println("\nВыводим имена всех животных, которые родились в високосный год");
        for (String elem : searchService.findLeapYearNames(arr)) {
            System.out.println(elem);
        }

        System.out.println("\nВыводим всех животных, у которых возраст больше определенного параметра");
        for (Animal elem : searchService.findOlderAnimal(arr, 30)) {
            System.out.println(elem);
        }

        System.out.println("\nИскусственно создаем случай, когда встречаются дупликаты животных");
        Animal[] arr1 = {
                new Bear(BearBreeds.PANDA, NamesEnum.Cassie, 149327.54, CharacterEnum.KIND, LocalDate.of(2020, 1, 9)),
                new Bear(BearBreeds.PANDA, NamesEnum.Cassie, 149327.54, CharacterEnum.KIND, LocalDate.of(2020, 1, 9)),
                AnimalFactory.createTiger(),
                AnimalFactory.createTiger(),
                new Tiger(TigerBreeds.JAPAN_TIGER, NamesEnum.Cassie, 917318.36, CharacterEnum.KIND, LocalDate.of(2020, 1, 9)),
                new Tiger(TigerBreeds.JAPAN_TIGER, NamesEnum.Cassie, 917318.36, CharacterEnum.KIND, LocalDate.of(2020, 1, 9))
        };

        System.out.println();
        searchService.findDuplicate(arr1);
    }
}