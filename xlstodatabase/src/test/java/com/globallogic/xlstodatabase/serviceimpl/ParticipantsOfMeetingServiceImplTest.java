package com.globallogic.xlstodatabase.serviceimpl;

import com.globallogic.xlstodatabase.config.DriveQuickstart;
import com.globallogic.xlstodatabase.dto.EmployeeDto;
import com.globallogic.xlstodatabase.dto.MeetingDto;
import com.globallogic.xlstodatabase.exception.ExcelReadingException;
import com.globallogic.xlstodatabase.modal.Employee;
import com.globallogic.xlstodatabase.modal.MeetingDetails;
import com.globallogic.xlstodatabase.modal.ParticipantOfMeeting;
import com.globallogic.xlstodatabase.repository.EmployeeRepository;
import com.globallogic.xlstodatabase.repository.MeetingDetailsRepository;
import com.globallogic.xlstodatabase.repository.ParticipantsofMeetingRepository;
import com.globallogic.xlstodatabase.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ParticipantsOfMeetingServiceImplTest {

    private ParticipantsOfMeetingServiceImpl participantsOfMeetingServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        participantsOfMeetingServiceImplUnderTest = new ParticipantsOfMeetingServiceImpl();
        participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository = mock(MeetingDetailsRepository.class);
        participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository = mock(
                ParticipantsofMeetingRepository.class);
        participantsOfMeetingServiceImplUnderTest.employeeService = mock(EmployeeService.class);
        participantsOfMeetingServiceImplUnderTest.driveQuickstart = mock(DriveQuickstart.class);
        participantsOfMeetingServiceImplUnderTest.employeeRepository = mock(EmployeeRepository.class);
    }

    @Test
    void testSaveExcel() throws Exception {
        // Setup
        // Configure DriveQuickstart.getFilesFromDrive(...).
        final MeetingDto meetingDto = new MeetingDto();
        meetingDto.setId(0L);
        meetingDto.setMeetingId("meetingId");
        meetingDto.setFirstName("firstName");
        meetingDto.setLastName("lastName");
        meetingDto.setEmail("email");
        meetingDto.setDuration("duration");
        meetingDto.setTimeJoined("timeJoined");
        meetingDto.setTimeExited("timeExisted");
        meetingDto.setMeetingDate("meetingDate");
        meetingDto.setAssessmentScore("assesmentScore");
        final List<List<MeetingDto>> lists = List.of(List.of(meetingDto));
        when(participantsOfMeetingServiceImplUnderTest.driveQuickstart.getFilesFromDrive()).thenReturn(lists);

        // Configure MeetingDetailsRepository.save(...).
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        meetingAnchor.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant));
        meetingDetails.setMeetingAnchor(meetingAnchor);
        meetingDetails.setTotalHours("totalHours");
        when(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository.save(new MeetingDetails()))
                .thenReturn(meetingDetails);

        // Configure EmployeeRepository.findByEmail(...).
        final Employee employee = new Employee();
        employee.setEid(0L);
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setEmail("email");
        employee.setProjectCode("projectCode");
        employee.setLocation("location");
        final MeetingDetails meetingDetails1 = new MeetingDetails();
        meetingDetails1.setMeetingId("meetingId");
        meetingDetails1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails1.setTopic("topic");
        meetingDetails1.setTotalHours("totalHours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        participant1.setAssesmentScore("assesmentScore");
        meetingDetails1.setParticipantOfMeetings(List.of(participant1));
        employee.setMeetingDetails(List.of(meetingDetails1));
        when(participantsOfMeetingServiceImplUnderTest.employeeRepository.findByEmail("email")).thenReturn(employee);

        // Configure ParticipantsofMeetingRepository.save(...).
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant2.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor1);
        mid.setTotalHours("totalHours");
        participant2.setMid(mid);
        participant2.setTimeExisted("timeExisted");
        participant2.setDuration("duration");
        participant2.setAssesmentScore("assesmentScore");
        participant2.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.save(
                new ParticipantOfMeeting())).thenReturn(participant2);

        // Configure ParticipantsofMeetingRepository.findByMidAndEid(...).
        final ParticipantOfMeeting participant3 = new ParticipantOfMeeting();
        final Employee eid1 = new Employee();
        eid1.setEid(0L);
        eid1.setFirstName("firstName");
        eid1.setLastName("lastName");
        eid1.setEmail("email");
        participant3.setEid(eid1);
        final MeetingDetails mid1 = new MeetingDetails();
        mid1.setMeetingId("meetingId");
        mid1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid1.setTopic("topic");
        final Employee meetingAnchor2 = new Employee();
        meetingAnchor2.setEid(0L);
        meetingAnchor2.setFirstName("firstName");
        meetingAnchor2.setLastName("lastName");
        meetingAnchor2.setEmail("email");
        mid1.setMeetingAnchor(meetingAnchor2);
        mid1.setTotalHours("totalHours");
        participant3.setMid(mid1);
        participant3.setTimeExisted("timeExisted");
        participant3.setDuration("duration");
        participant3.setAssesmentScore("assesmentScore");
        participant3.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMidAndEid("meetingId",
                0L)).thenReturn(participant3);

        // Run the test
        final Object result = participantsOfMeetingServiceImplUnderTest.saveExcel();

        // Verify the results
        verify(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository).save(new MeetingDetails());
        verify(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository).save(
                new ParticipantOfMeeting());
    }

    @Test
    void testSaveExcel_DriveQuickstartReturnsNoItems() throws Exception {
        // Setup
        when(participantsOfMeetingServiceImplUnderTest.driveQuickstart.getFilesFromDrive())
                .thenReturn(Collections.emptyList());

        // Configure MeetingDetailsRepository.save(...).
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        meetingAnchor.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant));
        meetingDetails.setMeetingAnchor(meetingAnchor);
        meetingDetails.setTotalHours("totalHours");
        when(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository.save(new MeetingDetails()))
                .thenReturn(meetingDetails);

        // Configure EmployeeRepository.findByEmail(...).
        final Employee employee = new Employee();
        employee.setEid(0L);
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setEmail("email");
        employee.setProjectCode("projectCode");
        employee.setLocation("location");
        final MeetingDetails meetingDetails1 = new MeetingDetails();
        meetingDetails1.setMeetingId("meetingId");
        meetingDetails1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails1.setTopic("topic");
        meetingDetails1.setTotalHours("totalHours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        participant1.setAssesmentScore("assesmentScore");
        meetingDetails1.setParticipantOfMeetings(List.of(participant1));
        employee.setMeetingDetails(List.of(meetingDetails1));
        when(participantsOfMeetingServiceImplUnderTest.employeeRepository.findByEmail("email")).thenReturn(employee);

        // Configure ParticipantsofMeetingRepository.save(...).
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant2.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor1);
        mid.setTotalHours("totalHours");
        participant2.setMid(mid);
        participant2.setTimeExisted("timeExisted");
        participant2.setDuration("duration");
        participant2.setAssesmentScore("assesmentScore");
        participant2.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.save(
                new ParticipantOfMeeting())).thenReturn(participant2);

        // Configure ParticipantsofMeetingRepository.findByMidAndEid(...).
        final ParticipantOfMeeting participant3 = new ParticipantOfMeeting();
        final Employee eid1 = new Employee();
        eid1.setEid(0L);
        eid1.setFirstName("firstName");
        eid1.setLastName("lastName");
        eid1.setEmail("email");
        participant3.setEid(eid1);
        final MeetingDetails mid1 = new MeetingDetails();
        mid1.setMeetingId("meetingId");
        mid1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid1.setTopic("topic");
        final Employee meetingAnchor2 = new Employee();
        meetingAnchor2.setEid(0L);
        meetingAnchor2.setFirstName("firstName");
        meetingAnchor2.setLastName("lastName");
        meetingAnchor2.setEmail("email");
        mid1.setMeetingAnchor(meetingAnchor2);
        mid1.setTotalHours("totalHours");
        participant3.setMid(mid1);
        participant3.setTimeExisted("timeExisted");
        participant3.setDuration("duration");
        participant3.setAssesmentScore("assesmentScore");
        participant3.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMidAndEid("meetingId",
                0L)).thenReturn(participant3);

        // Run the test
        final Object result = participantsOfMeetingServiceImplUnderTest.saveExcel();

        // Verify the results
        verify(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository).save(new MeetingDetails());
        verify(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository).save(
                new ParticipantOfMeeting());
    }

    @Test
    void testSaveExcel_DriveQuickstartThrowsIOException() throws Exception {
        // Setup
        when(participantsOfMeetingServiceImplUnderTest.driveQuickstart.getFilesFromDrive())
                .thenThrow(IOException.class);

        // Run the test
        assertThatThrownBy(() -> participantsOfMeetingServiceImplUnderTest.saveExcel())
                .isInstanceOf(ExcelReadingException.class);
    }

    @Test
    void testSaveExcel_DriveQuickstartThrowsGeneralSecurityException() throws Exception {
        // Setup
        when(participantsOfMeetingServiceImplUnderTest.driveQuickstart.getFilesFromDrive())
                .thenThrow(GeneralSecurityException.class);

        // Run the test
        assertThatThrownBy(() -> participantsOfMeetingServiceImplUnderTest.saveExcel())
                .isInstanceOf(ExcelReadingException.class);
    }

    @Test
    void testSaveExcel_EmployeeRepositoryReturnsNull() throws Exception {
        // Setup
        // Configure DriveQuickstart.getFilesFromDrive(...).
        final MeetingDto meetingDto = new MeetingDto();
        meetingDto.setId(0L);
        meetingDto.setMeetingId("meetingId");
        meetingDto.setFirstName("firstName");
        meetingDto.setLastName("lastName");
        meetingDto.setEmail("email");
        meetingDto.setDuration("duration");
        meetingDto.setTimeJoined("timeJoined");
        meetingDto.setTimeExited("timeExisted");
        meetingDto.setMeetingDate("meetingDate");
        meetingDto.setAssessmentScore("assesmentScore");
        final List<List<MeetingDto>> lists = List.of(List.of(meetingDto));
        when(participantsOfMeetingServiceImplUnderTest.driveQuickstart.getFilesFromDrive()).thenReturn(lists);

        // Configure MeetingDetailsRepository.save(...).
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        meetingAnchor.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant));
        meetingDetails.setMeetingAnchor(meetingAnchor);
        meetingDetails.setTotalHours("totalHours");
        when(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository.save(new MeetingDetails()))
                .thenReturn(meetingDetails);

        when(participantsOfMeetingServiceImplUnderTest.employeeRepository.findByEmail("email")).thenReturn(null);

        // Configure ParticipantsofMeetingRepository.save(...).
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant1.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor1);
        mid.setTotalHours("totalHours");
        participant1.setMid(mid);
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        participant1.setAssesmentScore("assesmentScore");
        participant1.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.save(
                new ParticipantOfMeeting())).thenReturn(participant1);

        // Configure ParticipantsofMeetingRepository.findByMidAndEid(...).
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        final Employee eid1 = new Employee();
        eid1.setEid(0L);
        eid1.setFirstName("firstName");
        eid1.setLastName("lastName");
        eid1.setEmail("email");
        participant2.setEid(eid1);
        final MeetingDetails mid1 = new MeetingDetails();
        mid1.setMeetingId("meetingId");
        mid1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid1.setTopic("topic");
        final Employee meetingAnchor2 = new Employee();
        meetingAnchor2.setEid(0L);
        meetingAnchor2.setFirstName("firstName");
        meetingAnchor2.setLastName("lastName");
        meetingAnchor2.setEmail("email");
        mid1.setMeetingAnchor(meetingAnchor2);
        mid1.setTotalHours("totalHours");
        participant2.setMid(mid1);
        participant2.setTimeExisted("timeExisted");
        participant2.setDuration("duration");
        participant2.setAssesmentScore("assesmentScore");
        participant2.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMidAndEid("meetingId",
                0L)).thenReturn(participant2);

        // Run the test
        final Object result = participantsOfMeetingServiceImplUnderTest.saveExcel();

        // Verify the results
        verify(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository).save(new MeetingDetails());
        verify(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository).save(
                new ParticipantOfMeeting());
    }

    @Test
    void testStoreInDatabase() throws Exception {
        // Setup
        final MeetingDto meetingDto = new MeetingDto();
        meetingDto.setId(0L);
        meetingDto.setMeetingId("meetingId");
        meetingDto.setFirstName("firstName");
        meetingDto.setLastName("lastName");
        meetingDto.setEmail("email");
        meetingDto.setDuration("duration");
        meetingDto.setTimeJoined("timeJoined");
        meetingDto.setTimeExited("timeExisted");
        meetingDto.setMeetingDate("meetingDate");
        meetingDto.setAssessmentScore("assesmentScore");
        final List<MeetingDto> listData = List.of(meetingDto);

        // Configure MeetingDetailsRepository.save(...).
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        meetingAnchor.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant));
        meetingDetails.setMeetingAnchor(meetingAnchor);
        meetingDetails.setTotalHours("totalHours");
        when(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository.save(new MeetingDetails()))
                .thenReturn(meetingDetails);

        // Configure EmployeeRepository.findByEmail(...).
        final Employee employee = new Employee();
        employee.setEid(0L);
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setEmail("email");
        employee.setProjectCode("projectCode");
        employee.setLocation("location");
        final MeetingDetails meetingDetails1 = new MeetingDetails();
        meetingDetails1.setMeetingId("meetingId");
        meetingDetails1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails1.setTopic("topic");
        meetingDetails1.setTotalHours("totalHours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        participant1.setAssesmentScore("assesmentScore");
        meetingDetails1.setParticipantOfMeetings(List.of(participant1));
        employee.setMeetingDetails(List.of(meetingDetails1));
        when(participantsOfMeetingServiceImplUnderTest.employeeRepository.findByEmail("email")).thenReturn(employee);

        // Configure ParticipantsofMeetingRepository.save(...).
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant2.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor1);
        mid.setTotalHours("totalHours");
        participant2.setMid(mid);
        participant2.setTimeExisted("timeExisted");
        participant2.setDuration("duration");
        participant2.setAssesmentScore("assesmentScore");
        participant2.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.save(
                new ParticipantOfMeeting())).thenReturn(participant2);

        // Run the test
        participantsOfMeetingServiceImplUnderTest.storeInDatabase(listData);

        // Verify the results
        verify(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository).save(new MeetingDetails());
        verify(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository).save(
                new ParticipantOfMeeting());
    }

    @Test
    void testStoreInDatabase_EmployeeRepositoryReturnsNull() throws Exception {
        // Setup
        final MeetingDto meetingDto = new MeetingDto();
        meetingDto.setId(0L);
        meetingDto.setMeetingId("meetingId");
        meetingDto.setFirstName("firstName");
        meetingDto.setLastName("lastName");
        meetingDto.setEmail("email");
        meetingDto.setDuration("duration");
        meetingDto.setTimeJoined("timeJoined");
        meetingDto.setTimeExited("timeExisted");
        meetingDto.setMeetingDate("meetingDate");
        meetingDto.setAssessmentScore("assesmentScore");
        final List<MeetingDto> listData = List.of(meetingDto);

        // Configure MeetingDetailsRepository.save(...).
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        meetingAnchor.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant));
        meetingDetails.setMeetingAnchor(meetingAnchor);
        meetingDetails.setTotalHours("totalHours");
        when(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository.save(new MeetingDetails()))
                .thenReturn(meetingDetails);

        when(participantsOfMeetingServiceImplUnderTest.employeeRepository.findByEmail("email")).thenReturn(null);

        // Configure ParticipantsofMeetingRepository.save(...).
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant1.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor1);
        mid.setTotalHours("totalHours");
        participant1.setMid(mid);
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        participant1.setAssesmentScore("assesmentScore");
        participant1.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.save(
                new ParticipantOfMeeting())).thenReturn(participant1);

        // Run the test
        participantsOfMeetingServiceImplUnderTest.storeInDatabase(listData);

        // Verify the results
        verify(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository).save(new MeetingDetails());
        verify(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository).save(
                new ParticipantOfMeeting());
    }

    @Test
    void testStoreInDatabase_ThrowsException() {
        // Setup
        final MeetingDto meetingDto = new MeetingDto();
        meetingDto.setId(0L);
        meetingDto.setMeetingId("meetingId");
        meetingDto.setFirstName("firstName");
        meetingDto.setLastName("lastName");
        meetingDto.setEmail("email");
        meetingDto.setDuration("duration");
        meetingDto.setTimeJoined("timeJoined");
        meetingDto.setTimeExited("timeExisted");
        meetingDto.setMeetingDate("meetingDate");
        meetingDto.setAssessmentScore("assesmentScore");
        final List<MeetingDto> listData = List.of(meetingDto);

        // Configure MeetingDetailsRepository.save(...).
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        meetingAnchor.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant));
        meetingDetails.setMeetingAnchor(meetingAnchor);
        meetingDetails.setTotalHours("totalHours");
        when(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository.save(new MeetingDetails()))
                .thenReturn(meetingDetails);

        // Configure EmployeeRepository.findByEmail(...).
        final Employee employee = new Employee();
        employee.setEid(0L);
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setEmail("email");
        employee.setProjectCode("projectCode");
        employee.setLocation("location");
        final MeetingDetails meetingDetails1 = new MeetingDetails();
        meetingDetails1.setMeetingId("meetingId");
        meetingDetails1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails1.setTopic("topic");
        meetingDetails1.setTotalHours("totalHours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        participant1.setAssesmentScore("assesmentScore");
        meetingDetails1.setParticipantOfMeetings(List.of(participant1));
        employee.setMeetingDetails(List.of(meetingDetails1));
        when(participantsOfMeetingServiceImplUnderTest.employeeRepository.findByEmail("email")).thenReturn(employee);

        // Configure ParticipantsofMeetingRepository.save(...).
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant2.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor1);
        mid.setTotalHours("totalHours");
        participant2.setMid(mid);
        participant2.setTimeExisted("timeExisted");
        participant2.setDuration("duration");
        participant2.setAssesmentScore("assesmentScore");
        participant2.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.save(
                new ParticipantOfMeeting())).thenReturn(participant2);

        // Run the test
        assertThatThrownBy(() -> participantsOfMeetingServiceImplUnderTest.storeInDatabase(listData))
                .isInstanceOf(Exception.class);
        verify(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository).save(new MeetingDetails());
        verify(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository).save(
                new ParticipantOfMeeting());
    }

    @Test
    void testStoreMarksInDatabase() throws Exception {
        // Setup
        final MeetingDto meetingDto = new MeetingDto();
        meetingDto.setId(0L);
        meetingDto.setMeetingId("meetingId");
        meetingDto.setFirstName("firstName");
        meetingDto.setLastName("lastName");
        meetingDto.setEmail("email");
        meetingDto.setDuration("duration");
        meetingDto.setTimeJoined("timeJoined");
        meetingDto.setTimeExited("timeExisted");
        meetingDto.setMeetingDate("meetingDate");
        meetingDto.setAssessmentScore("assesmentScore");
        final List<MeetingDto> listData = List.of(meetingDto);

        // Configure EmployeeRepository.findByEmail(...).
        final Employee employee = new Employee();
        employee.setEid(0L);
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setEmail("email");
        employee.setProjectCode("projectCode");
        employee.setLocation("location");
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        meetingDetails.setTotalHours("totalHours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        employee.setMeetingDetails(List.of(meetingDetails));
        when(participantsOfMeetingServiceImplUnderTest.employeeRepository.findByEmail("email")).thenReturn(employee);

        // Configure ParticipantsofMeetingRepository.findByMidAndEid(...).
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant1.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor);
        mid.setTotalHours("totalHours");
        participant1.setMid(mid);
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        participant1.setAssesmentScore("assesmentScore");
        participant1.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMidAndEid("meetingId",
                0L)).thenReturn(participant1);

        // Configure ParticipantsofMeetingRepository.save(...).
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        final Employee eid1 = new Employee();
        eid1.setEid(0L);
        eid1.setFirstName("firstName");
        eid1.setLastName("lastName");
        eid1.setEmail("email");
        participant2.setEid(eid1);
        final MeetingDetails mid1 = new MeetingDetails();
        mid1.setMeetingId("meetingId");
        mid1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid1.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        mid1.setMeetingAnchor(meetingAnchor1);
        mid1.setTotalHours("totalHours");
        participant2.setMid(mid1);
        participant2.setTimeExisted("timeExisted");
        participant2.setDuration("duration");
        participant2.setAssesmentScore("assesmentScore");
        participant2.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.save(
                new ParticipantOfMeeting())).thenReturn(participant2);

        // Run the test
        participantsOfMeetingServiceImplUnderTest.storeMarksInDatabase(listData);

        // Verify the results
        verify(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository).save(
                new ParticipantOfMeeting());
    }

    @Test
    void testStoreMarksInDatabase_EmployeeRepositoryReturnsNull() throws Exception {
        // Setup
        final MeetingDto meetingDto = new MeetingDto();
        meetingDto.setId(0L);
        meetingDto.setMeetingId("meetingId");
        meetingDto.setFirstName("firstName");
        meetingDto.setLastName("lastName");
        meetingDto.setEmail("email");
        meetingDto.setDuration("duration");
        meetingDto.setTimeJoined("timeJoined");
        meetingDto.setTimeExited("timeExisted");
        meetingDto.setMeetingDate("meetingDate");
        meetingDto.setAssessmentScore("assesmentScore");
        final List<MeetingDto> listData = List.of(meetingDto);
        when(participantsOfMeetingServiceImplUnderTest.employeeRepository.findByEmail("email")).thenReturn(null);

        // Configure ParticipantsofMeetingRepository.findByMidAndEid(...).
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor);
        mid.setTotalHours("totalHours");
        participant.setMid(mid);
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        participant.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMidAndEid("meetingId",
                0L)).thenReturn(participant);

        // Configure ParticipantsofMeetingRepository.save(...).
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        final Employee eid1 = new Employee();
        eid1.setEid(0L);
        eid1.setFirstName("firstName");
        eid1.setLastName("lastName");
        eid1.setEmail("email");
        participant1.setEid(eid1);
        final MeetingDetails mid1 = new MeetingDetails();
        mid1.setMeetingId("meetingId");
        mid1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid1.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        mid1.setMeetingAnchor(meetingAnchor1);
        mid1.setTotalHours("totalHours");
        participant1.setMid(mid1);
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        participant1.setAssesmentScore("assesmentScore");
        participant1.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.save(
                new ParticipantOfMeeting())).thenReturn(participant1);

        // Run the test
        participantsOfMeetingServiceImplUnderTest.storeMarksInDatabase(listData);

        // Verify the results
        verify(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository).save(
                new ParticipantOfMeeting());
    }

    @Test
    void testStoreMarksInDatabase_ThrowsException() {
        // Setup
        final MeetingDto meetingDto = new MeetingDto();
        meetingDto.setId(0L);
        meetingDto.setMeetingId("meetingId");
        meetingDto.setFirstName("firstName");
        meetingDto.setLastName("lastName");
        meetingDto.setEmail("email");
        meetingDto.setDuration("duration");
        meetingDto.setTimeJoined("timeJoined");
        meetingDto.setTimeExited("timeExisted");
        meetingDto.setMeetingDate("meetingDate");
        meetingDto.setAssessmentScore("assesmentScore");
        final List<MeetingDto> listData = List.of(meetingDto);

        // Configure EmployeeRepository.findByEmail(...).
        final Employee employee = new Employee();
        employee.setEid(0L);
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setEmail("email");
        employee.setProjectCode("projectCode");
        employee.setLocation("location");
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        meetingDetails.setTotalHours("totalHours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        employee.setMeetingDetails(List.of(meetingDetails));
        when(participantsOfMeetingServiceImplUnderTest.employeeRepository.findByEmail("email")).thenReturn(employee);

        // Configure ParticipantsofMeetingRepository.findByMidAndEid(...).
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant1.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor);
        mid.setTotalHours("totalHours");
        participant1.setMid(mid);
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        participant1.setAssesmentScore("assesmentScore");
        participant1.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMidAndEid("meetingId",
                0L)).thenReturn(participant1);

        // Configure ParticipantsofMeetingRepository.save(...).
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        final Employee eid1 = new Employee();
        eid1.setEid(0L);
        eid1.setFirstName("firstName");
        eid1.setLastName("lastName");
        eid1.setEmail("email");
        participant2.setEid(eid1);
        final MeetingDetails mid1 = new MeetingDetails();
        mid1.setMeetingId("meetingId");
        mid1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid1.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        mid1.setMeetingAnchor(meetingAnchor1);
        mid1.setTotalHours("totalHours");
        participant2.setMid(mid1);
        participant2.setTimeExisted("timeExisted");
        participant2.setDuration("duration");
        participant2.setAssesmentScore("assesmentScore");
        participant2.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.save(
                new ParticipantOfMeeting())).thenReturn(participant2);

        // Run the test
        assertThatThrownBy(() -> participantsOfMeetingServiceImplUnderTest.storeMarksInDatabase(listData))
                .isInstanceOf(Exception.class);
        verify(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository).save(
                new ParticipantOfMeeting());
    }

    @Test
    void testGetAllMeetingsParticipantsList() {
        // Setup
        // Configure ParticipantsofMeetingRepository.findAll(...).
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor);
        mid.setTotalHours("totalHours");
        participant.setMid(mid);
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        participant.setTimeJoined("timeJoined");
        final List<ParticipantOfMeeting> participantOfMeetings = List.of(participant);
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findAll())
                .thenReturn(participantOfMeetings);

        // Run the test
        final Object result = participantsOfMeetingServiceImplUnderTest.getAllMeetingsParticipantsList();

        // Verify the results
    }

    @Test
    void testGetAllMeetingsParticipantsList_ParticipantsofMeetingRepositoryReturnsNoItems() {
        // Setup
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findAll())
                .thenReturn(Collections.emptyList());

        // Run the test
        final Object result = participantsOfMeetingServiceImplUnderTest.getAllMeetingsParticipantsList();

        // Verify the results
    }

    @Test
    void testGetParticipantsByMeetingId() {
        // Setup
        final MeetingDto meetingDto = new MeetingDto();
        meetingDto.setId(0L);
        meetingDto.setMeetingId("meetingId");
        meetingDto.setFirstName("firstName");
        meetingDto.setLastName("lastName");
        meetingDto.setEmail("email");
        meetingDto.setDuration("duration");
        meetingDto.setTimeJoined("timeJoined");
        meetingDto.setTimeExited("timeExisted");
        meetingDto.setMeetingDate("meetingDate");
        meetingDto.setAssessmentScore("assesmentScore");
        final List<MeetingDto> expectedResult = List.of(meetingDto);

        // Configure ParticipantsofMeetingRepository.findByMid(...).
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor);
        mid.setTotalHours("totalHours");
        participant.setMid(mid);
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        participant.setTimeJoined("timeJoined");
        final List<ParticipantOfMeeting> participantOfMeetings = List.of(participant);
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMid(
                "meetingId")).thenReturn(participantOfMeetings);

        // Run the test
        final List<MeetingDto> result = participantsOfMeetingServiceImplUnderTest.getParticipantsByMeetingId(
                "meetingId");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetParticipantsByMeetingId_ParticipantsofMeetingRepositoryReturnsNull() {
        // Setup
        final MeetingDto meetingDto = new MeetingDto();
        meetingDto.setId(0L);
        meetingDto.setMeetingId("meetingId");
        meetingDto.setFirstName("firstName");
        meetingDto.setLastName("lastName");
        meetingDto.setEmail("email");
        meetingDto.setDuration("duration");
        meetingDto.setTimeJoined("timeJoined");
        meetingDto.setTimeExited("timeExisted");
        meetingDto.setMeetingDate("meetingDate");
        meetingDto.setAssessmentScore("assesmentScore");
        final List<MeetingDto> expectedResult = List.of(meetingDto);
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMid(
                "meetingId")).thenReturn(null);

        // Run the test
        final List<MeetingDto> result = participantsOfMeetingServiceImplUnderTest.getParticipantsByMeetingId(
                "meetingId");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetParticipantsByMeetingId_ParticipantsofMeetingRepositoryReturnsNoItems() {
        // Setup
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMid(
                "meetingId")).thenReturn(Collections.emptyList());

        // Run the test
        final List<MeetingDto> result = participantsOfMeetingServiceImplUnderTest.getParticipantsByMeetingId(
                "meetingId");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetParticipantsAssesmentScoreByMeetingId() {
        // Setup
        // Configure ParticipantsofMeetingRepository.findByMid(...).
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor);
        mid.setTotalHours("totalHours");
        participant.setMid(mid);
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        participant.setTimeJoined("timeJoined");
        final List<ParticipantOfMeeting> participantOfMeetings = List.of(participant);
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMid(
                "meetingId")).thenReturn(participantOfMeetings);

        // Run the test
        final Object result = participantsOfMeetingServiceImplUnderTest.getParticipantsAssesmentScoreByMeetingId(
                "meetingId");

        // Verify the results
    }

    @Test
    void testGetParticipantsAssesmentScoreByMeetingId_ParticipantsofMeetingRepositoryReturnsNull() {
        // Setup
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMid(
                "meetingId")).thenReturn(null);

        // Run the test
        final Object result = participantsOfMeetingServiceImplUnderTest.getParticipantsAssesmentScoreByMeetingId(
                "meetingId");

        // Verify the results
    }

    @Test
    void testGetParticipantsAssesmentScoreByMeetingId_ParticipantsofMeetingRepositoryReturnsNoItems() {
        // Setup
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMid(
                "meetingId")).thenReturn(Collections.emptyList());

        // Run the test
        final Object result = participantsOfMeetingServiceImplUnderTest.getParticipantsAssesmentScoreByMeetingId(
                "meetingId");

        // Verify the results
    }

    @Test
    void testListOfAbsentees() {
        // Setup
        // Configure ParticipantsofMeetingRepository.findByMid(...).
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor);
        mid.setTotalHours("totalHours");
        participant.setMid(mid);
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        participant.setTimeJoined("timeJoined");
        final List<ParticipantOfMeeting> participantOfMeetings = List.of(participant);
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMid(
                "meetingId")).thenReturn(participantOfMeetings);

        // Configure MeetingDetailsRepository.findByMeetingId(...).
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        meetingAnchor1.setProjectCode("projectCode");
        meetingAnchor1.setLocation("location");
        meetingAnchor1.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        meetingAnchor1.setParticipantsOfMeeting(List.of(participant1));
        meetingDetails.setMeetingAnchor(meetingAnchor1);
        meetingDetails.setTotalHours("totalHours");
        when(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository.findByMeetingId(
                "meetingId")).thenReturn(meetingDetails);

        // Configure EmployeeService.getAllEmployee(...).
        final EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEid(0L);
        employeeDto.setFirstName("firstName");
        employeeDto.setLastName("lastName");
        employeeDto.setEmail("email");
        employeeDto.setProjectCode("projectCode");
        employeeDto.setLocation("location");
        final List<EmployeeDto> employeeDtos = List.of(employeeDto);
        when(participantsOfMeetingServiceImplUnderTest.employeeService.getAllEmployee()).thenReturn(employeeDtos);

        // Run the test
        final List<EmployeeDto> result = participantsOfMeetingServiceImplUnderTest.listOfAbsentees("meetingId");

        // Verify the results
    }

    @Test
    void testListOfAbsentees_ParticipantsofMeetingRepositoryReturnsNull() {
        // Setup
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMid(
                "meetingId")).thenReturn(null);

        // Configure MeetingDetailsRepository.findByMeetingId(...).
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        meetingAnchor.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant));
        meetingDetails.setMeetingAnchor(meetingAnchor);
        meetingDetails.setTotalHours("totalHours");
        when(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository.findByMeetingId(
                "meetingId")).thenReturn(meetingDetails);

        // Configure EmployeeService.getAllEmployee(...).
        final EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEid(0L);
        employeeDto.setFirstName("firstName");
        employeeDto.setLastName("lastName");
        employeeDto.setEmail("email");
        employeeDto.setProjectCode("projectCode");
        employeeDto.setLocation("location");
        final List<EmployeeDto> employeeDtos = List.of(employeeDto);
        when(participantsOfMeetingServiceImplUnderTest.employeeService.getAllEmployee()).thenReturn(employeeDtos);

        // Run the test
        final List<EmployeeDto> result = participantsOfMeetingServiceImplUnderTest.listOfAbsentees("meetingId");

        // Verify the results
    }

    @Test
    void testListOfAbsentees_ParticipantsofMeetingRepositoryReturnsNoItems() {
        // Setup
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMid(
                "meetingId")).thenReturn(Collections.emptyList());

        // Configure MeetingDetailsRepository.findByMeetingId(...).
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        meetingAnchor.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant));
        meetingDetails.setMeetingAnchor(meetingAnchor);
        meetingDetails.setTotalHours("totalHours");
        when(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository.findByMeetingId(
                "meetingId")).thenReturn(meetingDetails);

        // Configure EmployeeService.getAllEmployee(...).
        final EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEid(0L);
        employeeDto.setFirstName("firstName");
        employeeDto.setLastName("lastName");
        employeeDto.setEmail("email");
        employeeDto.setProjectCode("projectCode");
        employeeDto.setLocation("location");
        final List<EmployeeDto> employeeDtos = List.of(employeeDto);
        when(participantsOfMeetingServiceImplUnderTest.employeeService.getAllEmployee()).thenReturn(employeeDtos);

        // Run the test
        final List<EmployeeDto> result = participantsOfMeetingServiceImplUnderTest.listOfAbsentees("meetingId");

        // Verify the results
    }

    @Test
    void testListOfAbsentees_EmployeeServiceReturnsNoItems() {
        // Setup
        // Configure ParticipantsofMeetingRepository.findByMid(...).
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor);
        mid.setTotalHours("totalHours");
        participant.setMid(mid);
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        participant.setTimeJoined("timeJoined");
        final List<ParticipantOfMeeting> participantOfMeetings = List.of(participant);
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMid(
                "meetingId")).thenReturn(participantOfMeetings);

        // Configure MeetingDetailsRepository.findByMeetingId(...).
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        meetingAnchor1.setProjectCode("projectCode");
        meetingAnchor1.setLocation("location");
        meetingAnchor1.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        meetingAnchor1.setParticipantsOfMeeting(List.of(participant1));
        meetingDetails.setMeetingAnchor(meetingAnchor1);
        meetingDetails.setTotalHours("totalHours");
        when(participantsOfMeetingServiceImplUnderTest.meetingDetailsRepository.findByMeetingId(
                "meetingId")).thenReturn(meetingDetails);

        when(participantsOfMeetingServiceImplUnderTest.employeeService.getAllEmployee())
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<EmployeeDto> result = participantsOfMeetingServiceImplUnderTest.listOfAbsentees("meetingId");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetAssesmentScoreByMeetingIdAndEid() {
        // Setup
        // Configure ParticipantsofMeetingRepository.findByMidAndEid(...).
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        participant.setEid(eid);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        mid.setMeetingAnchor(meetingAnchor);
        mid.setTotalHours("totalHours");
        participant.setMid(mid);
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        participant.setTimeJoined("timeJoined");
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.findByMidAndEid("meetingId",
                0L)).thenReturn(participant);

        // Run the test
        final Object result = participantsOfMeetingServiceImplUnderTest.getAssesmentScoreByMeetingIdAndEid(0L,
                "meetingId");

        // Verify the results
    }

    @Test
    void testGetParticipantsDetailsBetweenParticularDate() {
        // Setup
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.countSeesionAttened(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), 0L)).thenReturn(0);
        when(participantsOfMeetingServiceImplUnderTest.participantsofMeetingRepository.getAverageScore(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), 0L)).thenReturn(0);

        // Run the test
        final Object result = participantsOfMeetingServiceImplUnderTest.getParticipantsDetailsBetweenParticularDate(0L,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Verify the results
    }
}
