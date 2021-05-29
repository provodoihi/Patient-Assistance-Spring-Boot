package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.example.demo.models.HospitalClinicInfo;

public interface HospitalClinicRepository extends JpaRepositoryImplementation<HospitalClinicInfo, Long> {
	List<HospitalClinicInfo> findBySpeciality(String speciality);
	
	@Query("SELECT s FROM HospitalClinicInfo s WHERE s.name LIKE %?1%"
			+ "OR s.address LIKE %?1%"
			+ "OR s.speciality LIKE %?1%")
	List<HospitalClinicInfo> search(String keyword);
}
