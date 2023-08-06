package com.example.myfirstapp.models;

public class OwnerModel {
    String username, email, password, store_name;
    public OwnerModel()
    {

    }

    public OwnerModel(String username, String store_name, String email, String password)
    {
        this.username = username;
        this.store_name = store_name;
        this.email = email;
        this.password = password;
    }

    public String getUsername()
    {
        return this.username;
    }
    public void setUsername()
    {
        this.username = username;
    }
    public String getEmail()
    {
        return this.email;
    }
    public void setEmail()
    {
        this.email = email;
    }
    public String getPassword()
    {
        return this.password;
    }
    public void setPassword()
    {
        this.password = password;
    }
    public String getStore_name()
    {
        return this.store_name;
    }
    public void setStore_name()
    {
        this.store_name = store_name;
    }

}
