package com.example.demo;

import com.example.demo.dtos.projectionDtos.BloodGroupStats;
import com.example.demo.dtos.projectionDtos.CPatient;
import com.example.demo.dtos.projectionDtos.IPatient;
import com.example.demo.dtos.projectionDtos.TPatient;
import com.example.demo.entities.Patient;
import com.example.demo.entities.type.BloodGroupType;
import com.example.demo.repositories.PatientRepository;
import jakarta.websocket.OnClose;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testPatient(){
        List<Patient> allPatients =  this.patientRepository.findAll();

//        System.out.println(allPatients);
        for(Patient p : allPatients){
            System.out.println(p);
        }
    }

    @Test
    public void getIPatient(){
        List<IPatient> allpatients = this.patientRepository.getAllPatientInfo();

//        System.out.println(allpatients);
//        allpatients.forEach(System.out::println);
        for(IPatient p : allpatients){
            System.out.println("ID: " + p.getId() + ", Name: " + p.getName() + ", Email: " + p.getEmail());
        }

        List<CPatient> allpatients2 = this.patientRepository.getAllCPatientInfo();
        allpatients2.forEach(System.out::println);

        List<BloodGroupStats> allBloodGroupStats = this.patientRepository.getBloodGroupStats();
        allBloodGroupStats.forEach(System.out::println);

//        int rowsAffected = this.patientRepository.updatePatientNameById("yashwanth" , 1L);
//        System.out.println(rowsAffected);

        List<TPatient> all = this.patientRepository.getPatientByNameOrBloodGroup("yashwanth" , BloodGroupType.A_POSITIVE);
        for(var a : all){
            System.out.println( a);
        }

//        List<TPatient> all2 = this.patientRepository.getPatients();
        List<TPatient> all2 = this.patientRepository.getPatients2();
        all2.forEach(System.out::println);
    }


}
