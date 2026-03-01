package com.springBoot.flightBooking.controller;

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

import com.springBoot.flightBooking.dto.ResponseStructure;
import com.springBoot.flightBooking.entity.Flight;
import com.springBoot.flightBooking.service.FlightService;

@RequestMapping("/flight")
@RestController
public class FlightController {

	@Autowired
	private FlightService flightService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Flight>> addFlight(@RequestBody Flight flight){
		return flightService.addFlight(flight);
	}

	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlights(){
		return flightService.getAllFlights();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Flight>> getFlightById(@PathVariable Integer id) {
		return flightService.getFlightById(id);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<Flight>> updateFlight(@RequestBody Flight flight) {
		return flightService.updateFlight(flight);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteFlight(@PathVariable Integer id) {
		return flightService.deleteFlight(id);
	}
	
	@GetMapping("/getBySourceAndDestination/{source}/{destination}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightBySourceAndDestination(@PathVariable String source,@PathVariable String destination) {
		return flightService.getFlightBySourceAndDestination(source, destination);
	}
	
	@GetMapping("/getByAirline/{airline}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightByAirlines(@PathVariable String airline) {
		return flightService.getFlightByAirlines(airline);
	}

	@GetMapping("/pageAndSort/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<Flight>>>getFlightByPageAndSort(@PathVariable int pageNumber,@PathVariable int pageSize,@PathVariable String field){
		return flightService.getFlightByPageAndSort(pageNumber, pageSize, field);
	}



	
}
