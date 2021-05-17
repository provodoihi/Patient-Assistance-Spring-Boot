package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "hospital_clinic_info")

public class HospitalClinicInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	private String name;

	@NotBlank
	private String address;
	
	@NotBlank
	private String district;
	
	@NotBlank
	private String city;

	@NotBlank
	@Size(max = 20)
	private String phone;

	@NotBlank
	private String speciality;
	
	@NotBlank
	private String description;
	
	public HospitalClinicInfo() {
	}

	public HospitalClinicInfo(String name, String address, String district, String city, String phone, String speciality, String description) {
		this.name = name;
		this.address = address;
		this.district = district;
		this.city = city;
		this.phone = phone;
		this.speciality = speciality;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
