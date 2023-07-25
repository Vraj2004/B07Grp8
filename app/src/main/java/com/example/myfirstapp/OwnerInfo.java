package com.example.myfirstapp;

public class OwnerInfo {
    private String email;
    private String pw;
    private String storeName;
    private String username;

    public OwnerInfo(String email, String pw, String storeName, String username) {
        this.email = email;
        this.pw = pw;
        this.storeName = storeName;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
