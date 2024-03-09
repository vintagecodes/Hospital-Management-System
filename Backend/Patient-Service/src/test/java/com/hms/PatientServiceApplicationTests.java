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
import com.hms.model.Patient;

@SpringBootTest
@AutoConfigureMockMvc
class PatientServiceApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;

	@Test
	void contextLoads() throws Exception {
		Patient patient = Patient.builder()
				.name("Nimpura")
				.email("sharon@gmail.com")
				.phoneNo(980056458)
				.address("BikanerKiDukan")
				.age(25)
				.build();
		
		ResultActions response = mockMvc.perform(post("/patient/created")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(patient)));
		response.andDo(print()).andExpect(status().isOk());
				
	}
	
	@Test
	void contextLoadsGet() throws Exception {
		List<Patient> listOfPatient = new ArrayList<>();
		Patient patient = Patient.builder()
				.name("Nimpura")
				.email("sharon@gmail.com")
				.phoneNo(980056458)
				.address("BikanerKiDukan")
				.age(25)
				.build();
		listOfPatient.add(patient);
		ResultActions response = mockMvc.perform(get("/getById/5010")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(listOfPatient)));
		response.andDo(print()).andExpect(status().isOk());
	}

}
