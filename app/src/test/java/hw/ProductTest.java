package hw;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hw.dto.Product;

public class ProductTest {
    @Test
    void testBuyWithDiscountProduct1() {
        Product product1 = new Product(2, new BigDecimal(15.2), new BigDecimal(0.75));

        BigDecimal expected = new BigDecimal(30.17);
        expected = expected.setScale(2, RoundingMode.HALF_UP);

        BigDecimal actual = product1.buyWithDiscount();

        Assertions.assertEquals(expected, actual, "Test failed for product1");
    }

    @Test
    void testBuyWithDiscountProduct2() {
        Product product2 = new Product(5, new BigDecimal(10.29), new BigDecimal(45.575));

        BigDecimal expected = new BigDecimal(28.00);
        expected = expected.setScale(2, RoundingMode.HALF_UP);

        BigDecimal actual = product2.buyWithDiscount();

        Assertions.assertEquals(expected, actual, "Test failed for product2");
    }

    @Test
    void testBuyWithDiscountProduct3() {
        Product product3 = new Product(1, new BigDecimal(9.0), new BigDecimal(59.1));

        BigDecimal expected = new BigDecimal(3.68);
        expected = expected.setScale(2, RoundingMode.HALF_UP);

        BigDecimal actual = product3.buyWithDiscount();

        Assertions.assertEquals(expected, actual, "Test failed for product3");
    }
}
