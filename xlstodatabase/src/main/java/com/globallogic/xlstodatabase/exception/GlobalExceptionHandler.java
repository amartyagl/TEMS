package com.globallogic.xlstodatabase.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EmployeeNotFound.class)
	public Map<String, String> handleEmployeeNotFoundException(EmployeeNotFound exception) {
		Map<String, String> errorMap = new HashMap<>();
		log.error("Employee not found {}", exception.getMessage());
		errorMap.put("errorMessage", exception.getMessage());
		return errorMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ExcelReadingException.class)
	public Map<String, String> excelReadingExceptionException(ExcelReadingException exception) {
		Map<String, String> errorMap = new HashMap<>();
		log.error("error in reading excel {}", exception.getMessage());
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
