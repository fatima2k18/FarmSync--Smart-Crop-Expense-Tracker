package com.example.FarmSyncProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="Expense")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category; // Fertilizer, Seed, Labor, etc.

    private Double amount;

    private LocalDate date;


    @Column(name = "note") // ✅ Add this
    private String note;

    @ManyToOne
    @JoinColumn(name = "crop_id")
    private Crop crop;

    @ManyToOne
    @JoinColumn(name = "user_id")  // ✅ Add this
    private User user;
}
