package com.example.FarmSyncProject.service.impl;

import com.example.FarmSyncProject.dto.CropRequestDto;
import com.example.FarmSyncProject.dto.CropResponseDto;
import com.example.FarmSyncProject.model.Crop;
import com.example.FarmSyncProject.model.User;
import com.example.FarmSyncProject.repository.CropRepository;
import com.example.FarmSyncProject.repository.UserRepository;
import com.example.FarmSyncProject.service.CropService;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service

@RequiredArgsConstructor
public class CropServiceImpl implements CropService {
    private final CropRepository cropRepository;
    @Autowired
    private  UserRepository userRepository;

    @Override
    public CropResponseDto addCrop(CropRequestDto dto, Long userId) {
//        User user = userRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Crop crop = new Crop();
        crop.setName(dto.getName());
        crop.setSeason(dto.getSeason());
        crop.setStartDate(dto.getStartDate());
        crop.setEndDate(dto.getEndDate());
        crop.setUser(user);

        Crop saved = cropRepository.save(crop);
        return mapToDto(saved);
    }

    @Override
    public List<CropResponseDto> getAllCrops() {
        return cropRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CropResponseDto> getCropsByUserId(Long userId) {
        return cropRepository.findByUser_Id(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CropResponseDto updateCrop(Long cropId, CropRequestDto cropRequestDto, Long userId) {
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        // Only owner or admin can update
        if (!crop.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied: You can update only your crops.");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        crop.setName(cropRequestDto.getName());
        crop.setSeason(cropRequestDto.getSeason());
        crop.setStartDate(cropRequestDto.getStartDate());
        crop.setEndDate(cropRequestDto.getEndDate());
        crop.setUser(user); // ✅ This must be set!

        Crop updated = cropRepository.save(crop);
        return mapToDto(updated);
    }

    @Override
    public CropResponseDto deleteCrop(Long cropId, Long userId) {
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        // Only owner or admin can delete
        if (!crop.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied: You can delete only your crops.");
        }

        cropRepository.delete(crop);
        return mapToDto(crop); // Optional: returning deleted crop's details

    }

    private CropResponseDto mapToDto(Crop crop) {
        CropResponseDto dto = new CropResponseDto();
        dto.setId(crop.getId());
        dto.setName(crop.getName());
        dto.setSeason(crop.getSeason());
        dto.setStartDate(crop.getStartDate());
        dto.setEndDate(crop.getEndDate());
        dto.setFarmerName(crop.getUser().getName());
        return dto;
    }

}
