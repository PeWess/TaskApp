package com.example.springboottaskmanager.UnitTests;

import com.example.springboottaskmanager.TestConfiguration.TestConfig;
import com.example.springboottaskmanager.controller.TaskController;
import com.example.springboottaskmanager.exception.body.UnknownException;
import com.example.springboottaskmanager.security.authconfig.JwtService;
import com.example.springboottaskmanager.service.taskservices.TaskServiceFacade;
import org.junit.jupiter.api.Assertions;
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
class TaskControllerDeleteTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    TaskServiceFacade taskServiceFacade;
    @MockBean
    JwtService jwtService;

    @Test
    void deleteTaskNoParamIdCheck_success() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/taskApp/delete/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    void deleteTaskNoParamCheck_fail() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/taskApp/delete/sdxs")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isInternalServerError())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof UnknownException));
    }
}
