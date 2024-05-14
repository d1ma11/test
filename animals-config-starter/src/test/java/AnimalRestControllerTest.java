import com.fasterxml.jackson.databind.ObjectMapper;
import controller.AnimalRestController;
import dto.Animal;
import dto.AnimalType;
import dto.Breed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import service.AnimalService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AnimalRestController.class)
@AutoConfigureMockMvc
public class AnimalRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    private Animal animal;

    @BeforeEach
    public void setUp() {
        animal = new Animal(
                1,
                new Breed(),
                new AnimalType(),
                "Misha",
                BigDecimal.valueOf(12345.53),
                "Angry",
                LocalDate.of(2016, 5, 5),
                "Likes raspberries"
        );
    }

    @Test
    public void testGetAnimals() throws Exception {
        when(animalService.getAnimals()).thenReturn(List.of(animal));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/animals/getAnimals"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(animal.getName()));
    }

    @Test
    public void testAddAnimal() throws Exception {
        mockMvc.perform(
                post("/api/animals/addAnimal")
                        .content(animal.toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    @Test
    public void testDeleteAnimal() throws Exception {
        mockMvc.perform(delete("/api/animals/deleteAnimal/1"))
                .andExpect(status().isNoContent());

        verify(animalService, times(1)).deleteAnimalById(1);
    }
}
