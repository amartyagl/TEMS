package com.globallogic.xlstodatabase.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import com.globallogic.xlstodatabase.config.CalendarQuickstart;
import com.globallogic.xlstodatabase.dto.CreateMeetingDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import com.globallogic.xlstodatabase.repository.SMEDetailsRepository;
import com.globallogic.xlstodatabase.utility.Utility;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MeetingDetailsImpl implements MeetingDetailsService {

	Logger logger = LoggerFactory.getLogger(MeetingDetailsImpl.class);

	@Autowired
	MeetingDetailsRepository meetingDetailsRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	SMEDetailsRepository smeDetailsRepository;

	@Autowired
	CalendarQuickstart calendarQuickstart;

	@Override
	public String createMeeting(CreateMeetingDto createMeetingDto) throws Exception {
		log.info("Inside meeting service impl createMeeting");
		MeetingDetailsDto meetingDetailsDto=calendarQuickstart.createMeeting(createMeetingDto);
		MeetingDetails meetingDetails=new MeetingDetails();
		meetingDetails.setMeetingId(meetingDetailsDto.getMeetingId());
		meetingDetails.setMeetingAnchor(null);
		meetingDetails.setTopic(meetingDetailsDto.getTopic());
		meetingDetails.setMeetingDate(Utility.stringToDate(meetingDetailsDto.getMeetingDate()));
		meetingDetails.setTotalHours(meetingDetailsDto.getHours());
		meetingDetailsRepository.save(meetingDetails);
		return "meeting created";
	}

	@Override
	public Object updateSMEAndTopic(SmeTopicDto smeTopicDto) throws EmployeeNotFound, SMESubjectAvailiability {
			logger.info("Inside updateSMEAndTopic of MeetingDetailsImpl");
			Optional<Employee> employee = employeeRepository.findById(smeTopicDto.getSmeId());
			if (employee.isEmpty()) {
				throw new EmployeeNotFound("employee does not exist");
			}
			if(smeDetailsRepository.findyByEidAndTopic(smeTopicDto.getSmeId(),smeTopicDto.getTopic().toLowerCase())!=null) {
				logger.info("Inside updateSMEAndTopic of MeetingDetailsImpl setting up the topic and anchor");
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
	public List<MeetingDetailsDto> getMeetingDetailsFor2Week() throws ParseException, MeetingNotExist {
		LocalDate localDate = LocalDate.now();
		Date fromDate= new SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString());
		Date toDate=new SimpleDateFormat("yyyy-MM-dd").parse(localDate.plusDays(14).toString());
		logger.info("Calling meeting repo in getMeetingDetailsFor2Week");
		List<MeetingDetails> meetingDetailsList=meetingDetailsRepository.getMeetingDetailsBetweenDates(fromDate,toDate);
		if (meetingDetailsList.isEmpty())
		{
			throw new MeetingNotExist("No meeting exist for next 2 week");
		}
		List<MeetingDetailsDto> meetingDetailsDtoList=new ArrayList<>();
		for(MeetingDetails meetingDetails:meetingDetailsList)
		{
			MeetingDetailsDto meetingDetailsDto=new MeetingDetailsDto();
			meetingDetailsDto.setMeetingId(meetingDetailsDto.getMeetingId());
			meetingDetailsDto.setMeetingDate(meetingDetailsDto.getMeetingDate());
			meetingDetailsDto.setTopic(meetingDetails.getTopic());
			meetingDetailsDto.setEid(meetingDetails.getMeetingAnchor().getEid());
			meetingDetailsDto.setHours(meetingDetails.getTotalHours());
			meetingDetailsDtoList.add(meetingDetailsDto);
		}
		return meetingDetailsDtoList;
	}

	@Override
	public Map<String,Object> getLeaderBoard() throws ParseException {
		LocalDate localDate = LocalDate.now();
		Date fromDate= new SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString());
		Date toDate=new SimpleDateFormat("yyyy-MM-dd").parse(localDate.minusMonths(3).toString());
		logger.info("Calling meeting repo in getLeaderBoard");
		int totalNumberOfMeetings=meetingDetailsRepository.getMeetingDetailsBetweenDates(toDate,fromDate).size();
		int smeInvolved=meetingDetailsRepository.getMeetingAnchorCountBetweenDates(toDate,fromDate);
		List<String> hoursList=meetingDetailsRepository.getTotalHoursBetweenDates(toDate,fromDate);
		String totalHours=Utility.sumDuration(hoursList);
		int totalParticipants=meetingDetailsRepository.totalParticipantsBetweenDates(toDate,fromDate);
		Map<String,Object> leaderBoard=new HashMap<>();
		leaderBoard.put("totalNumberOfMeetings",totalNumberOfMeetings);
		leaderBoard.put("smeInvolved",smeInvolved);
		leaderBoard.put("totalHours",totalHours);
		leaderBoard.put("totalParticipants",totalParticipants);
		leaderBoard.put("fromDate",toDate);
		leaderBoard.put("toDate",fromDate);
		return leaderBoard;
	}
	@Override
	public List<MeetingDetailsDto> getAllMeetings()
	{

		List<MeetingDetails> meetingDetailsList=meetingDetailsRepository.findAll();
		logger.info("Calling meeting repo in getAllMeetings");
		List<MeetingDetailsDto> meetingDetailsDtoList=new ArrayList<>();
		for(MeetingDetails meetingDetails:meetingDetailsList)
		{
			MeetingDetailsDto meetingDetailsDto=new MeetingDetailsDto();
			meetingDetailsDto.setHours(meetingDetails.getTotalHours());
			meetingDetailsDto.setMeetingDate(String.valueOf(meetingDetails.getMeetingDate()));
			meetingDetailsDto.setTopic(meetingDetails.getTopic());
			meetingDetailsDto.setMeetingId(meetingDetails.getMeetingId());
			if(meetingDetails.getMeetingAnchor()!=null)
			{
				meetingDetailsDto.setEid(meetingDetails.getMeetingAnchor().getEid());
			}
			else{
				meetingDetailsDto.setEid(0);
			}

			meetingDetailsDtoList.add(meetingDetailsDto);
		}
		return meetingDetailsDtoList;
	}
}
