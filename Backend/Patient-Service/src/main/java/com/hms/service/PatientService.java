package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.exception.CustomException;
import com.hms.model.Patient;
import com.hms.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private SequenceGeneratorService generatorService;
	
	public Patient createPatient(Patient patient) throws CustomException{
		
		if(patient==null) {
			throw new CustomException("Invalid Entry for Patient");
		}
		patient.setPatientId(String.valueOf(generatorService.generateSequence(Patient.SEQUENCE_NAME)));
		return patientRepository.save(patient);
		
	}
	
	public List<Patient> getPatientById(String pateintId) throws CustomException{
		
		List<Patient> getPatient = patientRepository.findByPatientId(pateintId);
		if(getPatient==null) {
			throw new CustomException("Invalid pateintId");
		}
		return getPatient;
	}
	
public List<Patient> getPatientByName(String name) throws CustomException, Exception{
		
		if(patientRepository.existsByName(name)==false) {
			throw new CustomException("Cannot Find Details with this Name");
		}
		List<Patient> getPatient = patientRepository.findByName(name);
		return getPatient;
	}

public Patient getPatientByNameObj(String name) throws CustomException, Exception{
	
	if(patientRepository.existsByName(name)==false) {
		throw new CustomException("Cannot Find Details with this Name");
	}
	Patient getPatient = patientRepository.getByName(name);
	return getPatient;
}

	
	

}
