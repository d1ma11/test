package hw3.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public abstract class AbstractAnimal implements Animal {
    protected String breed; // порода
    protected String name; // имя
    protected BigDecimal cost; // цена
    protected String character; // характер
    protected LocalDate birthDate; // день рождения

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof AbstractAnimal))
            return false;
        AbstractAnimal that = (AbstractAnimal) obj;
        return breed.equals(that.breed) &&
                name.equals(that.name) &&
                cost.equals(that.cost) &&
                character.equals(that.character) &&
                birthDate.equals(that.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(breed, name, cost, character, birthDate);
    }
}