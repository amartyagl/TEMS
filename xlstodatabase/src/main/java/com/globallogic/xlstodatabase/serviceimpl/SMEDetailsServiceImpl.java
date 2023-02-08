package com.globallogic.xlstodatabase.serviceimpl;

import com.globallogic.xlstodatabase.dto.SMEDetailsDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import com.globallogic.xlstodatabase.modal.Employee;
import com.globallogic.xlstodatabase.modal.SMEDetails;
import com.globallogic.xlstodatabase.repository.EmployeeRepository;
import com.globallogic.xlstodatabase.repository.SMEDetailsRepository;
import com.globallogic.xlstodatabase.service.SMEDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SMEDetailsServiceImpl implements SMEDetailsService {

    @Autowired
    SMEDetailsRepository smeDetailsRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public SMEDetailsDto assignTopic(SMEDetailsDto smeDetailsDto) throws EmployeeNotFound, SMESubjectAvailiability {
        Optional<Employee> employee=employeeRepository.findById(smeDetailsDto.getEid());
        SMEDetails smeDetails=new SMEDetails();
        if (employee.isPresent())
        {
        smeDetails.setEid(employee.get());
        smeDetails.setTopic(smeDetailsDto.getTopic().toLowerCase());
        SMEDetails findSme=smeDetailsRepository.findyByEidAndTopic(smeDetailsDto.getEid(), smeDetailsDto.getTopic().toLowerCase());
        if (findSme==null) {
            smeDetailsRepository.save(smeDetails);
        }
        else {
            throw new SMESubjectAvailiability("Sme with same subject already registered");
        }
        }
        else {
            throw new EmployeeNotFound("Employee with given eid not present");
        }
        return smeDetailsDto ;
    }

    @Override
    public List<SMEDetailsDto> getSMEAllTopics(long eid) {
        List<SMEDetails> smeDetailsList=smeDetailsRepository.getAllTopics(eid);
        List<SMEDetailsDto> smeDetailsDtoList=new ArrayList<>();
        for (SMEDetails smeDetails:smeDetailsList)
        {
            SMEDetailsDto smeDetailsDto=new SMEDetailsDto();
            smeDetailsDto.setEid(smeDetails.getEid().getEid());
            smeDetailsDto.setTopic(smeDetails.getTopic());
            smeDetailsDto.setSmeId(smeDetails.getSmeId());
            smeDetailsDtoList.add(smeDetailsDto);
        }
        return smeDetailsDtoList;
    }

    @Override
    @Transactional
    public void deleteTopicByEid(long eid, String topic) throws SMESubjectAvailiability {

        SMEDetails findSme=smeDetailsRepository.findyByEidAndTopic(eid,topic.toLowerCase());
        if (findSme!=null) {
            log.info("in smedetailsserviceimpl deleteTopicByEid calling delete query");
           smeDetailsRepository.deleteTopic(eid,topic.toLowerCase());
        }
        else {
            throw new SMESubjectAvailiability("Sme with given subject not exist");
        }
    }
}
