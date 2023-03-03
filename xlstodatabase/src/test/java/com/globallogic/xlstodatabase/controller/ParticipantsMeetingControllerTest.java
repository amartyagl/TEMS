package com.globallogic.xlstodatabase.controller;

import com.globallogic.xlstodatabase.dto.EmployeeDto;
import com.globallogic.xlstodatabase.dto.MeetingDto;
import com.globallogic.xlstodatabase.service.ParticipantsOfMeetingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParticipantsMeetingController.class)
class ParticipantsMeetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParticipantsOfMeetingService mockParticipantsOfMeetingService;

    @Test
    void testUpload() throws Exception {
        // Setup
        when(mockParticipantsOfMeetingService.saveExcel()).thenReturn("body");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/meeting/upload")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllParticipants() throws Exception {
        // Setup
        when(mockParticipantsOfMeetingService.getAllMeetingsParticipantsList()).thenReturn("body");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/participants")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetByMeetingId() throws Exception {
        // Setup
        // Configure ParticipantsOfMeetingService.getParticipantsByMeetingId(...).
        final MeetingDto meetingDto = new MeetingDto();
        meetingDto.setId(0L);
        meetingDto.setMeetingId("meetingId");
        meetingDto.setFirstName("firstName");
        meetingDto.setLastName("lastName");
        meetingDto.setEmail("email");
        meetingDto.setDuration("duration");
        meetingDto.setTimeJoined("timeJoined");
        meetingDto.setTimeExited("timeExited");
        meetingDto.setMeetingDate("meetingDate");
        meetingDto.setAssessmentScore("assessmentScore");
        final List<MeetingDto> meetingDtos = List.of(meetingDto);
        when(mockParticipantsOfMeetingService.getParticipantsByMeetingId("meetingId")).thenReturn(meetingDtos);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/getParticipantsByMeetingId")
                        .param("meetingId", "meetingId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetByMeetingId_ParticipantsOfMeetingServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockParticipantsOfMeetingService.getParticipantsByMeetingId("meetingId"))
                .thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/getParticipantsByMeetingId")
                        .param("meetingId", "meetingId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetScoreByMeetingId() throws Exception {
        // Setup
        when(mockParticipantsOfMeetingService.getParticipantsAssesmentScoreByMeetingId("meetingId")).thenReturn("body");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/getParticipantsScoreByMeetingId")
                        .param("meetingId", "meetingId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAbsentees() throws Exception {
        // Setup
        // Configure ParticipantsOfMeetingService.listOfAbsentees(...).
        final EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEid(0L);
        employeeDto.setFirstName("firstName");
        employeeDto.setLastName("lastName");
        employeeDto.setEmail("email");
        employeeDto.setProjectCode("projectCode");
        employeeDto.setLocation("location");
        final List<EmployeeDto> employeeDtos = List.of(employeeDto);
        when(mockParticipantsOfMeetingService.listOfAbsentees("meetingId")).thenReturn(employeeDtos);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/getAbsenteesByMeetingId")
                        .param("meetingId", "meetingId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAbsentees_ParticipantsOfMeetingServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockParticipantsOfMeetingService.listOfAbsentees("meetingId")).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/getAbsenteesByMeetingId")
                        .param("meetingId", "meetingId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetScoreByMeetingIdAndEid() throws Exception {
        // Setup
        when(mockParticipantsOfMeetingService.getAssesmentScoreByMeetingIdAndEid(0L, "meetingId")).thenReturn("body");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/getScoreByMeetingIdAndEid")
                        .param("meetingId", "meetingId")
                        .param("eid", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetParticipantsDetailsBetweenParticularDate() throws Exception {
        // Setup
        when(mockParticipantsOfMeetingService.getParticipantsDetailsBetweenParticularDate(0L,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn("body");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/getParticipantsDetailsBetweenParticularDate")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
