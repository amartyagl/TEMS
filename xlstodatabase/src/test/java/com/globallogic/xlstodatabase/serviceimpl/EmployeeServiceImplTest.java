package com.globallogic.xlstodatabase.serviceimpl;

import com.globallogic.xlstodatabase.dto.EmployeeDto;
import com.globallogic.xlstodatabase.dto.EmployeeHoursDto;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import com.globallogic.xlstodatabase.modal.Employee;
import com.globallogic.xlstodatabase.modal.MeetingDetails;
import com.globallogic.xlstodatabase.modal.ParticipantOfMeeting;
import com.globallogic.xlstodatabase.repository.EmployeeRepository;
import com.globallogic.xlstodatabase.repository.MeetingDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    private EmployeeServiceImpl employeeServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        employeeServiceImplUnderTest = new EmployeeServiceImpl();
        employeeServiceImplUnderTest.employeeRepository = mock(EmployeeRepository.class);
        employeeServiceImplUnderTest.meetingDetailsRepository = mock(MeetingDetailsRepository.class);
    }

    @Test
    void testAddEmployee() {
        // Setup
        final EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEid(0L);
        employeeDto.setFirstName("firstName");
        employeeDto.setLastName("lastName");
        employeeDto.setEmail("email");
        employeeDto.setProjectCode("projectCode");
        employeeDto.setLocation("location");

        // Configure EmployeeRepository.save(...).
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
        when(employeeServiceImplUnderTest.employeeRepository.save(new Employee())).thenReturn(employee);

        // Run the test
        final Object result = employeeServiceImplUnderTest.addEmployee(employeeDto);

        // Verify the results
        verify(employeeServiceImplUnderTest.employeeRepository).save(new Employee());
    }

    @Test
    void testGetSmeDetails() throws Exception {
        // Setup
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
        participant.setAssesmentScore("assesmentScore");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant));
        meetingDetails.setMeetingAnchor(meetingAnchor);
        when(employeeServiceImplUnderTest.meetingDetailsRepository.findByMeetingId("meetingId"))
                .thenReturn(meetingDetails);

        // Run the test
        final Object result = employeeServiceImplUnderTest.getSmeDetails("meetingId");

        // Verify the results
    }

    @Test
    void testGetSmeDetails_MeetingDetailsRepositoryReturnsNull() {
        // Setup
        when(employeeServiceImplUnderTest.meetingDetailsRepository.findByMeetingId("meetingId")).thenReturn(null);

        // Run the test
        assertThatThrownBy(() -> employeeServiceImplUnderTest.getSmeDetails("meetingId"))
                .isInstanceOf(MeetingNotExist.class);
    }

    @Test
    void testGetSmeDetails_ThrowsMeetingNotExist() {
        // Setup
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
        participant.setAssesmentScore("assesmentScore");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant));
        meetingDetails.setMeetingAnchor(meetingAnchor);
        when(employeeServiceImplUnderTest.meetingDetailsRepository.findByMeetingId("meetingId"))
                .thenReturn(meetingDetails);

        // Run the test
        assertThatThrownBy(() -> employeeServiceImplUnderTest.getSmeDetails("meetingId"))
                .isInstanceOf(MeetingNotExist.class);
    }

    @Test
    void testGetAllEmployee() {
        // Setup
        // Configure EmployeeRepository.findAll(...).
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
        final List<Employee> employeeList = List.of(employee);
        when(employeeServiceImplUnderTest.employeeRepository.findAll()).thenReturn(employeeList);

        // Run the test
        final List<EmployeeDto> result = employeeServiceImplUnderTest.getAllEmployee();

        // Verify the results
    }

    @Test
    void testGetAllEmployee_EmployeeRepositoryReturnsNoItems() {
        // Setup
        when(employeeServiceImplUnderTest.employeeRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<EmployeeDto> result = employeeServiceImplUnderTest.getAllEmployee();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetTotalHours() {
        // Setup
        final EmployeeHoursDto employeeHoursDto = new EmployeeHoursDto();
        employeeHoursDto.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        employeeHoursDto.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        employeeHoursDto.setEid(0L);
        employeeHoursDto.setTotalHours(0);

        final EmployeeHoursDto expectedResult = new EmployeeHoursDto();
        expectedResult.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setEid(0L);
        expectedResult.setTotalHours(0);

        when(employeeServiceImplUnderTest.meetingDetailsRepository.getTotalHours(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), 0L)).thenReturn(0);

        // Run the test
        final EmployeeHoursDto result = employeeServiceImplUnderTest.getTotalHours(employeeHoursDto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetSmeDetailsByTopic() throws Exception {
        // Setup
        // Configure MeetingDetailsRepository.findByTopic(...).
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
        participant.setAssesmentScore("assesmentScore");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant));
        meetingDetails.setMeetingAnchor(meetingAnchor);
        final List<MeetingDetails> meetingDetailsList = List.of(meetingDetails);
        when(employeeServiceImplUnderTest.meetingDetailsRepository.findByTopic("topic")).thenReturn(meetingDetailsList);

        // Run the test
        final Set<EmployeeDto> result = employeeServiceImplUnderTest.getSmeDetailsByTopic("topic");

        // Verify the results
    }

    @Test
    void testGetSmeDetailsByTopic_MeetingDetailsRepositoryReturnsNoItems() {
        // Setup
        when(employeeServiceImplUnderTest.meetingDetailsRepository.findByTopic("topic"))
                .thenReturn(Collections.emptyList());

        // Run the test
        assertThatThrownBy(() -> employeeServiceImplUnderTest.getSmeDetailsByTopic("topic"))
                .isInstanceOf(SMESubjectAvailiability.class);
    }

    @Test
    void testGetSmeDetailsByTopic_ThrowsSMESubjectAvailiability() {
        // Setup
        // Configure MeetingDetailsRepository.findByTopic(...).
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
        participant.setAssesmentScore("assesmentScore");
        meetingAnchor.setParticipantsOfMeeting(List.of(participant));
        meetingDetails.setMeetingAnchor(meetingAnchor);
        final List<MeetingDetails> meetingDetailsList = List.of(meetingDetails);
        when(employeeServiceImplUnderTest.meetingDetailsRepository.findByTopic("topic")).thenReturn(meetingDetailsList);

        // Run the test
        assertThatThrownBy(() -> employeeServiceImplUnderTest.getSmeDetailsByTopic("topic"))
                .isInstanceOf(SMESubjectAvailiability.class);
    }
}
