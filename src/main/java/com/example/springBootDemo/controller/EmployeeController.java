package com.example.springBootDemo.controller;

import com.example.springBootDemo.dto.EmployeeRequest;
import com.example.springBootDemo.dto.EmployeeResponse;
import com.example.springBootDemo.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee Controller")
@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new employee")
    public Long addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.addEmployee(employeeRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all employees")
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find employee by id")
    public EmployeeResponse findEmployeeById(@PathVariable(value = "id") Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update employee")
    public EmployeeResponse updateEmployee(@PathVariable(value = "id") Long employeeId,
                                           @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.updateEmployee(employeeId, employeeRequest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete employee")
    public void deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
