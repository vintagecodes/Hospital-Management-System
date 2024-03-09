package com.appoint.service;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.appoint.exception.CustomException;
import com.appoint.model.Appointment;
import com.appoint.model.Doctor;
import com.appoint.model.Patient;
import com.appoint.repository.AppointRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@Service
public class AppointmentService {
	
	@Autowired
	private AppointRepository appointRepository;
	
	@Autowired
	private SequenceGeneratorService generatorService;
	
	@Autowired
	private JmsTemplate template;
	
	@Autowired
	private RestTemplate restTemplate;
	
//	private StringBuilder latestAppointmentMessage = new StringBuilder(); 
	private String latestAppointmentMessage = "";
	
	public Appointment createAppointment(String doctorId, String name, Appointment appoint) throws CustomException, InvalidClassException{
		Appointment app = new Appointment();
		try {
			
			Doctor doctor = restTemplate.getForObject("http://localhost:5000/doctor/getById/"+doctorId, Doctor.class);
//			System.out.println("Doctor "+doctor);
			Patient patient = restTemplate.getForObject("http://localhost:5001/patient/getByNameObj/"+name, Patient.class);
//			System.out.println("Patient "+patient);
			if(doctor!=null && patient!=null) {
				app.setAppointId(String.valueOf(generatorService.generateSequence(Appointment.SEQUENCE_NAME)));
				app.setUsername(name);
				app.setDoctor(doctor);
				app.setPatient(patient);
				app.setCause(appoint.getCause());
				app.setStatus(appoint.getStatus());
				app.setPaymentStatus(appoint.getPaymentStatus());
				app.setSchedule(appoint.getSchedule());
				app.setTotalFees(doctor.getFees());
			}
			  ObjectMapper objectMapper = new ObjectMapper();
			  String message = objectMapper.writeValueAsString(app);
			template.convertAndSend("appointment-message", message);
		}catch (Exception e) {
            throw new InvalidClassException("Error creating rental: " + e.getMessage());
        }
		return appointRepository.save(app);
		
	}
	
	public Appointment getAppointmentById(String appointId) throws CustomException{
		Appointment app1 = appointRepository.findByAppointId(appointId);
//		System.out.println("List :"+app1);
		if(app1==null) {
			throw new CustomException("Invalid ID");
		}
		return app1;
		
	}
	
	public List<Appointment> getAppointByUsername(String username) throws CustomException{
		
		List<Appointment> getU = appointRepository.findByUsername(username);
//		System.out.println("List :"+getU);
		if(getU.isEmpty()) {
			throw new CustomException("Invalid Username");
		}
		return getU;
	}
	
	public List<Appointment> getAppointByDoctorUsername(String username) throws CustomException{
		
		List<Appointment> getU = findByUsername(username);
//		System.out.println("List :"+getU);
		if(getU.isEmpty()) {
			throw new CustomException("Invalid Username");
		}
		return getU;
	}
	
	private List<Appointment> findByUsername(String username) {
		// TODO Auto-generated method stub
		List<Appointment> newCreatedList = new ArrayList<>();
		List<Appointment> getAppointmentDetailsPerDoctor=appointRepository.findAll();
		for(Appointment b:getAppointmentDetailsPerDoctor) {
			if(b.getDoctor().getName().equals(username)) {
				newCreatedList.add(b);
				
			}
		}
		return newCreatedList;
	}
	
	public List<Appointment> getAppointList() throws CustomException{
		List<Appointment> getU = appointRepository.findAll();
//		System.out.println("List :"+getU);
		if(getU.isEmpty()) {
			throw new CustomException("Something went Wrong!");
		}
		return getU;
	}
	
	public Appointment updatePaymentStatus(String appointId, Appointment appointment) throws CustomException{
		Appointment app1 = appointRepository.findByAppointId(appointId);
		app1.setPaymentStatus(appointment.getPaymentStatus());
		return appointRepository.save(app1);
	}
	
	public Appointment updateStatus(String appointId, Appointment appointment) throws CustomException{
		Appointment app1 = appointRepository.findByAppointId(appointId);
		app1.setStatus(appointment.getStatus());
		return appointRepository.save(app1);
	}
	
	@JmsListener(destination = "appointment-message")
	public void setLatestAppointmentMessage(String latestAppointmentMessage)
	{
		System.out.println("The message was: "+latestAppointmentMessage);
		this.latestAppointmentMessage = latestAppointmentMessage;
		System.out.println("Appointment Message1: "+this.latestAppointmentMessage);
	}
	
	public String getLatestAppointmentMessage()
	{
		System.out.println("Appointment Message2: "+latestAppointmentMessage);
		return latestAppointmentMessage;
	}
}
