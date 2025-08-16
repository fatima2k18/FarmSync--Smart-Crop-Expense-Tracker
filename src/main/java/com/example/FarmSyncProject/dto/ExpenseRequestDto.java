package com.example.FarmSyncProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseRequestDto {
   // private String type;
    private String category;     // ✅ Required if you're using .getCategory()
    private Double amount;
    //private Long userId;
    private Long cropId; // ✅ Add this field

    private String note;
    private LocalDate date;


}
