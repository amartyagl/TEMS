package com.globallogic.xlstodatabase.controller;

import com.globallogic.xlstodatabase.dto.CreateMeetingDto;
import com.globallogic.xlstodatabase.dto.MeetingDetailsDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.globallogic.xlstodatabase.dto.SmeTopicDto;
import com.globallogic.xlstodatabase.service.MeetingDetailsService;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/meeting")
public class MeetingDetailsController {

	Logger logger = LoggerFactory.getLogger(MeetingDetailsController.class);

	@Autowired
	MeetingDetailsService meetingDetailsService;

	@PostMapping(value = "/addFutureMeeting")
	public ResponseEntity<String> addMeeting(@RequestBody CreateMeetingDto createMeetingDto) throws Exception {
		logger.info("in meetingDetails controller Request for addMeeting ");
		meetingDetailsService.createMeeting(createMeetingDto);
		return new ResponseEntity<String>("Meeting created successfully", HttpStatus.OK);
	}

	@PostMapping(value = "/updateSmeAndTopic")
	public ResponseEntity<Object> updateSmeAnsdTopic(@RequestBody SmeTopicDto smeTopicDto) throws SMESubjectAvailiability, EmployeeNotFound {
		logger.info("Request for updateSmeAnsdTopic {}", smeTopicDto);
		return new ResponseEntity<Object>(meetingDetailsService.updateSMEAndTopic(smeTopicDto), HttpStatus.OK);

	}
	
	
	@GetMapping(value = "/getMeetingDetailsBySmeId")
	public ResponseEntity<Object> getMeetingDetailsSpecificSme(@RequestParam Long smeId) {
		logger.info("Request for getMeetingDetailsSpecificSme {}",smeId);
		return new ResponseEntity<Object>(meetingDetailsService.getMeetingDetailsSpecificSME(smeId), HttpStatus.OK);
	}
	@GetMapping(value = "/getMeetingDetailsOfNext2Week")
	public ResponseEntity<List<MeetingDetailsDto>> getMeetingDetailsofNext2Week() throws ParseException, MeetingNotExist {
		logger.info("Request for getMeetingDetailsOfNext2Week");
		return new ResponseEntity<List<MeetingDetailsDto>>(meetingDetailsService.getMeetingDetailsFor2Week(), HttpStatus.OK);
	}
	@GetMapping(value = "/getLeaderboard")
	public ResponseEntity<Map<String,Object>> getLeaderboard() throws ParseException {
		logger.info("Request for getLeaderboard");
		return new ResponseEntity<Map<String,Object>>(meetingDetailsService.getLeaderBoard(), HttpStatus.OK);
	}
	@GetMapping(value = "/getAllMeetings")
	ResponseEntity<List<MeetingDetailsDto>> getAllMeetings()
	{
		logger.info("Request for getting all the meetings list");
		return new ResponseEntity<List<MeetingDetailsDto>>(meetingDetailsService.getAllMeetings(),HttpStatus.OK);
	}

}
