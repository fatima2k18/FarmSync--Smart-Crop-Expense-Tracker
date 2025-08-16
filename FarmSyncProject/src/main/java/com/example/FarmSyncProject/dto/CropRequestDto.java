package com.example.FarmSyncProject.dto;

import lombok.*;
import java.time.LocalDate;@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CropRequestDto {
    private String name;
    private String season;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;
}
