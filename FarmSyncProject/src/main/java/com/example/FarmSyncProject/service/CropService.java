package com.example.FarmSyncProject.service;

import com.example.FarmSyncProject.dto.CropRequestDto;
import com.example.FarmSyncProject.dto.CropResponseDto;
import java.util.List;
public interface CropService {
    // To add a crop
    CropResponseDto addCrop(CropRequestDto cropRequestDto);

    // To get all crops (for admin)
    List<CropResponseDto> getAllCrops();

    // To get crops of a specific user (farmer)
    List<CropResponseDto> getCropsByUserId(Long userId);
}
