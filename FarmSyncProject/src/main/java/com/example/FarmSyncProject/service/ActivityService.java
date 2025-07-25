package com.example.FarmSyncProject.service;

import com.example.FarmSyncProject.dto.ActivityRequestDto;
import com.example.FarmSyncProject.dto.ActivityResponseDto;

import java.util.List;

public interface ActivityService {
    ActivityResponseDto createActivity(ActivityRequestDto dto);
    List<ActivityResponseDto> getByCropId(Long cropId);
}
