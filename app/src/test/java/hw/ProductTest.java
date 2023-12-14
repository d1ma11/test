package hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hw.dto.Product;

public class ProductTest {
    @Test
    void testBuyWithDiscountProduct1() {
        Product product1 = new Product(2, 15.2, 0.75);
        double expected = 30.17;
        double actual = product1.buyWithDiscount();
        Assertions.assertEquals(expected, actual, "Test failed for product1");
    }

    @Test
    void testBuyWithDiscountProduct2() {
        Product product2 = new Product(5, 10.29, 45.575);
        double expected = 28.0;
        double actual = product2.buyWithDiscount();
        Assertions.assertEquals(expected, actual, "Test failed for product2");
    }

    @Test
    void testBuyWithDiscountProduct3() {
        Product product3 = new Product(1, 9.0, 59.1);
        double expected = 3.68;
        double actual = product3.buyWithDiscount();
        Assertions.assertEquals(expected, actual, "Test failed for product3");
    }
}
