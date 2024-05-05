package dto.db_objects;

import jakarta.persistence.*;

@Entity
@Table(name = "animals_provider", schema = "animals")
public class AnimalsProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAnimalType;
    private int idProvider;

    public AnimalsProvider() {
    }

    public AnimalsProvider(int idAnimalType, int idProvider) {
        this.idAnimalType = idAnimalType;
        this.idProvider = idProvider;
    }

    public int getIdAnimalType() {
        return idAnimalType;
    }

    public void setIdAnimalType(int idAnimalType) {
        this.idAnimalType = idAnimalType;
    }

    public int getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(int idProvider) {
        this.idProvider = idProvider;
    }
}
