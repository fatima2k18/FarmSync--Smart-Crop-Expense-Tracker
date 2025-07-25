package com.example.FarmSyncProject.controller;

import com.example.FarmSyncProject.dto.ActivityRequestDto;
import com.example.FarmSyncProject.dto.ActivityResponseDto;
import com.example.FarmSyncProject.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponseDto> create(@RequestBody ActivityRequestDto dto) {
        return new ResponseEntity<>(activityService.createActivity(dto), HttpStatus.CREATED);
    }

    @GetMapping("/crop/{cropId}")
    public ResponseEntity<List<ActivityResponseDto>> getByCrop(@PathVariable Long cropId) {
        return ResponseEntity.ok(activityService.getByCropId(cropId));
    }
}
