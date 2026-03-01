package com.springBoot.flightBooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springBoot.flightBooking.dto.ResponseStructure;
import com.springBoot.flightBooking.entity.Passenger;
import com.springBoot.flightBooking.service.PassengerService;

@RequestMapping("/passenger")
@RestController
public class PassengerController {

	@Autowired
	private PassengerService passengerService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Passenger>> addPassenger(@RequestBody Passenger passenger) {
		return passengerService.addPassenger(passenger);
	}

	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Passenger>>> gettAllPAssenger() {
		return passengerService.gettAllPAssenger();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Passenger>> getBookById(@PathVariable Integer id) {
		return passengerService.getPassengerById(id);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<Passenger>> updateBook(@RequestBody Passenger passenger) {
		return passengerService.updatePassenger(passenger);
	}

	@GetMapping("/getByContact/{contactNo}")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengerByContactNo(@PathVariable Long contactNo) {
		return passengerService.getPassengerByContactNo(contactNo);
	}

	@GetMapping("/pageAndSort/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<Passenger>>> getPassengerByPageAndSort(
			@PathVariable Integer pageNumber, @PathVariable Integer pageSize, @PathVariable String field) {
		return passengerService.getPassengerByPageAndSort(pageNumber, pageSize, field);
	}

}
