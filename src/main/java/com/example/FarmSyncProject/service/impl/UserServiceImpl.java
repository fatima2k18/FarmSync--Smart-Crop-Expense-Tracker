package com.example.FarmSyncProject.service.impl;

import com.example.FarmSyncProject.dto.UserResponseDto;
import com.example.FarmSyncProject.model.User;
import com.example.FarmSyncProject.repository.UserRepository;
import com.example.FarmSyncProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
