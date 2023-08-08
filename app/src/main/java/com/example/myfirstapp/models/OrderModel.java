package com.example.myfirstapp.models;

import java.util.List;

public class OrderModel {
    String orderID;
    String status;
    String userID;
    List<CartItem> products;

    public OrderModel() {
    }

    public OrderModel(String orderID, String status, String userID, List<CartItem> products) {
        this.orderID = orderID;
        this.status = status;
        this.userID = userID;
        this.products = products;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }
}
