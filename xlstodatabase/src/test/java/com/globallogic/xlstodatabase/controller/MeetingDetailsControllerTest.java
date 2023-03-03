package com.globallogic.xlstodatabase.controller;

import com.globallogic.xlstodatabase.dto.MeetingDetailsDto;
import com.globallogic.xlstodatabase.dto.SmeTopicDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import com.globallogic.xlstodatabase.service.MeetingDetailsService;
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

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MeetingDetailsController.class)
class MeetingDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeetingDetailsService mockMeetingDetailsService;

    @Test
    void testAddMeeting() throws Exception {
        // Setup
        when(mockMeetingDetailsService.createMeeting(new MeetingDetailsDto())).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/v1/addFutureMeeting")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockMeetingDetailsService).createMeeting(new MeetingDetailsDto());
    }

    @Test
    void testAddMeeting_MeetingDetailsServiceThrowsException() throws Exception {
        // Setup
        when(mockMeetingDetailsService.createMeeting(new MeetingDetailsDto())).thenThrow(Exception.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/v1/addFutureMeeting")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdateSmeAnsdTopic() throws Exception {
        // Setup
        when(mockMeetingDetailsService.updateSMEAndTopic(new SmeTopicDto())).thenReturn("body");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/v1/updateSmeAndTopic")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdateSmeAnsdTopic_MeetingDetailsServiceThrowsEmployeeNotFound() throws Exception {
        // Setup
        when(mockMeetingDetailsService.updateSMEAndTopic(new SmeTopicDto())).thenThrow(EmployeeNotFound.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/v1/updateSmeAndTopic")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdateSmeAnsdTopic_MeetingDetailsServiceThrowsSMESubjectAvailiability() throws Exception {
        // Setup
        when(mockMeetingDetailsService.updateSMEAndTopic(new SmeTopicDto())).thenThrow(SMESubjectAvailiability.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/v1/updateSmeAndTopic")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetMeetingDetailsSpecificSme() throws Exception {
        // Setup
        when(mockMeetingDetailsService.getMeetingDetailsSpecificSME(0L)).thenReturn("body");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getMeetingDetailsBySmeId")
                        .param("smeId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetMeetingDetailsofNext2Week() throws Exception {
        // Setup
        // Configure MeetingDetailsService.getMeetingDetailsFor2Week(...).
        final MeetingDetailsDto meetingDetailsDto = new MeetingDetailsDto();
        meetingDetailsDto.setEid(0L);
        meetingDetailsDto.setMeetingId("meetingId");
        meetingDetailsDto.setTopic("topic");
        meetingDetailsDto.setMeetingDate("meetingDate");
        meetingDetailsDto.setHours("hours");
        final List<MeetingDetailsDto> meetingDetailsDtoList = List.of(meetingDetailsDto);
        when(mockMeetingDetailsService.getMeetingDetailsFor2Week()).thenReturn(meetingDetailsDtoList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getMeetingDetailsOfNext2Week")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetMeetingDetailsofNext2Week_MeetingDetailsServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockMeetingDetailsService.getMeetingDetailsFor2Week()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getMeetingDetailsOfNext2Week")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetMeetingDetailsofNext2Week_MeetingDetailsServiceThrowsParseException() throws Exception {
        // Setup
        when(mockMeetingDetailsService.getMeetingDetailsFor2Week()).thenThrow(ParseException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getMeetingDetailsOfNext2Week")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetMeetingDetailsofNext2Week_MeetingDetailsServiceThrowsMeetingNotExist() throws Exception {
        // Setup
        when(mockMeetingDetailsService.getMeetingDetailsFor2Week()).thenThrow(MeetingNotExist.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getMeetingDetailsOfNext2Week")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetLeaderboard() throws Exception {
        // Setup
        // Configure MeetingDetailsService.getLeaderBoard(...).
        final Map<String, Object> stringObjectMap = Map.ofEntries(Map.entry("value", "value"));
        when(mockMeetingDetailsService.getLeaderBoard()).thenReturn(stringObjectMap);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getLeaderboard")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetLeaderboard_MeetingDetailsServiceThrowsParseException() throws Exception {
        // Setup
        when(mockMeetingDetailsService.getLeaderBoard()).thenThrow(ParseException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getLeaderboard")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllMeetings() throws Exception {
        // Setup
        // Configure MeetingDetailsService.getAllMeetings(...).
        final MeetingDetailsDto meetingDetailsDto = new MeetingDetailsDto();
        meetingDetailsDto.setEid(0L);
        meetingDetailsDto.setMeetingId("meetingId");
        meetingDetailsDto.setTopic("topic");
        meetingDetailsDto.setMeetingDate("meetingDate");
        meetingDetailsDto.setHours("hours");
        final List<MeetingDetailsDto> meetingDetailsDtoList = List.of(meetingDetailsDto);
        when(mockMeetingDetailsService.getAllMeetings()).thenReturn(meetingDetailsDtoList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getAllMeetings")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllMeetings_MeetingDetailsServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockMeetingDetailsService.getAllMeetings()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getAllMeetings")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
