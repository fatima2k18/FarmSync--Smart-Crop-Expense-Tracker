package com.example.FarmSyncProject.controller;

import com.example.FarmSyncProject.dto.CropRequestDto;
import com.example.FarmSyncProject.dto.CropResponseDto;
import com.example.FarmSyncProject.service.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {
   @Autowired
     CropService cropService;
    // In CropController
    @PreAuthorize("hasRole('FARMER')")
    @PostMapping
    public CropResponseDto addCrop(@RequestBody CropRequestDto dto) {

        return cropService.addCrop(dto);
    }
    @PreAuthorize("hasAnyRole('FARMER', 'ADMIN')")
    @GetMapping
    public List<CropResponseDto> getAllCrops() {

        return cropService.getAllCrops();
    }
    @PreAuthorize("hasAnyRole('FARMER', 'ADMIN')")

    @GetMapping("/user/{userId}")
    public List<CropResponseDto> getCropsByUser(@PathVariable Long userId) {
        return cropService.getCropsByUserId(userId);
    }
}
