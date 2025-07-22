package com.example.FarmSyncProject.service;

import com.example.FarmSyncProject.dto.UserResponseDto;

public interface UserService {
    UserResponseDto getUserById(Long id);
}
