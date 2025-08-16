package com.example.FarmSyncProject.service.impl;

import com.example.FarmSyncProject.model.User;
import com.example.FarmSyncProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // UserDetailsImpl.build(user); // This wraps your User entity
        UserDetails userDetails = UserDetailsImpl.build(user);
        // ✅ Log here
        System.out.println("Authenticated user: " + userDetails.getUsername());
        System.out.println("Authorities: " + userDetails.getAuthorities());

        return userDetails;
    }

}

