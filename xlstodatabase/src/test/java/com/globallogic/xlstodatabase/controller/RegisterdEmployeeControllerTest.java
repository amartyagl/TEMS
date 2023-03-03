package com.globallogic.xlstodatabase.controller;

import com.globallogic.xlstodatabase.dto.MeetingDetailsDto;
import com.globallogic.xlstodatabase.dto.RegisterEmployeeDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.service.RegisteredEmployeeService;
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

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RegisterdEmployeeController.class)
class RegisterdEmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisteredEmployeeService mockEmployeeService;

    @Test
    void testRegisterEmployee() throws Exception {
        // Setup
        // Configure RegisteredEmployeeService.register(...).
        final RegisterEmployeeDto registerEmployeeDto = new RegisterEmployeeDto();
        registerEmployeeDto.setEid(0L);
        registerEmployeeDto.setMid("mid");
        when(mockEmployeeService.register(new RegisterEmployeeDto())).thenReturn(registerEmployeeDto);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/register")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockEmployeeService).register(new RegisterEmployeeDto());
    }

    @Test
    void testRegisterEmployee_RegisteredEmployeeServiceThrowsMeetingNotExist() throws Exception {
        // Setup
        when(mockEmployeeService.register(new RegisterEmployeeDto())).thenThrow(MeetingNotExist.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/register")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testRegisterEmployee_RegisteredEmployeeServiceThrowsEmployeeNotFound() throws Exception {
        // Setup
        when(mockEmployeeService.register(new RegisterEmployeeDto())).thenThrow(EmployeeNotFound.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/register")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetEmployeeList() throws Exception {
        // Setup
        // Configure RegisteredEmployeeService.getRegisteredMeeting(...).
        final MeetingDetailsDto meetingDetailsDto = new MeetingDetailsDto();
        meetingDetailsDto.setEid(0L);
        meetingDetailsDto.setMeetingId("meetingId");
        meetingDetailsDto.setTopic("topic");
        meetingDetailsDto.setMeetingDate("meetingDate");
        meetingDetailsDto.setHours("hours");
        final List<MeetingDetailsDto> meetingDetailsDtoList = List.of(meetingDetailsDto);
        when(mockEmployeeService.getRegisteredMeeting(0L)).thenReturn(meetingDetailsDtoList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/getRegisteredMeetingByEid")
                        .param("eid", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetEmployeeList_RegisteredEmployeeServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockEmployeeService.getRegisteredMeeting(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/getRegisteredMeetingByEid")
                        .param("eid", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
