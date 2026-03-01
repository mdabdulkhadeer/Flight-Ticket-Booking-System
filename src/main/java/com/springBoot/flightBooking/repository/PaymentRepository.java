package com.springBoot.flightBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springBoot.flightBooking.dto.PaymentMode;
import com.springBoot.flightBooking.dto.PaymentStatus;
import com.springBoot.flightBooking.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	//get payment by status
		public List<Payment> findByStatus(PaymentStatus status);
		
		
		//get payment amount greater then particular amount
		public List<Payment>findByAmountGreaterThan(double amount);
		
		//get payment by mode of transaction
		public List<Payment>findByMode(PaymentMode mode);
}
