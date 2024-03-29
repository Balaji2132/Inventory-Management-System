package com.example.demo;

public class Product {

    private int productId;
    private String productName;
    private String description;
    private double unitPrice;

    public Product(int productId, String productName, String description, double unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}
