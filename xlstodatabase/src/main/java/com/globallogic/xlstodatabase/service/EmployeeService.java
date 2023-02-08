package com.globallogic.xlstodatabase.service;

import com.globallogic.xlstodatabase.dto.EmployeeHoursDto;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.modal.Employee;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.globallogic.xlstodatabase.dto.EmployeeDto;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public interface EmployeeService {

	 Object addEmployee(EmployeeDto employeeDto);
	 Object getSmeDetails(String getSmeByMeetingId) throws MeetingNotExist;
	 List<EmployeeDto> getAllEmployee();
	 EmployeeHoursDto getTotalHours(EmployeeHoursDto employeeHoursDto);
	 Set<EmployeeDto> getSmeDetailsByTopic(String topic);



}
