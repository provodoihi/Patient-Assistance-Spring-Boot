package com.example.demo.controllers;

import com.example.demo.models.Appointment;
import com.example.demo.security.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
	public AppointmentController() {
    }

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    
    // GET request to return all appointments 
	@RequestMapping(path = "/all", method = RequestMethod.GET)
    List<Appointment> findAll() {
        return appointmentService.findAll();
    }
	
    // GET request to return specific appointments
	@PreAuthorize("hasAnyRole('CLINIC', 'ADMIN', 'PATIENT')")
    @RequestMapping(path = "/{appointmentId}", method = RequestMethod.GET)
    public Optional<Appointment> findById(@PathVariable Long appointmentId) {
        return appointmentService.findById(appointmentId);
    }

    // GET request to return appointments with specific clinicId
	@PreAuthorize("hasAnyRole('CLINIC', 'ADMIN', 'PATIENT')")
    @RequestMapping(path = "/clinic/{clinicId}", method = RequestMethod.GET)
    public List<Appointment> findByClinicId(@PathVariable long clinicId) {
        return appointmentService.findByClinicId(clinicId);
    }

    // GET request to return appointments with specific patientId
	@PreAuthorize("hasAnyRole('CLINIC', 'ADMIN', 'PATIENT')")
    @RequestMapping(path = "/patient/{patientId}", method = RequestMethod.GET)
    public List<Appointment> findByPatientId(@PathVariable long patientId) {
        return appointmentService.findByPatientId(patientId);
    }
	
    // POST request to add new appointments
	@PreAuthorize("hasRole('PATIENT')")
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment create(@RequestBody Appointment appointment) {
        return appointmentService.create(appointment);
    }

    // PUT request to update appointments
	@PreAuthorize("hasAnyRole('CLINIC', 'ADMIN')")
    @RequestMapping(path = "/{appointmentId}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Appointment update(@PathVariable Long appointmentId, @RequestBody Appointment appointment) {
        return appointmentService.update(appointmentId, appointment);
    }

    // PATCH request to update status of an appointment
	@PreAuthorize("hasAnyRole('CLINIC', 'PATIENT')")
    @RequestMapping(path = "/{appointmentId}", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Appointment updateStatus(@PathVariable Long appointmentId, @RequestBody Appointment appointment) {
        return appointmentService.updateStatus(appointmentId, appointment);
    }

    // DELETE request to delete specific appointments
	@PreAuthorize("hasAnyRole('CLINIC', 'ADMIN')")
    @RequestMapping(path = "/{appointmentId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    void deleteById(@PathVariable Long appointmentId) {
        appointmentService.deleteById(appointmentId);
    }
}
