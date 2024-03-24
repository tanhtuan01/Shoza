package com.tat.shoza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tat.shoza.model.Payment;
import com.tat.shoza.repository.PaymentRepository;
import com.tat.shoza.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Override
	public Payment get(Long id) {
		// TODO Auto-generated method stub
		return paymentRepository.getById(id);
	}
	
	@Override
	public Payment save(Payment payment) {
		// TODO Auto-generated method stub
		return paymentRepository.save(payment);
	}
}
