package com.globallogic.xlstodatabase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.xlstodatabase.dto.EmployeeDto;
import com.globallogic.xlstodatabase.service.EmployeeService;

@RestController
public class EmployeeController {
	
	Logger logger= LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeService;
	
	@PostMapping(value="/v1/addEmployee")
	public ResponseEntity<Object> addEmployee(@RequestBody EmployeeDto employeeDto){
		logger.info("Request for addEmployee {}", employeeDto);
		return new ResponseEntity<Object>(employeeService.addEmployee(employeeDto),HttpStatus.OK);
		
	}

}
