package com.springBoot.flightBooking.entity;

import java.time.LocalDate;


import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springBoot.flightBooking.dto.PaymentMode;
import com.springBoot.flightBooking.dto.PaymentStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@CreationTimestamp
	private LocalDate paymentDate;
	private Double amount;
	
	@Enumerated(EnumType.STRING)
	private PaymentMode mode;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@JsonIgnore
	@OneToOne(mappedBy = "payment")
	private Booking booking;

}
