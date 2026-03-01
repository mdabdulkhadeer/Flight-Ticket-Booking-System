package com.springBoot.flightBooking.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.springBoot.flightBooking.dto.BookingStatus;
import com.springBoot.flightBooking.entity.Booking;
import com.springBoot.flightBooking.entity.Passenger;
import com.springBoot.flightBooking.entity.Payment;
import com.springBoot.flightBooking.repository.BookingRepository;

@Repository
public class BookingDao {

	@Autowired
	private BookingRepository bookingRepository;

	public Booking addBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	// get all bookings
	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	// get Booking by id
	public Optional<Booking> getBookingById(Integer id) {
		return bookingRepository.findById(id);
	}

	// get booking by flightId
	public List<Booking> getBookingByFlightId(Integer id) {
		return bookingRepository.getBookingByFlightId(id);
	}

	// GetBookingByDate
	public List<Booking> getBookingByDateAndTime(LocalDateTime dateAndTime) {
		return bookingRepository.findByBookingDateTime(dateAndTime);
	}

	// get booking by status
	public List<Booking> getBookingByStatus(BookingStatus status) {
		return bookingRepository.findByStatus(status);
	}

	// getAllPassengersInTheBooking
	public List<Passenger> getAllPassengersInTheBooking(Integer id) {
		return bookingRepository.getAllPassengersInTheBooking(id);
	}

	// getPaymentDetailsOfABooking
	public Optional<Payment> getPaymentDetailsOfABooking(Integer bookingId) {
		return bookingRepository.getPaymentDetailsOfABooking(bookingId);
	}

	// delete booking
	public String deleteBooking(Booking booking) {
		bookingRepository.delete(booking);
		return "Deleted sucssfully";
	}

	// get booking by pagination and sorting
	public Page<Booking> getBookingByPageAndSort(Integer pageNumber, Integer pageSize, String field) {
		return bookingRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(field)));

	}

}
