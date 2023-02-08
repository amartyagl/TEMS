package com.globallogic.xlstodatabase.controller;

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

@RestController
public class MeetingDetailsController {

	Logger logger = LoggerFactory.getLogger(MeetingDetailsController.class);

	@Autowired
	MeetingDetailsService meetingDetailsService;

	@PostMapping(value = "/v1/updateSmeAndTopic")
	public ResponseEntity<Object> updateSmeAnsdTopic(@RequestBody SmeTopicDto smeTopicDto) {
		logger.info("Request for updateSmeAnsdTopic {}", smeTopicDto);
		return new ResponseEntity<Object>(meetingDetailsService.updateSMEAndTopic(smeTopicDto), HttpStatus.OK);

	}
	
	
	@GetMapping(value = "/v1/getMeetingDetailsBySmeId")
	public ResponseEntity<Object> getMeetingDetailsSpecificSme(@RequestParam Long smeId) {
		logger.info("Request for getMeetingDetailsSpecificSme {}",smeId);
		return new ResponseEntity<Object>(meetingDetailsService.getMeetingDetailsSpecificSME(smeId), HttpStatus.OK);
	}
	@GetMapping(value = "/v1/getMeetingDetailsofNext2Week")
	public ResponseEntity<Object> getMeetingDetailsofNext2Week() throws ParseException {
		logger.info("Request for getMeetingDetailsofNext2Week");
		return new ResponseEntity<Object>(meetingDetailsService.getMeetingDetailsFor2Week(), HttpStatus.OK);
	}


}
