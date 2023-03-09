package com.globallogic.xlstodatabase.controller;

import com.globallogic.xlstodatabase.dto.EmployeeHoursDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.globallogic.xlstodatabase.dto.EmployeeDto;
import com.globallogic.xlstodatabase.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {
	
	Logger logger= LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeService;
	
	@PostMapping(value="/addEmployee")
	public ResponseEntity<Object> addEmployee(@RequestBody EmployeeDto employeeDto){
		logger.info("Request for addEmployee {}", employeeDto);
		return new ResponseEntity<>(employeeService.addEmployee(employeeDto),HttpStatus.OK);
		
	}
	@PutMapping(value="/updateEmployee")
	public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeDto employeeDto) throws EmployeeNotFound {
		logger.info("Request for updateEmployee {}", employeeDto);
		employeeService.updateEmployee(employeeDto);
		return new ResponseEntity<>("Employee updated successfully",HttpStatus.OK);

	}
	@DeleteMapping(value="/deleteEmployee")
	public ResponseEntity<Object> deleteEmployee(@RequestParam Long eid) throws EmployeeNotFound {
		logger.info("Request for deleteEmployee {}", eid);
		employeeService.deleteEmployee(eid);
		return new ResponseEntity<>("Employee deleted Successfully",HttpStatus.OK);

	}
	
	@GetMapping(value="/getSmeByMeetingId")
	public ResponseEntity<Object> getSmeByMeetingId(@RequestParam String meetingId) throws MeetingNotExist {
		logger.info("Request for getSmeByMeetingId {}", meetingId);
		return new ResponseEntity<Object>(employeeService.getSmeDetails(meetingId),HttpStatus.OK);
		
	}
	@GetMapping(value="/getAllEmployee")
	public ResponseEntity<List<EmployeeDto>> getEmployeeList(){
		logger.info("In controller for getting list of all employee");
		return new ResponseEntity<List<EmployeeDto>>(employeeService.getAllEmployee(),HttpStatus.OK);
	}

	@GetMapping(value = "/getTotalHours")
	public ResponseEntity<EmployeeHoursDto> getTotalHours(@RequestBody EmployeeHoursDto employeeHoursDto)
	{
		logger.info("Inside controller for getting totalHours");
		return new ResponseEntity<>(employeeService.getTotalHours(employeeHoursDto),HttpStatus.OK);
	}

	@GetMapping(value="/getSmeByTopic")
	public ResponseEntity<Object> getSmeByTopic(@RequestParam String topic) throws SMESubjectAvailiability {
		logger.info("Request for getSmeByTopic {}", topic);
		return new ResponseEntity<>(employeeService.getSmeDetailsByTopic(topic),HttpStatus.OK);

	}

}
