package com.example.FarmSyncProject.controller;

import com.example.FarmSyncProject.config.JwtUtil;
import com.example.FarmSyncProject.dto.ExpenseRequestDto;
import com.example.FarmSyncProject.dto.ExpenseResponseDto;
import com.example.FarmSyncProject.model.Expense;
import com.example.FarmSyncProject.service.ExpenseService;
import com.example.FarmSyncProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @PreAuthorize("hasRole('FARMER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ExpenseResponseDto> create(@RequestBody ExpenseRequestDto dto,@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.extractUserId(token); // ✅ extract user ID from JWT
        ExpenseResponseDto response = expenseService.createExpense(dto, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
    @PreAuthorize("hasRole('FARMER') or hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseResponseDto>> getByUser(@PathVariable Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName(); // usually the username/email
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            Long currentUserId = userService.getUserIdByUsername(currentUsername); // You must implement this method
            if (!userId.equals(currentUserId)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        return ResponseEntity.ok(expenseService.getExpensesByUserId(userId));
    }
    @PreAuthorize("hasRole('FARMER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Long currentUserId = userService.getUserIdByUsername(currentUsername);
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        Expense expense = expenseService.getExpenseById(id); // You’ll need to implement this

        if (expense == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found");
        }

        if (!isAdmin && !expense.getUser().getId().equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot delete others' expenses");
        }

        expenseService.deleteExpense(id);
        return ResponseEntity.ok("Expense deleted");
    }

}
