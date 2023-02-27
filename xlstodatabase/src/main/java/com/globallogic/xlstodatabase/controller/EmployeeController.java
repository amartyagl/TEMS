package com.globallogic.xlstodatabase.controller;

import com.globallogic.xlstodatabase.dto.EmployeeHoursDto;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
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

import java.util.List;

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
	public ResponseEntity<Object> getSmeByMeetingId(@RequestParam String meetingId) throws MeetingNotExist {
		logger.info("Request for getSmeByMeetingId {}", meetingId);
		return new ResponseEntity<Object>(employeeService.getSmeDetails(meetingId),HttpStatus.OK);
		
	}
	@GetMapping(value="/v1/getAllEmployee")
	public ResponseEntity<List<EmployeeDto>> getEmployeeList(){
		logger.info("In controller for getting list of all employee");
		return new ResponseEntity<List<EmployeeDto>>(employeeService.getAllEmployee(),HttpStatus.OK);
	}

	@GetMapping(value = "v1/getTotalHours")
	public ResponseEntity<EmployeeHoursDto> getTotalHours(@RequestBody EmployeeHoursDto employeeHoursDto)
	{
		logger.info("Inside controller for getting totalHours");
		return new ResponseEntity<>(employeeService.getTotalHours(employeeHoursDto),HttpStatus.OK);
	}

	@GetMapping(value="/v1/getSmeByTopic")
	public ResponseEntity<Object> getSmeByTopic(@RequestParam String topic) throws SMESubjectAvailiability {
		logger.info("Request for getSmeByTopic {}", topic);
		return new ResponseEntity<Object>(employeeService.getSmeDetailsByTopic(topic),HttpStatus.OK);

	}

}
