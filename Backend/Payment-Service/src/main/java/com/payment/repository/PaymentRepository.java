package com.payment.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.payment.model.Payment;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String>{
	

}