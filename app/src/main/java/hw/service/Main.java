package hw.service;

import java.math.BigDecimal;

import hw.dto.Product;

public class Main {
    public static void main(String[] args) {
        Product product1 = new Product(2, new BigDecimal(15.2), new BigDecimal(0.75));
        Product product2 = new Product(5, new BigDecimal(10.29), new BigDecimal(45.575));
        Product product3 = new Product(1, new BigDecimal(9.0), new BigDecimal(59.1));

        product1.buyProduct();
        product2.buyProduct();
        product3.buyProduct();
    }
}