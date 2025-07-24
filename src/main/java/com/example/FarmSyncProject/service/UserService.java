package com.example.FarmSyncProject.service;

import com.example.FarmSyncProject.dto.UserResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public interface UserService {
    UserResponseDto getUserById(Long id);
    List<UserResponseDto> getAllUsers(); // ✅ Add this

}


