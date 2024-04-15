package com.hms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.exception.CustomException;
import com.hms.model.Doctor;

import com.hms.repository.DoctorRepository;
import com.hms.service.DoctorService;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@PostMapping("/created")
	public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) throws CustomException {
		try {
			Doctor createDoctor = doctorService.createDoctor(doctor);
			return ResponseEntity.ok(createDoctor);
		} catch (CustomException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/qualifications/{qualification}")
	public ResponseEntity<List<Doctor>> getDoctorWithQualification(@PathVariable String qualification){
		try {
			List<Doctor> getDoctors = doctorService.getDoctorWithQualification(qualification);
			return ResponseEntity.ok(getDoctors);
		} catch (CustomException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{doctorId}")
	public Doctor updateDoctorDetails(@PathVariable String doctorId, @RequestBody Doctor doctor) throws CustomException {
		Doctor update = new Doctor();
		try {
			update = doctorService.updateDoctorDetails(doctorId, doctor);
		} catch (CustomException e) {
			throw new CustomException("Incoorect Input");
		}
		return update;
	}
	
	@GetMapping("/getById/{doctorId}")
	public ResponseEntity<Doctor> getDoctorDetailsById(@PathVariable String doctorId) throws CustomException {
		try {
			Doctor getDoctor = doctorService.getDoctorById(doctorId);
			return ResponseEntity.ok(getDoctor);
		}catch (CustomException e) {
			throw new CustomException("Incorrect Input");
		}
	}
	
	@GetMapping("/viewAll")
	public ResponseEntity<List<Doctor>> getAllDoctors() throws CustomException{
		try {
			List<Doctor> getAllDoctor = doctorService.getAllDoctors();
			return ResponseEntity.ok(getAllDoctor);
		}catch (CustomException e) {
			throw new CustomException("Incorrect Input");
		}
	}
	
	
	@DeleteMapping("/deleteByName/{name}")
	public void Doctor(@PathVariable String name) {
		doctorRepository.deleteByName(name);
	}

}
