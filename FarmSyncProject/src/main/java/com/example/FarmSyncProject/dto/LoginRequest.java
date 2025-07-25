package com.example.FarmSyncProject.dto;

public class LoginRequest {
    private String email;   // or username based on your login logic
    private String password;

    // Constructors
    public void LoginRequest() {}

    public void LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
