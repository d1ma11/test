package hw.service;

import hw.dto.Product;

public class Main {
    public static void main(String[] args) {
        Product product1 = new Product(2, 15.2, 0.75);
        Product product2 = new Product(5, 10.29, 45.575);
        Product product3 = new Product(1, 9.0, 59.1);

        product1.buyProduct();
        product2.buyProduct();
        product3.buyProduct();
    }
}