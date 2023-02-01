package com.globallogic.xlstodatabase.serviceimpl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.xlstodatabase.dto.MeetingDetailsDto;
import com.globallogic.xlstodatabase.dto.SmeTopicDto;
import com.globallogic.xlstodatabase.exception.ExcelReadingException;
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

	@Override
	public Object updateSMEAndTopic(SmeTopicDto smeTopicDto) {
		try {
			logger.info("Inside updateSMEAndTopic of MeetingDetailsImpl");
			Optional<Employee> employee = employeeRepository.findById(smeTopicDto.getSmeId());
			if (!employee.isPresent()) {
				throw new ExcelReadingException("SME does not exist");
			}
			MeetingDetails meetingDetails = meetingDetailsRepository.findByMeetingId(smeTopicDto.getMeetingId());
			meetingDetails.setMeetingAnchor(employee.get());
			meetingDetails.setTopic(smeTopicDto.getTopic());
			meetingDetailsRepository.save(meetingDetails);
			return new Response<>("1", "Sem and Topic Updated");
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = MessageFormat.format("Exception caught in updateSMEAndTopic of MeetingDetailsImpl :{0}",
					e);
			logger.error(errorMsg);
			throw new ExcelReadingException(errorMsg);
		}
	}

	@Override
	public Object getMeetingDetailsSpecificSME(Long smeId) {
		try {
			logger.info("Inside getMeetingDetailsSpecificSME of MeetingDetailsImpl");
			List<MeetingDetailsDto> reponseList = new ArrayList<>();
			List<MeetingDetails> meetingDetails = meetingDetailsRepository.getBySME(smeId);
			for (MeetingDetails meetingDetails2 : meetingDetails) {
				MeetingDetailsDto meetingDetailsDto = new MeetingDetailsDto();
				meetingDetailsDto.setMeetingDate(meetingDetails2.getMeetingDate());
				meetingDetailsDto.setMeetingId(meetingDetails2.getMeetingId());
				meetingDetailsDto.setTopic(meetingDetails2.getTopic());
				reponseList.add(meetingDetailsDto);
			}
			return new Response<>("1", "Sme Meeting Details fetched successfully", reponseList);
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = MessageFormat
					.format("Exception caught in getMeetingDetailsSpecificSME of MeetingDetailsImpl :{0}", e);
			logger.error(errorMsg);
			throw new ExcelReadingException(errorMsg);
		}
	}
}
