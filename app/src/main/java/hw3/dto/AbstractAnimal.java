package hw3.dto;

import java.math.BigDecimal;

public abstract class AbstractAnimal implements Animal {
    protected String breed; // порода
    protected String name; // имя
    protected BigDecimal cost; // цена
    protected String character; // характер
}