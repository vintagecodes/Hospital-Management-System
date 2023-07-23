package com.hms.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hms.model.Doctor;

@Repository
public interface DoctorRepository  extends MongoRepository<Doctor,String>{
	
	List<Doctor> getDoctorByQualification(String qualification);
	
	Doctor findByDoctorId(String doctorId);


}
