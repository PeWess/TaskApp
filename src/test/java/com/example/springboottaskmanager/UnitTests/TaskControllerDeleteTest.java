package com.example.springboottaskmanager.UnitTests;

import com.example.springboottaskmanager.TestConfiguration.TestConfig;
import com.example.springboottaskmanager.controller.TaskController;
import com.example.springboottaskmanager.exception.Body.UnknownException;
import com.example.springboottaskmanager.security.authConfig.JwtService;
import com.example.springboottaskmanager.service.TaskServiceDelete;
import com.example.springboottaskmanager.service.TaskServiceFacade;
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

//Тест на обработку DELETE запросов контроллером
@WebMvcTest(value = TaskController.class, properties = "spring.liquibase.enabled=false")
@AutoConfigureDataJpa
@Import(TestConfig.class)
public class TaskControllerDeleteTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskServiceFacade taskServiceFacade;
    @MockBean
    JwtService jwtService;

    //Попытка отправить запрос с указанием ID в адресе запроса, возвращается HTTP статус 200 (OK)
    @Test
    public void DeleteTaskNoParamIdCheck_success() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/tasks/delete/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    //Попытка отправить запрос с некорректным типом данных в адресе запроса, возвращается UnknownException и HTTP статус 500 (INTERNAL_SERVER_ERROR)
    @Test
    public void DeleteTaskNoParamCheck_fail() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/tasks/delete/sdxs")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isInternalServerError())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof UnknownException));
    }
}