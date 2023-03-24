package com.example.springboottaskmanager.UnitTests;

import com.example.springboottaskmanager.TestConfiguration.TestConfig;
import com.example.springboottaskmanager.TestData.TestTaskData;
import com.example.springboottaskmanager.controller.TaskController;
import com.example.springboottaskmanager.mapper.TaskMapper;
import com.example.springboottaskmanager.security.authConfig.JwtService;
import com.example.springboottaskmanager.service.TaskServiceFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TaskController.class, properties = "spring.liquibase.enabled=false")
@AutoConfigureDataJpa
@Import(TestConfig.class)
public class TaskControllerPostTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    TaskServiceFacade taskServiceFacade;
    @MockBean
    JwtService jwtService;

    @Test
    public void PostTaskValidBodyCheck_success() throws Exception {
        for (int goodTaskNumber = 0; goodTaskNumber < TestTaskData.goodTasks.size(); goodTaskNumber++) {
            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/tasks/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(TaskMapper.INSTANCE.toViewModel(TestTaskData.goodTasks.get(goodTaskNumber))));

            mockMvc.perform(mockRequest)
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void PostTaskValidBodyCheck_fail() throws Exception {
        for (int badTaskNumber = 0; badTaskNumber < TestTaskData.badTasks.size(); badTaskNumber++) {
            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/tasks/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(TaskMapper.INSTANCE.toViewModel(TestTaskData.badTasks.get(badTaskNumber))));

            mockMvc.perform(mockRequest)
                    .andExpect(status().isBadRequest());
        }
    }
}