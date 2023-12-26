package hw3.dto.Pet;

import java.math.BigDecimal;
import java.math.RoundingMode;

import hw3.dto.CharacterEnum;
import hw3.dto.NamesEnum;

public class Parrot extends Pet{

    public Parrot(ParrotBreeds breed, NamesEnum name, double cost, CharacterEnum character) {
        this.breed = breed.toString().replace("_", " ");
        this.name = name.toString();
        this.cost = new BigDecimal(cost).setScale(2, RoundingMode.HALF_UP);
        this.character = character.toString();
    }

    @Override
    public String getBreed() {
        return breed;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public String getCharacter() {
        return character;
    }

    @Override
    protected String getCare() {
        return "The " + this.getBreed().toLowerCase().replace("_", " ") + " wants to get care";
    }

    @Override
    public String toString() {
        return "Parrot [breed=" + breed + ", name=" + name + ", cost=" + cost + ", character=" + character + "]";
    }
}
