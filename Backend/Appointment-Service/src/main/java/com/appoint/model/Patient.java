package com.appoint.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

	private String patientId;
	private String name;
	private String email;
	private long phoneNo;
	private String address;
	private int age;

}
