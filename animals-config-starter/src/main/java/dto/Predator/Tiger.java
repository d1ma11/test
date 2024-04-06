package dto.Predator;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import dto.AnimalsEnum;
import dto.CharacterEnum;
import service.helper.Base64Serializer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static service.helper.UtilityClass.getSecretLine;

@JsonTypeName("tiger")
public class Tiger extends Predator {

    public Tiger() {
    }

    public Tiger(TigerBreeds breed, String name, double cost, CharacterEnum character, LocalDate birthDate) {
        this.breed = breed.toString().replace("_", " ");
        this.name = name;
        this.cost = new BigDecimal(cost).setScale(2, RoundingMode.HALF_UP);
        this.character = character.toString();
        this.birthDate = birthDate;
        this.secretInformation = getSecretLine(AnimalsEnum.TIGER);
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

    @JsonSerialize(using = LocalDateSerializer.class)
    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @JsonSerialize(using = Base64Serializer.class)
    @Override
    public String getSecretInformation() {
        return secretInformation;
    }

    @Override
    protected String hunt() {
        return "The " + this.getBreed().toLowerCase().replace("_", " ") + " starts hunting";
    }

    @Override
    public String toString() {
        return "Tiger{" +
                "breed='" + breed + '\'' +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", character='" + character + '\'' +
                ", birthDate=" + birthDate +
                ", secretInformation='" + secretInformation + '\'' +
                '}';
    }
}
