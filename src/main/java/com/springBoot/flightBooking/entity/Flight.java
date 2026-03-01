package com.springBoot.flightBooking.entity;

import java.time.LocalTime;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;


//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString(exclude = "bookings")
//@EqualsAndHashCode(exclude = "bookings")
@Data
@Entity
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String airline;
	private String source;
	private String destination;
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime departureTime;
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime arrivalTime;
	private Integer totalSeats;
	private Double price;

	@JsonIgnore
	@OneToMany(mappedBy = "flight",cascade = CascadeType.ALL)
	private List<Booking> bookings;

}
