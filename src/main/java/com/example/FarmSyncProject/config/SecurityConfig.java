package com.example.FarmSyncProject.config;

import com.example.FarmSyncProject.service.impl.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
@EnableMethodSecurity // ✅ Add this here
@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomerUserDetailsService userDetailsService;
    @Autowired
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
               .cors(cors -> cors.configurationSource(corsConfigurationSource())) // <-- ADD THIS
              //  .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                                // Swagger & Public Docs
                                // 🔓 Public auth & Swagger endpoints
                                .requestMatchers("/api/auth/**").permitAll()

                                // Swagger
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/swagger-resources/**",
                                        "/webjars/**",
                                        "/error"
                                ).permitAll()

                                // CORS preflight
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

//                        .requestMatchers(HttpMethod.POST, "/api/crops").hasRole("FARMER")
//                        .requestMatchers(HttpMethod.GET, "/api/crops/user/**").hasAnyRole("FARMER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/api/crops").hasRole("ADMIN")

                        // User-related
                        .requestMatchers("/api/auth/**").permitAll() // public auth endpoints
                        .requestMatchers(HttpMethod.GET, "/api/users/me").hasAnyRole("FARMER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("FARMER", "ADMIN")



                        // Crop
                              //  .requestMatchers(HttpMethod.POST, "/api/crops").hasAuthority("FARMER")
                       .requestMatchers(HttpMethod.POST, "/api/crops").hasRole("FARMER")
                             //    .requestMatchers(HttpMethod.POST, "/api/crops").permitAll()
                       .requestMatchers(HttpMethod.GET, "/api/crops").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/crops/my-crops").hasRole("FARMER")
                        .requestMatchers(HttpMethod.PUT, "/api/crops/**").hasRole("FARMER")
                        .requestMatchers(HttpMethod.DELETE, "/api/crops/**").hasRole("FARMER")

                                //Activity
                                .requestMatchers(HttpMethod.POST, "/api/activities").hasAnyRole("FARMER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/activities/crop/**").hasAnyRole("FARMER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/activities").hasRole("ADMIN")

                                 //Expenses
                                .requestMatchers(HttpMethod.POST, "/api/expenses").hasAnyRole("FARMER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/expenses/user/**").hasAnyRole("FARMER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/expenses/**").hasAnyRole("FARMER", "ADMIN")

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
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
