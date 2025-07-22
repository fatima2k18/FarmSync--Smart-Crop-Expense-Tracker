package com.example.FarmSyncProject.controller;

import com.example.FarmSyncProject.dto.ExpenseRequestDto;
import com.example.FarmSyncProject.dto.ExpenseResponseDto;
import com.example.FarmSyncProject.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponseDto> create(@RequestBody ExpenseRequestDto dto) {
        return new ResponseEntity<>(expenseService.createExpense(dto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseResponseDto>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(expenseService.getExpensesByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok("Expense deleted");
    }
}
