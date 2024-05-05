package dto;

import java.math.BigDecimal;
import java.time.LocalDate;

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