package com.tat.shoza.service;

import com.tat.shoza.model.Payment;

public interface PaymentService {

	Payment get(Long id);
	
	Payment save(Payment payment);
	
}
