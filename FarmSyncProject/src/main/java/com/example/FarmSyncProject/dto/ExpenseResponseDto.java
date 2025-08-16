package com.example.FarmSyncProject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExpenseResponseDto {
    private Long id;
    private String type;
    private Double amount;
    private String username;
    private String cropName; // ✅ Add this

}
