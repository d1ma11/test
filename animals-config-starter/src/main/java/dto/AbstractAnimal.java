package dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public abstract class AbstractAnimal implements Animal {
    protected String breed; // порода
    protected String name; // имя
    protected BigDecimal cost; // цена
    protected String character; // характер
    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate birthDate; // день рождения
    protected String secretInformation;

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setSecretInformation(String secretInformation) {
        this.secretInformation = secretInformation;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof AbstractAnimal that))
            return false;
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