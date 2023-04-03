package com.example.springboottaskmanager.IntegrationTests;

import com.example.springboottaskmanager.TestData.TestTaskData;
import com.example.springboottaskmanager.mapper.TaskMapper;
import com.example.springboottaskmanager.model.Task;
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
public class TaskPostIntegrationTest {
    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:15.2")
            .withDatabaseName("testTaskDatabase")
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
    void setUp() {
        taskRepo.deleteAll();
        auditRepo.deleteAll();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void postRequestIntegrationTest_success() throws Exception{
        for(int goodTaskNumber = 0; goodTaskNumber < TestTaskData.goodTasks.size(); goodTaskNumber++) {
            Task curTask = TestTaskData.goodTasks.get(goodTaskNumber);

            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/taskApp/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(TaskMapper.INSTANCE.toViewModel(curTask)));

            ResultActions response = mockMvc.perform(mockRequest);

            int finalGoodTaskNumber = goodTaskNumber;
            response.andExpect(status().isCreated())
                    .andExpect(result -> Assertions.assertEquals(curTask.getId(), taskRepo.findAll().get(finalGoodTaskNumber).getId()));
        }

        Assertions.assertEquals(TestTaskData.goodTasks.size(), auditRepo.findAll().size());
        Assertions.assertEquals(TestTaskData.goodTasks.size(), taskRepo.findAll().size());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void postRequestIntegrationTest_fail() throws Exception{
        for(int i = 0; i < TestTaskData.badTasks.size(); i++) {
            Task curTask = TestTaskData.badTasks.get(i);

            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/taskApp/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(TaskMapper.INSTANCE.toViewModel(curTask)));

            ResultActions response = mockMvc.perform(mockRequest);

            response.andExpect(status().isBadRequest());
        }
        Assertions.assertEquals(0, auditRepo.findAll().size());
        Assertions.assertEquals(0, taskRepo.findAll().size());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void postRequestIntegrationTest_authFailed() throws Exception{
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/taskApp/post")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TaskMapper.INSTANCE.toViewModel(TestTaskData.fullWrittenTask)));

        ResultActions response = mockMvc.perform(mockRequest);

        response.andExpect(status().isForbidden());

        Assertions.assertEquals(0, auditRepo.findAll().size());
        Assertions.assertEquals(0, taskRepo.findAll().size());
    }
}
