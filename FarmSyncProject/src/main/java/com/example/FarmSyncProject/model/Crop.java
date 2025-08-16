package com.example.FarmSyncProject.model;


import jakarta.persistence.*;
import lombok.*;

import com.example.FarmSyncProject.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="crops")

public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String season; // e.g., Rabi, Kharif, Zaid

    private LocalDate startDate;


    private LocalDate endDate;


    // Each crop belongs to a user (farmer)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Crop> crops = new ArrayList<>();

}




