package dto;

import jakarta.persistence.*;

@Entity
@Table(name = "animals_habitats", schema = "animals")
public class AnimalsHabitats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAnimalType;
    private int idArea;

    public AnimalsHabitats() {
    }

    public AnimalsHabitats(int idAnimalType, int idArea) {
        this.idAnimalType = idAnimalType;
        this.idArea = idArea;
    }

    public int getIdAnimalType() {
        return idAnimalType;
    }

    public void setIdAnimalType(int idAnimalType) {
        this.idAnimalType = idAnimalType;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }
}
