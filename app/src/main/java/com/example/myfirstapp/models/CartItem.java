package com.example.myfirstapp.models;

public class CartItem {

    String store_id;
    String product_name;
    String quantity;
    String price;


    public CartItem() {
    }

    public CartItem(String store_id, String product_name, String quantity, String amount) {
        this.store_id = store_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = amount;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String amount) {
        this.price = amount;
    }


}
