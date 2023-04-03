package com.example.springBootDemo.service;

import com.example.springBootDemo.dto.EmployeeRequest;
import com.example.springBootDemo.dto.EmployeeResponse;
import com.example.springBootDemo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest
class EmployeeServiceTestV2 {
    @Container
    private static final PostgreSQLContainer database = new PostgreSQLContainer<>("postgres:12.9-alpine");

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> database.getJdbcUrl());
        registry.add("spring.datasource.username", () -> database.getUsername());
        registry.add("spring.datasource.password", () -> database.getPassword());
    }

    @Autowired
    private EmployeeService employeeService;

    @Test
    void testAddEmployee() {
        final String empName = "John";
        Long empId = employeeService.addEmployee(EmployeeRequest.builder().name(empName).build());

        EmployeeResponse employeeResponse = employeeService.getEmployee(empId);

        assertNotNull(employeeResponse);
        assertEquals(empId, employeeResponse.getId());
        assertEquals(empName, employeeResponse.getName());
    }

    @Test
    void testGetAllEmployees() {
        employeeService.addEmployee(EmployeeRequest.builder().name("Peter").build());
        employeeService.addEmployee(EmployeeRequest.builder().name("David").build());

        List<EmployeeResponse> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(4, employees.size());
    }

    @Test
    void testUpdateEmployee() {
        final String empName = "Nick";
        Long empId = employeeService.addEmployee(EmployeeRequest.builder().name("Jack").build());

        EmployeeResponse empResponse = employeeService.updateEmployee(empId, new EmployeeRequest(empName));

        assertNotNull(empResponse);
        assertEquals(empId, empResponse.getId());
        assertEquals(empName, empResponse.getName());
    }

    @Test
    void testDeleteEmployee() {
        Long empId = employeeService.addEmployee(EmployeeRequest.builder().name("Jason").build());

        employeeService.deleteEmployee(empId);

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.getEmployee(empId),
                String.format("Not existing employee id {}", empId));
    }
}