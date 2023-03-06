package com.globallogic.xlstodatabase.service;

import com.globallogic.xlstodatabase.dto.CreateMeetingDto;
import com.globallogic.xlstodatabase.dto.MeetingDetailsDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.globallogic.xlstodatabase.dto.SmeTopicDto;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public interface MeetingDetailsService {


    String createMeeting(CreateMeetingDto createMeetingDto) throws Exception;

    public Object updateSMEAndTopic(SmeTopicDto smeTopicDto) throws EmployeeNotFound, SMESubjectAvailiability;

	public Object getMeetingDetailsSpecificSME(Long smeId);

	List<MeetingDetailsDto> getMeetingDetailsFor2Week() throws ParseException, MeetingNotExist;

	Map<String,Object> getLeaderBoard() throws ParseException;

    List<MeetingDetailsDto> getAllMeetings();
}
