package com.globallogic.xlstodatabase.controller;

import com.globallogic.xlstodatabase.dto.MeetingDetailsDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.globallogic.xlstodatabase.dto.SmeTopicDto;
import com.globallogic.xlstodatabase.service.MeetingDetailsService;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
public class MeetingDetailsController {

	Logger logger = LoggerFactory.getLogger(MeetingDetailsController.class);

	@Autowired
	MeetingDetailsService meetingDetailsService;

	@PostMapping(value = "/v1/addFutureMeeting")
	public ResponseEntity<String> addMeeting(@RequestBody MeetingDetailsDto meetingDetailsDto) throws Exception {
		logger.info("in meetingDetails controller Request for addMeeting ");
		meetingDetailsService.createMeeting(meetingDetailsDto);
		return new ResponseEntity<String>("Meeting created successfully", HttpStatus.OK);
	}

	@PostMapping(value = "/v1/updateSmeAndTopic")
	public ResponseEntity<Object> updateSmeAnsdTopic(@RequestBody SmeTopicDto smeTopicDto) throws SMESubjectAvailiability, EmployeeNotFound {
		logger.info("Request for updateSmeAnsdTopic {}", smeTopicDto);
		return new ResponseEntity<Object>(meetingDetailsService.updateSMEAndTopic(smeTopicDto), HttpStatus.OK);

	}
	
	
	@GetMapping(value = "/v1/getMeetingDetailsBySmeId")
	public ResponseEntity<Object> getMeetingDetailsSpecificSme(@RequestParam Long smeId) {
		logger.info("Request for getMeetingDetailsSpecificSme {}",smeId);
		return new ResponseEntity<Object>(meetingDetailsService.getMeetingDetailsSpecificSME(smeId), HttpStatus.OK);
	}
	@GetMapping(value = "/v1/getMeetingDetailsOfNext2Week")
	public ResponseEntity<List<MeetingDetailsDto>> getMeetingDetailsofNext2Week() throws ParseException, MeetingNotExist {
		logger.info("Request for getMeetingDetailsOfNext2Week");
		return new ResponseEntity<List<MeetingDetailsDto>>(meetingDetailsService.getMeetingDetailsFor2Week(), HttpStatus.OK);
	}
	@GetMapping(value = "/v1/getLeaderboard")
	public ResponseEntity<Map<String,Object>> getLeaderboard() throws ParseException {
		logger.info("Request for getLeaderboard");
		return new ResponseEntity<Map<String,Object>>(meetingDetailsService.getLeaderBoard(), HttpStatus.OK);
	}
	@GetMapping(value = "/v1/getAllMeetings")
	ResponseEntity<List<MeetingDetailsDto>> getAllMeetings()
	{
		logger.info("Request for getting all the meetings list");
		return new ResponseEntity<List<MeetingDetailsDto>>(meetingDetailsService.getAllMeetings(),HttpStatus.OK);
	}

}
