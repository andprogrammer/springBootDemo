package com.example.springBootDemo.controller;

import com.example.springBootDemo.dto.EmployeeRequest;
import com.example.springBootDemo.dto.EmployeeResponse;
import com.example.springBootDemo.model.Employee;
import com.example.springBootDemo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        employeeService.addEmployee(employeeRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse findEmployeeById(@PathVariable(value = "id") Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse updateEmployee(@PathVariable(value = "id") Long employeeId,
                                           @RequestBody Employee employeeDetails) {
        return employeeService.updateEmployee(employeeId, employeeDetails);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
