package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;


    @ManyToOne()
    @JoinColumn(name = "patient_id" , nullable = false)
    // nullable flase makes sure that patientId must be there to create an new appointment row in this table
//    @ToString.Exclude
    private Patient patient; // Many Entityname (Appointments) To One (Patient)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
//    @ToString.Exclude
    private Doctor doctor;

}
