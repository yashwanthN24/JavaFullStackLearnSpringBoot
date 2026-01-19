package com.example.demo.entities;


import com.example.demo.entities.type.BloodGroupType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private  String name;

    private LocalDate birthDate;

    private String email;

    private String gender;

    @Enumerated(value = EnumType.STRING)
    private BloodGroupType bloodGroup;

    @CreationTimestamp
    private LocalDateTime createdAt;


    @OneToOne(cascade = CascadeType.ALL , orphanRemoval = true  , fetch = FetchType.LAZY) // ALL is same as Merge , persist etc
//    orphanRemoval means for parent entity liek this Parent how its should handle child entity when parent sets it to null
    @JoinColumn(name = "patient_insurance_id")
    @ToString.Exclude
    private Insurance insurance; // owning side patient itself can have insurance insurance cant exist indepnently

    @OneToMany(mappedBy = "patient" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
//    @ToString.Exclude
    private List<Appointment> appointments = new ArrayList<>(); // inverse side of one to many relationship , Owing side is always the many side


}
