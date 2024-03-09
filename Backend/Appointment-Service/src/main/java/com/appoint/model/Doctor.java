package com.appoint.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
	
	private String doctorId;
	private String name;
	private String email;
	private String specialization;
	private long phoneNo;
	private Integer fees;
	private String qualification;
	private String yearsOfExp;
	private String daysOfAvail;

}
