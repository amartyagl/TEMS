package com.globallogic.xlstodatabase.service;

import com.globallogic.xlstodatabase.dto.MeetingDetailsDto;
import com.globallogic.xlstodatabase.dto.RegisterEmployeeDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;

import java.util.List;

public interface RegisteredEmployeeService {
    RegisterEmployeeDto register(RegisterEmployeeDto registerEmployeeDto) throws MeetingNotExist, EmployeeNotFound;
    List<MeetingDetailsDto> getRegisteredMeeting(Long eid);
}
