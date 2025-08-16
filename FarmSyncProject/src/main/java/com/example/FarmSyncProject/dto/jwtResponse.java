package com.example.FarmSyncProject.dto;

public class jwtResponse {
    private  String token;

    public void JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
