package com.example.demo.controllers;

import com.example.demo.models.Appointment;
import com.example.demo.security.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<Appointment>> findAll() {
		try {
			List<Appointment> appointments = appointmentService.findAll();
			if (appointments.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
    // GET request to return specific appointments
    @GetMapping("/{appointmentId}")
    @PreAuthorize("hasAnyRole('CLINIC', 'ADMIN', 'PATIENT')")
    public ResponseEntity<Appointment> findById(@PathVariable("appointmentId") Long appointmentId) {
    	Optional<Appointment> appointments = appointmentService.findById(appointmentId);
        if (appointments.isPresent()) {
			return new ResponseEntity<>(appointments.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    // GET request to return appointments with specific clinicId
    @GetMapping("/clinic/{clinicId}")
    @PreAuthorize("hasAnyRole('CLINIC', 'ADMIN')")
    public ResponseEntity<List<Appointment>> findByClinicId(@PathVariable("clinicId") long clinicId) {
		try {
			List<Appointment> appointments = appointmentService.findByClinicId(clinicId);
			if (appointments.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	

    // GET request to return appointments with specific patientId
	@GetMapping("/patient/{patientId}")
	@PreAuthorize("hasAnyRole('CLINIC', 'ADMIN', 'PATIENT')")
    public ResponseEntity<List<Appointment>> findByPatientId(@PathVariable("patientId") long patientId) {
		try {
			List<Appointment> appointments = appointmentService.findByPatientId(patientId);
			if (appointments.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
	
    // POST request to add new appointments
	@PreAuthorize("hasRole('PATIENT')")
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment create(@RequestBody Appointment appointment) {
        return appointmentService.create(appointment);
    }

    // PATCH request to update status of an appointment 
	@PreAuthorize("hasAnyRole('CLINIC', 'PATIENT')")
    @RequestMapping(path = "/{appointmentId}", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Appointment updateStatus(@PathVariable("appointmentId") Long appointmentId, @RequestBody Appointment appointment) {
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
