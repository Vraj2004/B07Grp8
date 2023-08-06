package com.example.myfirstapp.models;

public class CartItem {

    String store_name;
    String product_name;
    String quantity;
    String amount;

    public CartItem() {
    }

    public CartItem(String store_name, String product_name, String quantity, String amount) {
        this.store_name = store_name;
        this.product_name = product_name;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


}
