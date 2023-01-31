package com.globallogic.xlstodatabase.serviceimpl;

import org.apache.poi.xssf.XLSBUnsupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.xlstodatabase.dto.EmployeeDto;
import com.globallogic.xlstodatabase.exception.ExcelReadingException;
import com.globallogic.xlstodatabase.exception.Response;
import com.globallogic.xlstodatabase.modal.Employee;
import com.globallogic.xlstodatabase.repository.EmployeeRepository;
import com.globallogic.xlstodatabase.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	
	@Autowired
	EmployeeRepository employeeRepository;
	
	Logger logger= LoggerFactory.getLogger(EmployeeServiceImpl.class);
	@Override
	public Object addEmployee(EmployeeDto employeeDto) {
		try {
			logger.info("Inside addEmployee of EmployeeServiceImpl");
			Employee employee= new Employee();
			employee.setEid(employeeDto.getEid());
			employee.setEmail(employeeDto.getEmail());
			employee.setFirstName(employeeDto.getFirstName());
			employee.setLastName(employeeDto.getLastName());
			employee.setLocation(employeeDto.getLocation());
			employee.setProjectCode(employeeDto.getProjectCode());
			employeeRepository.save(employee);
			return new Response<>("1","Employee details saved succesfully");
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new ExcelReadingException("Getting error in saving employee");
		}
	}

}
