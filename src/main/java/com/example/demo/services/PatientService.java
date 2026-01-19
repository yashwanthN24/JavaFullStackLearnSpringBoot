package com.example.demo.services;

import com.example.demo.entities.Patient;
import com.example.demo.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service

public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void testPatientRepository(){
        Patient p1 = patientRepository.findById(1L).orElseThrow();
        Patient p2 = patientRepository.findById(1L).orElseThrow();


        System.out.println(p1);
        System.out.println(p2);

        System.out.println(p1==p2);
    }

    @Transactional
    public void testPatientRepository2(){
        Patient p1 = patientRepository.findById(1L).orElseThrow();
        Patient p2 = patientRepository.findById(1L).orElseThrow();


        System.out.println(p1);
        System.out.println(p2);

        System.out.println(p1==p2);
    }

    @Transactional
    public void deletePatient(Long id){
        patientRepository.findById(id).orElseThrow();
        patientRepository.deleteById(id);
    }


}
