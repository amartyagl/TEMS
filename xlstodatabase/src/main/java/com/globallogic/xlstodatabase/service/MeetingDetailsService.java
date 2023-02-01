package com.globallogic.xlstodatabase.service;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.globallogic.xlstodatabase.dto.SmeTopicDto;

@Service
public interface MeetingDetailsService {
	
	public Object updateSMEAndTopic(SmeTopicDto smeTopicDto);

	public Object getMeetingDetailsSpecificSME(Long smeId);

}
