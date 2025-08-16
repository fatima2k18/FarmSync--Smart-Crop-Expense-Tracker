package com.example.FarmSyncProject.config;

import com.example.FarmSyncProject.service.impl.CustomerUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomerUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // ✅ Extract JWT token from header
        String token = resolveToken(request);

        try {
            if (token != null) {
                // ✅ Extract username from token
                String username = jwtUtil.extractUsername(token);

                // ✅ Authenticate only if not already authenticated
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // ✅ Validate token with username
                    if (jwtUtil.validateToken(token, username)) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                        // ✅ Create Spring Security Authentication object
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());

                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // ✅ Set authentication to security context
                        SecurityContextHolder.getContext().setAuthentication(auth);

                        // ✅ Optional: log success
                        System.out.println("JWT validated for user: " + username);
                    } else {
                        System.out.println("Invalid JWT token for user: " + username);
                    }
                }
            }
        } catch (Exception e) {
            // ✅ Log authentication failure
            System.err.println("JWT authentication failed: " + e.getMessage());
        }

        // Continue with next filters
        filterChain.doFilter(request, response);
    }

    /**
     * Helper method to extract token from Authorization header
     */
    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
