package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentStartTime;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentEndTime;
    
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
//    private LocalDateTime canceledAt;

    private String subject;
    
    private String notes;
    
    private String description;
    
    private String nameOfClinic;

    private String nameOfPatient;

    @NotBlank
	private long clinicId;
    
    @NotBlank
	private long patientId;
    
	private AppointmentStatus status = AppointmentStatus.PENDING;
    
	private BigDecimal price;
	
	public Appointment() {

    }

    public Appointment(Timestamp createdAt, long clinicId, long patientId, String subject, LocalDate appointmentDate, LocalDateTime appointmentStartTime, LocalDateTime appointmentEndTime, String nameOfClinic, String nameOfPatient, AppointmentStatus status, String notes, String description, BigDecimal price) {
        this.createdAt = createdAt;
        this.clinicId = clinicId;
        this.patientId = patientId;
        this.subject = subject;
        this.notes = notes;
        this.description = description;
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentEndTime = appointmentEndTime;
        this.nameOfClinic = nameOfClinic;
        this.nameOfPatient = nameOfPatient;
        this.status = status;
        this.price = price;
    }

    public Appointment(LocalDate appointmentDate, LocalDateTime appointmentStartTime, LocalDateTime appointmentEndTime, String nameOfClinic, BigDecimal price) {
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentEndTime = appointmentEndTime;
        this.nameOfClinic = nameOfClinic;
        this.price = price;
    }

    public Appointment(LocalDate appointmentDate, String nameOfClinic, BigDecimal price) {
        this.nameOfClinic = nameOfClinic;
        this.price = price;
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
	
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
    }

    public LocalDateTime getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public void setAppointmentStartTime(LocalDateTime appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    public LocalDateTime getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public void setAppointmentEndTime(LocalDateTime appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
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
    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNotes() {
    	return notes;
    }
    
    public void setNotes(String notes) {
    	this.notes = notes;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
    

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
