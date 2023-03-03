package com.globallogic.xlstodatabase.controller;

import com.globallogic.xlstodatabase.dto.EmployeeDto;
import com.globallogic.xlstodatabase.dto.EmployeeHoursDto;
import com.globallogic.xlstodatabase.exception.MeetingNotExist;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import com.globallogic.xlstodatabase.service.EmployeeService;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService mockEmployeeService;

    @Test
    void testAddEmployee() throws Exception {
        // Setup
        when(mockEmployeeService.addEmployee(any(EmployeeDto.class))).thenReturn("body");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/v1/addEmployee")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetSmeByMeetingId() throws Exception {
        // Setup
        when(mockEmployeeService.getSmeDetails("meetingId")).thenReturn("body");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getSmeByMeetingId")
                        .param("meetingId", "meetingId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetSmeByMeetingId_EmployeeServiceThrowsMeetingNotExist() throws Exception {
        // Setup
        when(mockEmployeeService.getSmeDetails("meetingId")).thenThrow(MeetingNotExist.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getSmeByMeetingId")
                        .param("meetingId", "meetingId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetEmployeeList() throws Exception {
        // Setup
        // Configure EmployeeService.getAllEmployee(...).
        final EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEid(0L);
        employeeDto.setFirstName("firstName");
        employeeDto.setLastName("lastName");
        employeeDto.setEmail("email");
        employeeDto.setProjectCode("projectCode");
        employeeDto.setLocation("location");
        final List<EmployeeDto> employeeDtos = List.of(employeeDto);
        when(mockEmployeeService.getAllEmployee()).thenReturn(employeeDtos);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getAllEmployee")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetEmployeeList_EmployeeServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockEmployeeService.getAllEmployee()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getAllEmployee")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetTotalHours() throws Exception {
        // Setup
        // Configure EmployeeService.getTotalHours(...).
        final EmployeeHoursDto employeeHoursDto = new EmployeeHoursDto();
        employeeHoursDto.setStartDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        employeeHoursDto.setEndDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        employeeHoursDto.setEid(0L);
        employeeHoursDto.setTotalHours(0);
        when(mockEmployeeService.getTotalHours(new EmployeeHoursDto())).thenReturn(employeeHoursDto);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getTotalHours")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetSmeByTopic() throws Exception {
        // Setup
        // Configure EmployeeService.getSmeDetailsByTopic(...).
        final EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEid(0L);
        employeeDto.setFirstName("firstName");
        employeeDto.setLastName("lastName");
        employeeDto.setEmail("email");
        employeeDto.setProjectCode("projectCode");
        employeeDto.setLocation("location");
        final Set<EmployeeDto> employeeDtos = Set.of(employeeDto);
        when(mockEmployeeService.getSmeDetailsByTopic("topic")).thenReturn(employeeDtos);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getSmeByTopic")
                        .param("topic", "topic")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetSmeByTopic_EmployeeServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockEmployeeService.getSmeDetailsByTopic("topic")).thenReturn(Collections.emptySet());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getSmeByTopic")
                        .param("topic", "topic")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetSmeByTopic_EmployeeServiceThrowsSMESubjectAvailiability() throws Exception {
        // Setup
        when(mockEmployeeService.getSmeDetailsByTopic("topic")).thenThrow(SMESubjectAvailiability.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/getSmeByTopic")
                        .param("topic", "topic")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
