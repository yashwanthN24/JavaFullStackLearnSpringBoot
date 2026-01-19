package com.example.demo.repositories;

import com.example.demo.dtos.projectionDtos.BloodGroupStats;
import com.example.demo.dtos.projectionDtos.CPatient;
import com.example.demo.dtos.projectionDtos.IPatient;
import com.example.demo.dtos.projectionDtos.TPatient;
import com.example.demo.entities.Patient;
import com.example.demo.entities.type.BloodGroupType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface PatientRepository extends JpaRepository<Patient , Long> {
    List<Patient> findByEmailContaining(String email);

    @Query("select p.id as id , p.name as name , p.email as email from Patient p")
    List<IPatient> getAllPatientInfo();

    @Query("select  new com.example.demo.dtos.projectionDtos.CPatient(p.id , p.name) from Patient p ")
    List<CPatient> getAllCPatientInfo();

    @Query("select new com.example.demo.dtos.projectionDtos.BloodGroupStats(p.bloodGroup, " + " count(distinct  p.id)) as countId from Patient  p group by  p.bloodGroup order by  count(distinct  p.id) DESC  "   )
    List<BloodGroupStats> getBloodGroupStats();

    @Transactional
    @Modifying
    @Query("update  Patient  p  set p.name = :name where  p.id=:id")
    int updatePatientNameById(@Param("name") String name , @Param("id") Long id);

//    @Query("select p.name , p.bloodGroup from Patient  p where p.name like :name or p.bloodGroup = :bloodGroup")
//    List<TPatient> getPatientByNameOrBloodGroup(String name , BloodGroupType bloodGroup);


//    above one also works but below one is better for production
    @Query("select new com.example.demo.dtos.projectionDtos.TPatient(p.name, p.bloodGroup) " +
            "from Patient p where lower(p.name) like lower(concat('%', :name, '%')) " +
            "or p.bloodGroup = :bloodGroup")
    List<TPatient> getPatientByNameOrBloodGroup(@Param("name") String name,
                                                @Param("bloodGroup") BloodGroupType bloodGroup);

    // This FAILS (order mismatch)
    @Query("select p.bloodGroup, p.name from Patient p")  // bloodGroup FIRST
    List<TPatient> getPatients();  // TPatient expects name FIRST

    // This WORKS (explicit constructor wins)
    @Query("select new com.example.demo.dtos.projectionDtos.TPatient(p.name, p.bloodGroup) from Patient p")
    List<TPatient> getPatients2();  // Order irrelevant

    @Query("""
        select  p  from Patient p  left join fetch p.appointments
""")
    List<Patient> getAllPatientsWithAppointment();
}

