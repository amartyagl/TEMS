package com.globallogic.xlstodatabase.controller;

import com.globallogic.xlstodatabase.dto.SMEDetailsDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import com.globallogic.xlstodatabase.service.SMEDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class SMEDetailsController {

    @Autowired
    SMEDetailsService smeDetailsService;

    @PostMapping("/assignSubject")
    ResponseEntity<String> addSubject(@RequestBody SMEDetailsDto smeDetailsDto) throws SMESubjectAvailiability, EmployeeNotFound {
        log.info("In smeDetailsController adding new subject");
        smeDetailsService.assignTopic(smeDetailsDto);
        return new ResponseEntity<>("Subject Assigned successfully", HttpStatus.OK);
    }
    @GetMapping("/getAllSubjectOfSme")
    ResponseEntity<List<SMEDetailsDto>> getAllSubject(@RequestParam long eid) {
        log.info("In smeDetailsController getAllSubject method");
        return new ResponseEntity<>(smeDetailsService.getSMEAllTopics(eid), HttpStatus.OK);
    }
    @DeleteMapping("/deleteSubject")
    ResponseEntity<String> deleteSubject(@RequestParam long eid,@RequestParam String topic) throws SMESubjectAvailiability{
        log.info("In smeDetailsController deleteSubject");
        smeDetailsService.deleteTopicByEid(eid,topic);
        return new ResponseEntity<>("Subject Deleted successfully", HttpStatus.OK);
    }

}
