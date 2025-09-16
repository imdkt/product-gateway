package com.example.productgateway.dto;

public class ProductResponse {

    private String id;
    private String name;
    private double price;
    private String availability_status;

    public ProductResponse(String id, String name, double price, String availability_status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availability_status = availability_status;
    }

    // Getters (needed for JSON serialization)
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getAvailability_status() {
        return availability_status;
    }
}
