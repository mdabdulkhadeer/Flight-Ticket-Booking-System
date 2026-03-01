package com.springBoot.flightBooking.entity;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;

import com.springBoot.flightBooking.dto.BookingStatus;

import jakarta.persistence.*;
import lombok.*;


//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString(exclude = {"payment", "passengers", "flight"})
//@EqualsAndHashCode(exclude = {"payment", "passengers", "flight"})

@Entity
@Data
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@CreationTimestamp
	private LocalDateTime bookingDateTime;
	
	@Enumerated(EnumType.STRING)
	private BookingStatus status;

	@JoinColumn
	@OneToOne(cascade = CascadeType.ALL)
	private Payment payment;

	@OneToMany(mappedBy = "booking",cascade = CascadeType.ALL)
	private List<Passenger> passengers;

	@JoinColumn
	@ManyToOne
	private Flight flight;

}
