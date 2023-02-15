package com.example.springBootDemo.controller;

import com.example.springBootDemo.model.Employee;
import com.example.springBootDemo.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = EmployeeControllerTest.DataSourceInitializer.class)
class EmployeeControllerTest {
    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:12.9-alpine");
    private static final String API_URL_EMPLOYEE = "/api/employee";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee setUp() throws Exception {
        employeeRepository.deleteAll();
        var employee = new Employee();
        mockMvc.perform(post(API_URL_EMPLOYEE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());
        return employee;
    }

    @Test
    void registerNewEmployee() throws Exception {
        // given
        var employee = setUp();

        // then
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees)
                .usingElementComparatorIgnoringFields("id")
                .contains(employee);
    }

    @Test
    void getAllEmployees() throws Exception {
        // given
        var employee = setUp();

        // when
        ResultActions resultActions = mockMvc
                .perform(get(API_URL_EMPLOYEE)).andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(containsString("")))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(employee.getName())));
    }

    @Test
    void getEmployee() throws Exception {
        // given
        var employee = setUp();

        // when
        ResultActions resultActions = mockMvc
                .perform(get(API_URL_EMPLOYEE + "/1")).andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("name", is(employee.getName())));
    }

    @Test
    void deleteEmployee() throws Exception {
        // given
        var employee = new Employee();
        mockMvc.perform(post(API_URL_EMPLOYEE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());

        // when
        ResultActions resultActions = mockMvc
                .perform(delete(API_URL_EMPLOYEE + "/1")).andDo(print());

        // then
        resultActions.andExpect(status().isOk());
    }

    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + database.getJdbcUrl(),
                    "spring.datasource.username=" + database.getUsername(),
                    "spring.datasource.password=" + database.getPassword()
            );
        }
    }
}