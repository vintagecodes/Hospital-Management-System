package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.exception.CustomException;
import com.hms.model.Doctor;
import com.hms.repository.DoctorRepository;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private SequenceGeneratorService generatorService;
	
	public Doctor createDoctor(Doctor doctor) throws CustomException{
		
		if(doctor==null) {
			throw new CustomException("Invalid Entry for Doctor");
		}
		doctor.setDoctorId(String.valueOf(generatorService.generateSequence(Doctor.SEQUENCE_NAME)));
		return doctorRepository.save(doctor);
		
	}
	
	public List<Doctor> getDoctorWithQualification(String qualification) throws CustomException{
		
		List<Doctor> getDoctor = doctorRepository.getDoctorByQualification(qualification);
		if(getDoctor==null) {
			throw new CustomException("Invalid Qualification");
		}
		return getDoctor;
	}
	
	public Doctor getDoctorById(String doctorId) throws CustomException{
		
		Doctor getDoctor = doctorRepository.findByDoctorId(doctorId);
		if(getDoctor==null) {
			throw new CustomException("Invalid doctorId");
		}
		return getDoctor;
	}
	
	public List<Doctor> getAllDoctors() throws CustomException{
		List<Doctor> getAll = doctorRepository.findAll();
		return getAll;
	}
	
	
	public Doctor updateDoctorDetails(String doctorId,Doctor doctor) throws CustomException {
		
		Doctor getDoctor = doctorRepository.findByDoctorId(doctorId);
		if(getDoctor==null)
		{
			throw new CustomException("Incorrect DoctorId");
		}
		getDoctor.setName(doctor.getName());
		getDoctor.setEmail(doctor.getEmail());
		getDoctor.setSpecialization(doctor.getSpecialization());
		getDoctor.setPhoneNo(doctor.getPhoneNo());
		getDoctor.setFees(doctor.getFees());
		getDoctor.setQualification(doctor.getQualification());
		getDoctor.setYearsOfExp(doctor.getYearsOfExp());
		getDoctor.setDaysOfAvail(doctor.getDaysOfAvail());
		
		return doctorRepository.save(getDoctor);
		
	}
	
	

}
