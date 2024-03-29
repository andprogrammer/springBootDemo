package com.example.springBootDemo.service;

import com.example.springBootDemo.dto.EmployeeRequest;
import com.example.springBootDemo.dto.EmployeeResponse;
import com.example.springBootDemo.exception.ResourceNotFoundException;
import com.example.springBootDemo.model.Employee;
import com.example.springBootDemo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Long addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .name(employeeRequest.getName())
                .build();

        employeeRepository.save(employee);
        log.info("Employee {} is saved", employee.getName());
        return employee.getId();
    }

    public List<EmployeeResponse> getAllEmployees() {
        log.info("List all employees");
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(this::mapToEmployeeResponse).toList();
    }

    public EmployeeResponse getEmployee(Long employeeId) {
        log.info("List single employee with id {}", employeeId);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Employee {} not found", employeeId)));
        return mapToEmployeeResponse(employee);
    }

    public EmployeeResponse updateEmployee(Long employeeId, EmployeeRequest employeeRequest) {
        log.info("Update employee with id {}", employeeId);
        Employee currentEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        currentEmployee.setName(employeeRequest.getName());
        final Employee updatedEmployee = employeeRepository.save(currentEmployee);
        return mapToEmployeeResponse(updatedEmployee);
    }

    public void deleteEmployee(Long employeeId) {
        log.info("Delete employee with id {}", employeeId);
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found" + employeeId));
        employeeRepository.delete(employee);
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .build();
    }
}
