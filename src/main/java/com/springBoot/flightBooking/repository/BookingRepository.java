package com.springBoot.flightBooking.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springBoot.flightBooking.dto.BookingStatus;
import com.springBoot.flightBooking.entity.Booking;
import com.springBoot.flightBooking.entity.Passenger;
import com.springBoot.flightBooking.entity.Payment;

public interface BookingRepository extends JpaRepository<Booking, Integer>{

	@Query("Select b from Booking b where b.flight.id=:id")
	public List<Booking> getBookingByFlightId(Integer id);
	
	//GetBookingByDate
	public List<Booking> findByBookingDateTime(LocalDateTime dateAndTime);
	
	
	//getBooking by status
	public List<Booking> findByStatus(BookingStatus status);
	
	//get all the passengers in the booking
	@Query("Select b.passengers from Booking b where b.id=?1")
	public List<Passenger> getAllPassengersInTheBooking(Integer id);
	
	//get payment details of a booking (i am taking id as a unique based on id i will find the payment details
	@Query("Select b.payment from Booking b where b.id=?1")
	public Optional<Payment> getPaymentDetailsOfABooking(Integer bookingId);


}
