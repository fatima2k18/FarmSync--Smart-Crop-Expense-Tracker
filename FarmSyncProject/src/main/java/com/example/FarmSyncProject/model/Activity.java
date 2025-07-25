package com.example.FarmSyncProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    private String type; // Watering, Sowing, Harvest, etc.

    private String description;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "crop_id")
    private Crop crop;
    
}
