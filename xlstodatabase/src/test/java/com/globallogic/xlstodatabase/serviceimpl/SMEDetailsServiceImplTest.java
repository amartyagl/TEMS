package com.globallogic.xlstodatabase.serviceimpl;

import com.globallogic.xlstodatabase.dto.SMEDetailsDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import com.globallogic.xlstodatabase.modal.Employee;
import com.globallogic.xlstodatabase.modal.MeetingDetails;
import com.globallogic.xlstodatabase.modal.ParticipantOfMeeting;
import com.globallogic.xlstodatabase.modal.SMEDetails;
import com.globallogic.xlstodatabase.repository.EmployeeRepository;
import com.globallogic.xlstodatabase.repository.SMEDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class SMEDetailsServiceImplTest {

    private SMEDetailsServiceImpl smeDetailsServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        smeDetailsServiceImplUnderTest = new SMEDetailsServiceImpl();
        smeDetailsServiceImplUnderTest.smeDetailsRepository = mock(SMEDetailsRepository.class);
        smeDetailsServiceImplUnderTest.employeeRepository = mock(EmployeeRepository.class);
    }

    @Test
    void testAssignTopic() throws Exception {
        // Setup
        final SMEDetailsDto smeDetailsDto = new SMEDetailsDto();
        smeDetailsDto.setSmeId(0);
        smeDetailsDto.setEid(0L);
        smeDetailsDto.setTopic("topic");

        final SMEDetailsDto expectedResult = new SMEDetailsDto();
        expectedResult.setSmeId(0);
        expectedResult.setEid(0L);
        expectedResult.setTopic("topic");

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
        meetingDetails.setTotalHours("totalHours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        employee1.setMeetingDetails(List.of(meetingDetails));
        final Optional<Employee> employee = Optional.of(employee1);
        when(smeDetailsServiceImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

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
        meetingDetails1.setTotalHours("totalHours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        meetingDetails1.setParticipantOfMeetings(List.of(participant1));
        eid.setMeetingDetails(List.of(meetingDetails1));
        smeDetails.setEid(eid);
        smeDetails.setTopic("topic");
        when(smeDetailsServiceImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic"))
                .thenReturn(smeDetails);

        // Configure SMEDetailsRepository.save(...).
        final SMEDetails smeDetails1 = new SMEDetails();
        smeDetails1.setSmeId(0);
        final Employee eid1 = new Employee();
        eid1.setEid(0L);
        eid1.setFirstName("firstName");
        eid1.setLastName("lastName");
        eid1.setEmail("email");
        eid1.setProjectCode("projectCode");
        eid1.setLocation("location");
        final MeetingDetails meetingDetails2 = new MeetingDetails();
        meetingDetails2.setMeetingId("meetingId");
        meetingDetails2.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails2.setTopic("topic");
        meetingDetails2.setTotalHours("totalHours");
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        meetingDetails2.setParticipantOfMeetings(List.of(participant2));
        eid1.setMeetingDetails(List.of(meetingDetails2));
        smeDetails1.setEid(eid1);
        smeDetails1.setTopic("topic");
        when(smeDetailsServiceImplUnderTest.smeDetailsRepository.save(new SMEDetails())).thenReturn(smeDetails1);

        // Run the test
        final SMEDetailsDto result = smeDetailsServiceImplUnderTest.assignTopic(smeDetailsDto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(smeDetailsServiceImplUnderTest.smeDetailsRepository).save(new SMEDetails());
    }

    @Test
    void testAssignTopic_EmployeeRepositoryReturnsAbsent() {
        // Setup
        final SMEDetailsDto smeDetailsDto = new SMEDetailsDto();
        smeDetailsDto.setSmeId(0);
        smeDetailsDto.setEid(0L);
        smeDetailsDto.setTopic("topic");

        when(smeDetailsServiceImplUnderTest.employeeRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> smeDetailsServiceImplUnderTest.assignTopic(smeDetailsDto))
                .isInstanceOf(EmployeeNotFound.class);
    }

    @Test
    void testAssignTopic_SMEDetailsRepositoryFindyByEidAndTopicReturnsNull() throws Exception {
        // Setup
        final SMEDetailsDto smeDetailsDto = new SMEDetailsDto();
        smeDetailsDto.setSmeId(0);
        smeDetailsDto.setEid(0L);
        smeDetailsDto.setTopic("topic");

        final SMEDetailsDto expectedResult = new SMEDetailsDto();
        expectedResult.setSmeId(0);
        expectedResult.setEid(0L);
        expectedResult.setTopic("topic");

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
        meetingDetails.setTotalHours("totalHours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        employee1.setMeetingDetails(List.of(meetingDetails));
        final Optional<Employee> employee = Optional.of(employee1);
        when(smeDetailsServiceImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

        when(smeDetailsServiceImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic")).thenReturn(null);

        // Configure SMEDetailsRepository.save(...).
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
        meetingDetails1.setTotalHours("totalHours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        meetingDetails1.setParticipantOfMeetings(List.of(participant1));
        eid.setMeetingDetails(List.of(meetingDetails1));
        smeDetails.setEid(eid);
        smeDetails.setTopic("topic");
        when(smeDetailsServiceImplUnderTest.smeDetailsRepository.save(new SMEDetails())).thenReturn(smeDetails);

        // Run the test
        final SMEDetailsDto result = smeDetailsServiceImplUnderTest.assignTopic(smeDetailsDto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(smeDetailsServiceImplUnderTest.smeDetailsRepository).save(new SMEDetails());
    }

    @Test
    void testAssignTopic_ThrowsEmployeeNotFound() {
        // Setup
        final SMEDetailsDto smeDetailsDto = new SMEDetailsDto();
        smeDetailsDto.setSmeId(0);
        smeDetailsDto.setEid(0L);
        smeDetailsDto.setTopic("topic");

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
        meetingDetails.setTotalHours("totalHours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        employee1.setMeetingDetails(List.of(meetingDetails));
        final Optional<Employee> employee = Optional.of(employee1);
        when(smeDetailsServiceImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

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
        meetingDetails1.setTotalHours("totalHours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        meetingDetails1.setParticipantOfMeetings(List.of(participant1));
        eid.setMeetingDetails(List.of(meetingDetails1));
        smeDetails.setEid(eid);
        smeDetails.setTopic("topic");
        when(smeDetailsServiceImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic"))
                .thenReturn(smeDetails);

        // Configure SMEDetailsRepository.save(...).
        final SMEDetails smeDetails1 = new SMEDetails();
        smeDetails1.setSmeId(0);
        final Employee eid1 = new Employee();
        eid1.setEid(0L);
        eid1.setFirstName("firstName");
        eid1.setLastName("lastName");
        eid1.setEmail("email");
        eid1.setProjectCode("projectCode");
        eid1.setLocation("location");
        final MeetingDetails meetingDetails2 = new MeetingDetails();
        meetingDetails2.setMeetingId("meetingId");
        meetingDetails2.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails2.setTopic("topic");
        meetingDetails2.setTotalHours("totalHours");
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        meetingDetails2.setParticipantOfMeetings(List.of(participant2));
        eid1.setMeetingDetails(List.of(meetingDetails2));
        smeDetails1.setEid(eid1);
        smeDetails1.setTopic("topic");
        when(smeDetailsServiceImplUnderTest.smeDetailsRepository.save(new SMEDetails())).thenReturn(smeDetails1);

        // Run the test
        assertThatThrownBy(() -> smeDetailsServiceImplUnderTest.assignTopic(smeDetailsDto))
                .isInstanceOf(EmployeeNotFound.class);
        verify(smeDetailsServiceImplUnderTest.smeDetailsRepository).save(new SMEDetails());
    }

    @Test
    void testAssignTopic_ThrowsSMESubjectAvailiability() {
        // Setup
        final SMEDetailsDto smeDetailsDto = new SMEDetailsDto();
        smeDetailsDto.setSmeId(0);
        smeDetailsDto.setEid(0L);
        smeDetailsDto.setTopic("topic");

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
        meetingDetails.setTotalHours("totalHours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        participant.setTimeExisted("timeExisted");
        participant.setDuration("duration");
        participant.setAssesmentScore("assesmentScore");
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        employee1.setMeetingDetails(List.of(meetingDetails));
        final Optional<Employee> employee = Optional.of(employee1);
        when(smeDetailsServiceImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

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
        meetingDetails1.setTotalHours("totalHours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        meetingDetails1.setParticipantOfMeetings(List.of(participant1));
        eid.setMeetingDetails(List.of(meetingDetails1));
        smeDetails.setEid(eid);
        smeDetails.setTopic("topic");
        when(smeDetailsServiceImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic"))
                .thenReturn(smeDetails);

        // Configure SMEDetailsRepository.save(...).
        final SMEDetails smeDetails1 = new SMEDetails();
        smeDetails1.setSmeId(0);
        final Employee eid1 = new Employee();
        eid1.setEid(0L);
        eid1.setFirstName("firstName");
        eid1.setLastName("lastName");
        eid1.setEmail("email");
        eid1.setProjectCode("projectCode");
        eid1.setLocation("location");
        final MeetingDetails meetingDetails2 = new MeetingDetails();
        meetingDetails2.setMeetingId("meetingId");
        meetingDetails2.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails2.setTopic("topic");
        meetingDetails2.setTotalHours("totalHours");
        final ParticipantOfMeeting participant2 = new ParticipantOfMeeting();
        meetingDetails2.setParticipantOfMeetings(List.of(participant2));
        eid1.setMeetingDetails(List.of(meetingDetails2));
        smeDetails1.setEid(eid1);
        smeDetails1.setTopic("topic");
        when(smeDetailsServiceImplUnderTest.smeDetailsRepository.save(new SMEDetails())).thenReturn(smeDetails1);

        // Run the test
        assertThatThrownBy(() -> smeDetailsServiceImplUnderTest.assignTopic(smeDetailsDto))
                .isInstanceOf(SMESubjectAvailiability.class);
        verify(smeDetailsServiceImplUnderTest.smeDetailsRepository).save(new SMEDetails());
    }

    @Test
    void testGetSMEAllTopics() {
        // Setup
        final SMEDetailsDto smeDetailsDto = new SMEDetailsDto();
        smeDetailsDto.setSmeId(0);
        smeDetailsDto.setEid(0L);
        smeDetailsDto.setTopic("topic");
        final List<SMEDetailsDto> expectedResult = List.of(smeDetailsDto);

        // Configure SMEDetailsRepository.getAllTopics(...).
        final SMEDetails smeDetails = new SMEDetails();
        smeDetails.setSmeId(0);
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        eid.setProjectCode("projectCode");
        eid.setLocation("location");
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        meetingDetails.setTotalHours("totalHours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        eid.setMeetingDetails(List.of(meetingDetails));
        smeDetails.setEid(eid);
        smeDetails.setTopic("topic");
        final List<SMEDetails> smeDetailsList = List.of(smeDetails);
        when(smeDetailsServiceImplUnderTest.smeDetailsRepository.getAllTopics(0L)).thenReturn(smeDetailsList);

        // Run the test
        final List<SMEDetailsDto> result = smeDetailsServiceImplUnderTest.getSMEAllTopics(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetSMEAllTopics_SMEDetailsRepositoryReturnsNoItems() {
        // Setup
        when(smeDetailsServiceImplUnderTest.smeDetailsRepository.getAllTopics(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<SMEDetailsDto> result = smeDetailsServiceImplUnderTest.getSMEAllTopics(0L);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testDeleteTopicByEid() throws Exception {
        // Setup
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
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        meetingDetails.setTotalHours("totalHours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        eid.setMeetingDetails(List.of(meetingDetails));
        smeDetails.setEid(eid);
        smeDetails.setTopic("topic");
        when(smeDetailsServiceImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic"))
                .thenReturn(smeDetails);

        // Run the test
        smeDetailsServiceImplUnderTest.deleteTopicByEid(0L, "topic");

        // Verify the results
        verify(smeDetailsServiceImplUnderTest.smeDetailsRepository).deleteTopic(0L, "topic");
    }

    @Test
    void testDeleteTopicByEid_SMEDetailsRepositoryFindyByEidAndTopicReturnsNull() {
        // Setup
        when(smeDetailsServiceImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic")).thenReturn(null);

        // Run the test
        assertThatThrownBy(() -> smeDetailsServiceImplUnderTest.deleteTopicByEid(0L, "topic"))
                .isInstanceOf(SMESubjectAvailiability.class);
    }

    @Test
    void testDeleteTopicByEid_ThrowsSMESubjectAvailiability() {
        // Setup
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
        final MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setMeetingId("meetingId");
        meetingDetails.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails.setTopic("topic");
        meetingDetails.setTotalHours("totalHours");
        final ParticipantOfMeeting participant = new ParticipantOfMeeting();
        meetingDetails.setParticipantOfMeetings(List.of(participant));
        eid.setMeetingDetails(List.of(meetingDetails));
        smeDetails.setEid(eid);
        smeDetails.setTopic("topic");
        when(smeDetailsServiceImplUnderTest.smeDetailsRepository.findyByEidAndTopic(0L, "topic"))
                .thenReturn(smeDetails);

        // Run the test
        assertThatThrownBy(() -> smeDetailsServiceImplUnderTest.deleteTopicByEid(0L, "topic"))
                .isInstanceOf(SMESubjectAvailiability.class);
        verify(smeDetailsServiceImplUnderTest.smeDetailsRepository).deleteTopic(0L, "topic");
    }
}
