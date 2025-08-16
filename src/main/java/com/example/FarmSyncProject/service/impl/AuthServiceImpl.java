package com.example.FarmSyncProject.service.impl;
import com.example.FarmSyncProject.config.JwtUtil;
import com.example.FarmSyncProject.dto.LoginRequest;
import com.example.FarmSyncProject.dto.LoginResponse;
import com.example.FarmSyncProject.dto.RegisterRequest;
import com.example.FarmSyncProject.enums.Role;
import com.example.FarmSyncProject.model.User;
import com.example.FarmSyncProject.repository.UserRepository;
import com.example.FarmSyncProject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager; // optional for advanced auth

    @Override
    public String registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        // ✅ Validate role field
        String roleInput = request.getRole();
        if (roleInput == null || roleInput.isBlank()) {
            throw new IllegalArgumentException("Role must be provided.");
        }

        Role role;
        try {
            role = Role.valueOf(roleInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + roleInput);
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        //user.setRole(Role.valueOf(request.getRole()));
        user.setRole(role);            // ✅ store for display or simplicity
//        user.setRoles(Set.of(role));  // ✅ Spring Security uses this for hasRole() checks
//
//        user.setRoles(Collections.singleton(role)); // ✅ Works in Java 8


        userRepository.save(user);
        return "User registered successfully!";
    }
    @Override
    public LoginResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));


        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user); // using email as subject
        // ✅ Extract first role from the roles set (if you allow only one role per user)
        String roleName = user.getRole() != null ? user.getRole().name() : "NO_ROLE";
        return new LoginResponse(token, roleName);
    }
}
