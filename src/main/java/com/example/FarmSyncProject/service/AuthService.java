package com.example.FarmSyncProject.service;

import com.example.FarmSyncProject.config.JwtUtil;
import com.example.FarmSyncProject.dto.LoginRequest;
import com.example.FarmSyncProject.dto.LoginResponse;
import com.example.FarmSyncProject.dto.RegisterRequest;
import com.example.FarmSyncProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;


public interface AuthService {

    String registerUser(RegisterRequest request);
    LoginResponse loginUser(LoginRequest request);
}
