package com.example.FarmSyncProject.dto;

import com.example.FarmSyncProject.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String role;
    public static UserResponseDto mapToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name()); // Convert enum to String
        return dto;
    }

    //dto.setRole(user.getRole().name());  // Converts enum to String
}
