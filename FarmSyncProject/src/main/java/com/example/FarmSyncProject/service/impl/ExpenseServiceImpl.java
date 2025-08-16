package com.example.FarmSyncProject.service.impl;

import com.example.FarmSyncProject.dto.ExpenseRequestDto;
import com.example.FarmSyncProject.dto.ExpenseResponseDto;
import com.example.FarmSyncProject.dto.MonthlyExpenseDto;
import com.example.FarmSyncProject.model.Crop;
import com.example.FarmSyncProject.model.Expense;
import com.example.FarmSyncProject.model.User;
import com.example.FarmSyncProject.repository.CropRepository;
import com.example.FarmSyncProject.repository.ExpenseRepository;
import com.example.FarmSyncProject.repository.UserRepository;
import com.example.FarmSyncProject.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service

public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CropRepository cropRepository;

    @Override
    public ExpenseResponseDto createExpense(ExpenseRequestDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Crop crop = cropRepository.findById(dto.getCropId())
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        Expense expense = new Expense();
        expense.setCategory(dto.getCategory());
        expense.setAmount(dto.getAmount());
        expense.setUser(user);
        expense.setCrop(crop);
        expense.setNote(dto.getNote() != null ? dto.getNote() : "");
        expense.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());

        Expense saved = expenseRepository.save(expense);

        ExpenseResponseDto res = new ExpenseResponseDto();
        res.setId(saved.getId());
        res.setType(saved.getCategory());
        res.setAmount(saved.getAmount());
        res.setUsername(saved.getUser().getName());
        res.setCropName(saved.getCrop().getName());

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
            dto.setCropName(exp.getCrop().getName()); // ✅ Set crop name here
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteExpense(Long id) {

        expenseRepository.deleteById(id);
    }
    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
    }
    @Override
    public List<MonthlyExpenseDto> getMonthlyExpenseSummary(Long userId) {
        List<Object[]> results = expenseRepository.findMonthlySummaryRaw(userId);
        return results.stream()
                .map(obj -> new MonthlyExpenseDto((String) obj[0], ((Number) obj[1]).doubleValue()))
                .collect(Collectors.toList());

    }
}