package com.example.FarmSyncProject.controller;

import com.example.FarmSyncProject.dto.UserResponseDto;
import com.example.FarmSyncProject.enums.Role;
import com.example.FarmSyncProject.model.User;
import com.example.FarmSyncProject.repository.UserRepository;
import com.example.FarmSyncProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    //For any authenticated user (Farmer/Admin)
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyProfile(Authentication authentication) {
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return ResponseEntity.ok(userService.getUserById(currentUser.getId()));
    }
    //For Admin only
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(Authentication authentication) {
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (currentUser.getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(userService.getAllUsers());
    }

   // For Admin OR Farmer accessing their own profile

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id, Authentication authentication) {
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (currentUser.getRole() != Role.ADMIN && !currentUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(userService.getUserById(id));
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id, Authentication authentication) {
//        //return ResponseEntity.ok(userService.getUserById(id));
//        String currentUsername = authentication.getName();  // From JWT token
//
//        User currentUser = userRepository.findByEmail(currentUsername)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        // If current user is ADMIN → allow access
//        if (currentUser.getRole() == Role.ADMIN) {
//            return ResponseEntity.ok(userService.getUserById(id));
//        }
//
//        // If current user is not ADMIN → allow only if accessing their own profile
//        if (!currentUser.getId().equals(id)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        return ResponseEntity.ok(userService.getUserById(id));
//    }
//    @GetMapping
//    public ResponseEntity<List<UserResponseDto>> getAllUsers(Authentication authentication) {
//        String currentUsername = authentication.getName();
//
//        User currentUser = userRepository.findByEmail(currentUsername)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        if (currentUser.getRole() != Role.ADMIN) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
//    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
}
