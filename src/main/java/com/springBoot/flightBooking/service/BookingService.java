package com.springBoot.flightBooking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springBoot.flightBooking.dao.BookingDao;
import com.springBoot.flightBooking.dao.FlightDao;
import com.springBoot.flightBooking.dto.BookingStatus;
import com.springBoot.flightBooking.dto.ResponseStructure;
import com.springBoot.flightBooking.entity.Booking;
import com.springBoot.flightBooking.entity.Flight;
import com.springBoot.flightBooking.entity.Passenger;
import com.springBoot.flightBooking.entity.Payment;
import com.springBoot.flightBooking.exception.IdNotFoundException;
import com.springBoot.flightBooking.exception.NoRecordAvailableException;

@Service
public class BookingService {

	@Autowired
	private BookingDao bookingDao;
	@Autowired
	private FlightDao flightDao;

	public ResponseEntity<ResponseStructure<Booking>> addBooking(Booking booking) {

        ResponseStructure<Booking> response = new ResponseStructure<>();

        // 1️ Validate flight ID
        validateFlightId(booking);

        // 2️ Fetch flight
        Flight flight = getFlightOrThrow(booking.getFlight().getId());

        // 3️⃣ Validate seat availability
        List<Passenger> passengers = booking.getPassengers();
        validateSeatAvailability(flight, passengers.size());

        // 4️⃣ Setup entity relationships
        setupBookingRelationships(booking, flight, passengers);

        // 5️⃣ Save booking
        Booking savedBooking = bookingDao.addBooking(booking);

        // 6️⃣ Reduce available seats
        reduceAvailableSeats(flight, passengers.size());

        // 7️⃣ Build response
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Booking confirmed successfully! Enjoy your journey...😊✌️");
        response.setData(savedBooking);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ---------------------------------------------------------------------
    // 💡 Helper methods (each does one job clearly)
    // ---------------------------------------------------------------------

    private void validateFlightId(Booking booking) {
        if (booking.getFlight() == null || booking.getFlight().getId() == null) {
            throw new IdNotFoundException("Flight ID is needed to proceed Booking...⚠️");
        }
    }

    private Flight getFlightOrThrow(Integer flightId) {
        return flightDao.getFlightById(flightId)
                .orElseThrow(() -> new NoRecordAvailableException(
                        "No flight exists with the id '" + flightId + "'. Please provide a valid one...😵"));
    }

    private void validateSeatAvailability(Flight flight, int passengerCount) {
        if (flight.getTotalSeats() < passengerCount) {
            throw new NoRecordAvailableException("Not enough seats available! Only " + flight.getTotalSeats() + " left...⚠️");
        }
    }

    private void setupBookingRelationships(Booking booking, Flight flight, List<Passenger> passengers) {
        // Link passengers
        for (Passenger p : passengers) {
            p.setBooking(booking);
        }

        // Link payment
        if (booking.getPayment() != null) {
            booking.getPayment().setBooking(booking);
            booking.getPayment().setAmount(passengers.size() * booking.getPayment().getAmount());
        }

        // Link flight
        booking.setFlight(flight);
        flight.getBookings().add(booking);

        // Optional: set booking status automatically
        booking.setStatus(BookingStatus.CONFIRMED);
    }

    private void reduceAvailableSeats(Flight flight, int passengerCount) {
        flight.setTotalSeats(flight.getTotalSeats() - passengerCount);
        flightDao.addFlight(flight);
    }

	// get all bookings
	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBookings() {
		ResponseStructure<List<Booking>> response = new ResponseStructure<List<Booking>>();
		List<Booking> bookings = bookingDao.getAllBookings();
		if (bookings.isEmpty())
			throw new NoRecordAvailableException("No Booking records are avilable...⚠️");

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Hear is your Booking records...😊");
		response.setData(bookings);
		return new ResponseEntity<ResponseStructure<List<Booking>>>(response, HttpStatus.OK);

	}

	// get booking by id
	public ResponseEntity<ResponseStructure<Booking>> getBookingById(Integer id) {
		ResponseStructure<Booking> response = new ResponseStructure<Booking>();
		Optional<Booking> booking = bookingDao.getBookingById(id);
		if (booking.isEmpty())
			throw new IdNotFoundException("Based on your id '" + id + "' no record exist in DB...⚠️");

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Hear is your Booking info...👇🏽");
		response.setData(booking.get());
		return new ResponseEntity<ResponseStructure<Booking>>(response, HttpStatus.OK);

	}

	// get booking by flight id
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByFlightId(Integer id) {
		List<Booking> bookings = bookingDao.getBookingByFlightId(id);
		if (bookings.isEmpty())
			throw new NoRecordAvailableException("No Bookings are  found on selected flight id '" +id+"' ...😵");
		else {
			ResponseStructure<List<Booking>> response = new ResponseStructure<List<Booking>>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("The Bookings info of flight id '" + id +"' was retrieved successfully...✅");
			response.setData(bookings);

			return new ResponseEntity<ResponseStructure<List<Booking>>>(response, HttpStatus.OK);
		}

	}

	// GetBookingByDate
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByDateAndTime(LocalDateTime dateAndTime) {
		List<Booking> bookings = bookingDao.getBookingByDateAndTime(dateAndTime);
		if (bookings.isEmpty())
			throw new NoRecordAvailableException("No Booking records are found on selected date...⚠️");

		
		ResponseStructure<List<Booking>> response = new ResponseStructure<List<Booking>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Hear is your Booking records of selected date  '" + dateAndTime+"'...👇🏽");
		response.setData(bookings);
		
		return new ResponseEntity<ResponseStructure<List<Booking>>>(response, HttpStatus.OK);
	}

	// get booking by status
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByStatus(BookingStatus status) {
		List<Booking> bookings = bookingDao.getBookingByStatus(status);
		if (bookings.isEmpty())
			throw new NoRecordAvailableException("No Booking records are found on selected status '" + status+"'...😵");

		ResponseStructure<List<Booking>> response = new ResponseStructure<List<Booking>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Hear is your Booking info with respect to the selected status '" + status+"'...👇🏽");
		response.setData(bookings);
		return new ResponseEntity<ResponseStructure<List<Booking>>>(response, HttpStatus.OK);
	}

	// getAllPassengersInTheBooking
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassengersInTheBooking(Integer id) {
		List<Passenger> passengers = bookingDao.getAllPassengersInTheBooking(id);
		if (passengers.isEmpty())
			throw new NoRecordAvailableException("No Passengers are  found in booking");

		ResponseStructure<List<Passenger>> response = new ResponseStructure<List<Passenger>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Hear is your Passenger list in the booking...👇🏽");
		response.setData(passengers);
		return new ResponseEntity<ResponseStructure<List<Passenger>>>(response, HttpStatus.OK);
	}

	// getPaymentDetailsOfABooking
	public ResponseEntity<ResponseStructure<Payment>> getPaymentDetailsOfABooking(Integer bookingId) {
		ResponseStructure<Payment> response = new ResponseStructure<Payment>();
		Optional<Payment> payment = bookingDao.getPaymentDetailsOfABooking(bookingId);
		if (payment.isEmpty())
			throw new IdNotFoundException("Based on your id '" + bookingId + "', No payment exist in DB...⚠️");

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Hear is your Payment details...👇🏽");
		response.setData(payment.get());
		return new ResponseEntity<ResponseStructure<Payment>>(response, HttpStatus.OK);

	}

	// update booking details
	public ResponseEntity<ResponseStructure<Booking>> updateBookingStatus(Integer bookingId, BookingStatus status) {
		ResponseStructure<Booking> response = new ResponseStructure<Booking>();
		Optional<Booking> booking = bookingDao.getBookingById(bookingId);
		if (booking.isEmpty())
			throw new IdNotFoundException("Based on your id '" + bookingId + "' No record exist in DB...⚠️");

		booking.get().setStatus(status);
		Booking updatedBooking = bookingDao.addBooking(booking.get());
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Here is your Updated Booking record...👇🏽");
		response.setData(updatedBooking);
		return new ResponseEntity<ResponseStructure<Booking>>(response, HttpStatus.OK);

	}

	// delete booking
	public ResponseEntity<ResponseStructure<String>> deleteBooking(Integer bookingId) {
		ResponseStructure<String> response = new ResponseStructure<String>();
		Optional<Booking> booking = bookingDao.getBookingById(bookingId);
		if (booking.isEmpty())
			throw new IdNotFoundException("Based on your id '" + bookingId + "', no record exist in DB to delete...⚠️");

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Booking deleted Successfully...✌️");
		response.setData(bookingDao.deleteBooking(booking.get()));
		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.OK);

	}

	// get Booking By Pagination And Sorting
	public ResponseEntity<ResponseStructure<Page<Booking>>> getBookingByPageAndSort(Integer pageNumber,
			Integer pageSize, String field) {
		ResponseStructure<Page<Booking>> response = new ResponseStructure<Page<Booking>>();
		Page<Booking> bookings = bookingDao.getBookingByPageAndSort(pageNumber, pageSize, field);
		if (bookings.isEmpty())
			throw new NoRecordAvailableException("No Booking records are avilable...⚠️ ");

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Here your Payment records paged and sorted Based on '"+ field +"'...✌️");
		response.setData(bookings);
		return new ResponseEntity<ResponseStructure<Page<Booking>>>(response, HttpStatus.OK);

	}

}
