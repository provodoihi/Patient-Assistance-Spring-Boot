package com.example.demo.security.services;

import com.example.demo.models.Appointment;


import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    Optional<Appointment> findById(Long appointmentId);

    List<Appointment> findByClinicId(long clinicId);
    
    List<Appointment> findByNameOfClinic(String nameOfClinic);
    
    List<Appointment> findByPatientId(long patientId);
    
    List<Appointment> findAll();
    
    Appointment create(Appointment appointment);
    
    Appointment update(Long appointmentId, Appointment appointment);
    
    Appointment cancel(Long appointmentId, Appointment appointment);
    
    Appointment updateStatus(Long appointmentId, Appointment appointment);
    
    void updateAllAppointmentsStatuses();

    void deleteById(Long appointmentId);

}
