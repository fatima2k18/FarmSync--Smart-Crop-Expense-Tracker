package com.example.FarmSyncProject.controller;

import com.example.FarmSyncProject.dto.ActivityRequestDto;
import com.example.FarmSyncProject.dto.ActivityResponseDto;
import com.example.FarmSyncProject.service.ActivityService;
import com.example.FarmSyncProject.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.FarmSyncProject.service.impl.UserDetailsImpl;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @PreAuthorize("hasAnyRole('FARMER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ActivityResponseDto> create(@RequestBody ActivityRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();  // ✅ This will now work
        ActivityResponseDto response = activityService.createActivity(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    //fetch all activities associated with a specific crop
    @PreAuthorize("hasRole('FARMER') or hasRole('ADMIN')")
    @GetMapping("/crop/{cropId}")
    public ResponseEntity<List<ActivityResponseDto>> getByCrop(@PathVariable Long cropId) {
        return ResponseEntity.ok(activityService.getByCropId(cropId));
    }
    // Get All Activities (Admin Only)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ActivityResponseDto>> getAllActivities() {
        return ResponseEntity.ok(activityService.getAllActivities());
    }
//    @PreAuthorize("hasRole('FARMER') or hasRole('ADMIN')")
//    @GetMapping("/{activityId}")
//    public ResponseEntity<ActivityResponseDto> getActivity(@PathVariable Long activityId) {
//        return ResponseEntity.ok(activityService.getActivityById(activityId));
//    }
//@PreAuthorize("hasRole('FARMER') or hasRole('ADMIN')")
//@DeleteMapping("/{activityId}")
//public ResponseEntity<Void> deleteActivity(@PathVariable Long activityId) {
//    activityService.deleteActivity(activityId);
//    return ResponseEntity.noContent().build();
//}
}
