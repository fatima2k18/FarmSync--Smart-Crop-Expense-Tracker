package com.example.FarmSyncProject.controller;

import com.example.FarmSyncProject.dto.LoginRequest;
import com.example.FarmSyncProject.dto.LoginResponse;
import com.example.FarmSyncProject.dto.RegisterRequest;
import com.example.FarmSyncProject.repository.UserRepository;
import com.example.FarmSyncProject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository; // ✅ Inject the repository

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("User already registered!");
        }

        String result = authService.registerUser(request);
        return ResponseEntity.ok(result);
    }

        @PostMapping("/login")
        public ResponseEntity<?> login (@RequestBody LoginRequest request){

            try {
                LoginResponse response = authService.loginUser(request); // contains token + role
                return ResponseEntity.ok(response);
            } catch (RuntimeException e) {
                return ResponseEntity
                        .badRequest()
                        .body(new LoginResponse(null, e.getMessage()));
            }
        }
    }
