package com.example.FarmSyncProject.service;

import com.example.FarmSyncProject.dto.ExpenseRequestDto;
import com.example.FarmSyncProject.dto.ExpenseResponseDto;
import com.example.FarmSyncProject.dto.MonthlyExpenseDto;
import com.example.FarmSyncProject.model.Expense;

import java.util.List;

public interface ExpenseService {
    ExpenseResponseDto createExpense(ExpenseRequestDto dto,Long userId);
    List<ExpenseResponseDto> getExpensesByUserId(Long userId);
    void deleteExpense(Long id);
    Expense getExpenseById(Long id);
    List<MonthlyExpenseDto> getMonthlyExpenseSummary(Long userId);
}
