package com.globallogic.xlstodatabase.controller;

import com.globallogic.xlstodatabase.dto.SMEDetailsDto;
import com.globallogic.xlstodatabase.exception.EmployeeNotFound;
import com.globallogic.xlstodatabase.exception.SMESubjectAvailiability;
import com.globallogic.xlstodatabase.service.SMEDetailsService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SMEDetailsController.class)
class SMEDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SMEDetailsService mockSmeDetailsService;

    @Test
    void testAddSubject() throws Exception {
        // Setup
        // Configure SMEDetailsService.assignTopic(...).
        final SMEDetailsDto smeDetailsDto = new SMEDetailsDto();
        smeDetailsDto.setSmeId(0);
        smeDetailsDto.setEid(0L);
        smeDetailsDto.setTopic("topic");
        when(mockSmeDetailsService.assignTopic(new SMEDetailsDto())).thenReturn(smeDetailsDto);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/assignSubject")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockSmeDetailsService).assignTopic(new SMEDetailsDto());
    }

    @Test
    void testAddSubject_SMEDetailsServiceThrowsEmployeeNotFound() throws Exception {
        // Setup
        when(mockSmeDetailsService.assignTopic(new SMEDetailsDto())).thenThrow(EmployeeNotFound.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/assignSubject")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testAddSubject_SMEDetailsServiceThrowsSMESubjectAvailiability() throws Exception {
        // Setup
        when(mockSmeDetailsService.assignTopic(new SMEDetailsDto())).thenThrow(SMESubjectAvailiability.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/assignSubject")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllSubject() throws Exception {
        // Setup
        // Configure SMEDetailsService.getSMEAllTopics(...).
        final SMEDetailsDto smeDetailsDto = new SMEDetailsDto();
        smeDetailsDto.setSmeId(0);
        smeDetailsDto.setEid(0L);
        smeDetailsDto.setTopic("topic");
        final List<SMEDetailsDto> smeDetailsDtoList = List.of(smeDetailsDto);
        when(mockSmeDetailsService.getSMEAllTopics(0L)).thenReturn(smeDetailsDtoList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/getAllSubjectOfSme")
                        .param("eid", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllSubject_SMEDetailsServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockSmeDetailsService.getSMEAllTopics(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/getAllSubjectOfSme")
                        .param("eid", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testDeleteSubject() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/deleteSubject")
                        .param("eid", "0")
                        .param("topic", "topic")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockSmeDetailsService).deleteTopicByEid(0L, "topic");
    }

    @Test
    void testDeleteSubject_SMEDetailsServiceThrowsSMESubjectAvailiability() throws Exception {
        // Setup
        doThrow(SMESubjectAvailiability.class).when(mockSmeDetailsService).deleteTopicByEid(0L, "topic");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/deleteSubject")
                        .param("eid", "0")
                        .param("topic", "topic")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
