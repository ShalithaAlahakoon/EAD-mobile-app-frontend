package com.example.eadmobileapp.models;

public class User {

    private String username;

    //constructor
    public User(String username) {
        this.username = username;
    }
    //getters and setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
