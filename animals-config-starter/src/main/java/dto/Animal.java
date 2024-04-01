package dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import dto.Pet.Hamster;
import dto.Pet.Parrot;
import dto.Predator.Bear;
import dto.Predator.Tiger;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonSubTypes({
        @JsonSubTypes.Type(value = Bear.class, name = "bear"),
        @JsonSubTypes.Type(value = Tiger.class, name = "tiger"),
        @JsonSubTypes.Type(value = Parrot.class, name = "parrot"),
        @JsonSubTypes.Type(value = Hamster.class, name = "hamster")
})
public interface Animal {
    /**
     * Возвращает породу животного.
     *
     * @return порода животного
     */
    String getBreed();

    /**
     * Возвращает имя животного.
     *
     * @return имя животного
     */
    String getName();

    /**
     * Возвращает стоимость животного в магазине.
     *
     * @return стоимость животного
     */
    BigDecimal getCost();

    /**
     * Возвращает характер животного.
     *
     * @return характер животного
     */
    String getCharacter();

    /**
     * Возвращает день рождения животного
     *
     * @return день рождения животного
     */
    LocalDate getBirthDate();

    /**
     * Возвращает секретную информацию
     *
     * @return секретная информация
     */
    String getSecretInformation();
}