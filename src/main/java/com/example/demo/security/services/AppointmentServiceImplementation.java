package com.example.demo.security.services;

import com.example.demo.models.Appointment;
import com.example.demo.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;
import java.util.*;

@Component("appointmentService")
public class AppointmentServiceImplementation implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    public AppointmentServiceImplementation() {
    }

    public AppointmentServiceImplementation(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Optional<Appointment> findById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    @Override
    public List<Appointment> findByClinicId(long clinicId) {
        return appointmentRepository.findByClinicId(clinicId);
    }
    
    @Override
    public List<Appointment> findByNameOfClinic(String nameOfClinic) {
    	return appointmentRepository.findByNameOfClinic(nameOfClinic);
    }
    
    @Override
    public List<Appointment> findByPatientId(long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }
    
    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment create(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment update(Long appointmentId, Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    
    @Override
    public void updateAllAppointmentsStatuses() {
        appointmentRepository.findScheduledWithEndBeforeDate(OffsetDateTime.now())
                .forEach(appointment -> {
                    appointment.setStatus(appointment.getStatus().CONFIRMED);
                    create(appointment);
                });
    }
    
    @Override
    public Appointment updateStatus(Long appointmentId, Appointment appointment) {

        Optional<Appointment> appointmentList = appointmentRepository.findById(appointmentId);

        if(appointmentList.isPresent()){
            if(appointment.getStatus() != null){
                appointmentList.get().setStatus(appointment.getStatus());
            }
            return appointmentRepository.save(appointmentList.get());
        }
        return null;
    }
    
    @Override
    public Appointment cancel(Long appointmentId, Appointment appointment) {
        Optional<Appointment> appointmentList = appointmentRepository.findById(appointmentId);

        if(appointmentList.isPresent()){
            if(appointment.getStatus() != null){
                appointmentList.get().setStatus(appointment.getStatus().CANCELED);
            }
            return appointmentRepository.save(appointmentList.get());
        }
        return null;
    }

    @Override
    public void deleteById(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }
}
