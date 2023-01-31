package com.globallogic.xlstodatabase.advice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.globallogic.xlstodatabase.exception.ExcelReadingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import lombok.extern.slf4j.Slf4j;

/*
 * @Slf4j annotation is for adding loggeer 
 * and @RestControllerAdvice is advice for RestController 
 * which help controller class to handle exception
 * */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	Environment environment;

	/* This method is for handling validations exception on different attributes */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
		Map<String, String> errorMap = new HashMap<>();
		log.error("Error while validating input from user");
		exception.getBindingResult().getFieldErrors()
				.forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
		return errorMap;
	}

	/*
	 * This method is for handling custom exception which will be thrown when user
	 * try to access data which not exist in database
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ExcelReadingException.class)
	public Map<String, String> handleUserNotFoundException(ExcelReadingException exception) {
		Map<String, String> errorMap = new HashMap<>();
		log.error("Issue in reading data from Spreadsheet database");
		errorMap.put("errorMessage",exception.getMessage());
		return errorMap;
	}



	/*
	 * This method is for handling all the exception which will be raised from
	 * Exception class
	 */
	
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	@ExceptionHandler(SQLException.class)
	public Map<String, String> handleSQLException(SQLException exception) {
		Map<String, String> errorMap = new HashMap<>();
		log.error("Error while getting user from database {}", exception.getMessage());
		errorMap.put("errorMessage", exception.getMessage());
		return errorMap;
	}
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Map<String, String> handleParentException(Exception exception) {
		Map<String, String> errorMap = new HashMap<>();
		log.error("Some error occurred {}", exception.getMessage());
		errorMap.put("errorMessage", exception.getMessage());
		return errorMap;
	}

}
