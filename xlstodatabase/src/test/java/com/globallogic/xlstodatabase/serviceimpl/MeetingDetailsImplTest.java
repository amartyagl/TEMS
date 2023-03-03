package com.globallogic.xlstodatabase.serviceimpl;

import com.globallogic.xlstodatabase.dto.MeetingDetailsDto;
import com.globallogic.xlstodatabase.dto.SmeTopicDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import com.globallogic.xlstodatabase.modal.Employee;
import com.globallogic.xlstodatabase.modal.MeetingDetails;
import com.globallogic.xlstodatabase.modal.ParticipantOfMeeting;
import com.globallogic.xlstodatabase.modal.SMEDetails;
import com.globallogic.xlstodatabase.repository.EmployeeRepository;
import com.globallogic.xlstodatabase.repository.MeetingDetailsRepository;
import com.globallogic.xlstodatabase.repository.SMEDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class MeetingDetailsImplTest {

    private MeetingDetailsImpl meetingDetailsImplUnderTest;

    @BeforeEach
    void setUp() {
        meetingDetailsImplUnderTest = new MeetingDetailsImpl();
        meetingDetailsImplUnderTest.meetingDetailsRepository = mock(MeetingDetailsRepository.class);
        meetingDetailsImplUnderTest.employeeRepository = mock(EmployeeRepository.class);
        meetingDetailsImplUnderTest.smeDetailsRepository = mock(SMEDetailsRepository.class);
    }

    @Test
    void testCreateMeeting() throws Exception {
        // Setup
        final MeetingDetailsDto meetingDetailsDto = new MeetingDetailsDto();
        meetingDetailsDto.setEid(0L);
        meetingDetailsDto.setMeetingId("meetingId");
        meetingDetailsDto.setTopic("topic");
        meetingDetailsDto.setMeetingDate("meetingDate");
        meetingDetailsDto.setHours("hours");

        // Configure EmployeeRepository.findById(...).
        final Employee employee1 = new Employee();
        employee1.setEid(0L);
        employee1.setFirstName("firstName");
        employee1.setLastName("lastName");
        employee1.setEmail("email");
        employee1.setProjectCode("projectCode");
        employee1.setLocation("location");
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        meetingDetails.setTotalHours("hours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        employee1.setMeetingDetails(List.of(meetingDetails));
        final Optional<Employee> employee = Optional.of(employee1);
        when(meetingDetailsImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

        // Configure SMEDetailsRepository.findyByEidAndTopic(...).
        final SMEDetails smeDetails = new SMEDetails();
        smeDetails.setSmeId(0);
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        eid.setProjectCode("projectCode");
        eid.setLocation("location");
        final MeetingDetails meetingDetails1 = new MeetingDetails();
        meetingDetails1.setMeetingId("meetingId");
        meetingDetails1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails1.setTopic("topic");
        meetingDetails1.setTotalHours("hours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        meetingDetails1.setParticipantOfMeetings(List.of(participant1));
        eid.setMeetingDetails(List.of(meetingDetails1));
        smeDetails.setEid(eid);
        when(meetingDetailsImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic")).thenReturn(smeDetails);

        // Configure MeetingDetailsRepository.save(...).
        final MeetingDetails meetingDetails2 = new MeetingDetails();
        meetingDetails2.setMeetingId("meetingId");
        meetingDetails2.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails2.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        meetingAnchor.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        participant2.setTimeExisted("timeExisted");
        participant2.setDuration("duration");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant2));
        meetingDetails2.setMeetingAnchor(meetingAnchor);
        meetingDetails2.setTotalHours("hours");
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.save(new MeetingDetails()))
                .thenReturn(meetingDetails2);

        // Run the test
        final Object result = meetingDetailsImplUnderTest.createMeeting(meetingDetailsDto);

        // Verify the results
        verify(meetingDetailsImplUnderTest.meetingDetailsRepository).save(new MeetingDetails());
    }

    @Test
    void testCreateMeeting_EmployeeRepositoryReturnsAbsent() {
        // Setup
        final MeetingDetailsDto meetingDetailsDto = new MeetingDetailsDto();
        meetingDetailsDto.setEid(0L);
        meetingDetailsDto.setMeetingId("meetingId");
        meetingDetailsDto.setTopic("topic");
        meetingDetailsDto.setMeetingDate("meetingDate");
        meetingDetailsDto.setHours("hours");

        when(meetingDetailsImplUnderTest.employeeRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> meetingDetailsImplUnderTest.createMeeting(meetingDetailsDto))
                .isInstanceOf(EmployeeNotFound.class);
    }

    @Test
    void testCreateMeeting_SMEDetailsRepositoryReturnsNull() {
        // Setup
        final MeetingDetailsDto meetingDetailsDto = new MeetingDetailsDto();
        meetingDetailsDto.setEid(0L);
        meetingDetailsDto.setMeetingId("meetingId");
        meetingDetailsDto.setTopic("topic");
        meetingDetailsDto.setMeetingDate("meetingDate");
        meetingDetailsDto.setHours("hours");

        // Configure EmployeeRepository.findById(...).
        final Employee employee1 = new Employee();
        employee1.setEid(0L);
        employee1.setFirstName("firstName");
        employee1.setLastName("lastName");
        employee1.setEmail("email");
        employee1.setProjectCode("projectCode");
        employee1.setLocation("location");
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        meetingDetails.setTotalHours("hours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        employee1.setMeetingDetails(List.of(meetingDetails));
        final Optional<Employee> employee = Optional.of(employee1);
        when(meetingDetailsImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

        when(meetingDetailsImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic")).thenReturn(null);

        // Run the test
        assertThatThrownBy(() -> meetingDetailsImplUnderTest.createMeeting(meetingDetailsDto))
                .isInstanceOf(SMESubjectAvailiability.class);
    }

    @Test
    void testCreateMeeting_ThrowsException() {
        // Setup
        final MeetingDetailsDto meetingDetailsDto = new MeetingDetailsDto();
        meetingDetailsDto.setEid(0L);
        meetingDetailsDto.setMeetingId("meetingId");
        meetingDetailsDto.setTopic("topic");
        meetingDetailsDto.setMeetingDate("meetingDate");
        meetingDetailsDto.setHours("hours");

        // Configure EmployeeRepository.findById(...).
        final Employee employee1 = new Employee();
        employee1.setEid(0L);
        employee1.setFirstName("firstName");
        employee1.setLastName("lastName");
        employee1.setEmail("email");
        employee1.setProjectCode("projectCode");
        employee1.setLocation("location");
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        meetingDetails.setTotalHours("hours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        employee1.setMeetingDetails(List.of(meetingDetails));
        final Optional<Employee> employee = Optional.of(employee1);
        when(meetingDetailsImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

        // Configure SMEDetailsRepository.findyByEidAndTopic(...).
        final SMEDetails smeDetails = new SMEDetails();
        smeDetails.setSmeId(0);
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        eid.setProjectCode("projectCode");
        eid.setLocation("location");
        final MeetingDetails meetingDetails1 = new MeetingDetails();
        meetingDetails1.setMeetingId("meetingId");
        meetingDetails1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails1.setTopic("topic");
        meetingDetails1.setTotalHours("hours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        meetingDetails1.setParticipantOfMeetings(List.of(participant1));
        eid.setMeetingDetails(List.of(meetingDetails1));
        smeDetails.setEid(eid);
        when(meetingDetailsImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic")).thenReturn(smeDetails);

        // Configure MeetingDetailsRepository.save(...).
        final MeetingDetails meetingDetails2 = new MeetingDetails();
        meetingDetails2.setMeetingId("meetingId");
        meetingDetails2.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails2.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        meetingAnchor.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        participant2.setTimeExisted("timeExisted");
        participant2.setDuration("duration");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant2));
        meetingDetails2.setMeetingAnchor(meetingAnchor);
        meetingDetails2.setTotalHours("hours");
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.save(new MeetingDetails()))
                .thenReturn(meetingDetails2);

        // Run the test
        assertThatThrownBy(() -> meetingDetailsImplUnderTest.createMeeting(meetingDetailsDto))
                .isInstanceOf(Exception.class);
        verify(meetingDetailsImplUnderTest.meetingDetailsRepository).save(new MeetingDetails());
    }

    @Test
    void testUpdateSMEAndTopic() throws Exception {
        // Setup
        final SmeTopicDto smeTopicDto = new SmeTopicDto();
        smeTopicDto.setSmeId(0L);
        smeTopicDto.setTopic("topic");
        smeTopicDto.setMeetingId("meetingId");

        // Configure EmployeeRepository.findById(...).
        final Employee employee1 = new Employee();
        employee1.setEid(0L);
        employee1.setFirstName("firstName");
        employee1.setLastName("lastName");
        employee1.setEmail("email");
        employee1.setProjectCode("projectCode");
        employee1.setLocation("location");
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        meetingDetails.setTotalHours("hours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        employee1.setMeetingDetails(List.of(meetingDetails));
        final Optional<Employee> employee = Optional.of(employee1);
        when(meetingDetailsImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

        // Configure SMEDetailsRepository.findyByEidAndTopic(...).
        final SMEDetails smeDetails = new SMEDetails();
        smeDetails.setSmeId(0);
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        eid.setProjectCode("projectCode");
        eid.setLocation("location");
        final MeetingDetails meetingDetails1 = new MeetingDetails();
        meetingDetails1.setMeetingId("meetingId");
        meetingDetails1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails1.setTopic("topic");
        meetingDetails1.setTotalHours("hours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        meetingDetails1.setParticipantOfMeetings(List.of(participant1));
        eid.setMeetingDetails(List.of(meetingDetails1));
        smeDetails.setEid(eid);
        when(meetingDetailsImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic")).thenReturn(smeDetails);

        // Configure MeetingDetailsRepository.findByMeetingId(...).
        final MeetingDetails meetingDetails2 = new MeetingDetails();
        meetingDetails2.setMeetingId("meetingId");
        meetingDetails2.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails2.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        meetingAnchor.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        participant2.setTimeExisted("timeExisted");
        participant2.setDuration("duration");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant2));
        meetingDetails2.setMeetingAnchor(meetingAnchor);
        meetingDetails2.setTotalHours("hours");
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.findByMeetingId("meetingId"))
                .thenReturn(meetingDetails2);

        // Configure MeetingDetailsRepository.save(...).
        final MeetingDetails meetingDetails3 = new MeetingDetails();
        meetingDetails3.setMeetingId("meetingId");
        meetingDetails3.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails3.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        meetingAnchor1.setProjectCode("projectCode");
        meetingAnchor1.setLocation("location");
        meetingAnchor1.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant3 = new ParticipantOfMeeting();
        participant3.setTimeExisted("timeExisted");
        participant3.setDuration("duration");
        meetingAnchor1.setParticipantsOfMeeting(List.of(participant3));
        meetingDetails3.setMeetingAnchor(meetingAnchor1);
        meetingDetails3.setTotalHours("hours");
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.save(new MeetingDetails()))
                .thenReturn(meetingDetails3);

        // Run the test
        final Object result = meetingDetailsImplUnderTest.updateSMEAndTopic(smeTopicDto);

        // Verify the results
        verify(meetingDetailsImplUnderTest.meetingDetailsRepository).save(new MeetingDetails());
    }

    @Test
    void testUpdateSMEAndTopic_EmployeeRepositoryReturnsAbsent() {
        // Setup
        final SmeTopicDto smeTopicDto = new SmeTopicDto();
        smeTopicDto.setSmeId(0L);
        smeTopicDto.setTopic("topic");
        smeTopicDto.setMeetingId("meetingId");

        when(meetingDetailsImplUnderTest.employeeRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> meetingDetailsImplUnderTest.updateSMEAndTopic(smeTopicDto))
                .isInstanceOf(EmployeeNotFound.class);
    }

    @Test
    void testUpdateSMEAndTopic_SMEDetailsRepositoryReturnsNull() {
        // Setup
        final SmeTopicDto smeTopicDto = new SmeTopicDto();
        smeTopicDto.setSmeId(0L);
        smeTopicDto.setTopic("topic");
        smeTopicDto.setMeetingId("meetingId");

        // Configure EmployeeRepository.findById(...).
        final Employee employee1 = new Employee();
        employee1.setEid(0L);
        employee1.setFirstName("firstName");
        employee1.setLastName("lastName");
        employee1.setEmail("email");
        employee1.setProjectCode("projectCode");
        employee1.setLocation("location");
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        meetingDetails.setTotalHours("hours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        employee1.setMeetingDetails(List.of(meetingDetails));
        final Optional<Employee> employee = Optional.of(employee1);
        when(meetingDetailsImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

        when(meetingDetailsImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic")).thenReturn(null);

        // Run the test
        assertThatThrownBy(() -> meetingDetailsImplUnderTest.updateSMEAndTopic(smeTopicDto))
                .isInstanceOf(SMESubjectAvailiability.class);
    }

    @Test
    void testUpdateSMEAndTopic_ThrowsEmployeeNotFound() {
        // Setup
        final SmeTopicDto smeTopicDto = new SmeTopicDto();
        smeTopicDto.setSmeId(0L);
        smeTopicDto.setTopic("topic");
        smeTopicDto.setMeetingId("meetingId");

        // Configure EmployeeRepository.findById(...).
        final Employee employee1 = new Employee();
        employee1.setEid(0L);
        employee1.setFirstName("firstName");
        employee1.setLastName("lastName");
        employee1.setEmail("email");
        employee1.setProjectCode("projectCode");
        employee1.setLocation("location");
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        meetingDetails.setTotalHours("hours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        employee1.setMeetingDetails(List.of(meetingDetails));
        final Optional<Employee> employee = Optional.of(employee1);
        when(meetingDetailsImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

        // Configure SMEDetailsRepository.findyByEidAndTopic(...).
        final SMEDetails smeDetails = new SMEDetails();
        smeDetails.setSmeId(0);
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        eid.setProjectCode("projectCode");
        eid.setLocation("location");
        final MeetingDetails meetingDetails1 = new MeetingDetails();
        meetingDetails1.setMeetingId("meetingId");
        meetingDetails1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails1.setTopic("topic");
        meetingDetails1.setTotalHours("hours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        meetingDetails1.setParticipantOfMeetings(List.of(participant1));
        eid.setMeetingDetails(List.of(meetingDetails1));
        smeDetails.setEid(eid);
        when(meetingDetailsImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic")).thenReturn(smeDetails);

        // Configure MeetingDetailsRepository.findByMeetingId(...).
        final MeetingDetails meetingDetails2 = new MeetingDetails();
        meetingDetails2.setMeetingId("meetingId");
        meetingDetails2.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails2.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        meetingAnchor.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        participant2.setTimeExisted("timeExisted");
        participant2.setDuration("duration");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant2));
        meetingDetails2.setMeetingAnchor(meetingAnchor);
        meetingDetails2.setTotalHours("hours");
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.findByMeetingId("meetingId"))
                .thenReturn(meetingDetails2);

        // Configure MeetingDetailsRepository.save(...).
        final MeetingDetails meetingDetails3 = new MeetingDetails();
        meetingDetails3.setMeetingId("meetingId");
        meetingDetails3.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails3.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        meetingAnchor1.setProjectCode("projectCode");
        meetingAnchor1.setLocation("location");
        meetingAnchor1.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant3 = new ParticipantOfMeeting();
        participant3.setTimeExisted("timeExisted");
        participant3.setDuration("duration");
        meetingAnchor1.setParticipantsOfMeeting(List.of(participant3));
        meetingDetails3.setMeetingAnchor(meetingAnchor1);
        meetingDetails3.setTotalHours("hours");
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.save(new MeetingDetails()))
                .thenReturn(meetingDetails3);

        // Run the test
        assertThatThrownBy(() -> meetingDetailsImplUnderTest.updateSMEAndTopic(smeTopicDto))
                .isInstanceOf(EmployeeNotFound.class);
        verify(meetingDetailsImplUnderTest.meetingDetailsRepository).save(new MeetingDetails());
    }

    @Test
    void testUpdateSMEAndTopic_ThrowsSMESubjectAvailiability() {
        // Setup
        final SmeTopicDto smeTopicDto = new SmeTopicDto();
        smeTopicDto.setSmeId(0L);
        smeTopicDto.setTopic("topic");
        smeTopicDto.setMeetingId("meetingId");

        // Configure EmployeeRepository.findById(...).
        final Employee employee1 = new Employee();
        employee1.setEid(0L);
        employee1.setFirstName("firstName");
        employee1.setLastName("lastName");
        employee1.setEmail("email");
        employee1.setProjectCode("projectCode");
        employee1.setLocation("location");
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        meetingDetails.setTotalHours("hours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        employee1.setMeetingDetails(List.of(meetingDetails));
        final Optional<Employee> employee = Optional.of(employee1);
        when(meetingDetailsImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

        // Configure SMEDetailsRepository.findyByEidAndTopic(...).
        final SMEDetails smeDetails = new SMEDetails();
        smeDetails.setSmeId(0);
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        eid.setProjectCode("projectCode");
        eid.setLocation("location");
        final MeetingDetails meetingDetails1 = new MeetingDetails();
        meetingDetails1.setMeetingId("meetingId");
        meetingDetails1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails1.setTopic("topic");
        meetingDetails1.setTotalHours("hours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        meetingDetails1.setParticipantOfMeetings(List.of(participant1));
        eid.setMeetingDetails(List.of(meetingDetails1));
        smeDetails.setEid(eid);
        when(meetingDetailsImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic")).thenReturn(smeDetails);

        // Configure MeetingDetailsRepository.findByMeetingId(...).
        final MeetingDetails meetingDetails2 = new MeetingDetails();
        meetingDetails2.setMeetingId("meetingId");
        meetingDetails2.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails2.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        meetingAnchor.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        participant2.setTimeExisted("timeExisted");
        participant2.setDuration("duration");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant2));
        meetingDetails2.setMeetingAnchor(meetingAnchor);
        meetingDetails2.setTotalHours("hours");
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.findByMeetingId("meetingId"))
                .thenReturn(meetingDetails2);

        // Configure MeetingDetailsRepository.save(...).
        final MeetingDetails meetingDetails3 = new MeetingDetails();
        meetingDetails3.setMeetingId("meetingId");
        meetingDetails3.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails3.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        meetingAnchor1.setProjectCode("projectCode");
        meetingAnchor1.setLocation("location");
        meetingAnchor1.setMeetingDetails(List.of(new MeetingDetails()));
        final ParticipantOfMeeting participant3 = new ParticipantOfMeeting();
        participant3.setTimeExisted("timeExisted");
        participant3.setDuration("duration");
        meetingAnchor1.setParticipantsOfMeeting(List.of(participant3));
        meetingDetails3.setMeetingAnchor(meetingAnchor1);
        meetingDetails3.setTotalHours("hours");
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.save(new MeetingDetails()))
                .thenReturn(meetingDetails3);

        // Run the test
        assertThatThrownBy(() -> meetingDetailsImplUnderTest.updateSMEAndTopic(smeTopicDto))
                .isInstanceOf(SMESubjectAvailiability.class);
        verify(meetingDetailsImplUnderTest.meetingDetailsRepository).save(new MeetingDetails());
    }

    @Test
    void testGetMeetingDetailsSpecificSME() {
        // Setup
        // Configure MeetingDetailsRepository.getBySME(...).
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
        meetingDetails.setTotalHours("hours");
        final List<MeetingDetails> meetingDetailsList = List.of(meetingDetails);
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getBySME(0L)).thenReturn(meetingDetailsList);

        // Run the test
        final Object result = meetingDetailsImplUnderTest.getMeetingDetailsSpecificSME(0L);

        // Verify the results
    }

    @Test
    void testGetMeetingDetailsSpecificSME_MeetingDetailsRepositoryReturnsNoItems() {
        // Setup
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getBySME(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final Object result = meetingDetailsImplUnderTest.getMeetingDetailsSpecificSME(0L);

        // Verify the results
    }

    @Test
    void testGetMeetingDetailsFor2Week() throws Exception {
        // Setup
        final MeetingDetailsDto meetingDetailsDto = new MeetingDetailsDto();
        meetingDetailsDto.setEid(0L);
        meetingDetailsDto.setMeetingId("meetingId");
        meetingDetailsDto.setTopic("topic");
        meetingDetailsDto.setMeetingDate("meetingDate");
        meetingDetailsDto.setHours("hours");
        final List<MeetingDetailsDto> expectedResult = List.of(meetingDetailsDto);

        // Configure MeetingDetailsRepository.getMeetingDetailsBetweenDates(...).
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
        meetingDetails.setTotalHours("hours");
        final List<MeetingDetails> meetingDetailsList = List.of(meetingDetails);
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getMeetingDetailsBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(meetingDetailsList);

        // Run the test
        final List<MeetingDetailsDto> result = meetingDetailsImplUnderTest.getMeetingDetailsFor2Week();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetMeetingDetailsFor2Week_MeetingDetailsRepositoryReturnsNoItems() {
        // Setup
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getMeetingDetailsBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(Collections.emptyList());

        // Run the test
        assertThatThrownBy(() -> meetingDetailsImplUnderTest.getMeetingDetailsFor2Week())
                .isInstanceOf(MeetingNotExist.class);
    }

    @Test
    void testGetMeetingDetailsFor2Week_ThrowsParseException() {
        // Setup
        // Configure MeetingDetailsRepository.getMeetingDetailsBetweenDates(...).
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
        meetingDetails.setTotalHours("hours");
        final List<MeetingDetails> meetingDetailsList = List.of(meetingDetails);
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getMeetingDetailsBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(meetingDetailsList);

        // Run the test
        assertThatThrownBy(() -> meetingDetailsImplUnderTest.getMeetingDetailsFor2Week())
                .isInstanceOf(ParseException.class);
    }

    @Test
    void testGetMeetingDetailsFor2Week_ThrowsMeetingNotExist() {
        // Setup
        // Configure MeetingDetailsRepository.getMeetingDetailsBetweenDates(...).
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
        meetingDetails.setTotalHours("hours");
        final List<MeetingDetails> meetingDetailsList = List.of(meetingDetails);
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getMeetingDetailsBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(meetingDetailsList);

        // Run the test
        assertThatThrownBy(() -> meetingDetailsImplUnderTest.getMeetingDetailsFor2Week())
                .isInstanceOf(MeetingNotExist.class);
    }

    @Test
    void testGetLeaderBoard() throws Exception {
        // Setup
        // Configure MeetingDetailsRepository.getMeetingDetailsBetweenDates(...).
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
        meetingDetails.setTotalHours("hours");
        final List<MeetingDetails> meetingDetailsList = List.of(meetingDetails);
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getMeetingDetailsBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(meetingDetailsList);

        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getMeetingAnchorCountBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(0);
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getTotalHoursBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(List.of("value"));
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.totalParticipantsBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(0);

        // Run the test
        final Map<String, Object> result = meetingDetailsImplUnderTest.getLeaderBoard();

        // Verify the results
    }

    @Test
    void testGetLeaderBoard_MeetingDetailsRepositoryGetMeetingDetailsBetweenDatesReturnsNoItems() throws Exception {
        // Setup
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getMeetingDetailsBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(Collections.emptyList());
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getMeetingAnchorCountBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(0);
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getTotalHoursBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(List.of("value"));
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.totalParticipantsBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(0);

        // Run the test
        final Map<String, Object> result = meetingDetailsImplUnderTest.getLeaderBoard();

        // Verify the results
    }

    @Test
    void testGetLeaderBoard_MeetingDetailsRepositoryGetTotalHoursBetweenDatesReturnsNoItems() throws Exception {
        // Setup
        // Configure MeetingDetailsRepository.getMeetingDetailsBetweenDates(...).
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
        meetingDetails.setTotalHours("hours");
        final List<MeetingDetails> meetingDetailsList = List.of(meetingDetails);
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getMeetingDetailsBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(meetingDetailsList);

        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getMeetingAnchorCountBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(0);
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getTotalHoursBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(Collections.emptyList());
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.totalParticipantsBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(0);

        // Run the test
        final Map<String, Object> result = meetingDetailsImplUnderTest.getLeaderBoard();

        // Verify the results
    }

    @Test
    void testGetLeaderBoard_ThrowsParseException() {
        // Setup
        // Configure MeetingDetailsRepository.getMeetingDetailsBetweenDates(...).
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
        meetingDetails.setTotalHours("hours");
        final List<MeetingDetails> meetingDetailsList = List.of(meetingDetails);
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getMeetingDetailsBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(meetingDetailsList);

        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getMeetingAnchorCountBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(0);
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.getTotalHoursBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(List.of("value"));
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.totalParticipantsBetweenDates(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(0);

        // Run the test
        assertThatThrownBy(() -> meetingDetailsImplUnderTest.getLeaderBoard()).isInstanceOf(ParseException.class);
    }

    @Test
    void testGetAllMeetings() {
        // Setup
        final MeetingDetailsDto meetingDetailsDto = new MeetingDetailsDto();
        meetingDetailsDto.setEid(0L);
        meetingDetailsDto.setMeetingId("meetingId");
        meetingDetailsDto.setTopic("topic");
        meetingDetailsDto.setMeetingDate("meetingDate");
        meetingDetailsDto.setHours("hours");
        final List<MeetingDetailsDto> expectedResult = List.of(meetingDetailsDto);

        // Configure MeetingDetailsRepository.findAll(...).
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
        meetingDetails.setTotalHours("hours");
        final List<MeetingDetails> meetingDetailsList = List.of(meetingDetails);
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.findAll()).thenReturn(meetingDetailsList);

        // Run the test
        final List<MeetingDetailsDto> result = meetingDetailsImplUnderTest.getAllMeetings();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllMeetings_MeetingDetailsRepositoryReturnsNoItems() {
        // Setup
        when(meetingDetailsImplUnderTest.meetingDetailsRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<MeetingDetailsDto> result = meetingDetailsImplUnderTest.getAllMeetings();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
