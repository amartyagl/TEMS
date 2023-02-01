package com.globallogic.xlstodatabase.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface ParticipantsOfMeetingService {

	Object saveExcel() throws GeneralSecurityException, IOException;
	Object getAllMeetingsParticipantsList();
	Object getParticipantsByMeetingId(String meetingId);
	Object getParticipantsAssesmentScoreByMeetingId(String meetingId);


}
