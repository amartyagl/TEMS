package com.globallogic.xlstodatabase.controller;

import com.globallogic.xlstodatabase.dto.EmployeeDto;
import com.globallogic.xlstodatabase.dto.MeetingDetailsDto;
import com.globallogic.xlstodatabase.dto.RegisterEmployeeDto;
import com.globallogic.xlstodatabase.dto.SMEDetailsDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import com.globallogic.xlstodatabase.service.RegisteredEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/meetingemployee")
public class RegisterdEmployeeController {

    @Autowired
    RegisteredEmployeeService employeeService;

    @PostMapping("/register")
    ResponseEntity<String> registerEmployee(@RequestBody RegisterEmployeeDto registerEmployeeDto) throws MeetingNotExist, EmployeeNotFound {
        log.info("In RegisterdEmployeeController for registering");
        employeeService.register(registerEmployeeDto);
        return new ResponseEntity<>("Employee Registered successfully", HttpStatus.OK);
    }
    @GetMapping(value="/getRegisteredMeetingByEid")
    public ResponseEntity<List<MeetingDetailsDto>> getEmployeeList(@RequestParam long eid){
        log.info("In RegisterdEmployeeController for getting list of all registered meeting");
        return new ResponseEntity<List<MeetingDetailsDto>>(employeeService.getRegisteredMeeting(eid),HttpStatus.OK);
    }
}
