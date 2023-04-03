package com.example.springboottaskmanager.IntegrationTests;

import com.example.springboottaskmanager.exception.body.UserExitsException;
import com.example.springboottaskmanager.model.Role;
import com.example.springboottaskmanager.model.RoleName;
import com.example.springboottaskmanager.repository.AuditRepo;
import com.example.springboottaskmanager.repository.RoleRepo;
import com.example.springboottaskmanager.repository.UserRepo;
import com.example.springboottaskmanager.security.atoken.TokenRepo;
import com.example.springboottaskmanager.security.authconfig.JwtService;
import com.example.springboottaskmanager.security.securitymodels.LoginRequest;
import com.example.springboottaskmanager.security.securitymodels.RegisterRequest;
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
public class UserRegistrationTest {
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
    UserRepo userRepo;
    @Autowired
    AuditRepo auditRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    TokenRepo tokenRepo;
    @Autowired
    JwtService jwtService;

    @Before
    public void saveRoles() {
        roleRepo.save(new Role(1L, RoleName.ADMIN));
        roleRepo.save(new Role(2L, RoleName.USER));
    }
    @BeforeEach
    public void setUp() {
        tokenRepo.deleteAll();
        userRepo.deleteAll();
        auditRepo.deleteAll();
    }

    @Test
    void registrationAndLoginTest_success() throws Exception{
        MockHttpServletRequestBuilder mockRequestRegistration = MockMvcRequestBuilders.post("/taskApp/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RegisterRequest("testUsername", "testPassword", "testName", "testSurname")));

        ResultActions responseRegistration = mockMvc.perform(mockRequestRegistration);

        responseRegistration.andExpect(status().isOk());
        Assertions.assertEquals(1, auditRepo.findAll().size());
        Assertions.assertEquals("testName", userRepo.findByUsername("testUsername").get().getFirstname());

        MockHttpServletRequestBuilder mockRequestLogin = MockMvcRequestBuilders.post("/taskApp/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new LoginRequest("testUsername", "testPassword")));

        ResultActions responseLogin = mockMvc.perform(mockRequestLogin);

        responseLogin.andExpect(status().isOk());
        Assertions.assertEquals(2, auditRepo.findAll().size());
    }

    @Test
    void registrationSameUsername_fail() throws Exception {
        MockHttpServletRequestBuilder mockRequestRegistration = MockMvcRequestBuilders.post("/taskApp/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RegisterRequest("testUsername", "testPassword", "testName", "testSurname")));

        ResultActions responseRegistration = mockMvc.perform(mockRequestRegistration);

        responseRegistration.andExpect(status().isOk());
        Assertions.assertEquals(1, auditRepo.findAll().size());
        Assertions.assertEquals("testName", userRepo.findByUsername("testUsername").get().getFirstname());

        ResultActions responseRegistrationSecondTime = mockMvc.perform(mockRequestRegistration);

        responseRegistrationSecondTime.andExpect(status().isForbidden())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof UserExitsException));
        Assertions.assertEquals(2, auditRepo.findAll().size());
    }
}