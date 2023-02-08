package com.globallogic.xlstodatabase.service;

import com.globallogic.xlstodatabase.dto.SMEDetailsDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;

import java.util.List;

public interface SMEDetailsService {
    SMEDetailsDto assignTopic(SMEDetailsDto smeDetailsDto) throws EmployeeNotFound, SMESubjectAvailiability;
    List<SMEDetailsDto> getSMEAllTopics(long eid);
    void deleteTopicByEid(long eid,String topic) throws SMESubjectAvailiability;
}
