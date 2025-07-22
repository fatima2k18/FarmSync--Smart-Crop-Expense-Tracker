package com.example.FarmSyncProject.service.impl;

import com.example.FarmSyncProject.dto.ExpenseRequestDto;
import com.example.FarmSyncProject.dto.ExpenseResponseDto;
import com.example.FarmSyncProject.model.Expense;
import com.example.FarmSyncProject.model.User;
import com.example.FarmSyncProject.repository.ExpenseRepository;
import com.example.FarmSyncProject.repository.UserRepository;
import com.example.FarmSyncProject.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service

public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ExpenseResponseDto createExpense(ExpenseRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense();
        expense.setCategory(dto.getType());
        expense.setAmount(dto.getAmount());
        expense.setUser(user);

        Expense saved = expenseRepository.save(expense);

        ExpenseResponseDto res = new ExpenseResponseDto();
        res.setId(saved.getId());
        res.setType(saved.getCategory());
        res.setAmount(saved.getAmount());
        res.setUsername(user.getName());

        return res;
    }

    @Override
    public List<ExpenseResponseDto> getExpensesByUserId(Long userId) {
        List<Expense> list = expenseRepository.findByUserId(userId);

        return list.stream().map(exp -> {
            ExpenseResponseDto dto = new ExpenseResponseDto();
            dto.setId(exp.getId());
            dto.setType(exp.getCategory());
            dto.setAmount(exp.getAmount());
            dto.setUsername(exp.getUser().getName());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}