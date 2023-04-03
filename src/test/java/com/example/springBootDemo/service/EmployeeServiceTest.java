package com.example.springBootDemo.service;

import com.example.springBootDemo.dto.EmployeeRequest;
import com.example.springBootDemo.model.Employee;
import com.example.springBootDemo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    //    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    void givenProductToAddShouldReturnAddedProduct() {
        Employee emp = new Employee();
        EmployeeRequest employeeRequest = EmployeeRequest.builder().name("John").build();
        when(employeeRepository.save(any())).thenReturn(emp);
        employeeService.addEmployee(employeeRequest);
        verify(employeeRepository, times(1)).save(any());
    }
}