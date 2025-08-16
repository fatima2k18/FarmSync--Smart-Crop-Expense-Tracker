package com.example.FarmSyncProject.service;

import com.example.FarmSyncProject.dto.CropRequestDto;
import com.example.FarmSyncProject.dto.CropResponseDto;
import java.util.List;
public interface CropService {
    // To add a crop
    CropResponseDto addCrop(CropRequestDto cropRequestDto, Long userId);

    // To get all crops (for admin)
    List<CropResponseDto> getAllCrops();

    // To get crops of a specific user (farmer)
    List<CropResponseDto> getCropsByUserId(Long userId);
    // To update a crop (Farmer or Admin)

    CropResponseDto updateCrop(Long cropId, CropRequestDto cropRequestDto, Long userId); // userId to verify ownership
    CropResponseDto deleteCrop(Long cropId, Long userId); // userId to verify access
}
