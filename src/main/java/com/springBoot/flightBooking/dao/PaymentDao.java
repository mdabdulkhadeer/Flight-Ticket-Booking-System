package com.springBoot.flightBooking.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.springBoot.flightBooking.dto.PaymentMode;
import com.springBoot.flightBooking.dto.PaymentStatus;
import com.springBoot.flightBooking.entity.Payment;
import com.springBoot.flightBooking.repository.PaymentRepository;

@Repository
public class PaymentDao {

	@Autowired
	private PaymentRepository paymentRepository;

	// add payment
	public Payment addPayment(Payment payment) {
		return paymentRepository.save(payment);
	}

	// get all payment
	public List<Payment> getAllPayment() {
		return paymentRepository.findAll();
	}

	// get payment by id

	public Optional<Payment> getPaymentById(Integer id) {
		return paymentRepository.findById(id);
	}

	// get payment by status

	public List<Payment> getPaymentByStatus(PaymentStatus status) {
		return paymentRepository.findByStatus(status);
	}

	// get payment where amount is greater than particular amount

	public List<Payment> getPaymentWhereAmountGreaterThan(double amount) {
		return paymentRepository.findByAmountGreaterThan(amount);
	}

	// get payment by mode of transaction
	public List<Payment> getPaymentByMode(PaymentMode mode) {
		return paymentRepository.findByMode(mode);
	}

	// get payment by pagination and sorting
	public Page<Payment> getPaymentByPageAndSort(Integer pageNumber, Integer pageSize, String field) {
		return paymentRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(field)));

//			return PaymentRepository.findAll(PageRequest.of(pageNumber,pageSize,Sort.by(field)));
	}

}
