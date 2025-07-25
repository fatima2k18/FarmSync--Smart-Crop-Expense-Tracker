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
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;
    public AuthServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));

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

        String token = jwtUtil.generateToken(user.getEmail()); // using email as subject
        return new LoginResponse(token, user.getRole().name());
    }
}
