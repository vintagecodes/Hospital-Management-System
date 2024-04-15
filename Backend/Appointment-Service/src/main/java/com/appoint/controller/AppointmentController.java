package com.appoint.controller;

import java.io.InvalidClassException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appoint.exception.CustomException;
import com.appoint.model.Appointment;
import com.appoint.model.EmailRequest;
import com.appoint.service.AppointmentService;
import com.appoint.service.EmailService;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	 @Autowired
	 private EmailService emailService;
	
	@PostMapping("/created/{doctorId}/{name}")
	public ResponseEntity<?> createAppointment(@PathVariable String doctorId, @PathVariable String name, @RequestBody Appointment appointment)  throws CustomException, InvalidClassException{
			return appointmentService.createAppointment(doctorId, name, appointment);
	}
	
	@GetMapping("/appointment-message")
	public String getAppointMessage()
	{
		return appointmentService.getLatestAppointmentMessage();
	}
	
	@PostMapping("/send-email")
    public void sendEmail(@RequestBody EmailRequest request) {
        emailService.sendEmail(request.getTo(), request.getSubject(), request.getBody());
    }
	
	
	@GetMapping("/list/{appointId}")
	public ResponseEntity<Appointment> getAppointmentList (@PathVariable String appointId) throws CustomException {
		try {
			Appointment getAppointments = appointmentService.getAppointmentById(appointId);
			return ResponseEntity.ok(getAppointments);
		} catch (CustomException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/listPerUser/{username}")
	public ResponseEntity<List<Appointment>> getAppointmentListUsername (@PathVariable String username) throws CustomException {
		try {
			List<Appointment> getAppointments = appointmentService.getAppointByUsername(username);
			return ResponseEntity.ok(getAppointments);
		} catch (CustomException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/listPerDoctorName/{username}")
	public ResponseEntity<List<Appointment>> getAppointmentListUsernameDoctor (@PathVariable String username) throws CustomException {
		try {
			List<Appointment> getAppointments = appointmentService.getAppointByDoctorUsername(username);
			return ResponseEntity.ok(getAppointments);
		} catch (CustomException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/paymentstatus/{appointId}")
	public ResponseEntity<Appointment> updateAppointPaymentStatus(@PathVariable String appointId, @RequestBody Appointment appointment) throws CustomException{
		try {
			Appointment appointment2 = appointmentService.updatePaymentStatus(appointId,appointment);
			return ResponseEntity.ok(appointment2);
		} catch (CustomException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/status/{appointId}")
	public ResponseEntity<Appointment> updateAppointStatus(@PathVariable String appointId, @RequestBody Appointment appointment) throws CustomException{
		try {
			Appointment appointment2 = appointmentService.updateStatus(appointId,appointment);
			return ResponseEntity.ok(appointment2);
		} catch (CustomException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/listAllUsers")
	public ResponseEntity<List<Appointment>> getAppointmentList() throws CustomException {
		try {
			List<Appointment> getAppointments = appointmentService.getAppointList();
			return ResponseEntity.ok(getAppointments);
		} catch (CustomException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
