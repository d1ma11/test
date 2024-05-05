package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.stereotype.Component;
import service.helper.Base64Deserializer;
import service.helper.Base64Serializer;
import service.helper.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

@Component
public class AbstractAnimal implements Animal {
    private static final Random random = new Random();
    protected String breed; // порода
    protected String name; // имя
    protected BigDecimal cost; // цена
    protected String character; // характер
    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate birthDate; // день рождения
    @JsonSerialize(using = Base64Serializer.class)
    @JsonDeserialize(using = Base64Deserializer.class)
    protected String secretInformation;
    protected boolean isWild;
    protected String animalType;

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

    @JsonSerialize(using = Base64Serializer.class)
    public void setSecretInformation(String secretInformation) {
        this.secretInformation = secretInformation;
    }

    public AbstractAnimal() {
    }

    /**
     * Дефолтный конструктор со всеми параметрами
     *
     * @param breed     порода животного
     * @param name      имя животного
     * @param birthDate дата рождения
     * @param character характер животного
     * @param cost      цена животного
     */
    public AbstractAnimal(String breed, String name, BigDecimal cost, String character, LocalDate birthDate, String animalType) {
        this.breed = breed;
        this.name = name;
        this.cost = cost.setScale(2, RoundingMode.HALF_UP);
        this.character = character;
        this.birthDate = birthDate;
        this.secretInformation = UtilityClass.getSecretLine(animalType);
        this.animalType = animalType;
        switch (getAnimalType()) {
            case "PARROT", "HAMSTER" -> this.isWild = false;
            case "TIGER", "BEAR" -> this.isWild = true;
        }
    }

    /**
     * Конструктор AbstractAnimal, в котором значения некоторых параметров будут сгенерированы случайным образом,
     * кроме параметров animalType, name и character
     *
     * @param animalType тип животного
     * @param name       имя животного
     * @param character  характер животного
     */
    public AbstractAnimal(String animalType, String name, String character) {
        this.animalType = animalType;
        this.breed = "Number " + (random.nextInt(1000));
        this.name = name;
        this.birthDate = generateRandomDate();
        this.character = character;
        this.cost = (BigDecimal.valueOf(random.nextDouble() * 1000)).setScale(2, RoundingMode.HALF_UP);
        this.secretInformation = UtilityClass.getSecretLine(animalType);
        switch (getAnimalType()) {
            case "PARROT", "HAMSTER" -> this.isWild = false;
            case "TIGER", "BEAR" -> this.isWild = true;
        }
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
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    @JsonDeserialize(using = Base64Deserializer.class)
    public String getSecretInformation() {
        return secretInformation;
    }

    public boolean isWild() {
        return isWild;
    }

    public void setWild(boolean wild) {
        isWild = wild;
    }

    @JsonIgnore
    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    /**
     * Возвращает дату в отформатированном виде
     *
     * @param format формат строки
     * @return дату в формате format
     */
    public String getFormatDate(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(birthDate);
    }

    /**
     * Возвращает возраст животного в годах
     *
     * @return возраст животного
     */
    @JsonIgnore
    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * Данный метод возвращает рандомную дату
     *
     * @return дата
     */
    private LocalDate generateRandomDate() {
        long epoch = (long) (random.nextDouble() * (LocalDate.now().toEpochDay()));
        return LocalDate.ofEpochDay(epoch);
    }
}