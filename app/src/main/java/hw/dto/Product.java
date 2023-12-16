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
    private BigDecimal price;
    //Скидка на данный товар
    private BigDecimal discount;

    public Product(int count, BigDecimal price, BigDecimal discount) {
        this.count = count;
        this.price = price;
        this.discount = discount.divide(BigDecimal.valueOf(100.0));
    }

    public void buyProduct() {
        System.out.println("Product without discount: " + this.buyWithoutDiscount());
        System.out.println("Product with discount: " + this.buyWithDiscount() + '\n');
    }

    public BigDecimal buyWithoutDiscount() {
        return this.price.multiply(BigDecimal.valueOf(this.count)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal buyWithDiscount() {
        BigDecimal answer = this.buyWithoutDiscount().subtract(this.buyWithoutDiscount().multiply(this.discount));
        return answer.setScale(2, RoundingMode.HALF_UP);
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return this.discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String toString() {
        return "Product [count=" + this.count + ", price=" + this.price + ", discount=" + this.discount + "]";
    }
}
