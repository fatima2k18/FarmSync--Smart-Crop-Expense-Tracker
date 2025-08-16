package com.example.FarmSyncProject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CropResponseDto {

   private Long id;
    private String name;
    private String season;
    private LocalDate startDate;
    private LocalDate endDate;
    private String farmerName;

}
