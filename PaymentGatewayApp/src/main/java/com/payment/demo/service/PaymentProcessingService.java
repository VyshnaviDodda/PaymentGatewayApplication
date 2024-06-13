package com.payment.demo.service;

import org.springframework.stereotype.Service;

import com.payment.demo.model.TransactionDetails;

@Service
public class PaymentProcessingService {
	
	public String processPayment(TransactionDetails transactionDetails) {
        
		
		
        return "Payment Successful";

	}
	
	
}
