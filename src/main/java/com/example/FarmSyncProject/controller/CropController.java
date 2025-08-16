package com.example.FarmSyncProject.controller;

import com.example.FarmSyncProject.dto.CropRequestDto;
import com.example.FarmSyncProject.dto.CropResponseDto;
import com.example.FarmSyncProject.service.CropService;
import com.example.FarmSyncProject.service.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController @CrossOrigin(origins = "http://localhost:5174", allowCredentials = "true")
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {
   @Autowired
     CropService cropService;
    // In CropController
    @PreAuthorize("hasRole('FARMER')")
    @PostMapping
    public CropResponseDto addCrop(@RequestBody CropRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        //return cropService.addCrop(dto);
        Long userId = userDetails.getId(); // ✅ Secure way
        return cropService.addCrop(dto, userId);
    }

    //  Only ADMIN can view all crops
    //change
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<CropResponseDto> getAllCrops() {

        return cropService.getAllCrops();//All crops
    }
    //add

    //  Only FARMER can view their own crops
    @PreAuthorize("hasRole('FARMER')")
    @GetMapping("/my-crops")
    public List<CropResponseDto> getMyCrops(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId(); // From JWT
        return cropService.getCropsByUserId(userId);
    }
    //  FARMER and ADMIN can update, but FARMER can update only their own crop (check in service)
    @PreAuthorize("hasAnyRole('FARMER', 'ADMIN')")
    @PutMapping("/{id}")
    public CropResponseDto updateCrop(@PathVariable Long id,
                                      @RequestBody CropRequestDto dto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId(); //  Extract user ID
        return cropService.updateCrop(id, dto, userId); // ✅ pass userId only
    }
    // 🔐 FARMER and ADMIN can delete; FARMER should only delete their own crop (check in service)
    @PreAuthorize("hasAnyRole('FARMER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteCrop(@PathVariable Long id,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId(); // ✅ extract ID
        cropService.deleteCrop(id, userId);
        return "Crop deleted successfully.";
    }

}
