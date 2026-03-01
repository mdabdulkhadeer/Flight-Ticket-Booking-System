package com.springBoot.flightBooking.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.springBoot.flightBooking.entity.Passenger;
import com.springBoot.flightBooking.repository.PassengerRepository;

@Repository
public class PassengerDao {
	
	@Autowired
	private PassengerRepository passengerRepository;

	public Passenger addPassenger(Passenger passenger) {
		return passengerRepository.save(passenger);
	}
	
	public List<Passenger>  getAllPAssenger (){
		return passengerRepository.findAll();
	}
	
	public Optional<Passenger> getPassengerById(Integer id) {
		return passengerRepository.findById(id);
		
	}
	
	public Passenger updatePassenger(Passenger passenger) {
		return passengerRepository.save(passenger);
	}

	
	public List<Passenger> getPassengerByContactNo(Long contactNo) {
		return passengerRepository.getPassengerByContactNo(contactNo);
	}
	
	public Page<Passenger> getPassengerByPageAndSortAscending(Integer pageNumber, Integer pageSize, String field) {
		return passengerRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(field).ascending()));
	}
	
	
}
