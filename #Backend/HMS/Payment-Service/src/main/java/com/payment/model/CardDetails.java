package com.payment.model;

import javax.validation.constraints.Max;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDetails {
	
	private String name;
	@Max(16)
	private long cardNumber;
	@Max(3)
	private int cvv;
	

}
