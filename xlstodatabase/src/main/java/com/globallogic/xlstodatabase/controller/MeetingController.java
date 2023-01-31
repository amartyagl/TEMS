
package com.globallogic.xlstodatabase.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.xlstodatabase.service.ParticipantsOfMeetingService;

@RestController
public class MeetingController {

    Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    ParticipantsOfMeetingService participantsOfMeetingService;

    @GetMapping("/meeting/upload")
    public ResponseEntity<Object> upload() throws GeneralSecurityException, IOException {
        logger.info("Request for upload of MeetingController");
        return new ResponseEntity<Object>(participantsOfMeetingService.saveExcel(), HttpStatus.OK);
    }

    @GetMapping("/participants")
    public ResponseEntity<Object> getAllParticipants() {
        logger.info("Request for getAllParticipants of MeetingController");
        return
                new ResponseEntity<Object>(participantsOfMeetingService.getAllMeetingsParticipantsList(), HttpStatus.OK);
    }

    @GetMapping("/getPartiCipantsByMeetingId")
    public ResponseEntity<Object> getByMeetingId(@RequestParam String meetingId) {
        logger.info("Request for getAllParticipants of MeetingController :{}",
                meetingId);
        return new ResponseEntity<Object>(participantsOfMeetingService.getParticipantsByMeetingId(meetingId), HttpStatus.OK);
    }
}
