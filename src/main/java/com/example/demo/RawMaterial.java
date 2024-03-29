package com.example.demo;

public class RawMaterial {

    private int materialId;
    private String materialName;
    private int quantity;
    private double unitPrice;

    public RawMaterial(int materialId, String materialName, int quantity, double unitPrice) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getMaterialId() {
        return materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}
