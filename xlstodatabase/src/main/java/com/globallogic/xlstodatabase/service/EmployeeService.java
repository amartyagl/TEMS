package com.globallogic.xlstodatabase.service;

import com.globallogic.xlstodatabase.dto.EmployeeHoursDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import org.springframework.stereotype.Service;
import com.globallogic.xlstodatabase.dto.EmployeeDto;

import java.util.List;
import java.util.Set;

@Service
public interface EmployeeService {

	 Object addEmployee(EmployeeDto employeeDto);
	 Object updateEmployee(EmployeeDto employeeDto) throws EmployeeNotFound;
	 void deleteEmployee(Long eid) throws EmployeeNotFound;
	 Object getSmeDetails(String getSmeByMeetingId) throws MeetingNotExist;
	 List<EmployeeDto> getAllEmployee();
	 EmployeeHoursDto getTotalHours(EmployeeHoursDto employeeHoursDto);
	 Set<EmployeeDto> getSmeDetailsByTopic(String topic) throws SMESubjectAvailiability;



}
