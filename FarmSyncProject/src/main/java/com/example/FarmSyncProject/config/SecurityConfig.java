package com.example.FarmSyncProject.config;

import com.example.FarmSyncProject.service.impl.CustomerUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {


    private final JwtAuthFilter jwtAuthFilter;
    private final CustomerUserDetailsService userDetailsService;
    public SecurityConfig(JwtAuthFilter jwtAuthFilter, CustomerUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.POST, "/api/crops").hasRole("FARMER")
                        .requestMatchers(HttpMethod.GET, "/api/crops/user/**").hasAnyRole("FARMER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/crops").hasRole("ADMIN")

                        // User-related
                        .requestMatchers("/api/auth/**").permitAll() // public auth endpoints
                        .requestMatchers(HttpMethod.GET, "/api/users/me").hasAnyRole("FARMER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("FARMER", "ADMIN")

                        //Crop endpoints
                        .requestMatchers("/api/crops/**").hasRole("FARMER")
                                // Both FARMER and ADMIN can view crops
                                .requestMatchers(HttpMethod.GET, "/api/crops/**").hasAnyRole("FARMER", "ADMIN")

                                // Only FARMER can update crops
                                .requestMatchers(HttpMethod.PUT, "/api/crops/**").hasRole("FARMER")
                              // Only FARMER can delete crops
                             .requestMatchers(HttpMethod.DELETE, "/api/crops/**").hasRole("FARMER")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
