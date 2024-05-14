package service;

import dto.Animal;
import dto.AnimalType;
import dto.Breed;
import org.springframework.stereotype.Service;
import repository.AnimalTypeRepository;
import repository.AnimalsRepository;
import repository.BreedRepository;

import java.util.List;

@Service
public class AnimalService {
    private final AnimalsRepository animalsRepository;
    private final BreedRepository breedRepository;
    private final AnimalTypeRepository animalTypeRepository;

    public AnimalService(AnimalsRepository animalsRepository, BreedRepository breedRepository, AnimalTypeRepository animalTypeRepository) {
        this.animalsRepository = animalsRepository;
        this.breedRepository = breedRepository;
        this.animalTypeRepository = animalTypeRepository;
    }

    public List<Animal> getAnimals() {
        return animalsRepository.findAll();
    }

    public void addAnimal(Animal animal) {
        animalsRepository.save(animal);
    }

    public void deleteAnimalById(int id) {
        animalsRepository.deleteById(id);
    }

    public List<Breed> getBreeds() {
        return breedRepository.findAll();
    }

    public List<AnimalType> getAnimalTypes() {
        return animalTypeRepository.findAll();
    }
}
