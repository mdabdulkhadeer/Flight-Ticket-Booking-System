package com.springBoot.flightBooking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.flightBooking.dto.BookingStatus;
import com.springBoot.flightBooking.dto.ResponseStructure;
import com.springBoot.flightBooking.entity.Booking;
import com.springBoot.flightBooking.entity.Passenger;
import com.springBoot.flightBooking.entity.Payment;
import com.springBoot.flightBooking.service.BookingService;

@RequestMapping("/booking")
@RestController
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Booking>> createBooking(@RequestBody Booking booking) {
		return bookingService.addBooking(booking);
	}

	// get all bookings
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBookings() {
		return bookingService.getAllBookings();
	}

	// get booking by id
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Booking>> getBookingById(@PathVariable Integer id) {
		return bookingService.getBookingById(id);

	}

	// get bookings by flight id
	@GetMapping("/flightId/{id}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByFlightId(@PathVariable Integer id) {
		return bookingService.getBookingByFlightId(id);
	}

	// get booking by date
	
	
	@GetMapping("/dateAndTime/{dateAndTime}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByDate(@PathVariable LocalDateTime dateAndTime) {
		return bookingService.getBookingByDateAndTime(dateAndTime);
	}

	// get booking status
	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByStatus(@PathVariable BookingStatus status) {
		return bookingService.getBookingByStatus(status);
	}

	// getAllPassengersInTheBooking
	@GetMapping("/passengers/{id}")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassengersInTheBooking(@PathVariable Integer id) {
		return bookingService.getAllPassengersInTheBooking(id);
	}

	// getPaymentDetailsOfABooking
	@GetMapping("/{bookingId}/payment")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentDetailsOfABooking(@PathVariable Integer bookingId) {
		return bookingService.getPaymentDetailsOfABooking(bookingId);

	}

	// update booking status
	@PutMapping("/statusUpdate/{bookingId}/{status}")
	public ResponseEntity<ResponseStructure<Booking>> updateBookingStatus(@PathVariable Integer bookingId,@PathVariable BookingStatus status) {
		return bookingService.updateBookingStatus(bookingId, status);
	}

	// delete booking
	@DeleteMapping("/delete/{bookingId}")
	public ResponseEntity<ResponseStructure<String>> deleteBooking(@PathVariable Integer bookingId) {
		return bookingService.deleteBooking(bookingId);
	}

	// get booking by pagination and sorting
	@GetMapping("/pageAndSort/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<Booking>>> getBookingByPageAndSort(@PathVariable Integer pageNumber,
			@PathVariable Integer pageSize, @PathVariable String field) {
		return bookingService.getBookingByPageAndSort(pageNumber, pageSize, field);
	}

}
