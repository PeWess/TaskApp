package com.example.springboottaskmanager.IntegrationTests;

import com.example.springboottaskmanager.TestData.TestTaskData;
import com.example.springboottaskmanager.mapper.TaskMapper;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.repository.AuditRepo;
import com.example.springboottaskmanager.repository.TaskRepo;
import com.example.springboottaskmanager.security.authConfig.JwtService;
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

//Тесты на обработку POST запросов приложением
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


    //Очистка тестовых данных перед каждым тестом
    @BeforeEach
    void setUp() {
        taskRepo.deleteAll();
        auditRepo.deleteAll();
    }

    //Попытка добавления валидных записей в базу данных
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void PostRequestIntegrationTest_success() throws Exception{
        for(int goodTaskNumber = 0; goodTaskNumber < TestTaskData.goodTasks.size(); goodTaskNumber++) {
            Task curTask = TestTaskData.goodTasks.get(goodTaskNumber);

            //Поочередно каждая запись отправляется в теле POST запроса, проходит валидацию и сохраняется
            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/tasks/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(TaskMapper.INSTANCE.toViewModel(curTask)));

            ResultActions response = mockMvc.perform(mockRequest);

            //Каждая сохраненная в базе данных запись сравнивается с теми, которые были предоставлены в тестовом наборе данных
            int finalGoodTaskNumber = goodTaskNumber;
            response.andExpect(status().isCreated())
                    .andExpect(result -> Assertions.assertEquals(curTask.getId(), taskRepo.findAll().get(finalGoodTaskNumber).getId()));
        }

        //Убеждаемся, что количество записей в базе данных и в таблице Audit соответствует количеству записей в тестовом наборе данных
        Assertions.assertEquals(TestTaskData.goodTasks.size(), auditRepo.findAll().size());
        Assertions.assertEquals(TestTaskData.goodTasks.size(), taskRepo.findAll().size());
    }

    //Попытка добавления невалидных записей в базу данных
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void PostRequestIntegrationTest_fail() throws Exception{
        for(int i = 0; i < TestTaskData.badTasks.size(); i++) {
            Task curTask = TestTaskData.badTasks.get(i);

            //Поочередно каждая запись отправляется в теле POST запроса, не проходит валидацию и выбрасывает ошибку валидации
            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/tasks/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(TaskMapper.INSTANCE.toViewModel(curTask)));

            ResultActions response = mockMvc.perform(mockRequest);

            response.andExpect(status().isBadRequest());
        }
        //Убеждаемся, что количество записей в базе данных равно нулю, а в таблице Audit - соответствует количеству записей в тестовом наборе данных
        Assertions.assertEquals(0, auditRepo.findAll().size());
        Assertions.assertEquals(0, taskRepo.findAll().size());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void PostRequestIntegrationTest_authFailed() throws Exception{
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/tasks/post")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TaskMapper.INSTANCE.toViewModel(TestTaskData.fullWrittenTask)));

        ResultActions response = mockMvc.perform(mockRequest);

        response.andExpect(status().isForbidden());

        Assertions.assertEquals(0, auditRepo.findAll().size());
        Assertions.assertEquals(0, taskRepo.findAll().size());
    }
}
