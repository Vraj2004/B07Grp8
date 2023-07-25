package com.example.myfirstapp;

public class CustomerInfo {

    private String username;
    private String pw;
    private String email;

    public CustomerInfo(String username, String pw, String email) {
        this.username = username;
        this.pw = pw;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
