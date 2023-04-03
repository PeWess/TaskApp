package com.example.springboottaskmanager.IntegrationTests;

import com.example.springboottaskmanager.TestData.TestTaskData;
import com.example.springboottaskmanager.viewmodel.TaskViewModel;
import com.example.springboottaskmanager.mapper.TaskMapper;
import com.example.springboottaskmanager.repository.AuditRepo;
import com.example.springboottaskmanager.repository.TaskRepo;
import com.example.springboottaskmanager.security.authconfig.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Testcontainers
public class TaskGetIntegrationTest {
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

    @Before
    public void saveTask() {
        taskRepo.save(TestTaskData.fullWrittenTask);
    }

    @BeforeEach
    public void setUp() {
        auditRepo.deleteAll();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void getTaskById_success() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/taskApp/get/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(mockRequest);

        response.andExpect(status().isOk())
                .andExpect(content().json(TaskMapper.INSTANCE.toViewModel(TestTaskData.fullWrittenTask).toString()));
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void getTaskByNoSuchId_fail() throws Exception{
        taskRepo.save(TestTaskData.fullWrittenTask);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/taskApp/get/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(mockRequest);

        response.andExpect(status().isNotFound());
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void getTaskByWrongTypeId_fail() throws Exception{
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/taskApp/get/dfgdsdc")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(mockRequest);

        response.andExpect(status().isInternalServerError());
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    void getAllTasks_success() throws Exception{
        taskRepo.save(TestTaskData.onlyImportantParams);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/taskApp/get/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(mockRequest);

        response.andExpect(status().isOk())
                .andExpect(content().json(new ArrayList<TaskViewModel>(){{
                        add(TaskMapper.INSTANCE.toViewModel(TestTaskData.fullWrittenTask));
                        add(TaskMapper.INSTANCE.toViewModel(TestTaskData.onlyImportantParams));
                }}.toString()));
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }
}
