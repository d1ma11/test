package dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import service.helper.Base64Deserializer;
import service.helper.Base64Serializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "animal", schema = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAnimal;

    @ManyToOne(targetEntity = Breed.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_breed")
    @JsonManagedReference
    private Breed breed;

    @ManyToOne(targetEntity = AnimalType.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_type")
    @JsonManagedReference
    private AnimalType animalType;

    private String name;
    private BigDecimal cost;
    private String character;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @JsonSerialize(using = Base64Serializer.class)
    @JsonDeserialize(using = Base64Deserializer.class)
    private String secretInformation;

    public Animal() {
    }

    public Animal(int idAnimal, Breed breed, AnimalType animalType, String name, BigDecimal cost, String character, LocalDate birthDate, String secretInformation) {
        this.idAnimal = idAnimal;
        this.breed = breed;
        this.animalType = animalType;
        this.name = name;
        this.cost = cost;
        this.character = character;
        this.birthDate = birthDate;
        this.secretInformation = secretInformation;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getSecretInformation() {
        return secretInformation;
    }

    public void setSecretInformation(String secretInformation) {
        this.secretInformation = secretInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(animalType, animal.animalType) &&
                Objects.equals(birthDate, animal.birthDate) &&
                Objects.equals(name, animal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalType, birthDate, name);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "idAnimal=" + idAnimal +
                ", breed=" + breed +
                ", animalType=" + animalType +
                ", birthDate=" + birthDate +
                ", name='" + name + '\'' +
                ", character='" + character + '\'' +
                ", cost=" + cost +
                ", secretInformation='" + secretInformation + '\'' +
                '}';
    }
}
