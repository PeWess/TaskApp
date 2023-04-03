package com.example.department_app.unitTests

import com.example.department_app.service.DepartmentService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest
@AutoConfigureMockMvc
class DepartmentControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var departmentService: DepartmentService

    @Test
    fun postDepartmentController_success() {
        val mockRequest = MockMvcRequestBuilders.post("/departments/post/1/1")

        mockMvc.perform(mockRequest)
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun postDepartmentController_fail() {
        val mockRequest = MockMvcRequestBuilders.post("/departments/post/dsfds/sdfds")

        mockMvc.perform(mockRequest)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun getDepartmentController_success() {
        val mockRequest = MockMvcRequestBuilders.get("/departments/get/1")

        mockMvc.perform(mockRequest)
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun getDepartmentController_fail() {
        val mockRequest = MockMvcRequestBuilders.get("/departments/get/cdcsx")

        mockMvc.perform(mockRequest)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}