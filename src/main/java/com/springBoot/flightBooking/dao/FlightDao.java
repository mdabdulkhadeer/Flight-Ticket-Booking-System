package com.springBoot.flightBooking.dao;

import java.util.List;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.springBoot.flightBooking.entity.Flight;
import com.springBoot.flightBooking.repository.FlightRepository;

@Repository
public class FlightDao {
	
	@Autowired
	private FlightRepository flightRepository;
	
	public Flight addFlight(Flight flight) {
		return flightRepository.save(flight);
	}
	
	public List<Flight> getAllFlights(){
		return flightRepository.findAll();
	}
	public Optional<Flight> getFlightById(Integer id) {
		return flightRepository.findById(id);
		
	}
	
	public Flight updateFlight(Flight flight) {
		return flightRepository.save(flight);
	}

	public void deleteFlight(Flight flight) {
		flightRepository.delete(flight);
	}
	
	public List<Flight> getFlightBySourceAndDestination(String source, String destination) {
		return flightRepository.findFlightBySourceAndDestination(source, destination);
	}
	public List<Flight> getFlightByAirlines(String airline) {
		return flightRepository.findFlightByAirline(airline);
	}
	
	public Page<Flight> getFlightByPageAndSort(int pageNumber, int pageSize, String field) {
		return flightRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(field).ascending()));
	}

}
