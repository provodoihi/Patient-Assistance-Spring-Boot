package com.example.demo.repository;

import com.example.demo.models.Appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
		
    List<Appointment> findByClinicId(long clinicId);

    List<Appointment> findByPatientId(long patientId);
    
    List<Appointment> findByNameOfClinic(String nameOfClinic);
    
	@Query(value = "select * from appointments where status = 1;",nativeQuery = true)
    List<Appointment> findScheduledWithEndBeforeDate(@Param("now") OffsetDateTime offsetDateTime);

}
