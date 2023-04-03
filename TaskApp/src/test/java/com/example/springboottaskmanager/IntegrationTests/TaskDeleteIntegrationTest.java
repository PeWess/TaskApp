package com.example.springboottaskmanager.IntegrationTests;

import com.example.springboottaskmanager.TestData.TestTaskData;
import com.example.springboottaskmanager.repository.AuditRepo;
import com.example.springboottaskmanager.repository.TaskRepo;
import com.example.springboottaskmanager.security.authconfig.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Testcontainers
public class TaskDeleteIntegrationTest {
    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:15.2")
            .withDatabaseName("taskIntegrationTest")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    TaskRepo taskRepo;
    @Autowired
    AuditRepo auditRepo;
    @Autowired
    JwtService jwtService;

    @BeforeEach
    public void SetUp() {
        auditRepo.deleteAll();
        taskRepo.save(TestTaskData.fullWrittenTask);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deleteTask_success() throws Exception{
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/taskApp/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(mockRequest);

        response.andExpect(status().isOk());
        Assertions.assertEquals(0, taskRepo.findAll().size());
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void deleteTask_authFail() throws Exception{
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/taskApp/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(mockRequest);

        response.andExpect(status().isForbidden());

        Assertions.assertEquals(1, taskRepo.findAll().size());
        Assertions.assertEquals(0, auditRepo.findAll().size());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deleteTaskThatNotExist_fail() throws Exception{
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/taskApp/delete/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(mockRequest);

        response.andExpect(status().isNotFound());
        Assertions.assertEquals(1, taskRepo.findAll().size());
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deleteTask_validationFail() throws Exception{
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/taskApp/delete/vyhgyghf")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(mockRequest);

        response.andExpect(status().isInternalServerError());
        Assertions.assertEquals(1, taskRepo.findAll().size());
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }
}
