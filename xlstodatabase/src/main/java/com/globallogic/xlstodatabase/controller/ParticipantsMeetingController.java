
package com.globallogic.xlstodatabase.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.globallogic.xlstodatabase.dto.EmployeeHoursDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.xlstodatabase.service.ParticipantsOfMeetingService;

@RestController
public class ParticipantsMeetingController {

    Logger logger = LoggerFactory.getLogger(ParticipantsMeetingController.class);

    @Autowired
    ParticipantsOfMeetingService participantsOfMeetingService;

    @GetMapping("/meeting/upload")
    public ResponseEntity<Object> upload() {
        logger.info("Request for upload of MeetingController");
        return new ResponseEntity<>(participantsOfMeetingService.saveExcel(), HttpStatus.OK);
    }

    @GetMapping("/participants")
    public ResponseEntity<Object> getAllParticipants() {
        logger.info("Request for getAllParticipants of MeetingController");
        return
                new ResponseEntity<>(participantsOfMeetingService.getAllMeetingsParticipantsList(), HttpStatus.OK);
    }

    @GetMapping("/getParticipantsByMeetingId")
    public ResponseEntity<Object> getByMeetingId(@RequestParam String meetingId) {
        logger.info("Request for getAllParticipants of MeetingController :{}",
                meetingId);
        return new ResponseEntity<>(participantsOfMeetingService.getParticipantsByMeetingId(meetingId), HttpStatus.OK);
    }

    @GetMapping("/getParticipantsScoreByMeetingId")
    public ResponseEntity<Object> getScoreByMeetingId(@RequestParam String meetingId) {
        logger.info("Request for getAllParticipants of MeetingController :{}",
                meetingId);
        return new ResponseEntity<>(participantsOfMeetingService.getParticipantsAssesmentScoreByMeetingId(meetingId), HttpStatus.OK);
    }

    @GetMapping("/getAbsenteesByMeetingId")
    public ResponseEntity<Object> getAbsentees(@RequestParam String meetingId) {
        logger.info("Request for getAllParticipants of MeetingController :{}",
                meetingId);
        return new ResponseEntity<>(participantsOfMeetingService.listOfAbsentees(meetingId), HttpStatus.OK);
    }
    @GetMapping("/getScoreByMeetingIdAndEid")
    public ResponseEntity<Object> getScoreByMeetingIdAndEid(@RequestParam String meetingId,@RequestParam Long eid) {
        logger.info("Request for getAllParticipants of MeetingController :{}",
                meetingId);
        return new ResponseEntity<>(participantsOfMeetingService.getAssesmentScoreByMeetingIdAndEid(eid,meetingId), HttpStatus.OK);
    }

    @GetMapping("/getParticipantsDetailsBetweenParticularDate")
    public ResponseEntity<Object> getParticipantsDetailsBetweenParticularDate(@RequestBody EmployeeHoursDto employeeHoursDto) {
        logger.info("Request for getParticipantsDetailsBetweenParticularDate of MeetingController :{}",
                employeeHoursDto.getEid());
        return new ResponseEntity<>(participantsOfMeetingService.getParticipantsDetailsBetweenParticularDate(employeeHoursDto.getEid(),employeeHoursDto.getStartDate(),employeeHoursDto.getEndDate()), HttpStatus.OK);
    }
}
