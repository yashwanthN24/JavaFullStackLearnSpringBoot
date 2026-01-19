package com.example.demo.services;

import com.example.demo.entities.Insurance;
import com.example.demo.entities.Patient;
import com.example.demo.repositories.InsuranceRepository;
import com.example.demo.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    private  final PatientRepository patientRepository;



    @Transactional
    public Insurance assignInsuranceToPatient(Insurance insurance , Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        patient.setInsurance(insurance);

        insurance.setPatient(patient); // optional

        return insurance;
    }

    @Transactional
    public Insurance updateInsuranceToPatient(Insurance insurance , Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        patient.setInsurance(insurance);

        insurance.setPatient(patient); // optional

        return insurance;
    }

    @Transactional
    public Patient removeInsuranceToPatient(Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        patient.setInsurance(null);


        return patient;
    }



}
