package com.intelliment.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.intelliment.exception.CountApiErrorResponse;
import com.intelliment.exception.CountApiException;

/**
 * This Controller handles all types of exception throws in Count-Api
 * Application.
 * 
 * @author Intelliment
 *
 */
@ControllerAdvice
public class ExceptionController {

	private static final Logger logger = Logger.getLogger(ExceptionController.class);

	/**
	 * This method handles all types of CountApiException and return a response
	 * to user to take appropriate action.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(CountApiException.class)
	public ResponseEntity<CountApiErrorResponse> handleCustomException(CountApiException ex) {
		CountApiErrorResponse errorResponse = new CountApiErrorResponse();
		errorResponse.setErrorCode(ex.getErrCode());
		errorResponse.setMessage(ex.getErrMsg());
		logger.error(ex.getLocalizedMessage());
		return new ResponseEntity<CountApiErrorResponse>(errorResponse, HttpStatus.OK);

	}

	/**
	 * This method handles all generic types of exception and return a response
	 * to user to take appropriate action.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CountApiErrorResponse> handleAllException(Exception ex) {
		CountApiErrorResponse errorResponse = new CountApiErrorResponse();
		logger.error(ex.getLocalizedMessage());
		errorResponse.setErrorCode("System Error");
		errorResponse.setMessage("OOPS !!! Error Occurs,Kindly contact Admin");
		return new ResponseEntity<CountApiErrorResponse>(errorResponse, HttpStatus.OK);

	}

}
