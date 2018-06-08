package com.imperialsoupgmail.tesseractexample;

/**
 * Created by gery on 2/1/2018.
 */

public class Order {
    private String name;
    private Double count;
    private Double price;

    public Order(String name, Double count, Double price){
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
