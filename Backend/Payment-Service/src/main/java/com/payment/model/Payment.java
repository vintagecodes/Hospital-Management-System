package com.payment.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "PaymentDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	
	@Transient
	public static final String SEQUENCE_NAME = "payment_id_sequence";
	
	@Id
	private String paymentId;
	private String username;
	private String transactionId;
	private CardDetails cardDetails;
	private Appointment appoint;
	private double totalAmount;
	private String paymentStatus;
	
	

}
