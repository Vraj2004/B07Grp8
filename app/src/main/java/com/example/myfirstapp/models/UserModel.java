package com.example.myfirstapp.models;

public class UserModel {
    String username, email, password;
    int isOwner;
    public UserModel()
    {

    }

    public UserModel(String username, String email, String password, int isOwner)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isOwner = isOwner;
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

    public int getIsOwner() { return this.isOwner;}
    public void setIsOwner() {this.isOwner = isOwner;}
}
