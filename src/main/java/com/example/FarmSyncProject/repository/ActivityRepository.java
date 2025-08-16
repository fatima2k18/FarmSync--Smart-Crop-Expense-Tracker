package com.example.FarmSyncProject.repository;

import com.example.FarmSyncProject.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByCropId(Long cropId);

}
