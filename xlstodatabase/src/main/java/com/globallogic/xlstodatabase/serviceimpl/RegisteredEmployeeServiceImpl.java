package com.globallogic.xlstodatabase.serviceimpl;

import com.globallogic.xlstodatabase.dto.MeetingDetailsDto;
import com.globallogic.xlstodatabase.dto.RegisterEmployeeDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.modal.Employee;
import com.globallogic.xlstodatabase.modal.MeetingDetails;
import com.globallogic.xlstodatabase.modal.RegisteredEmployee;
import com.globallogic.xlstodatabase.repository.EmployeeRepository;
import com.globallogic.xlstodatabase.repository.MeetingDetailsRepository;
import com.globallogic.xlstodatabase.repository.RegisteredEmployeeRepo;
import com.globallogic.xlstodatabase.service.RegisteredEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RegisteredEmployeeServiceImpl implements RegisteredEmployeeService {

    @Autowired
    MeetingDetailsRepository meetingDetailsRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RegisteredEmployeeRepo registeredEmployeeRepo;
    @Override
    public RegisterEmployeeDto register(RegisterEmployeeDto registerEmployeeDto) throws MeetingNotExist, EmployeeNotFound {
        log.info("In RegisterEmployeeService register method getting meeting ");
        Optional<MeetingDetails> meetingDetails=meetingDetailsRepository.findById(registerEmployeeDto.getMid());
        RegisteredEmployee registeredEmployee=new RegisteredEmployee();
        log.info("In RegisterEmployeeService register method getting employee ");
        Optional<Employee> employee=employeeRepository.findById(registerEmployeeDto.getEid());
        if(meetingDetails.isPresent())
        {
            registeredEmployee.setMid(meetingDetails.get());
        }
        else
        {
            throw new MeetingNotExist("No meeting available to register");
        }
        if (employee.isPresent())
        {
            registeredEmployee.setEid(employee.get());
        }
        else
        {
            throw new EmployeeNotFound("Employee not found with this id");
        }
        log.info("In RegisterEmployeeService register method saving registered user details ");
        registeredEmployeeRepo.save(registeredEmployee);
        return registerEmployeeDto;
    }

    @Override
    public List<MeetingDetailsDto> getRegisteredMeeting(Long eid) {
        log.info("In RegisterEmployeeService getRegisteredMeeting method getting meeting list");
        List<RegisteredEmployee> registeredEmployeesList=registeredEmployeeRepo.findByEid(eid);
        List<MeetingDetailsDto> meetingDetailsDtoList=new ArrayList<>();
        for (RegisteredEmployee registeredEmployee:registeredEmployeesList)
        {
            MeetingDetailsDto meetingDetailsDto=new MeetingDetailsDto();
            MeetingDetails meetingDetails=registeredEmployee.getMid();
            meetingDetailsDto.setMeetingId(meetingDetails.getMeetingId());
            meetingDetailsDto.setMeetingDate(String.valueOf(meetingDetails.getMeetingDate()));
            meetingDetailsDto.setTopic(meetingDetails.getTopic());
            meetingDetailsDto.setHours(meetingDetails.getTotalHours());
            meetingDetailsDto.setEid(meetingDetails.getMeetingAnchor().getEid());
            meetingDetailsDtoList.add(meetingDetailsDto);
        }
        return meetingDetailsDtoList;
    }
}
