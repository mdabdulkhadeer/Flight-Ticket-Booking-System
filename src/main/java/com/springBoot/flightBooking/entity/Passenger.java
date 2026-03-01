package com.springBoot.flightBooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springBoot.flightBooking.dto.PassengerGender;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString(exclude = "booking")
//@EqualsAndHashCode(exclude = "booking")
public class Passenger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Integer age;
	
	@Enumerated(EnumType.STRING)
	private PassengerGender gender;
	
	private Integer seatNumber;
	private Long contactNo;
	
	@JoinColumn
	@JsonIgnore
	@ManyToOne
	private Booking booking;

}
