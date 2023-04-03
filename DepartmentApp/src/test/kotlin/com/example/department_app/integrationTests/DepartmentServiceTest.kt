package com.example.department_app.integrationTests

import com.example.department_app.repo.DepartmentRepo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class DepartmentServiceTest {

    companion object {
        @Container
        var postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:15.2")
            .withDatabaseName("taskIntegrationTest")
            .withUsername("test")
            .withPassword("test")

        @DynamicPropertySource
        fun postgresqlProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { postgreSQLContainer.jdbcUrl }
            registry.add("spring.datasource.password") { postgreSQLContainer.password }
            registry.add("spring.datasource.username") { postgreSQLContainer.username }
        }
    }

    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var departmentRepo: DepartmentRepo

    @Test
    fun postDepartment_success(){
        val mockRequest = MockMvcRequestBuilders.post("/departments/post/1/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        mockMvc.perform(mockRequest)
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("Added user to department"))
    }

    @Test
    fun postDepartmentUser_fail(){
        val mockRequest = MockMvcRequestBuilders.post("/departments/post/fdsfs/sdfsd")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        val response = mockMvc.perform(mockRequest)

        response.andExpect(status().isBadRequest)
    }

    @Test
    fun getDepartment_success(){
        val mockRequest = MockMvcRequestBuilders.get("/departments/get/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        mockMvc.perform(mockRequest)
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("Administration"))
    }

    @Test
    fun getDepartmentUserNotExist_fail(){
        val mockRequest = MockMvcRequestBuilders.get("/departments/get/3")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        val response = mockMvc.perform(mockRequest)

        response.andExpect(status().isNotFound)
    }

    @Test
    fun getDepartmentInvalidId_fail(){
        val mockRequest = MockMvcRequestBuilders.get("/departments/get/fdsfc")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        val response = mockMvc.perform(mockRequest)

        response.andExpect(status().isBadRequest)
    }
}