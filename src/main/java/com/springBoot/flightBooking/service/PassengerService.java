package com.springBoot.flightBooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springBoot.flightBooking.dao.PassengerDao;
import com.springBoot.flightBooking.dto.ResponseStructure;
import com.springBoot.flightBooking.entity.Passenger;
import com.springBoot.flightBooking.exception.IdNotFoundException;
import com.springBoot.flightBooking.exception.NoRecordAvailableException;

@Service
public class PassengerService {
	
	@Autowired
	private PassengerDao passengerDao;
	
	public ResponseEntity<ResponseStructure<Passenger>> addPassenger(Passenger passenger) {
		ResponseStructure<Passenger> response = new ResponseStructure<Passenger>();

		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Passenger Record Was Saved Successfully...✅");
		response.setData(passengerDao.addPassenger(passenger));

		return new ResponseEntity<ResponseStructure<Passenger>>(response, HttpStatus.CREATED);

	}
	
	public ResponseEntity<ResponseStructure<List<Passenger>>> gettAllPAssenger(){
		ResponseStructure<List<Passenger>> response = new ResponseStructure<List<Passenger>>();

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Passenger Record Was Retrieved Successfully...✅");
		response.setData(passengerDao.getAllPAssenger());

		return new ResponseEntity<ResponseStructure<List<Passenger>>>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerById(Integer id) {

		ResponseStructure<Passenger> response = new ResponseStructure<Passenger>();
		Optional<Passenger> opt = passengerDao.getPassengerById(id);

		if (opt.isPresent()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Passenger Record with id '" + id + "' was retrieved...✅");
			response.setData(opt.get());

			return new ResponseEntity<ResponseStructure<Passenger>>(response, HttpStatus.OK);

		} else {

			throw new IdNotFoundException("Passenger Record with id '" + id + "' was not exist in Database...😞");
		}

	}

	public ResponseEntity<ResponseStructure<Passenger>> updatePassenger(Passenger passenger) {
		ResponseStructure<Passenger> response = new ResponseStructure<Passenger>();

		if (passenger.getId() == null)
			throw new IdNotFoundException("Id is needed to update...⚠️");

		Optional<Passenger> opt = passengerDao.getPassengerById(passenger.getId());

		if (opt.isPresent()) {
			
			Passenger existingPassenger = opt.get();

		    // 🔹 Merge new data while preserving booking
		    existingPassenger.setName(passenger.getName());
		    existingPassenger.setAge(passenger.getAge());
		    existingPassenger.setGender(passenger.getGender());
		    existingPassenger.setSeatNumber(passenger.getSeatNumber());
		    existingPassenger.setContactNo(passenger.getContactNo());

			Passenger savedPassenger=passengerDao.addPassenger(existingPassenger);
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Passenger Record was Updated...✅");
			response.setData(savedPassenger);

			return new ResponseEntity<ResponseStructure<Passenger>>(response, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Passenger Record was unable to Update...😵");
		}

	}


	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengerByContactNo(long contactNo) {
		ResponseStructure<List<Passenger>> response = new ResponseStructure<List<Passenger>>();
		List<Passenger> passengers = passengerDao.getPassengerByContactNo(contactNo);

		if (!passengers.isEmpty()) {

			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Passenger record registered with '" + contactNo + "' was retrieved successfully...✅");
			response.setData(passengers);

			return new ResponseEntity<ResponseStructure<List<Passenger>>>(response, HttpStatus.OK);

		} else
			throw new NoRecordAvailableException("Passengers registered with '" + contactNo + "' was not found...😒");
	}

	public ResponseEntity<ResponseStructure<Page<Passenger>>> getPassengerByPageAndSort(int pageNumber, int pageSize, String field) {
		Page<Passenger> passengers = passengerDao.getPassengerByPageAndSortAscending(pageNumber, pageSize, field);
		ResponseStructure<Page<Passenger>> response = new ResponseStructure<Page<Passenger>>();
		if (!passengers.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Here your Passenger records paged and sorted Based on '"+ field +"'...✌️");
			response.setData(passengers);
			return new ResponseEntity<ResponseStructure<Page<Passenger>>>(response, HttpStatus.OK);

		} else
			throw new NoRecordAvailableException("No Passenger record found...😵");

	}


}
