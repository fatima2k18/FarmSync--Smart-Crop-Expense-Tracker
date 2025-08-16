package com.example.FarmSyncProject.controller;

import com.example.FarmSyncProject.dto.MonthlyExpenseDto;
import com.example.FarmSyncProject.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/monthly-summary/{userId}")
    @PreAuthorize("hasRole('FARMER') or hasRole('ADMIN')")
    public ResponseEntity<List<MonthlyExpenseDto>> getMonthlySummary(@PathVariable Long userId) {
        List<MonthlyExpenseDto> summary = expenseService.getMonthlyExpenseSummary(userId);
        return ResponseEntity.ok(summary);
    }




}
