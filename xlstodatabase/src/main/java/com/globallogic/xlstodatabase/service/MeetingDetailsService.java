package com.globallogic.xlstodatabase.service;

import com.globallogic.xlstodatabase.dto.MeetingDetailsDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.globallogic.xlstodatabase.dto.SmeTopicDto;

import java.text.ParseException;

@Service
public interface MeetingDetailsService {

	public Object createMeeting(MeetingDetailsDto meetingDetailsDto) throws Exception;
	
	public Object updateSMEAndTopic(SmeTopicDto smeTopicDto) throws EmployeeNotFound, SMESubjectAvailiability;

	public Object getMeetingDetailsSpecificSME(Long smeId);

	Object getMeetingDetailsFor2Week() throws ParseException;
}
