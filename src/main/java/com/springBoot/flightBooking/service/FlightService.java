package com.springBoot.flightBooking.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.springBoot.flightBooking.dao.FlightDao;
import com.springBoot.flightBooking.dto.ResponseStructure;
import com.springBoot.flightBooking.entity.Flight;
import com.springBoot.flightBooking.exception.IdNotFoundException;
import com.springBoot.flightBooking.exception.NoRecordAvailableException;
import org.springframework.data.domain.Page;

@Service
public class FlightService {

	@Autowired
	private FlightDao flightDao;

	public ResponseEntity<ResponseStructure<Flight>> addFlight(Flight flight) {
		ResponseStructure<Flight> response = new ResponseStructure<Flight>();

		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Flight Record Was Saved Successfully...✅");
		response.setData(flightDao.addFlight(flight));

		return new ResponseEntity<ResponseStructure<Flight>>(response, HttpStatus.CREATED);

	}

	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlights() {
		ResponseStructure<List<Flight>> response = new ResponseStructure<List<Flight>>();
		List<Flight> flights = flightDao.getAllFlights();
		if (!flights.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All Flight Records was retrieved Successfully...✅");
			response.setData(flights);

			return new ResponseEntity<ResponseStructure<List<Flight>>>(response, HttpStatus.OK);
		} else
			throw new NoRecordAvailableException("No Records are available in Database....😵");

	}

	public ResponseEntity<ResponseStructure<Flight>> getFlightById(Integer id) {

		ResponseStructure<Flight> response = new ResponseStructure<Flight>();
		Optional<Flight> opt = flightDao.getFlightById(id);

		if (opt.isPresent()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flight Record with id '" + id + "' was retrieved...✅");
			response.setData(opt.get());

			return new ResponseEntity<ResponseStructure<Flight>>(response, HttpStatus.OK);

		} else {

			throw new IdNotFoundException("Flight Record with id '" + id + "' was not exist in Database...😵");
		}

	}

	public ResponseEntity<ResponseStructure<Flight>> updateFlight(Flight flight) {
		ResponseStructure<Flight> response = new ResponseStructure<Flight>();

		if (flight.getId() == null)
			throw new IdNotFoundException("Id is needed to update...⚠️");

		Optional<Flight> opt = flightDao.getFlightById(flight.getId());

		if (opt.isPresent()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flight Record was Updated...✅");
			response.setData(flightDao.updateFlight(flight));

			return new ResponseEntity<ResponseStructure<Flight>>(response, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Flight Record was not Updated...😵");
		}

	}

	public ResponseEntity<ResponseStructure<String>> deleteFlight(Integer id) {
		Optional<Flight> opt = flightDao.getFlightById(id);

		ResponseStructure<String> response = new ResponseStructure<String>();

		if (opt.isPresent()) {
			flightDao.deleteFlight(opt.get());
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flight Record with id '" + id + "' deleted...✅");
			response.setData("Success");

			return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.OK);

		} else {
			throw new IdNotFoundException("Flight Record with id '" + id + "' was not exist in Database...😞");

		}

	}

	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightBySourceAndDestination(String source,
			String destination) {
		ResponseStructure<List<Flight>> response = new ResponseStructure<List<Flight>>();
		List<Flight> flights = flightDao.getFlightBySourceAndDestination(source, destination);

		if (!flights.isEmpty()) {

			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flights record, taking off from '" + source + "' and landing in '" + destination
					+ "' was retrieved successfully...✅");
			response.setData(flights);

			return new ResponseEntity<ResponseStructure<List<Flight>>>(response, HttpStatus.OK);

		} else
			throw new NoRecordAvailableException("Flight record, taking off from '" + source + "' and landing in '"
					+ destination + "' was not found...😵");
	}

	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightByAirlines(String airline) {
		ResponseStructure<List<Flight>> response = new ResponseStructure<List<Flight>>();
		List<Flight> flights = flightDao.getFlightByAirlines(airline);

		if (!flights.isEmpty()) {

			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Flights record running in '" + airline + "' was retrieved successfully...✅");
			response.setData(flights);

			return new ResponseEntity<ResponseStructure<List<Flight>>>(response, HttpStatus.OK);

		} else
			throw new NoRecordAvailableException("Flights running in '" + airline + "' was not found...😵");
	}

	public ResponseEntity<ResponseStructure<Page<Flight>>> getFlightByPageAndSort(int pageNumber, int pageSize,String field) {
		Page<Flight> flights = flightDao.getFlightByPageAndSort(pageNumber, pageSize, field);
		ResponseStructure<Page<Flight>> response = new ResponseStructure<Page<Flight>>();
		if (!flights.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Here your Flight records are paged and sorted Based on '"+ field +"'...✌️");
			response.setData(flights);
			return new ResponseEntity<ResponseStructure<Page<Flight>>>(response, HttpStatus.FOUND);

		} else
			throw new NoRecordAvailableException("No Flight record found...😒");

	}

}
