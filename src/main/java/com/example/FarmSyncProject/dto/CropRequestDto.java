package com.example.FarmSyncProject.dto;

import lombok.*;
import java.time.LocalDate;@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CropRequestDto {
    private String name;
    private String season;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;
}
