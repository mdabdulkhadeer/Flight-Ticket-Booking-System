package com.springBoot.flightBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.springBoot.flightBooking.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer>{

	List<Flight> findFlightBySourceAndDestination(String source, String destination);
	List<Flight> findFlightByAirline(String airline);
	

}
