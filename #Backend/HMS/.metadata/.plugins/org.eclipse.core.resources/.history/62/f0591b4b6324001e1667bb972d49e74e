package com.hms.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hms.model.Patient;

@Repository
public interface PatientRepository  extends MongoRepository<Patient,String>{
	
	List<Patient> findByPatientId(String patientId);
	
	List<Patient> findByName(String name);
	
	Patient getByName(String name);
	
	Boolean existsByName(String name);

}
