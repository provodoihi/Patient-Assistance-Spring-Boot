package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
   
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private OffsetDateTime appointmentStartTime;
    
    private String description;
    
    @NotBlank
	private long clinicId;
    
    @NotBlank
	private long patientId;
    
    private String nameOfClinic;

    private String nameOfPatient;
    
    private String phoneOfPatient;
    
	private AppointmentStatus status = AppointmentStatus.PENDING;
	
	public Appointment() {	
	}

    public Appointment(Timestamp createdAt, long clinicId, long patientId, OffsetDateTime appointmentStartTime, String nameOfClinic, String nameOfPatient, String phoneOfPatient, AppointmentStatus status, String description) {
        this.createdAt = createdAt;
        this.appointmentStartTime = appointmentStartTime;
        this.description = description;
        this.clinicId = clinicId;
        this.patientId = patientId;
        this.nameOfClinic = nameOfClinic;
        this.nameOfPatient = nameOfPatient;
        this.phoneOfPatient = phoneOfPatient;
        this.status = status;
    }

    public Appointment(OffsetDateTime appointmentStartTime, String nameOfClinic) {
        this.appointmentStartTime = appointmentStartTime;
        this.nameOfClinic = nameOfClinic;
    }

    public Appointment(String nameOfClinic) {
        this.nameOfClinic = nameOfClinic;
    }
    
    public int compareTo(Appointment o) {
        return this.getAppointmentStartTime().compareTo(o.getAppointmentStartTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public void setAppointmentStartTime(OffsetDateTime appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	
	public long getClinicId() {
		return clinicId;
	}

	public void setClinicId(long clinicId) {
		this.clinicId = clinicId;
	}

    public String getNameOfClinic() {
        return nameOfClinic;
    }

    public void setNameOfClinic(String nameOfClinic) {
        this.nameOfClinic = nameOfClinic;
    }
    
    public String getNameOfPatient() {
        return nameOfPatient;
    }

    public void setNameOfPatient(String nameOfPatient) {
        this.nameOfPatient = nameOfPatient;
    }
    
    public String getPhoneOfPatient() {
		return phoneOfPatient;
	}

	public void setPhoneOfPatient(String phoneOfPatient) {
		this.phoneOfPatient = phoneOfPatient;
	}
    
    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
