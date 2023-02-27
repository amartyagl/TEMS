package com.globallogic.xlstodatabase.serviceimpl;

import com.globallogic.xlstodatabase.dto.EmployeeHoursDto;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import org.apache.poi.xssf.XLSBUnsupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.xlstodatabase.dto.EmployeeDto;
import com.globallogic.xlstodatabase.exception.ExcelReadingException;
import com.globallogic.xlstodatabase.exception.Response;
import com.globallogic.xlstodatabase.modal.Employee;
import com.globallogic.xlstodatabase.modal.MeetingDetails;
import com.globallogic.xlstodatabase.repository.EmployeeRepository;
import com.globallogic.xlstodatabase.repository.MeetingDetailsRepository;
import com.globallogic.xlstodatabase.service.EmployeeService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	
	@Autowired
	EmployeeRepository employeeRepository;
	
	
	@Autowired
	MeetingDetailsRepository meetingDetailsRepository;

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
	
	@Override
	public Object getSmeDetails(String meetingId) throws MeetingNotExist {
			logger.info("Inside getSmeDetails of EmployeeServiceImpl");
			MeetingDetails meetingDetails= meetingDetailsRepository.findByMeetingId(meetingId);
			if(meetingDetails==null) {
				throw new MeetingNotExist("Meeting Does Not exist");
			}
            Employee employee= meetingDetails.getMeetingAnchor();
            EmployeeDto employeeDto= new EmployeeDto();
            employeeDto.setEid(employee.getEid());
            employeeDto.setEmail(employee.getEmail());
            employeeDto.setFirstName(employee.getFirstName());
            employeeDto.setLastName(employee.getLastName());
            employeeDto.setLocation(employee.getLocation());
            employeeDto.setProjectCode(employee.getProjectCode());
			return new Response<>("1","Employee fetched successfully", employeeDto);
	}
	@Override
	public List<EmployeeDto> getAllEmployee() {
		logger.info("Calling repo for getting list of all employee");
		List<Employee> employeeList=employeeRepository.findAll();
		List<EmployeeDto> employeeDtos=new ArrayList<>();
		for(Employee employee: employeeList)
		{
			EmployeeDto employeeDto=new EmployeeDto();
			employeeDto.setEid(employee.getEid());
			employeeDto.setEmail(employee.getEmail());
			employeeDto.setFirstName(employee.getFirstName());
			employeeDto.setLastName(employee.getLastName());
			employeeDto.setLocation(employee.getLocation());
			employeeDto.setProjectCode(employee.getProjectCode());
			employeeDtos.add(employeeDto);
		}
		return  employeeDtos;
	}

	@Override
	public EmployeeHoursDto getTotalHours(EmployeeHoursDto employeeHoursDto) {
		logger.info("Inside getTotalHours of EmployeeServiceImpl");
		int totalHours=meetingDetailsRepository.getTotalHours(employeeHoursDto.getStartDate(),employeeHoursDto.getEndDate(),employeeHoursDto.getEid());
		employeeHoursDto.setTotalHours(totalHours);
		return employeeHoursDto;
	}

	@Override
	public Set<EmployeeDto> getSmeDetailsByTopic(String topic) throws SMESubjectAvailiability {
		logger.info("Inside getTotalHours of getSmeDetailsByTopic");
		List<MeetingDetails> meetingDetailsList= meetingDetailsRepository.findByTopic(topic);
		Set<EmployeeDto> smeDetails=new HashSet<>();
		if(meetingDetailsList.isEmpty()) {
			throw new SMESubjectAvailiability("No employee exist for this topic");
		}
		for(MeetingDetails meetingDetails:meetingDetailsList) {
			Employee employee = meetingDetails.getMeetingAnchor();
			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setEid(employee.getEid());
			employeeDto.setEmail(employee.getEmail());
			employeeDto.setFirstName(employee.getFirstName());
			employeeDto.setLastName(employee.getLastName());
			employeeDto.setLocation(employee.getLocation());
			employeeDto.setProjectCode(employee.getProjectCode());
			smeDetails.add(employeeDto);
		}
		return smeDetails;
	}

}
