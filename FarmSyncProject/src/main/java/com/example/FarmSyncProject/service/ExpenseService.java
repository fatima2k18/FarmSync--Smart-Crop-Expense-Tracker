package com.example.FarmSyncProject.service;

import com.example.FarmSyncProject.dto.ExpenseRequestDto;
import com.example.FarmSyncProject.dto.ExpenseResponseDto;

import java.util.List;

public interface ExpenseService {
    ExpenseResponseDto createExpense(ExpenseRequestDto dto);
    List<ExpenseResponseDto> getExpensesByUserId(Long userId);
    void deleteExpense(Long id);
}
