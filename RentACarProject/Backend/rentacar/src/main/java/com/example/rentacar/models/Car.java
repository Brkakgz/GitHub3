package com.example.rentacar.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String color;
    private boolean isAutomatic;
    private int kmAge;
    private double dailyPrice;

    public Car() {
    }

    public Car(Long id, String brand, String model, String color, boolean isAutomatic, int kmAge, double dailyPrice) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.isAutomatic = isAutomatic;
        this.kmAge = kmAge;
        this.dailyPrice = dailyPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isAutomatic() {
        return isAutomatic;
    }

    public void setAutomatic(boolean automatic) {
        isAutomatic = automatic;
    }

    public int getKmAge() {
        return kmAge;
    }

    public void setKmAge(int kmAge) {
        this.kmAge = kmAge;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }
}
