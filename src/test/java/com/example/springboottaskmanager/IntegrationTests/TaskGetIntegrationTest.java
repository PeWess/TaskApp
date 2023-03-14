/*
package com.example.springboottaskmanager.IntegrationTests;

import com.example.springboottaskmanager.SpringBootTaskManagerApplication;
import com.example.springboottaskmanager.TestData.TestData;
import com.example.springboottaskmanager.repository.AuditRepo;
import com.example.springboottaskmanager.repository.TaskRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

//Тесты на обработку GET запросов приложением
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.liquibase.enabled=false")
@ContextConfiguration(classes = {SpringBootTaskManagerApplication.class})
@AutoConfigureMockMvc
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
    TaskRepo taskRepo;
    @Autowired
    AuditRepo auditRepo;
    @Autowired
    TestRestTemplate testRestTemplate;

    //Очистка тестовых данных перед каждым тестом
    @BeforeEach
    void SetUp() {
        taskRepo.deleteAll();;
        auditRepo.deleteAll();
    }

    //Попытка получения записи из базы данных с указанием корректного значения параметра
    @Test
    public void GetTaskByParamId_success() throws Exception{
        taskRepo.save(TestData.fullWrittenTask);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/tasks/?id=1", String.class);

        //Проверяем возвращение HTTP статуса 200 (OK), соответствие сохраненной и полученной записей, количество записей в Audit, равное количеству вызванных методов
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(TestData.fullWrittenTask.toString(), response.getBody());
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }

    //Попытка получения записи из базы данных с указанием ID несуществующей записи
    @Test
    public void GetTaskByParamNoSuchId_fail() throws Exception{
        taskRepo.save(TestData.fullWrittenTask);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/tasks/?id=2", String.class);

        //Проверка на возвращение HTTP статуса 404 (NOT_FOUND) и количества записей записей в Audit, равное количеству вызванных методов
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }

    //Попытка получения записи из базы данных с указанием некорректного названия параметра
    @Test
    public void GetTaskByWrongParamId_fail() throws Exception{
        taskRepo.save(TestData.fullWrittenTask);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/tasks/?taskId=2", String.class);

        //Проверка на возвращение HTTP статуса 500 (INTERNAL_SERVER_ERROR) и количества записей записей в Audit, равное количеству вызванных методов
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }

    //Попытка получения записи из базы данных с указанием некорректного типа данных в параметре
    @Test
    public void GetTaskByWrongTypeParamId_fail() throws Exception{
        taskRepo.save(TestData.fullWrittenTask);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/tasks/?id=sdfdsc", String.class);

        //Проверка на возвращение HTTP статуса 500 (INTERNAL_SERVER_ERROR) и количества записей записей в Audit, равное количеству вызванных методов
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }

    //Попытка получения записи из базы данных с указанием ID существующей записи в адресе запроса
    @Test
    public void GetTaskById_success() throws Exception{
        taskRepo.save(TestData.fullWrittenTask);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/tasks/1", String.class);

        //Проверяем возвращение HTTP статуса 200 (OK)б соответствие сохраненной и полученной записей, количество записей в Audit, равное количеству вызванных методов
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(TestData.fullWrittenTask.toString(), response.getBody());
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }

    //Попытка получения записи из базы данных с указанием ID несуществующей записи в адресе запроса
    @Test
    public void GetTaskByNoSuchId_fail() throws Exception{
        taskRepo.save(TestData.fullWrittenTask);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/tasks/2", String.class);

        //Проверка на возвращение HTTP статуса 404 (NOT_FOUND) и количества записей записей в Audit, равное количеству вызванных методов
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }

    //Попытка получения записи из базы данных с указанием некорректного типа данных в адресе запроса
    @Test
    public void GetTaskByWrongTypeId_fail() throws Exception{
        taskRepo.save(TestData.fullWrittenTask);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/tasks/sdfdsc", String.class);

        //Проверка на возвращение HTTP статуса 500 (INTERNAL_SERVER_ERROR) и количества записей записей в Audit, равное количеству вызванных методов
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals(1, auditRepo.findAll().size());
    }
}
*/
