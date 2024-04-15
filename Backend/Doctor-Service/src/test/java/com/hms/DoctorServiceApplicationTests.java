package com.hms;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hms.model.Doctor;

@SpringBootTest
@AutoConfigureMockMvc
class DoctorServiceApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;

	@Test
	void contextLoads() throws Exception {
		Doctor doctor = Doctor.builder()
				.name("Rosh")
				.email("rosh@gmail.com")
				.specialization("MBBS")
				.phoneNo(980000005)
				.fees(1256)
				.qualification("MBBS")
				.yearsOfExp("25")
				.daysOfAvail("M,T,S")
				.build();
		ResultActions response = mockMvc.perform(post("/doctor/created")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(doctor)));
		response.andDo(print()).andExpect(status().isOk());
				
	}
	
	@Test
	void contextLoadsGet() throws Exception {
		List<Doctor> listOfDoctor = new ArrayList<>();
		Doctor doctor = Doctor.builder()
				.name("Rosh")
				.email("rosh@gmail.com")
				.specialization("MBBS")
				.phoneNo(980000005)
				.fees(1256)
				.qualification("MBBS")
				.yearsOfExp("25")
				.daysOfAvail("M,T,S")
				.build();
		listOfDoctor.add(doctor);
		ResultActions response = mockMvc.perform(get("/doctor/qualifications/"+doctor.getQualification())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(listOfDoctor)));
		response.andDo(print()).andExpect(status().isOk());
				
	}
	
	@Test
	void contextLoadsGetByDoctorId() throws Exception {
		Doctor doctor = Doctor.builder()
				.name("Rosh")
				.email("rosh@gmail.com")
				.specialization("MBBS")
				.phoneNo(980000005)
				.fees(1256)
				.qualification("MBBS")
				.yearsOfExp("25")
				.daysOfAvail("M,T,S")
				.build();
		ResultActions response = mockMvc.perform(get("/doctor/getById/16")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(doctor)));
		response.andDo(print()).andExpect(status().isOk());
				
	}
	
	@Test
	void contextLoadsGetViewAll() throws Exception {
		List<Doctor> getViewList = new ArrayList<>();
		Doctor doctor = Doctor.builder()
				.name("Roshes")
				.email("roshes@gmail.com")
				.specialization("MBBS")
				.phoneNo(980000005)
				.fees(1256)
				.qualification("MBBS")
				.yearsOfExp("25")
				.daysOfAvail("M,T,S")
				.build();
		Doctor doctor1 = Doctor.builder()
				.name("Rosh")
				.email("rosh@gmail.com")
				.specialization("MBBS")
				.phoneNo(980000005)
				.fees(1256)
				.qualification("MBBS")
				.yearsOfExp("25")
				.daysOfAvail("M,T,S")
				.build();
		getViewList.add(doctor);
		getViewList.add(doctor1);
		ResultActions response = mockMvc.perform(get("/doctor/viewAll")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(getViewList)));
		response.andDo(print()).andExpect(status().isOk());
				
	}

}
