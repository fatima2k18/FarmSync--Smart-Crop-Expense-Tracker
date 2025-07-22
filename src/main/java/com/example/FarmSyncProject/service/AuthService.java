package com.example.FarmSyncProject.service;

import com.example.FarmSyncProject.dto.LoginRequest;
import com.example.FarmSyncProject.dto.LoginResponse;
import com.example.FarmSyncProject.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;


public interface AuthService {
    String registerUser(RegisterRequest request);
    LoginResponse loginUser(LoginRequest request);
}
