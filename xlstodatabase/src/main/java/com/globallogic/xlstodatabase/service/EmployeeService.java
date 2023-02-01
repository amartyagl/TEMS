package com.globallogic.xlstodatabase.service;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.globallogic.xlstodatabase.dto.EmployeeDto;

@Service
public interface EmployeeService {

	public Object addEmployee(EmployeeDto employeeDto);

	public Object getSmeDetails(String getSmeByMeetingId);

}
