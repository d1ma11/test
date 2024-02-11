package hw3;

import hw3.dto.Animal;
import hw3.dto.CharacterEnum;
import hw3.dto.NamesEnum;
import hw3.dto.Pet.Parrot;
import hw3.dto.Pet.ParrotBreeds;
import hw3.dto.Predator.Bear;
import hw3.dto.Predator.BearBreeds;
import hw3.dto.Predator.Tiger;
import hw3.dto.Predator.TigerBreeds;
import hw3.service.SearchService;
import hw3.service.SearchServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {
    //Задание 1
    @Nested
    class EqualsMethodTest {
        private static Bear bear_X;
        private static Bear bear_Y;
        private static Bear bear_Z;
        private static Tiger tiger;


        @BeforeAll
        public static void beforeAll() {
            bear_X = new Bear(BearBreeds.PANDA, NamesEnum.Cassie, 149327.54, CharacterEnum.KIND, LocalDate.of(2020, 1, 9));
            bear_Y = new Bear(BearBreeds.PANDA, NamesEnum.Cassie, 149327.54, CharacterEnum.KIND, LocalDate.of(2020, 1, 9));
            bear_Z = new Bear(BearBreeds.PANDA, NamesEnum.Cassie, 149327.54, CharacterEnum.KIND, LocalDate.of(2020, 1, 9));
            tiger = new Tiger(TigerBreeds.JAPAN_TIGER, NamesEnum.Cassie, 917318.36, CharacterEnum.KIND, LocalDate.of(2020, 1, 9));
        }

        /**
         * Для любой ненулевой ссылки на значение X выражение X.equals(X) должно возвращать TRUE
         */
        @Test
        public void equalityReflexivityTest() {
            assertEquals(bear_X, bear_X);
        }

        /**
         * Для любых ненулевых ссылок на значения X и Y выражение X.equals(Y) должно возвращать TRUE
         * тогда и только тогда, когда Y.equals(X) возвращает TRUE
         */
        @Test
        public void equalitySymmetryTest() {
            assertEquals(bear_X, bear_Y);
            assertEquals(bear_Y, bear_X);
        }

        /**
         * Для любых ненулевых ссылок на значения X, Y и Z, если X.equals(Y) возвращает TRUE
         * и Y.equals(Z) возвращает TRUE, тогда X.equals(Z) должно возвращать TRUE
         */
        @Test
        public void equalityTransitivityTest() {
            assertEquals(bear_X, bear_Y);
            assertEquals(bear_Y, bear_Z);
            assertEquals(bear_X, bear_Z);
        }

        /**
         * Для любой ненулевой ссылки на значение X выражение X.equals(NULL) должно возвращать FALSE
         */
        @Test
        @DisplayName("givenNullObject_whenTestEquality_thenReturnFalse")
        public void equalityNullTest() {
            assertNotEquals(null, bear_X);
        }

        @Test
        public void givenTwoDifferentObject_whenTestEquality_thenReturnFalse() {
            assertNotEquals(bear_X, tiger);
        }
    }

    //Задания 2, 3, 4
    @Nested
    class ServiceMethodsTest {
        private static final SearchService service = new SearchServiceImpl();

        private static Animal[] animals;
        private static Animal[] animalsWithDuplicates;

        @BeforeAll
        public static void beforeAll() {
            animalsWithDuplicates = new Animal[]{
                    new Bear(BearBreeds.PANDA, NamesEnum.Cassie, 100, CharacterEnum.KIND, LocalDate.of(1980, 10, 12)),
                    new Bear(BearBreeds.PANDA, NamesEnum.Suds, 100, CharacterEnum.TALKATIVE, LocalDate.of(1980, 10, 12)),
                    new Bear(BearBreeds.PANDA, NamesEnum.Killy, 200, CharacterEnum.WICKED, LocalDate.of(1975, 10, 12)),
                    new Bear(BearBreeds.PANDA, NamesEnum.Altai, 300, CharacterEnum.KIND, LocalDate.of(2000, 10, 12)),
                    new Bear(BearBreeds.PANDA, NamesEnum.Altai, 300, CharacterEnum.KIND, LocalDate.of(2000, 10, 12)),
                    new Tiger(TigerBreeds.JAPAN_TIGER, NamesEnum.Leeko, 228, CharacterEnum.SAD, LocalDate.of(2017, 1, 25)),
                    new Tiger(TigerBreeds.JAPAN_TIGER, NamesEnum.Paladin, 228, CharacterEnum.TRICKY, LocalDate.of(2017, 1, 25)),
                    new Tiger(TigerBreeds.JAPAN_TIGER, NamesEnum.Waly, 228, CharacterEnum.SAD, LocalDate.of(2017, 1, 25)),
                    new Tiger(TigerBreeds.JAPAN_TIGER, NamesEnum.Waly, 228, CharacterEnum.SAD, LocalDate.of(2017, 1, 25)),
                    new Parrot(ParrotBreeds.FINCH_PARROT, NamesEnum.Jaxon, 1337, CharacterEnum.SMART, LocalDate.of(2020, 2, 3)),
            };
            animals = new Animal[]{
                    new Bear(BearBreeds.PANDA, NamesEnum.Cassie, 100, CharacterEnum.KIND, LocalDate.of(1980, 10, 12)),
                    new Bear(BearBreeds.PANDA, NamesEnum.Jaxon, 200, CharacterEnum.KIND, LocalDate.of(1975, 10, 12)),
                    new Bear(BearBreeds.PANDA, NamesEnum.Nibbler, 300, CharacterEnum.KIND, LocalDate.of(2000, 10, 12)),
                    new Tiger(TigerBreeds.JAPAN_TIGER, NamesEnum.Ginger, 228, CharacterEnum.SAD, LocalDate.of(2017, 1, 25)),
                    new Parrot(ParrotBreeds.FINCH_PARROT, NamesEnum.Widget, 1337, CharacterEnum.TALKATIVE, LocalDate.of(2020, 2, 3))
            };
        }

        //Задание 2
        @Test
        public void testFindLeapYearNames_EMPTY_ARRAY() {
            Animal[] animal = {};
            String[] result = service.findLeapYearNames(animal);
            String[] expected = {};
            assertArrayEquals(expected, result);
        }

        @Test
        public void testFindLeapYearNames_NOT_EMPTY_ARRAY() {
            String[] result = service.findLeapYearNames(animals);
            String[] expected = {"Cassie", "Nibbler", "Widget"};
            assertArrayEquals(expected, result);
        }

        //Задание 3
        @ParameterizedTest(name = "Try to test with value: {arguments}")
        @ValueSource(ints = {1, 10, 40})
        public void testFindOlderAnimal(int n) {
            Animal[] result = service.findOlderAnimal(animals, n);

            switch (n) {
                case 1:
                    Animal[] expectedFirstArgument = {animals[0], animals[1], animals[2], animals[3], animals[4]};
                    assertArrayEquals(expectedFirstArgument, result);
                    break;
                case 10:
                    Animal[] expectedSecondArgument = {animals[0], animals[1], animals[2]};
                    assertArrayEquals(expectedSecondArgument, result);
                    break;
                case 40:
                    Animal[] expectedThirdArgument = {animals[0], animals[1]};
                    assertArrayEquals(expectedThirdArgument, result);
                    break;
            }
        }

        //Задание 4
        /**
         * Для реализации этого теста есть совет - переопределить реализацию метода findDuplicate()
         * и вынести вывод на экран в отдельный метод (например printDuplicate()),
         * который будет вызывать сам метод findDuplicate() в коде.
         */
        @Test
        public void testFindDuplicate() {

            SearchServiceImpl searchService = new SearchServiceImpl() {

                @Override
                public void findDuplicate(Animal[] animals) {
                    Set<Animal> seenAnimals = new HashSet<>();
                    for (Animal animal : animals) {
                        if (!seenAnimals.add(animal)) {
                            duplicates.add(animal);
                        }
                    }
                }
            };

            searchService.printDuplicates(animalsWithDuplicates);
            Animal[] expected = {animalsWithDuplicates[4], animalsWithDuplicates[8]};

            assertEquals(Arrays.toString(expected), searchService.getDuplicates().toString());
        }
    }
}
