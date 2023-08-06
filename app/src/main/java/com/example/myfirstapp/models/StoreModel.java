package com.example.myfirstapp.models;

public class StoreModel {

    String store_name;
    public StoreModel() {
    }

    public StoreModel(String store_name)
    {
        this.store_name = store_name;
    }
    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

}
