package com.example.FarmSyncProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseRequestDto {
    private String type;
    private Double amount;
    private Long userId;
}
