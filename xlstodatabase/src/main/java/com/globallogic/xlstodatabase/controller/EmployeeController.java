package com.globallogic.xlstodatabase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	
	@GetMapping(value="/v1/getSmeByMeetingId")
	public ResponseEntity<Object> getSmeByMeetingId(@RequestParam String meetingId){
		logger.info("Request for getSmeByMeetingId {}", meetingId);
		return new ResponseEntity<Object>(employeeService.getSmeDetails(meetingId),HttpStatus.OK);
		
	}

}
