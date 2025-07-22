package com.example.FarmSyncProject.service.impl;

import com.example.FarmSyncProject.dto.ActivityRequestDto;
import com.example.FarmSyncProject.dto.ActivityResponseDto;
import com.example.FarmSyncProject.model.Activity;
import com.example.FarmSyncProject.model.Crop;
import com.example.FarmSyncProject.repository.ActivityRepository;
import com.example.FarmSyncProject.repository.CropRepository;
import com.example.FarmSyncProject.service.ActivityService;
import lombok.Setter;
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
        Crop crop = cropRepository.findById(dto.getCropId())
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        Activity act = new Activity();
        act.setType(dto.getType());
        act.setDate(dto.getDate());
        act.setCrop(crop);

        Activity saved = activityRepository.save(act);

        ActivityResponseDto res = new ActivityResponseDto();
        res.setId(saved.getId());
        res.setType(saved.getType());
        res.setDate(saved.getDate());
        res.setCropName(crop.getName());

        return res;
    }

    @Override
    public List<ActivityResponseDto> getByCropId(Long cropId) {
        return activityRepository.findByCropId(cropId)
                .stream()
                .map(act -> {
                    ActivityResponseDto dto = new ActivityResponseDto();
                    dto.setId(act.getId());
                    dto.setType(act.getType());
                    dto.setDate(act.getDate());
                    dto.setCropName(act.getCrop().getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
