package com.example.FarmSyncProject.service.impl;

import com.example.FarmSyncProject.dto.ActivityRequestDto;
import com.example.FarmSyncProject.dto.ActivityResponseDto;
import com.example.FarmSyncProject.model.Activity;
import com.example.FarmSyncProject.model.Crop;
import com.example.FarmSyncProject.repository.ActivityRepository;
import com.example.FarmSyncProject.repository.CropRepository;
import com.example.FarmSyncProject.service.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CropRepository cropRepository;

    @Override
    public ActivityResponseDto createActivity(ActivityRequestDto dto) {
        if (dto.getCropId() == null) {
            throw new IllegalArgumentException("Crop ID must not be null");
        }
        Crop crop = cropRepository.findById(dto.getCropId())
                .orElseThrow(() -> new IllegalArgumentException("Crop with ID " + dto.getCropId() + " not found"));

        Activity act = new Activity();
        act.setType(dto.getType());
        act.setDate(dto.getDate());
        act.setCrop(crop);


        Activity saved = activityRepository.save(act);
        return convertToDto(saved);
    }
    @Override
    public List<ActivityResponseDto> getByCropId(Long cropId) {
        return activityRepository.findByCropId(cropId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<ActivityResponseDto> getAllActivities() {
        return activityRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ActivityResponseDto convertToDto(Activity act) {
        ActivityResponseDto dto = new ActivityResponseDto();
        dto.setId(act.getId());
        dto.setType(act.getType());
        dto.setDate(act.getDate());
        dto.setCropName(act.getCrop().getName());
        return dto;
    }
}
