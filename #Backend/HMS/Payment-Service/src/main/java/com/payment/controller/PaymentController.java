package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.model.Payment;
import com.payment.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/create/{appointId}/{username}")
	public ResponseEntity<Payment> createPayment(@PathVariable String appointId, @PathVariable String username, @RequestBody Payment pay){
		try {
			Payment createNewPayment = paymentService.createPayment(appointId, username, pay);
			return ResponseEntity.ok(createNewPayment);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
