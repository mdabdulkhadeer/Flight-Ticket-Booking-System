package com.springBoot.flightBooking.exception;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springBoot.flightBooking.dto.ResponseStructure;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleIdNotFoundException(IdNotFoundException e) {
		ResponseStructure<String> response = new ResponseStructure<String>();

		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Failure");
		response.setData(e.getMessage());

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
	}

	
	

	@ExceptionHandler(NoRecordAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> handleNoRecordAvailableException(NoRecordAvailableException e){
		ResponseStructure<String> response = new ResponseStructure<String>();

		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Failure");
		response.setData(e.getMessage());

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
	}

}
