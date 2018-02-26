package com.imperialsoupgmail.tesseractexample;

/**
 * Created by gery on 2/1/2018.
 */

public class Order {
    private String name;
    private double count;
    private double price;

    public Order(String name, double count, double price){
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

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
