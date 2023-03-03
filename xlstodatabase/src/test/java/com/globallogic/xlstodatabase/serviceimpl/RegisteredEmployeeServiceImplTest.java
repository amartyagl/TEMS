package com.globallogic.xlstodatabase.serviceimpl;

import com.globallogic.xlstodatabase.dto.MeetingDetailsDto;
import com.globallogic.xlstodatabase.dto.RegisterEmployeeDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.modal.Employee;
import com.globallogic.xlstodatabase.modal.MeetingDetails;
import com.globallogic.xlstodatabase.modal.ParticipantOfMeeting;
import com.globallogic.xlstodatabase.modal.RegisteredEmployee;
import com.globallogic.xlstodatabase.repository.EmployeeRepository;
import com.globallogic.xlstodatabase.repository.MeetingDetailsRepository;
import com.globallogic.xlstodatabase.repository.RegisteredEmployeeRepo;
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

class RegisteredEmployeeServiceImplTest {

    private RegisteredEmployeeServiceImpl registeredEmployeeServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        registeredEmployeeServiceImplUnderTest = new RegisteredEmployeeServiceImpl();
        registeredEmployeeServiceImplUnderTest.meetingDetailsRepository = mock(MeetingDetailsRepository.class);
        registeredEmployeeServiceImplUnderTest.employeeRepository = mock(EmployeeRepository.class);
        registeredEmployeeServiceImplUnderTest.registeredEmployeeRepo = mock(RegisteredEmployeeRepo.class);
    }

    @Test
    void testRegister() throws Exception {
        // Setup
        final RegisterEmployeeDto registerEmployeeDto = new RegisterEmployeeDto();
        registerEmployeeDto.setEid(0L);
        registerEmployeeDto.setMid("mid");

        final RegisterEmployeeDto expectedResult = new RegisterEmployeeDto();
        expectedResult.setEid(0L);
        expectedResult.setMid("mid");

        // Configure MeetingDetailsRepository.findById(...).
        final MeetingDetails meetingDetails1 = new MeetingDetails();
        meetingDetails1.setMeetingId("meetingId");
        meetingDetails1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails1.setTopic("topic");
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
        meetingDetails1.setMeetingAnchor(meetingAnchor);
        meetingDetails1.setTotalHours("hours");
        final Optional<MeetingDetails> meetingDetails = Optional.of(meetingDetails1);
        when(registeredEmployeeServiceImplUnderTest.meetingDetailsRepository.findById("mid"))
                .thenReturn(meetingDetails);

        // Configure EmployeeRepository.findById(...).
        final Employee employee1 = new Employee();
        employee1.setEid(0L);
        employee1.setFirstName("firstName");
        employee1.setLastName("lastName");
        employee1.setEmail("email");
        employee1.setProjectCode("projectCode");
        employee1.setLocation("location");
        final MeetingDetails meetingDetails2 = new MeetingDetails();
        meetingDetails2.setMeetingId("meetingId");
        meetingDetails2.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails2.setTopic("topic");
        meetingDetails2.setTotalHours("hours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        participant1.setAssesmentScore("assesmentScore");
        meetingDetails2.setParticipantOfMeetings(List.of(participant1));
        employee1.setMeetingDetails(List.of(meetingDetails2));
        final Optional<Employee> employee = Optional.of(employee1);
        when(registeredEmployeeServiceImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

        // Configure RegisteredEmployeeRepo.save(...).
        final RegisteredEmployee registeredEmployee = new RegisteredEmployee();
        registeredEmployee.setRegistrationId(0);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        meetingAnchor1.setProjectCode("projectCode");
        meetingAnchor1.setLocation("location");
        mid.setMeetingAnchor(meetingAnchor1);
        mid.setTotalHours("hours");
        registeredEmployee.setMid(mid);
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        eid.setProjectCode("projectCode");
        eid.setLocation("location");
        registeredEmployee.setEid(eid);
        when(registeredEmployeeServiceImplUnderTest.registeredEmployeeRepo.save(new RegisteredEmployee()))
                .thenReturn(registeredEmployee);

        // Run the test
        final RegisterEmployeeDto result = registeredEmployeeServiceImplUnderTest.register(registerEmployeeDto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(registeredEmployeeServiceImplUnderTest.registeredEmployeeRepo).save(new RegisteredEmployee());
    }

    @Test
    void testRegister_MeetingDetailsRepositoryReturnsAbsent() {
        // Setup
        final RegisterEmployeeDto registerEmployeeDto = new RegisterEmployeeDto();
        registerEmployeeDto.setEid(0L);
        registerEmployeeDto.setMid("mid");

        when(registeredEmployeeServiceImplUnderTest.meetingDetailsRepository.findById("mid"))
                .thenReturn(Optional.empty());

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
        when(registeredEmployeeServiceImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

        // Run the test
        assertThatThrownBy(() -> registeredEmployeeServiceImplUnderTest.register(registerEmployeeDto))
                .isInstanceOf(MeetingNotExist.class);
    }

    @Test
    void testRegister_EmployeeRepositoryReturnsAbsent() throws Exception {
        // Setup
        final RegisterEmployeeDto registerEmployeeDto = new RegisterEmployeeDto();
        registerEmployeeDto.setEid(0L);
        registerEmployeeDto.setMid("mid");

        final RegisterEmployeeDto expectedResult = new RegisterEmployeeDto();
        expectedResult.setEid(0L);
        expectedResult.setMid("mid");

        // Configure MeetingDetailsRepository.findById(...).
        final MeetingDetails meetingDetails1 = new MeetingDetails();
        meetingDetails1.setMeetingId("meetingId");
        meetingDetails1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails1.setTopic("topic");
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
        meetingDetails1.setMeetingAnchor(meetingAnchor);
        meetingDetails1.setTotalHours("hours");
        final Optional<MeetingDetails> meetingDetails = Optional.of(meetingDetails1);
        when(registeredEmployeeServiceImplUnderTest.meetingDetailsRepository.findById("mid"))
                .thenReturn(meetingDetails);

        when(registeredEmployeeServiceImplUnderTest.employeeRepository.findById(0L)).thenReturn(Optional.empty());

        // Configure RegisteredEmployeeRepo.save(...).
        final RegisteredEmployee registeredEmployee = new RegisteredEmployee();
        registeredEmployee.setRegistrationId(0);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        meetingAnchor1.setProjectCode("projectCode");
        meetingAnchor1.setLocation("location");
        mid.setMeetingAnchor(meetingAnchor1);
        mid.setTotalHours("hours");
        registeredEmployee.setMid(mid);
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        eid.setProjectCode("projectCode");
        eid.setLocation("location");
        registeredEmployee.setEid(eid);
        when(registeredEmployeeServiceImplUnderTest.registeredEmployeeRepo.save(new RegisteredEmployee()))
                .thenReturn(registeredEmployee);

        // Run the test
        final RegisterEmployeeDto result = registeredEmployeeServiceImplUnderTest.register(registerEmployeeDto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(registeredEmployeeServiceImplUnderTest.registeredEmployeeRepo).save(new RegisteredEmployee());
    }

    @Test
    void testRegister_ThrowsMeetingNotExist() {
        // Setup
        final RegisterEmployeeDto registerEmployeeDto = new RegisterEmployeeDto();
        registerEmployeeDto.setEid(0L);
        registerEmployeeDto.setMid("mid");

        // Configure MeetingDetailsRepository.findById(...).
        final MeetingDetails meetingDetails1 = new MeetingDetails();
        meetingDetails1.setMeetingId("meetingId");
        meetingDetails1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails1.setTopic("topic");
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
        meetingDetails1.setMeetingAnchor(meetingAnchor);
        meetingDetails1.setTotalHours("hours");
        final Optional<MeetingDetails> meetingDetails = Optional.of(meetingDetails1);
        when(registeredEmployeeServiceImplUnderTest.meetingDetailsRepository.findById("mid"))
                .thenReturn(meetingDetails);

        // Configure EmployeeRepository.findById(...).
        final Employee employee1 = new Employee();
        employee1.setEid(0L);
        employee1.setFirstName("firstName");
        employee1.setLastName("lastName");
        employee1.setEmail("email");
        employee1.setProjectCode("projectCode");
        employee1.setLocation("location");
        final MeetingDetails meetingDetails2 = new MeetingDetails();
        meetingDetails2.setMeetingId("meetingId");
        meetingDetails2.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails2.setTopic("topic");
        meetingDetails2.setTotalHours("hours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        participant1.setAssesmentScore("assesmentScore");
        meetingDetails2.setParticipantOfMeetings(List.of(participant1));
        employee1.setMeetingDetails(List.of(meetingDetails2));
        final Optional<Employee> employee = Optional.of(employee1);
        when(registeredEmployeeServiceImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

        // Configure RegisteredEmployeeRepo.save(...).
        final RegisteredEmployee registeredEmployee = new RegisteredEmployee();
        registeredEmployee.setRegistrationId(0);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        meetingAnchor1.setProjectCode("projectCode");
        meetingAnchor1.setLocation("location");
        mid.setMeetingAnchor(meetingAnchor1);
        mid.setTotalHours("hours");
        registeredEmployee.setMid(mid);
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        eid.setProjectCode("projectCode");
        eid.setLocation("location");
        registeredEmployee.setEid(eid);
        when(registeredEmployeeServiceImplUnderTest.registeredEmployeeRepo.save(new RegisteredEmployee()))
                .thenReturn(registeredEmployee);

        // Run the test
        assertThatThrownBy(() -> registeredEmployeeServiceImplUnderTest.register(registerEmployeeDto))
                .isInstanceOf(MeetingNotExist.class);
        verify(registeredEmployeeServiceImplUnderTest.registeredEmployeeRepo).save(new RegisteredEmployee());
    }

    @Test
    void testRegister_ThrowsEmployeeNotFound() {
        // Setup
        final RegisterEmployeeDto registerEmployeeDto = new RegisterEmployeeDto();
        registerEmployeeDto.setEid(0L);
        registerEmployeeDto.setMid("mid");

        // Configure MeetingDetailsRepository.findById(...).
        final MeetingDetails meetingDetails1 = new MeetingDetails();
        meetingDetails1.setMeetingId("meetingId");
        meetingDetails1.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails1.setTopic("topic");
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
        meetingDetails1.setMeetingAnchor(meetingAnchor);
        meetingDetails1.setTotalHours("hours");
        final Optional<MeetingDetails> meetingDetails = Optional.of(meetingDetails1);
        when(registeredEmployeeServiceImplUnderTest.meetingDetailsRepository.findById("mid"))
                .thenReturn(meetingDetails);

        // Configure EmployeeRepository.findById(...).
        final Employee employee1 = new Employee();
        employee1.setEid(0L);
        employee1.setFirstName("firstName");
        employee1.setLastName("lastName");
        employee1.setEmail("email");
        employee1.setProjectCode("projectCode");
        employee1.setLocation("location");
        final MeetingDetails meetingDetails2 = new MeetingDetails();
        meetingDetails2.setMeetingId("meetingId");
        meetingDetails2.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        meetingDetails2.setTopic("topic");
        meetingDetails2.setTotalHours("hours");
        final ParticipantOfMeeting participant1 = new ParticipantOfMeeting();
        participant1.setTimeExisted("timeExisted");
        participant1.setDuration("duration");
        participant1.setAssesmentScore("assesmentScore");
        meetingDetails2.setParticipantOfMeetings(List.of(participant1));
        employee1.setMeetingDetails(List.of(meetingDetails2));
        final Optional<Employee> employee = Optional.of(employee1);
        when(registeredEmployeeServiceImplUnderTest.employeeRepository.findById(0L)).thenReturn(employee);

        // Configure RegisteredEmployeeRepo.save(...).
        final RegisteredEmployee registeredEmployee = new RegisteredEmployee();
        registeredEmployee.setRegistrationId(0);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor1 = new Employee();
        meetingAnchor1.setEid(0L);
        meetingAnchor1.setFirstName("firstName");
        meetingAnchor1.setLastName("lastName");
        meetingAnchor1.setEmail("email");
        meetingAnchor1.setProjectCode("projectCode");
        meetingAnchor1.setLocation("location");
        mid.setMeetingAnchor(meetingAnchor1);
        mid.setTotalHours("hours");
        registeredEmployee.setMid(mid);
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        eid.setProjectCode("projectCode");
        eid.setLocation("location");
        registeredEmployee.setEid(eid);
        when(registeredEmployeeServiceImplUnderTest.registeredEmployeeRepo.save(new RegisteredEmployee()))
                .thenReturn(registeredEmployee);

        // Run the test
        assertThatThrownBy(() -> registeredEmployeeServiceImplUnderTest.register(registerEmployeeDto))
                .isInstanceOf(EmployeeNotFound.class);
        verify(registeredEmployeeServiceImplUnderTest.registeredEmployeeRepo).save(new RegisteredEmployee());
    }

    @Test
    void testGetRegisteredMeeting() {
        // Setup
        final MeetingDetailsDto meetingDetailsDto = new MeetingDetailsDto();
        meetingDetailsDto.setEid(0L);
        meetingDetailsDto.setMeetingId("meetingId");
        meetingDetailsDto.setTopic("topic");
        meetingDetailsDto.setMeetingDate("meetingDate");
        meetingDetailsDto.setHours("hours");
        final List<MeetingDetailsDto> expectedResult = List.of(meetingDetailsDto);

        // Configure RegisteredEmployeeRepo.findByEid(...).
        final RegisteredEmployee registeredEmployee = new RegisteredEmployee();
        registeredEmployee.setRegistrationId(0);
        final MeetingDetails mid = new MeetingDetails();
        mid.setMeetingId("meetingId");
        mid.setMeetingDate(Date.valueOf(LocalDate.of(2020, 1, 1)));
        mid.setTopic("topic");
        final Employee meetingAnchor = new Employee();
        meetingAnchor.setEid(0L);
        meetingAnchor.setFirstName("firstName");
        meetingAnchor.setLastName("lastName");
        meetingAnchor.setEmail("email");
        meetingAnchor.setProjectCode("projectCode");
        meetingAnchor.setLocation("location");
        mid.setMeetingAnchor(meetingAnchor);
        mid.setTotalHours("hours");
        registeredEmployee.setMid(mid);
        final Employee eid = new Employee();
        eid.setEid(0L);
        eid.setFirstName("firstName");
        eid.setLastName("lastName");
        eid.setEmail("email");
        eid.setProjectCode("projectCode");
        eid.setLocation("location");
        registeredEmployee.setEid(eid);
        final List<RegisteredEmployee> registeredEmployees = List.of(registeredEmployee);
        when(registeredEmployeeServiceImplUnderTest.registeredEmployeeRepo.findByEid(0L))
                .thenReturn(registeredEmployees);

        // Run the test
        final List<MeetingDetailsDto> result = registeredEmployeeServiceImplUnderTest.getRegisteredMeeting(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetRegisteredMeeting_RegisteredEmployeeRepoReturnsNoItems() {
        // Setup
        when(registeredEmployeeServiceImplUnderTest.registeredEmployeeRepo.findByEid(0L))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<MeetingDetailsDto> result = registeredEmployeeServiceImplUnderTest.getRegisteredMeeting(0L);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
