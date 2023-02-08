package com.globallogic.xlstodatabase.serviceimpl;

import java.text.MessageFormat;
import java.util.*;

import com.globallogic.xlstodatabase.dto.AssesmentScoreDto;
import com.globallogic.xlstodatabase.dto.EmployeeDto;
import com.globallogic.xlstodatabase.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.xlstodatabase.config.DriveQuickstart;
import com.globallogic.xlstodatabase.dto.MeetingDto;
import com.globallogic.xlstodatabase.exception.ExcelReadingException;
import com.globallogic.xlstodatabase.exception.Response;
import com.globallogic.xlstodatabase.modal.Employee;
import com.globallogic.xlstodatabase.modal.MeetingDetails;
import com.globallogic.xlstodatabase.modal.ParticipantOfMeeting;
import com.globallogic.xlstodatabase.repository.EmployeeRepository;
import com.globallogic.xlstodatabase.repository.MeetingDetailsRepository;
import com.globallogic.xlstodatabase.repository.ParticipantsofMeetingRepository;
import com.globallogic.xlstodatabase.service.ParticipantsOfMeetingService;
import com.globallogic.xlstodatabase.utility.Utility;

import jakarta.transaction.Transactional;

@Service
public class ParticipantsOfMeetingServiceImpl implements ParticipantsOfMeetingService {

	Logger logger = LoggerFactory.getLogger(ParticipantsOfMeetingService.class);

	@Autowired
	MeetingDetailsRepository meetingDetailsRepository;

	@Autowired
	ParticipantsofMeetingRepository participantsofMeetingRepository;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	DriveQuickstart driveQuickstart;

	@Autowired
	EmployeeRepository employeeRepository;


	@Override
	public Object saveExcel() {
		try {
			logger.info("Inside saveExcel of MeetingService");
			List<List<MeetingDto>> data = driveQuickstart.getFilesFromDrive();
			for (List<MeetingDto> data1 : data) {
				storeInDatabase(data1);
			}
			return new Response<>("1", "Data saved successfully");
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = MessageFormat.format("Exception caught in saveExcel of MeetingService :{0}", e);
			logger.error(errorMsg);
			throw new ExcelReadingException(errorMsg);
		}
	}

	@Transactional(rollbackOn = Exception.class)
	public Object storeInDatabase(List<MeetingDto> listData) {
		try {
			MeetingDetails meetingDetails = new MeetingDetails();
			meetingDetails.setMeetingDate(Utility.stringToDate(listData.get(0).getMeetingDate()));
			meetingDetails.setMeetingId(listData.get(0).getMeetingId());
			meetingDetails.setTopic(null);
			meetingDetails.setTotalHours(0);
			meetingDetails.setMeetingAnchor(null);
			MeetingDetails savedMeetingDetails = meetingDetailsRepository.save(meetingDetails);
			for (MeetingDto meetingDto : listData) {
				Employee employeeExist = employeeRepository.findByEmail(meetingDto.getEmail());
				if (employeeExist == null) {
					logger.info("Participant not in employee so can not be added");
					continue; }
				ParticipantOfMeeting participantOfMeeting = new ParticipantOfMeeting();
				participantOfMeeting.setAssesmentScore(null);
				participantOfMeeting.setEid(employeeExist);
				participantOfMeeting.setMid(savedMeetingDetails);
				participantOfMeeting.setTimeExisted(meetingDto.getTimeExited());
				participantOfMeeting.setTimeJoined(meetingDto.getTimeJoined());
				participantOfMeeting.setDuration(meetingDto.getDuration());
				participantsofMeetingRepository.save(participantOfMeeting);

			}
			return new Response<>("1", "Data saved successfully");

		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = MessageFormat.format("Exception caught in storeInDatabase of MeetingService :{0}", e);
			logger.error(errorMsg);
			throw new ExcelReadingException(errorMsg);
		}
	}

	public Object getAllMeetingsParticipantsList() {
		try {
			logger.info("Inside getMeetingList of MeetingService");
			List<ParticipantOfMeeting> dataFromRepo = participantsofMeetingRepository.findAll();
			List<MeetingDto> responseList = new ArrayList<>();
			if ( dataFromRepo.size() != 0) {
				for (ParticipantOfMeeting particpant : dataFromRepo) {
					Employee employeeData = particpant.getEid();
					MeetingDto meetingDto = new MeetingDto();
					meetingDto.setFirstName(employeeData.getFirstName());
					meetingDto.setLastName(employeeData.getLastName());
					meetingDto.setId(employeeData.getEid());
					meetingDto.setEmail(employeeData.getEmail());
					MeetingDetails meetingDetails = particpant.getMid();
					meetingDto.setMeetingId(meetingDetails.getMeetingId());
					meetingDto.setTimeExited(particpant.getTimeExisted());
					meetingDto.setMeetingDate(String.valueOf( meetingDetails.getMeetingDate()));
					meetingDto.setDuration(particpant.getDuration());
					meetingDto.setTimeJoined(particpant.getTimeJoined());
					responseList.add(meetingDto);
				}
			}
			return new Response<>(responseList, "1", "Data fetched successfully");
		} catch (Exception e) {
			String errorMsg = MessageFormat.format("Exception caught in getMeetingList of MeetingService :{0}", e);
			logger.error(errorMsg);
			throw new ExcelReadingException(errorMsg);
		}

	}

	public List<MeetingDto> getParticipantsByMeetingId(String meetingId) {
		try {
			logger.info("Inside getByMeetingId of MeetingService");
			List<ParticipantOfMeeting> dataFromRepo = participantsofMeetingRepository.findByMid(meetingId);
			List<MeetingDto> responseList = new ArrayList<>();
			if (dataFromRepo != null && dataFromRepo.size() != 0) {
				for (ParticipantOfMeeting particpant : dataFromRepo) {
					Employee employeeData = particpant.getEid();
					MeetingDto meetingDto = new MeetingDto();
					meetingDto.setFirstName(employeeData.getFirstName());
					meetingDto.setLastName(employeeData.getLastName());
					meetingDto.setId(employeeData.getEid());
					meetingDto.setEmail(employeeData.getEmail());
					MeetingDetails meetingDetails = particpant.getMid();
					meetingDto.setMeetingId(meetingDetails.getMeetingId());
					meetingDto.setTimeExited(particpant.getTimeExisted());
					meetingDto.setMeetingDate(String.valueOf(meetingDetails.getMeetingDate()));
					meetingDto.setDuration(particpant.getDuration());
					meetingDto.setTimeJoined(particpant.getTimeJoined());					
					responseList.add(meetingDto);
				}
			}
			return responseList;
		} catch (Exception e) {
			String errorMsg = MessageFormat.format("Exception caught in getByMeetingId of MeetingService :{0}", e);
			logger.error(errorMsg);
			throw new ExcelReadingException(errorMsg);
		}
	}

	public Object getParticipantsAssesmentScoreByMeetingId(String meetingId) {
		try {
			logger.info("Inside getByMeetingId of MeetingService");
			List<ParticipantOfMeeting> dataFromRepo = participantsofMeetingRepository.findByMid(meetingId);
			List<AssesmentScoreDto> responseList = new ArrayList<>();
			if (dataFromRepo != null && dataFromRepo.size() != 0) {
				for (ParticipantOfMeeting particpant : dataFromRepo) {
					Employee employeeData = particpant.getEid();
					AssesmentScoreDto scoreDto = new AssesmentScoreDto();
					scoreDto.setFirstName(employeeData.getFirstName());
					scoreDto.setLastName(employeeData.getLastName());
					scoreDto.setEid(employeeData.getEid());
					scoreDto.setEmail(employeeData.getEmail());
					scoreDto.setMeetingId(meetingId);
					scoreDto.setAssesmentScore(particpant.getAssesmentScore());
					responseList.add(scoreDto);
				}
			}
			return new Response<>(responseList, "1", "Data fetched successfully");
		} catch (Exception e) {
			String errorMsg = MessageFormat.format("Exception caught in getByMeetingId of MeetingService :{0}", e);
			logger.error(errorMsg);
			throw new ExcelReadingException(errorMsg);
		}
	}
	public List<EmployeeDto> listOfAbsentees(String meetingId)
	{
		List<MeetingDto> presentList=getParticipantsByMeetingId(meetingId);
		List<String> presentListEmails=new ArrayList<>();
		for(MeetingDto meetingDto:presentList)
		{
			presentListEmails.add(meetingDto.getEmail());
		}
		List<EmployeeDto> allEmployees=employeeService.getAllEmployee();
		Iterator<EmployeeDto> employeeIterator=allEmployees.listIterator();
		while (employeeIterator.hasNext())
		{
			EmployeeDto employeeDto=employeeIterator.next();
			if(presentListEmails.contains(employeeDto.getEmail()))
			{
				employeeIterator.remove();
			}

		}
		return allEmployees;
	}

	public Object getAssesmentScoreByMeetingIdAndEid(String eid,String meetingId) {
		try {
			logger.info("Inside getAssesmentScoreByMeetingIdAndEid of ParticipantsOfMeetingServiceImpl");
			ParticipantOfMeeting dataFromRepo = participantsofMeetingRepository.findByMidAndEid(meetingId,eid);
					Employee employeeData = dataFromRepo.getEid();
					AssesmentScoreDto scoreDto = new AssesmentScoreDto();
					scoreDto.setFirstName(employeeData.getFirstName());
					scoreDto.setLastName(employeeData.getLastName());
					scoreDto.setEid(employeeData.getEid());
					scoreDto.setEmail(employeeData.getEmail());
					scoreDto.setMeetingId(meetingId);
					scoreDto.setAssesmentScore(dataFromRepo.getAssesmentScore());
					return scoreDto;
		} catch (Exception e) {
			String errorMsg = MessageFormat.format("Exception caught in getAssesmentScoreByMeetingIdAndEid of ParticipantsOfMeetingServiceImpl :{0}", e);
			logger.error(errorMsg);
			throw new ExcelReadingException(errorMsg);
		}
	}

	@Override
	public Object getParticipantsDetailsBetweenParticularDate(long eid, Date fromDate, Date toDate) {
		logger.info("Inside getParticipantsDetailsBetweenParticularDate of ParticipantsOfMeetingServiceImpl");
		Map<String,Object> responseMap=new HashMap<>();
		responseMap.put("EmployeeID",eid);
		responseMap.put("SessionAttended",participantsofMeetingRepository.countSeesionAttened(fromDate,toDate,eid));
		responseMap.put("AverageScore",participantsofMeetingRepository.getAverageScore(fromDate,toDate,eid));
		return responseMap;
	}

}
