package com.example.myfirstapp.models;

public class StoreModel {

    String store_name, id;
    public StoreModel() {
    }

    public StoreModel(String store_name, String id)
    {
        this.store_name = store_name;
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

}
