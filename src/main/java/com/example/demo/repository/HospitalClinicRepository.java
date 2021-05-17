package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.example.demo.models.HospitalClinicInfo;

public interface HospitalClinicRepository extends JpaRepositoryImplementation<HospitalClinicInfo, Long> {
	List<HospitalClinicInfo> findHosclinInfoByDistrict(String district);
	List<HospitalClinicInfo> findHosclinInfoByCity(String city);
	List<HospitalClinicInfo> findHosclinInfoBySpeciality(String speciality);
}
