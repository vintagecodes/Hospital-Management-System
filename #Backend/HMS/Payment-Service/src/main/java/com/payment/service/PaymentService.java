package com.payment.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.payment.model.Appointment;
import com.payment.model.Payment;
import com.payment.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	public Payment createPayment(String appointId, String username, Payment payment) throws Exception{
		Appointment appointment = restTemplate.getForObject("http://localhost:5002/appointment/list/"+appointId, Appointment.class);
		String uniqueID = UUID.randomUUID().toString();
		Payment create = new Payment();
		create.setUsername(username);
		create.setTransactionId(uniqueID);
		create.setCardDetails(payment.getCardDetails());
		create.setAppoint(appointment);
		create.setTotalAmount(appointment.getTotalFees());
		create.setPaymentStatus(payment.getPaymentStatus());
		return paymentRepository.save(create);
	}
	
	

}
