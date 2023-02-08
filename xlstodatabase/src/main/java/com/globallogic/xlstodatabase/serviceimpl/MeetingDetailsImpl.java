package com.globallogic.xlstodatabase.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import com.globallogic.xlstodatabase.repository.SMEDetailsRepository;
import com.globallogic.xlstodatabase.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.xlstodatabase.dto.MeetingDetailsDto;
import com.globallogic.xlstodatabase.dto.SmeTopicDto;
import com.globallogic.xlstodatabase.exception.Response;
import com.globallogic.xlstodatabase.modal.Employee;
import com.globallogic.xlstodatabase.modal.MeetingDetails;
import com.globallogic.xlstodatabase.repository.EmployeeRepository;
import com.globallogic.xlstodatabase.repository.MeetingDetailsRepository;
import com.globallogic.xlstodatabase.service.MeetingDetailsService;

@Service
public class MeetingDetailsImpl implements MeetingDetailsService {

	Logger logger = LoggerFactory.getLogger(MeetingDetailsImpl.class);

	@Autowired
	MeetingDetailsRepository meetingDetailsRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	SMEDetailsRepository smeDetailsRepository;

	@Override
	public Object createMeeting(MeetingDetailsDto meetingDetailsDto) throws Exception {
		MeetingDetails meetingDetails=new MeetingDetails();
		Optional<Employee> employee=employeeRepository.findById(meetingDetailsDto.getEid());
		if(employee.isPresent()) {
			meetingDetails.setMeetingAnchor(employee.get());
			meetingDetails.setMeetingDate(Utility.stringToDate(meetingDetailsDto.getMeetingDate()));
			meetingDetails.setMeetingId(meetingDetailsDto.getMeetingId());
			meetingDetails.setTotalHours(meetingDetailsDto.getHours());
			if(smeDetailsRepository.findyByEidAndTopic(meetingDetailsDto.getEid(), meetingDetails.getTopic().toLowerCase())!=null) {
				meetingDetails.setTopic(meetingDetails.getTopic().toLowerCase());
			}
			else {
				throw new SMESubjectAvailiability("SME does not have this subject idea assign different subject");
			}
			meetingDetailsRepository.save(meetingDetails);
		}
		else {
			throw new EmployeeNotFound("Employee not found with given id");
		}
		return meetingDetailsDto;
	}

	@Override
	public Object updateSMEAndTopic(SmeTopicDto smeTopicDto) throws EmployeeNotFound, SMESubjectAvailiability {
			logger.info("Inside updateSMEAndTopic of MeetingDetailsImpl");
			Optional<Employee> employee = employeeRepository.findById(smeTopicDto.getSmeId());
			if (employee.isEmpty()) {
				throw new EmployeeNotFound("employee does not exist");
			}
			if(smeDetailsRepository.findyByEidAndTopic(smeTopicDto.getSmeId(),smeTopicDto.getTopic().toLowerCase())!=null) {
				MeetingDetails meetingDetails = meetingDetailsRepository.findByMeetingId(smeTopicDto.getMeetingId());
				meetingDetails.setMeetingAnchor(employee.get());
				meetingDetails.setTopic(smeTopicDto.getTopic());
				meetingDetailsRepository.save(meetingDetails);
				return new Response<>("1", "Sem and Topic Updated");
			}
			else {
				throw new SMESubjectAvailiability("SME does not have the given subject");
			}
	}

	@Override
	public Object getMeetingDetailsSpecificSME(Long smeId) {
			logger.info("Inside getMeetingDetailsSpecificSME of MeetingDetailsImpl");
			List<MeetingDetailsDto> reponseList = new ArrayList<>();
			List<MeetingDetails> meetingDetails = meetingDetailsRepository.getBySME(smeId);
			for (MeetingDetails meetingDetails2 : meetingDetails) {
				MeetingDetailsDto meetingDetailsDto = new MeetingDetailsDto();
				meetingDetailsDto.setMeetingDate(String.valueOf(meetingDetails2.getMeetingDate()));
				meetingDetailsDto.setMeetingId(meetingDetails2.getMeetingId());
				meetingDetailsDto.setTopic(meetingDetails2.getTopic());
				meetingDetailsDto.setHours(meetingDetails2.getTotalHours());
				meetingDetailsDto.setEid(smeId);
				reponseList.add(meetingDetailsDto);
			}
			return new Response<>("1", "Sme Meeting Details fetched successfully", reponseList);
	}

	@Override
	public Object getMeetingDetailsFor2Week() throws ParseException {
		LocalDate localDate = LocalDate.now();
		Date fromDate= new SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString());
		Date toDate=new SimpleDateFormat("yyyy-MM-dd").parse(localDate.plusDays(14).toString());
		logger.info("Calling meeting repo in getMeetingDetailsFor2Week");
		List<MeetingDetails> meetingDetails=meetingDetailsRepository.getMeetingDetailsOfNext2Week(fromDate,toDate);
		return null;
	}
}
