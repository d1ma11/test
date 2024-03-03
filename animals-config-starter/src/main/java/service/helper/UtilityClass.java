package service.helper;

import dto.Animal;
import dto.AnimalsEnum;
import dto.Pet.Parrot;
import dto.Predator.Bear;
import dto.Predator.Tiger;

public class UtilityClass {
    /**
     * Данный метод служит для определения типа животного, которое передается в виде параметра
     * @param animal животное, у которого нужно определить тип
     * @return тип животного
     */
    public static AnimalsEnum getAnimalType(Animal animal) {
        if (animal instanceof Bear) {
            return AnimalsEnum.BEAR;
        } else if (animal instanceof Tiger) {
            return AnimalsEnum.TIGER;
        } else if (animal instanceof Parrot) {
            return AnimalsEnum.PARROT;
        }
        return AnimalsEnum.HAMSTER;
    }
}
