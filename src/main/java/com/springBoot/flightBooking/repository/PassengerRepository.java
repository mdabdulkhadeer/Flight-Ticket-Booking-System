package com.springBoot.flightBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springBoot.flightBooking.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer>{
	
	public List<Passenger> getPassengerByContactNo(long contactNo);

}
