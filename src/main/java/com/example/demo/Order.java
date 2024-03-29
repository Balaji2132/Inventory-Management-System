package com.example.demo;

public class Order {

    private int orderId;
    private int customerId;
    private String orderDate;
    private double totalAmount;

    public Order(int orderId, int customerId, String orderDate, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
