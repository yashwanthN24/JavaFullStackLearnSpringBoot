package com.example.demo;

import com.example.demo.entities.Appointment;
import com.example.demo.entities.Insurance;
import com.example.demo.services.AppointmentService;
import com.example.demo.services.InsuranceService;
import com.example.demo.services.PatientService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public  void testAssignInsuranceToPatient(){
        Insurance insurance = Insurance.builder()
                .provider("HDFC Ergo")
                .policyNumber("HDFC_236")
                .validUntil(LocalDate.of(2030, 1, 1))
                .build();

        var updatedInsurance = insuranceService.assignInsuranceToPatient(insurance , 1L);

        System.out.println(updatedInsurance);

//        patientService.deletePatient(2L);

        var patient = insuranceService.removeInsuranceToPatient(1L);
        System.out.println(patient);
    }


    @Test
    public  void testCreateAppointment(){
        Appointment appointment = Appointment.builder()
                .appointmentTime(LocalDateTime.of(2025 , 11 , 1 , 14 , 0 , 0))
                .reason("Cancer")
                .build();
        var  newAppointment = appointmentService.createNewAppointment(appointment , 1L , 2L);
        System.out.println(newAppointment);

        patientService.deletePatient(1L);
    }
}
