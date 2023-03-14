/*
package com.example.springboottaskmanager.IntegrationTests;

import com.example.springboottaskmanager.SpringBootTaskManagerApplication;
import com.example.springboottaskmanager.TestData.TestData;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.repository.AuditRepo;
import com.example.springboottaskmanager.repository.TaskRepo;
import com.example.springboottaskmanager.service.TaskServicePost;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
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

//Тесты на обработку POST запросов приложением
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.liquibase.enabled=false")
@ContextConfiguration(classes = {SpringBootTaskManagerApplication.class})
@AutoConfigureMockMvc
@Testcontainers
public class TaskPostIntegrationTest {
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

    //Очистка тестовых данных перед каждым тестом
    @BeforeEach
    void setUp() {
        taskRepo.deleteAll();
        auditRepo.deleteAll();
    }

    //Попытка добавления валидных записей в базу данных
    @Test
    public void PostRequestIntegrationTest_success() throws Exception{
        for(int i = 0; i < TestData.goodTasks.size(); i++) {
            Task curTask = TestData.goodTasks.get(i);

            //Поочередно каждая запись отправляется в теле POST запроса, проходит валидацию и сохраняется
            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/tasks/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(curTask));

            ResultActions response = mockMvc.perform(mockRequest);

            //Каждая сохраненная в базе данных запись сравнивается с теми, которые были предоставлены в тестовом наборе данных
            int finalI = i;
            response.andExpect(status().isOk())
                    .andExpect(result -> Assertions.assertEquals(curTask, taskRepo.findAll().get(finalI)));
        }

        //Убеждаемся, что количество записей в базе данных и в таблице Audit соответствует количеству записей в тестовом наборе данных
        Assertions.assertEquals(TestData.goodTasks.size(), auditRepo.findAll().size());
        Assertions.assertEquals(TestData.goodTasks.size(), taskRepo.findAll().size());
    }

    //Попытка добавления невалидных записей в базу данных
    @Test
    public void PostRequestIntegrationTest_fail() throws Exception{
        for(int i = 0; i < TestData.badTasks.size(); i++) {
            Task curTask = TestData.badTasks.get(i);

            //Поочередно каждая запись отправляется в теле POST запроса, не проходит валидацию и выбрасывает ошибку валидации
            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/tasks/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(curTask));

            Assertions.assertThrows(ServletException.class, () -> mockMvc.perform(mockRequest));
        }
        //Убеждаемся, что количество записей в базе данных равно нулю, а в таблице Audit - соответствует количеству записей в тестовом наборе данных
        Assertions.assertEquals(TestData.badTasks.size(), auditRepo.findAll().size());
        Assertions.assertEquals(0, taskRepo.findAll().size());
    }
}*/
