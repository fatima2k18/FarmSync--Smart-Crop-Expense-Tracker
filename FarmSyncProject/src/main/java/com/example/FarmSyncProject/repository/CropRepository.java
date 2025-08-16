package com.example.FarmSyncProject.repository;

import com.example.FarmSyncProject.model.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CropRepository extends JpaRepository<Crop, Long>{
   // List<Crop> findByUserId(Long userId); // Get crops by farmer
    List<Crop> findByUser_Id(Long userId);
}
