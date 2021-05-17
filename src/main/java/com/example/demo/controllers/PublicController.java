package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.HospitalClinicInfo;
import com.example.demo.repository.HospitalClinicRepository;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/public")
public class PublicController {
	
	@Autowired
	private HospitalClinicRepository hospitalClinicRepository;
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content";
	}
	
	@GetMapping("/hospitalclinic")
	public ResponseEntity<List<HospitalClinicInfo>> getAllHospitalClinic(){
		try {
			List<HospitalClinicInfo> hospiclins = new ArrayList<HospitalClinicInfo>();
			hospitalClinicRepository.findAll().forEach(hospiclins::add);
			if (hospiclins.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(hospiclins, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping("/hospitalclinic/findDistrict")
	public ResponseEntity<List<HospitalClinicInfo>> findHospitalClinicByDistrict(@RequestParam("district") String district){
		try {
			List<HospitalClinicInfo> hosclinic = new ArrayList<HospitalClinicInfo>();
			if (district == null) {
				hospitalClinicRepository.findAll().forEach(hosclinic::add);
			}
			else {
				hospitalClinicRepository.findHosclinInfoByDistrict(district);
			}
			
			if (hosclinic.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(hosclinic, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/hospitalclinic/findCity")
	public ResponseEntity<List<HospitalClinicInfo>> findHospitalClinicByCity(@RequestParam("city") String city){
		try {
			List<HospitalClinicInfo> hosclinic = new ArrayList<HospitalClinicInfo>();
			if (city == null) {
				hospitalClinicRepository.findAll().forEach(hosclinic::add);
			}
			else {
				hospitalClinicRepository.findHosclinInfoByDistrict(city);
			}
			
			if (hosclinic.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(hosclinic, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/hospitalclinic/findSpeciality")
	public ResponseEntity<List<HospitalClinicInfo>> findHospitalClinicBySpeciality(@RequestParam("speciality") String speciality){
		try {
			List<HospitalClinicInfo> hosclinic = new ArrayList<HospitalClinicInfo>();
			if (speciality == null) {
				hospitalClinicRepository.findAll().forEach(hosclinic::add);
			}
			else {
				hospitalClinicRepository.findHosclinInfoByDistrict(speciality);
			}
			
			if (hosclinic.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(hosclinic, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}