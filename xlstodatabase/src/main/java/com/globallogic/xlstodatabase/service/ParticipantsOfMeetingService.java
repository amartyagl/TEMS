package com.globallogic.xlstodatabase.service;

import com.globallogic.xlstodatabase.dto.EmployeeDto;
import com.globallogic.xlstodatabase.dto.MeetingDto;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

public interface ParticipantsOfMeetingService {

	Object saveExcel();
	Object getAllMeetingsParticipantsList();
	List<MeetingDto> getParticipantsByMeetingId(String meetingId);
	Object getParticipantsAssesmentScoreByMeetingId(String meetingId);
	List<EmployeeDto> listOfAbsentees(String meetingId);
	Object getAssesmentScoreByMeetingIdAndEid(String eid,String meetingId);
	Object getParticipantsDetailsBetweenParticularDate(long eid, Date fromDate, Date toDate);
}
