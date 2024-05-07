package dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "breed", schema = "animals")
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBreed;

    private String breedName;

    @OneToMany(mappedBy = "breed", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Animal> animalList = new ArrayList<>();

    public Breed() {
    }

    public Breed(List<Animal> animalList) {
        this.animalList = animalList;
    }

    public int getIdBreed() {
        return idBreed;
    }

    public void setIdBreed(int id) {
        this.idBreed = id;
    }

    public List<Animal> getAnimalList() {
        return animalList;
    }

    public void setAnimalList(List<Animal> animalList) {
        this.animalList = animalList;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }
}
