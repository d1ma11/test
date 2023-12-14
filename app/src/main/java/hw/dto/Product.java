package hw.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Сущность, которая описывает объект "Товар"
 */
public class Product {
    //Количество товара
    private int count;
    //Цена одного товара
    private double price;
    //Скидка на данный товар
    private double discount;

    public Product(int count, double price, double discount) {
        this.count = count;
        this.price = price;
        this.discount = discount / 100.0;
    }

    public void buyProduct() {
        System.out.printf("Product without discount: %.2f\n", this.buyWithoutDiscount());
        System.out.printf("Product with discount: %.2f\n\n", this.buyWithDiscount());
    }

    public double buyWithoutDiscount() {
        double answer = (double) this.getCount() * this.getPrice();
        return answer;
    }

    public double buyWithDiscount() {
        double answer = this.buyWithoutDiscount() - this.buyWithoutDiscount() * this.discount;
        BigDecimal bd = new BigDecimal(Double.toString(answer));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = (double) price;
    }

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(int discount) {
        this.discount = (double) discount;
    }

    public String toString() {
        return "Product [count=" + this.count + ", price=" + this.price + ", discount=" + this.discount + "]";
    }
}
