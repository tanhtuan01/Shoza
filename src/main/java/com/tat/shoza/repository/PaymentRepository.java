package com.tat.shoza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tat.shoza.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
