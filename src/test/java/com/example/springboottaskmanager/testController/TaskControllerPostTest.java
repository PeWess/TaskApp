package com.example.springboottaskmanager.testController;

import com.example.springboottaskmanager.TestData.TestData;
import com.example.springboottaskmanager.controller.TaskControllerPost;
import com.example.springboottaskmanager.service.TaskServicePost;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Тест на обработку POST запросов контроллером
@WebMvcTest(TaskControllerPost.class)
public class TaskControllerPostTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TaskServicePost taskServicePost;

    //Попытка отправить POST запрос с данными в теле, возвращается HTTP статус 200 (OK)
    @Test
    public void PostTaskBodyCheck_success() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TestData.fullWrittenTask));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    //Поытка отправить POST запрос с данными в параметрах, возвращается HTTP статус 400 (BAD_REQUEST)
    @Test
    public void PostTaskParamsBodyCheck_fail() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "001")
                .param("taskName", "Cleaning")
                .param("taskType", "Administration")
                .param("status", "Failed")
                .param("executor", "Anton")
                .param("ownBy", "Anton");

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

    //Попытка отправления пустого POST запроса, возвращается HTTP статус 400(BAD_REQUEST)
    @Test
    public void PostTaskNoParamsBodyCheck_fail() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_CBOR);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }
}