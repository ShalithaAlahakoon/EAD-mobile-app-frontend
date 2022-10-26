package com.example.eadmobileapp.models;

public class User {

    private String username;
    private String password;
    private String email;
    private String nic;
    private String type;

    public User(String username, String password, String email, String nic, String type) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nic = nic;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNic() {
        return nic;
    }

    public String getType() {
        return type;
    }
}
