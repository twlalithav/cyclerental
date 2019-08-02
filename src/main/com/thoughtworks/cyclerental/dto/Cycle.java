package com.thoughtworks.cyclerental.dto;

import java.util.Date;

public class Cycle {
    public int id;
    public String name;
    public String brand;
    public Double basePrice;
    public Double pricePerDay;
    public int noOfDays = 0;
    public boolean isRented = false;
    public Date rentedOn;

    public Cycle(int id, String brand, String name, Double pricePerDay) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.pricePerDay = pricePerDay;
    }

    public Cycle(int id, String brand, String name, Double basePrice, int noOfDays, Double pricePerDay) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.basePrice = basePrice;
        this.noOfDays = noOfDays;
        this.pricePerDay = pricePerDay;
    }

    public String toString() {
        String price;
        if(noOfDays != 0) {
            price = String.format("%f for %d days. %f after each day.", this.basePrice, this.noOfDays, this.pricePerDay);
        } else {
            price = String.format("%f per day", this.pricePerDay);
        }
        return String.format("%d|%s|%s|%s", this.id, this.name, this.brand, price);
    }

    public boolean equals(String name) {
        return this.name.equals(name);
    }

}
